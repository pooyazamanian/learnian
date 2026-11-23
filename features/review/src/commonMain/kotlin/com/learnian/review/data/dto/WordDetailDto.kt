package com.learnian.review.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wire representation of a word detail payload (`GET /api/words/{wordId}`).
 *
 * Every field is nullable/defaulted so a changing backend contract never crashes
 * deserialization; [com.learnian.review.data.mapper.toDomain] applies safe defaults.
 */
@Serializable
data class WordDetailDto(
    @SerialName("word_id") val wordId: String? = null,
    val word: String? = null,
    val phonetic: String? = null,
    val level: String? = null,
    val lesson: String? = null,
    @SerialName("parts_of_speech") val partsOfSpeech: List<String>? = null,
    @SerialName("primary_part_of_speech_fa") val primaryPartOfSpeechFa: String? = null,
    @SerialName("primary_part_of_speech_en") val primaryPartOfSpeechEn: String? = null,
    @SerialName("persian_meanings") val persianMeanings: List<String>? = null,
    @SerialName("persian_explanation") val persianExplanation: String? = null,
    @SerialName("simple_english_definition") val simpleEnglishDefinition: String? = null,
    @SerialName("context_sentence") val contextSentence: ContextSentenceDto? = null,
    val synonyms: List<String>? = null,
    val antonyms: List<String>? = null,
    val mnemonic: WordMnemonicDto? = null,
    val etymology: WordEtymologyDto? = null,
    val collocations: List<WordCollocationDto>? = null,
    @SerialName("real_examples") val realExamples: List<WordExampleDto>? = null,
    @SerialName("review_progress") val reviewProgress: ReviewProgressDto? = null,
    @SerialName("fsrs_intervals") val fsrsIntervals: FsrsIntervalsDto? = null,
    @SerialName("is_bookmarked") val isBookmarked: Boolean? = null,
)

@Serializable
data class ContextSentenceDto(
    @SerialName("english_text") val englishText: String? = null,
    @SerialName("highlighted_word") val highlightedWord: String? = null,
    @SerialName("persian_text") val persianText: String? = null,
    @SerialName("audio_url") val audioUrl: String? = null,
)

@Serializable
data class WordMnemonicDto(
    val title: String? = null,
    val story: String? = null,
    @SerialName("hook_fa") val hookFa: String? = null,
    @SerialName("highlighted_hook") val highlightedHook: String? = null,
    @SerialName("coding_tag") val codingTag: String? = null,
    @SerialName("illustration_url") val illustrationUrl: String? = null,
)

@Serializable
data class WordEtymologyDto(
    @SerialName("root_origin") val rootOrigin: String? = null,
    @SerialName("root_term") val rootTerm: String? = null,
    @SerialName("root_meaning") val rootMeaning: String? = null,
    val derivatives: List<WordDerivativeDto>? = null,
)

@Serializable
data class WordDerivativeDto(
    val title: String? = null,
    @SerialName("english_term") val englishTerm: String? = null,
    @SerialName("part_of_speech") val partOfSpeech: String? = null,
    @SerialName("persian_meaning") val persianMeaning: String? = null,
    @SerialName("example_phrase") val examplePhrase: String? = null,
)

@Serializable
data class WordCollocationDto(
    @SerialName("english_phrase") val englishPhrase: String? = null,
    @SerialName("persian_meaning") val persianMeaning: String? = null,
    @SerialName("icon_key") val iconKey: String? = null,
)

@Serializable
data class WordExampleDto(
    @SerialName("english_text") val englishText: String? = null,
    @SerialName("highlighted_word") val highlightedWord: String? = null,
    @SerialName("persian_text") val persianText: String? = null,
)

@Serializable
data class ReviewProgressDto(
    @SerialName("current_index") val currentIndex: Int? = null,
    @SerialName("total_count") val totalCount: Int? = null,
    @SerialName("streak_days") val streakDays: Int? = null,
    @SerialName("coach_tip") val coachTip: String? = null,
)

@Serializable
data class FsrsIntervalsDto(
    @SerialName("again_text") val againText: String? = null,
    @SerialName("hard_text") val hardText: String? = null,
    @SerialName("good_text") val goodText: String? = null,
    @SerialName("easy_text") val easyText: String? = null,
)

@Serializable
internal data class RatingRequest(val rating: Int)

@Serializable
internal data class BookmarkRequest(val bookmarked: Boolean)

@Serializable
internal data class SimpleResponse(val success: Boolean = true, val message: String? = null)
