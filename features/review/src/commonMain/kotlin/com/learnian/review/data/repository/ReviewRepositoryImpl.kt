package com.learnian.review.data.repository

import com.learnian.base.ResourceKey
import com.learnian.review.data.mapper.toDomain
import com.learnian.review.data.remote.ReviewApi
import com.learnian.review.data.sample.WordCatalog
import com.learnian.review.domain.model.FsrsRating
import com.learnian.review.domain.model.WordReviewData
import com.learnian.review.domain.repository.ReviewRepository

/**
 * Offline-first implementation of [ReviewRepository].
 *
 * Resolution order for [getWordDetail]:
 * 1. Remote API (`GET words/{id}`) — authoritative, variable payload.
 * 2. Last in-memory copy — preserves local mutations (rating/bookmark) on refresh.
 * 3. Bundled [WordCatalog] — keeps the screen usable when the backend is offline.
 *
 * Ratings and bookmarks are applied locally first (optimistic, never blocks the
 * user) and then pushed to the server on a best-effort basis.
 */
class ReviewRepositoryImpl(
    private val api: ReviewApi,
    private val catalog: WordCatalog,
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

    override suspend fun submitRating(wordId: String, rating: FsrsRating): Result<Unit> {
        val current = cache[wordId] ?: catalog.find(wordId)
        if (current != null) {
            val progress = current.reviewProgress
            cache[wordId] = current.copy(
                reviewProgress = progress.copy(
                    currentIndex = (progress.currentIndex + 1).coerceAtMost(progress.totalCount),
                ),
            )
        }

        // Best effort: the local schedule is already updated, so a network
        // failure must not break the review flow.
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
