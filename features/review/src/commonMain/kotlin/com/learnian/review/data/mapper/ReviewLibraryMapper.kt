package com.learnian.review.data.mapper

import com.learnian.review.domain.model.LibraryWord
import com.learnian.review.domain.model.WordReviewData
import com.learnian.review.domain.model.WordReviewProgress

/**
 * Projects the full word payload onto the compact card model of the review hub.
 *
 * Only the fields a list entry shows are copied; the detail screen keeps reading
 * the complete [WordReviewData] through its own navigation id.
 */
fun WordReviewData.toLibraryWord(
    progress: WordReviewProgress,
    isSaved: Boolean,
): LibraryWord = LibraryWord(
    wordId = wordId,
    term = word,
    phonetic = phonetic,
    level = level,
    partOfSpeechFa = primaryPartOfSpeechFa,
    partOfSpeechEn = primaryPartOfSpeechEn,
    meaning = persianMeanings.firstOrNull().orEmpty(),
    explanation = persianExplanation,
    simpleEnglishDefinition = simpleEnglishDefinition,
    example = contextSentence.englishText,
    examplePersian = contextSentence.persianText,
    tags = progress.tags,
    isSaved = isSaved,
    masteryPercent = progress.masteryPercent,
    reviewCount = progress.reviewCount,
    stabilityHours = progress.stabilityHours,
    difficulty = progress.difficulty,
    lastReviewedHoursAgo = progress.lastReviewedHoursAgo,
    nextReviewDays = progress.nextReviewDays,
    status = progress.status,
)
