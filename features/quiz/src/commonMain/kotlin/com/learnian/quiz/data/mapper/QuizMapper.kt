package com.learnian.quiz.data.mapper

import com.learnian.quiz.data.dto.QuizOptionDto
import com.learnian.quiz.data.dto.QuizQuestionDto
import com.learnian.quiz.data.dto.QuizSessionDto
import com.learnian.quiz.domain.model.QuizOption
import com.learnian.quiz.domain.model.QuizPayload
import com.learnian.quiz.domain.model.QuizQuestion
import com.learnian.quiz.domain.model.QuizQuestionType
import com.learnian.quiz.domain.model.QuizSession
import com.learnian.quiz.domain.model.QuizWord

/**
 * Maps the shared wire payload into the typed domain model.
 *
 * This is the only place that knows about the `type` discriminator: every other layer
 * consumes a [QuizQuestion] whose [QuizQuestion.payload] already has a concrete shape.
 * Questions with an unknown type or missing required parameters are dropped instead of
 * rendering a half-empty screen.
 */
fun QuizSessionDto.toDomain(): QuizSession {
    val mappedQuestions = questions.mapNotNull { it.toDomain() }
    return QuizSession(
        id = sessionId.orEmpty(),
        streakDays = streakDays ?: 0,
        accuracyPercent = accuracyPercent ?: 0,
        currentIndex = currentIndex ?: 0,
        totalCount = totalCount ?: mappedQuestions.size,
        questions = mappedQuestions,
    )
}

fun QuizQuestionDto.toDomain(): QuizQuestion? {
    val questionType = QuizQuestionType.fromWire(type) ?: return null
    val term = word?.takeIf { it.isNotBlank() } ?: return null
    val options = options.mapIndexedNotNull { index, option -> option.toDomain(fallbackId = "option_$index") }
    val correctId = correctOptionId?.takeIf { it.isNotBlank() }

    val payload: QuizPayload = when (questionType) {
        QuizQuestionType.EN_TO_FA,
        QuizQuestionType.FA_TO_EN,
        -> QuizPayload.MeaningChoice(
            options = options,
            correctOptionId = correctId ?: return null,
        )

        QuizQuestionType.LISTENING -> QuizPayload.Listening(
            options = options,
            correctOptionId = correctId ?: return null,
            accent = accent?.takeIf { it.isNotBlank() } ?: "US",
            audioUrl = audioUrl,
        )

        QuizQuestionType.SENTENCE_BLANK -> QuizPayload.SentenceBlank(
            options = options,
            correctOptionId = correctId ?: return null,
            sentence = sentence?.takeIf { it.isNotBlank() } ?: return null,
            translation = sentenceTranslation.orEmpty(),
        )

        QuizQuestionType.SPELLING -> {
            val bank = letterBank.filter { it.isNotBlank() }
            if (bank.isEmpty()) return null

            QuizPayload.Spelling(
                answer = term,
                revealedIndices = revealedIndices.filter { it in term.indices }.toSet(),
                letterBank = bank,
                hint = hint.orEmpty(),
            )
        }
    }

    return QuizQuestion(
        id = id?.takeIf { it.isNotBlank() } ?: "question_${term.lowercase()}",
        type = questionType,
        word = QuizWord(
            term = term,
            phonetic = phonetic.orEmpty(),
            level = level.orEmpty(),
            partOfSpeech = partOfSpeech.orEmpty(),
            meaning = meaning.orEmpty(),
            definition = definition.orEmpty(),
            contextSentence = contextSentence.orEmpty(),
            contextTranslation = contextTranslation.orEmpty(),
            mnemonic = mnemonic.orEmpty(),
            etymology = etymology.orEmpty(),
            aiHint = aiHint.orEmpty(),
            audioUrl = audioUrl,
        ),
        payload = payload,
        prompt = prompt?.takeIf { it.isNotBlank() },
    )
}

fun QuizOptionDto.toDomain(fallbackId: String): QuizOption? {
    val label = text?.takeIf { it.isNotBlank() } ?: return null
    return QuizOption(
        id = id?.takeIf { it.isNotBlank() } ?: fallbackId,
        text = label,
        phonetic = phonetic?.takeIf { it.isNotBlank() },
        meaning = meaning?.takeIf { it.isNotBlank() },
        tag = tag?.takeIf { it.isNotBlank() },
    )
}
