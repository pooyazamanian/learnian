package com.learnian.review.domain.repository

import com.learnian.base.BaseRepository
import com.learnian.review.domain.model.FsrsRating
import com.learnian.review.domain.model.ReviewLibrary
import com.learnian.review.domain.model.WordReviewData

/**
 * Repository interface for vocabulary review, spaced repetition scheduling, and deep word analysis.
 */
interface ReviewRepository : BaseRepository<WordReviewData> {

    /**
     * Retrieves the complete vocabulary and review data for the specified [wordId].
     */
    suspend fun getWordDetail(wordId: String): Result<WordReviewData>

    /**
     * Loads the review hub snapshot: every vocabulary card enriched with its
     * FSRS progress and saved state. All three hub sections read this one list.
     */
    suspend fun getLibrary(): Result<ReviewLibrary>

    /**
     * Submits an FSRS rating for [wordId], updating the spaced repetition schedule.
     */
    suspend fun submitRating(wordId: String, rating: FsrsRating): Result<Unit>

    /**
     * Toggles the bookmark status for [wordId].
     * @return The updated bookmark state (true if bookmarked, false if unbookmarked).
     */
    suspend fun toggleBookmark(wordId: String): Result<Boolean>
}
