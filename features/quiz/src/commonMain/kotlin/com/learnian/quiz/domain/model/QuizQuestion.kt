package com.learnian.quiz.domain.model

/**
 * The five question formats the quiz engine can render.
 *
 * The backend serves every question from a single endpoint and only distinguishes the
 * format through [wireValue]. The UI switches on this type (and on the typed payload),
 * never on raw server strings, so a new format is added in exactly one place.
 */
enum class QuizQuestionType(val wireValue: String) {
    EN_TO_FA("en_to_fa"),
    FA_TO_EN("fa_to_en"),
    LISTENING("listening"),
    SPELLING("spelling"),
    SENTENCE_BLANK("sentence_blank"),
    ;

    companion object {
        fun fromWire(value: String?): QuizQuestionType? =
            entries.firstOrNull { it.wireValue.equals(value?.trim(), ignoreCase = true) }
    }
}

/**
 * Vocabulary data shared by every question format (the word card, the context sentence
 * and the learning support cards are rendered from it where the format needs them).
 */
data class QuizWord(
    val term: String,
    val phonetic: String = "",
    val level: String = "",
    val partOfSpeech: String = "",
    val meaning: String = "",
    val definition: String = "",
    val contextSentence: String = "",
    val contextTranslation: String = "",
    val mnemonic: String = "",
    val etymology: String = "",
    val aiHint: String = "",
    val audioUrl: String? = null,
)

/**
 * One selectable answer.
 *
 * [phonetic], [meaning] and [tag] are optional hints: each format decides which parts
 * of the option to surface (meaning quiz shows the Persian text, spelling/context quiz
 * shows the word with its part of speech).
 */
data class QuizOption(
    val id: String,
    val text: String,
    val phonetic: String? = null,
    val meaning: String? = null,
    val tag: String? = null,
)

/**
 * Format specific question body. Exactly one shape is attached to a [QuizQuestion];
 * the screen renders the matching body and nothing else.
 */
sealed interface QuizPayload {

    /** Formats where the learner picks one of [options]. */
    sealed interface Choice : QuizPayload {
        val options: List<QuizOption>
        val correctOptionId: String
    }

    /** English word -> Persian meaning, and Persian meaning -> English word. */
    data class MeaningChoice(
        override val options: List<QuizOption>,
        override val correctOptionId: String,
    ) : Choice

    /** Audio prompt -> pick the word the learner just heard. */
    data class Listening(
        override val options: List<QuizOption>,
        override val correctOptionId: String,
        val accent: String = "US",
        val audioUrl: String? = null,
    ) : Choice

    /** Fill the blank inside a real sentence. */
    data class SentenceBlank(
        override val options: List<QuizOption>,
        override val correctOptionId: String,
        val sentence: String,
        val translation: String,
    ) : Choice

    /**
     * Type the missing letters of [answer].
     *
     * [revealedIndices] are the letter positions the server already shows, [letterBank]
     * is the keypad and [hint] is the smart hint card content.
     */
    data class Spelling(
        val answer: String,
        val revealedIndices: Set<Int>,
        val letterBank: List<String>,
        val hint: String = "",
    ) : QuizPayload {
        /** Positions the learner still has to type, in reading order. */
        val missingIndices: List<Int>
            get() = answer.indices.filter { it !in revealedIndices }

        /**
         * Rebuilds the full word from the letters already revealed and the letters
         * the learner typed, so grading always compares complete words.
         */
        fun assemble(typedLetters: List<String>): String {
            val assembled = StringBuilder(answer.length)
            var cursor = 0
            answer.forEachIndexed { index, letter ->
                if (index in revealedIndices) {
                    assembled.append(letter)
                } else {
                    assembled.append(typedLetters.getOrNull(cursor) ?: " ")
                    cursor++
                }
            }
            return assembled.toString().trim()
        }
    }
}

/** A single quiz question, already mapped into its typed [payload]. */
data class QuizQuestion(
    val id: String,
    val type: QuizQuestionType,
    val word: QuizWord,
    val payload: QuizPayload,
    /** Optional server override for the instruction line (falls back to a localized string). */
    val prompt: String? = null,
) {
    /** Human readable correct answer, used by the inline result banner. */
    val correctAnswerText: String
        get() = when (val body = payload) {
            is QuizPayload.Choice -> body.options.firstOrNull { it.id == body.correctOptionId }?.text.orEmpty()
            is QuizPayload.Spelling -> body.answer
        }
}

/** What the learner submitted for a question. */
sealed interface QuizAnswer {
    data class Option(val optionId: String) : QuizAnswer
    data class Spelling(val text: String) : QuizAnswer
}

/** Result of checking one answer client-side, before the server becomes available. */
data class QuizAnswerResult(
    val isCorrect: Boolean,
    val correctAnswer: String,
)
