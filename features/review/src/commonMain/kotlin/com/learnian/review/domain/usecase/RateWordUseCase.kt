package com.learnian.review.domain.usecase

import com.learnian.review.domain.model.FsrsRating
import com.learnian.review.domain.repository.ReviewRepository

class RateWordUseCase(
    private val repository: ReviewRepository,
) {
    suspend operator fun invoke(wordId: String, rating: FsrsRating): Result<Unit> {
        return repository.submitRating(wordId, rating)
    }
}
