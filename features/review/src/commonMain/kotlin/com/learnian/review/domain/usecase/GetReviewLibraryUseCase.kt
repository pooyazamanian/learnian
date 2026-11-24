package com.learnian.review.domain.usecase

import com.learnian.base.BaseNoParamsUseCase
import com.learnian.review.domain.model.ReviewLibrary
import com.learnian.review.domain.repository.ReviewRepository

/**
 * Loads the review hub snapshot and guarantees the UI never receives an empty
 * payload (the bundled catalog always backs it).
 */
class GetReviewLibraryUseCase(
    private val repository: ReviewRepository,
) : BaseNoParamsUseCase<Result<ReviewLibrary>> {

    override suspend fun invoke(): Result<ReviewLibrary> =
        repository.getLibrary().mapCatching { library ->
            require(library.words.isNotEmpty()) { "Review library has no words" }
            library
        }
}
