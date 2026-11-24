package com.learnian.review.data.repository

import com.learnian.base.ResourceKey
import com.learnian.review.data.mapper.toDomain
import com.learnian.review.data.mapper.toLibraryWord
import com.learnian.review.data.remote.ReviewApi
import com.learnian.review.data.sample.ReviewLibraryCatalog
import com.learnian.review.data.sample.WordCatalog
import com.learnian.review.domain.model.FsrsRating
import com.learnian.review.domain.model.ReviewLibrary
import com.learnian.review.domain.model.WordReviewData
import com.learnian.review.domain.model.buildReviewLibrary
import com.learnian.review.domain.repository.ReviewRepository

/**
 * Offline-first implementation of [ReviewRepository].
 *
 * Resolution order for [getWordDetail]:
 * 1. Remote API (`GET words/{id}`) — authoritative, variable payload.
 * 2. Last in-memory copy — keeps the screen usable while the process is alive.
 * 3. Bundled [WordCatalog] — keeps the screen usable when the backend is offline.
 *
 * The review index (`review_progress.currentIndex`) is never recomputed here: it
 * is whatever the payload says, so it cannot drift or get stuck.
 * Bookmarks are applied locally first (optimistic) and pushed on a best-effort basis.
 */
class ReviewRepositoryImpl(
    private val api: ReviewApi,
    private val catalog: WordCatalog,
    private val libraryCatalog: ReviewLibraryCatalog,
) : ReviewRepository {

    override val resourceKey: ResourceKey = ResourceKey("word_review")

    private val cache = mutableMapOf<String, WordReviewData>()
    private val bookmarkOverrides = mutableMapOf<String, Boolean>()

    override suspend fun getWordDetail(wordId: String): Result<WordReviewData> {
        val remote = api.fetchWordDetail(wordId)
        val resolved = remote.getOrNull()?.toDomain(wordId)
            ?: cache[wordId]
            ?: catalog.find(wordId)

        if (resolved == null) {
            return Result.failure(
                remote.exceptionOrNull() ?: IllegalStateException("Word not found: $wordId"),
            )
        }

        val merged = resolved.copy(
            isBookmarked = bookmarkOverrides[wordId] ?: resolved.isBookmarked,
        )
        cache[wordId] = merged
        return Result.success(merged)
    }

    /**
     * Builds the hub from the same catalog the detail screen resolves, so every
     * card is guaranteed to open a real word card. Bookmarks are applied from
     * the local overrides, keeping the saved list in sync with the detail screen.
     */
    override suspend fun getLibrary(): Result<ReviewLibrary> {
        val words = catalog.all().map { word ->
            word.toLibraryWord(
                progress = libraryCatalog.progressFor(word.wordId),
                isSaved = bookmarkOverrides[word.wordId] ?: word.isBookmarked,
            )
        }

        return Result.success(buildReviewLibrary(words))
    }

    override suspend fun submitRating(wordId: String, rating: FsrsRating): Result<Unit> {
        // Best effort: the review queue position is owned by the backend, so a
        // network failure must not break the review flow. Nothing is written
        // back into `cache` — the index shown is always the one in the payload.
        api.submitRating(wordId, rating.score)
        return Result.success(Unit)
    }

    override suspend fun toggleBookmark(wordId: String): Result<Boolean> {
        val current = cache[wordId]?.isBookmarked
            ?: catalog.find(wordId)?.isBookmarked
            ?: false
        val isNowBookmarked = !current

        // Local override wins over the server value until the next sync.
        bookmarkOverrides[wordId] = isNowBookmarked

        cache[wordId]?.let { cached ->
            cache[wordId] = cached.copy(isBookmarked = isNowBookmarked)
        }

        api.setBookmark(wordId, isNowBookmarked)
        return Result.success(isNowBookmarked)
    }
}
