package com.learnian.base

import kotlinx.coroutines.flow.Flow

/**
 * Base interface for executable domain UseCases in KMP Clean Architecture.
 *
 * Guidelines:
 * - UseCases encapsulate business domain rules, client-side input validations,
 *   and error generation.
 * - Simple pass-through read fetches without business logic do NOT require an artificial
 *   UseCase and can be queried directly from Repository.
 */
interface BaseUseCase<in Params, out ReturnType> {
    suspend operator fun invoke(params: Params): ReturnType
}

interface BaseNoParamsUseCase<out ReturnType> {
    suspend operator fun invoke(): ReturnType
}

interface BaseFlowUseCase<in Params, out ReturnType> {
    operator fun invoke(params: Params): Flow<ReturnType>
}

interface BaseFlowNoParamsUseCase<out ReturnType> {
    operator fun invoke(): Flow<ReturnType>
}
