package com.learnian.review.presentation.mapper

import com.learnian.review.domain.model.LibraryWord
import com.learnian.review.domain.model.ReviewLibrary
import com.learnian.review.domain.model.WordMasteryStatus
import com.learnian.review.presentation.mvi.NotebookFilter

/** Words reviewed more often than this count as frequently practiced. */
const val FREQUENT_REVIEW_THRESHOLD: Int = 3

/** Notebook list for the active quick filter, narrowed by [query]. */
fun ReviewLibrary.notebookWords(
    filter: NotebookFilter,
    query: String = "",
): List<LibraryWord> = when (filter) {
    NotebookFilter.ALL -> words
    NotebookFilter.FREQUENT -> words.filter { it.reviewCount > FREQUENT_REVIEW_THRESHOLD }
    NotebookFilter.NEEDS_REVIEW -> words.filter { it.status != WordMasteryStatus.STABLE }
}.matching(query)

/** Badge counts of the notebook filters, computed once from the full library. */
fun ReviewLibrary.notebookCounts(): Map<NotebookFilter, Int> = mapOf(
    NotebookFilter.ALL to words.size,
    NotebookFilter.FREQUENT to words.count { it.reviewCount > FREQUENT_REVIEW_THRESHOLD },
    NotebookFilter.NEEDS_REVIEW to words.count { it.status != WordMasteryStatus.STABLE },
)

/** Dictionary list for the active part-of-speech filter, narrowed by [query]. */
fun ReviewLibrary.dictionaryWords(
    partOfSpeech: String?,
    query: String = "",
): List<LibraryWord> = words
    .filter { partOfSpeech == null || it.partOfSpeechFa == partOfSpeech }
    .matching(query)

/** Distinct Persian part-of-speech labels available in the dictionary. */
fun ReviewLibrary.partOfSpeechOptions(): List<String> =
    words.map { it.partOfSpeechFa }.distinct()

/** Saved cards for the active tag filter, narrowed by [query]. */
fun ReviewLibrary.savedWordsFor(
    tag: String?,
    query: String = "",
): List<LibraryWord> = savedWords
    .filter { tag == null || tag in it.tags }
    .matching(query)

/** One saved card roughly costs a minute of review. */
fun ReviewLibrary.savedReviewMinutes(): Int = savedWords.size.coerceAtLeast(1)

/** Case-insensitive search over the visible text of a card. */
fun List<LibraryWord>.matching(query: String): List<LibraryWord> {
    val term = query.trim()
    if (term.isEmpty()) return this

    return filter { word ->
        listOf(
            word.term,
            word.phonetic,
            word.meaning,
            word.explanation,
            word.simpleEnglishDefinition,
            word.level,
            word.partOfSpeechFa,
            word.partOfSpeechEn,
        ).any { it.contains(term, ignoreCase = true) } ||
            word.tags.any { it.contains(term, ignoreCase = true) }
    }
}
