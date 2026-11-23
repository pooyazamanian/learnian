package com.learnian.review.domain.model

import kotlinx.serialization.Serializable

/**
 * Complete domain representation of a vocabulary item for review and deep-dive detail.
 */
@Serializable
data class WordReviewData(
    val wordId: String,
    val word: String,
    val phonetic: String,
    val level: String,
    val lesson: String,
    val partsOfSpeech: List<String>,
    val primaryPartOfSpeechFa: String,
    val primaryPartOfSpeechEn: String,
    val persianMeanings: List<String>,
    val persianExplanation: String,
    val simpleEnglishDefinition: String,
    val contextSentence: ContextSentence,
    val synonyms: List<String>,
    val antonyms: List<String>,
    val mnemonic: WordMnemonic,
    val etymology: WordEtymology,
    val collocations: List<WordCollocation>,
    val realExamples: List<WordExample>,
    val reviewProgress: ReviewProgressInfo,
    val fsrsIntervals: FsrsIntervals,
    val isBookmarked: Boolean = false,
)

@Serializable
data class ContextSentence(
    val englishText: String,
    val highlightedWord: String,
    val persianText: String,
    val audioUrl: String? = null,
)

@Serializable
data class WordMnemonic(
    val title: String,
    val story: String,
    val hookFa: String,
    val highlightedHook: String,
    val codingTag: String,
    val illustrationUrl: String? = null,
)

@Serializable
data class WordEtymology(
    val rootOrigin: String,
    val rootTerm: String,
    val rootMeaning: String,
    val derivatives: List<WordDerivative>,
)

@Serializable
data class WordDerivative(
    val title: String,
    val englishTerm: String,
    val partOfSpeech: String,
    val persianMeaning: String,
    val examplePhrase: String? = null,
)

@Serializable
data class WordCollocation(
    val englishPhrase: String,
    val persianMeaning: String,
    val iconKey: String = "link", // "ship", "sad", "cancel", "link"
)

@Serializable
data class WordExample(
    val englishText: String,
    val highlightedWord: String,
    val persianText: String,
)

@Serializable
data class ReviewProgressInfo(
    val currentIndex: Int,
    val totalCount: Int,
    val streakDays: Int,
    val coachTip: String,
)

@Serializable
data class FsrsIntervals(
    val againText: String,
    val hardText: String,
    val goodText: String,
    val easyText: String,
)
