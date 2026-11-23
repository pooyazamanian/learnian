package com.learnian.review.data.mapper

import com.learnian.review.data.dto.ContextSentenceDto
import com.learnian.review.data.dto.FsrsIntervalsDto
import com.learnian.review.data.dto.ReviewProgressDto
import com.learnian.review.data.dto.WordCollocationDto
import com.learnian.review.data.dto.WordDerivativeDto
import com.learnian.review.data.dto.WordDetailDto
import com.learnian.review.data.dto.WordEtymologyDto
import com.learnian.review.data.dto.WordExampleDto
import com.learnian.review.data.dto.WordMnemonicDto
import com.learnian.review.domain.model.ContextSentence
import com.learnian.review.domain.model.FsrsIntervals
import com.learnian.review.domain.model.ReviewProgressInfo
import com.learnian.review.domain.model.WordCollocation
import com.learnian.review.domain.model.WordDerivative
import com.learnian.review.domain.model.WordEtymology
import com.learnian.review.domain.model.WordExample
import com.learnian.review.domain.model.WordMnemonic
import com.learnian.review.domain.model.WordReviewData

/**
 * Maps a wire payload to the UI-facing domain model, tolerant of missing fields.
 */
fun WordDetailDto.toDomain(requestedWordId: String): WordReviewData {
    return WordReviewData(
        wordId = wordId?.takeIf { it.isNotBlank() } ?: requestedWordId,
        word = word.orEmpty(),
        phonetic = phonetic.orEmpty(),
        level = level.orEmpty(),
        lesson = lesson.orEmpty(),
        partsOfSpeech = partsOfSpeech.orEmpty(),
        primaryPartOfSpeechFa = primaryPartOfSpeechFa.orEmpty(),
        primaryPartOfSpeechEn = primaryPartOfSpeechEn.orEmpty(),
        persianMeanings = persianMeanings.orEmpty(),
        persianExplanation = persianExplanation.orEmpty(),
        simpleEnglishDefinition = simpleEnglishDefinition.orEmpty(),
        contextSentence = contextSentence?.toDomain() ?: ContextSentence("", "", ""),
        synonyms = synonyms.orEmpty(),
        antonyms = antonyms.orEmpty(),
        mnemonic = mnemonic?.toDomain() ?: WordMnemonic("", "", "", "", ""),
        etymology = etymology?.toDomain() ?: WordEtymology("", "", "", emptyList()),
        collocations = collocations.orEmpty().map { it.toDomain() },
        realExamples = realExamples.orEmpty().map { it.toDomain() },
        reviewProgress = reviewProgress?.toDomain() ?: ReviewProgressInfo(0, 0, 0, ""),
        fsrsIntervals = fsrsIntervals?.toDomain() ?: FsrsIntervals("", "", "", ""),
        isBookmarked = isBookmarked ?: false,
    )
}

private fun ContextSentenceDto.toDomain(): ContextSentence = ContextSentence(
    englishText = englishText.orEmpty(),
    highlightedWord = highlightedWord.orEmpty(),
    persianText = persianText.orEmpty(),
    audioUrl = audioUrl,
)

private fun WordMnemonicDto.toDomain(): WordMnemonic = WordMnemonic(
    title = title.orEmpty(),
    story = story.orEmpty(),
    hookFa = hookFa.orEmpty(),
    highlightedHook = highlightedHook.orEmpty(),
    codingTag = codingTag.orEmpty(),
    illustrationUrl = illustrationUrl,
)

private fun WordEtymologyDto.toDomain(): WordEtymology = WordEtymology(
    rootOrigin = rootOrigin.orEmpty(),
    rootTerm = rootTerm.orEmpty(),
    rootMeaning = rootMeaning.orEmpty(),
    derivatives = derivatives.orEmpty().map { it.toDomain() },
)

private fun WordDerivativeDto.toDomain(): WordDerivative = WordDerivative(
    title = title.orEmpty(),
    englishTerm = englishTerm.orEmpty(),
    partOfSpeech = partOfSpeech.orEmpty(),
    persianMeaning = persianMeaning.orEmpty(),
    examplePhrase = examplePhrase,
)

private fun WordCollocationDto.toDomain(): WordCollocation = WordCollocation(
    englishPhrase = englishPhrase.orEmpty(),
    persianMeaning = persianMeaning.orEmpty(),
    iconKey = iconKey ?: "link",
)

private fun WordExampleDto.toDomain(): WordExample = WordExample(
    englishText = englishText.orEmpty(),
    highlightedWord = highlightedWord.orEmpty(),
    persianText = persianText.orEmpty(),
)

private fun ReviewProgressDto.toDomain(): ReviewProgressInfo = ReviewProgressInfo(
    currentIndex = currentIndex ?: 0,
    totalCount = totalCount ?: 0,
    streakDays = streakDays ?: 0,
    coachTip = coachTip.orEmpty(),
)

private fun FsrsIntervalsDto.toDomain(): FsrsIntervals = FsrsIntervals(
    againText = againText.orEmpty(),
    hardText = hardText.orEmpty(),
    goodText = goodText.orEmpty(),
    easyText = easyText.orEmpty(),
)
