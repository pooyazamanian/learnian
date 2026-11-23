package com.learnian.review.domain.usecase

import com.learnian.review.domain.repository.ReviewRepository

class ToggleBookmarkUseCase(
    private val repository: ReviewRepository,
) {
    suspend operator fun invoke(wordId: String): Result<Boolean> {
        return repository.toggleBookmark(wordId)
    }
}
