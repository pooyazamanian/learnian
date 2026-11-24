package com.learnian.quiz.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wire model of one quiz round.
 *
 * The backend exposes a single endpoint for every question format: the shape below is
 * shared and each question only carries a `type` discriminator plus the parameters its
 * format consumes. Unknown parameters are simply ignored, so the same payload keeps
 * working when a new format is added.
 */
@Serializable
data class QuizSessionDto(
    @SerialName("session_id") val sessionId: String? = null,
    @SerialName("streak_days") val streakDays: Int? = null,
    @SerialName("accuracy_percent") val accuracyPercent: Int? = null,
    @SerialName("current_index") val currentIndex: Int? = null,
    @SerialName("total_count") val totalCount: Int? = null,
    @SerialName("questions") val questions: List<QuizQuestionDto> = emptyList(),
)

@Serializable
data class QuizQuestionDto(
    @SerialName("id") val id: String? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("prompt") val prompt: String? = null,

    // Word card
    @SerialName("word") val word: String? = null,
    @SerialName("phonetic") val phonetic: String? = null,
    @SerialName("level") val level: String? = null,
    @SerialName("part_of_speech") val partOfSpeech: String? = null,
    @SerialName("meaning") val meaning: String? = null,
    @SerialName("definition") val definition: String? = null,

    // Context and learning support
    @SerialName("context_sentence") val contextSentence: String? = null,
    @SerialName("context_translation") val contextTranslation: String? = null,
    @SerialName("mnemonic") val mnemonic: String? = null,
    @SerialName("etymology") val etymology: String? = null,
    @SerialName("ai_hint") val aiHint: String? = null,

    // Options based formats
    @SerialName("options") val options: List<QuizOptionDto> = emptyList(),
    @SerialName("correct_option_id") val correctOptionId: String? = null,

    // Listening format
    @SerialName("audio_url") val audioUrl: String? = null,
    @SerialName("accent") val accent: String? = null,

    // Sentence blank format
    @SerialName("sentence") val sentence: String? = null,
    @SerialName("sentence_translation") val sentenceTranslation: String? = null,

    // Spelling format
    @SerialName("revealed_indices") val revealedIndices: List<Int> = emptyList(),
    @SerialName("letter_bank") val letterBank: List<String> = emptyList(),
    @SerialName("hint") val hint: String? = null,
)

@Serializable
data class QuizOptionDto(
    @SerialName("id") val id: String? = null,
    @SerialName("text") val text: String? = null,
    @SerialName("phonetic") val phonetic: String? = null,
    @SerialName("meaning") val meaning: String? = null,
    @SerialName("tag") val tag: String? = null,
)

@Serializable
data class QuizAnswerRequest(
    @SerialName("question_id") val questionId: String,
    @SerialName("is_correct") val isCorrect: Boolean,
)

@Serializable
data class QuizAckDto(
    @SerialName("success") val success: Boolean = true,
)
