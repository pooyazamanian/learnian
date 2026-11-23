package com.learnian.review.domain.usecase

import com.learnian.review.domain.model.WordReviewData
import com.learnian.review.domain.repository.ReviewRepository

class GetWordDetailUseCase(
    private val repository: ReviewRepository,
) {
    suspend operator fun invoke(wordId: String): Result<WordReviewData> {
        return repository.getWordDetail(wordId)
    }
}
