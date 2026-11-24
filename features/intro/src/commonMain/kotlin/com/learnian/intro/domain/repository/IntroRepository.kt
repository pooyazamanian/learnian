package com.learnian.intro.domain.repository

import com.learnian.base.BaseRepository
import com.learnian.intro.domain.model.IntroSlide

/**
 * Repository interface for Intro onboarding slides and completion status.
 */
interface IntroRepository : BaseRepository<List<IntroSlide>> {
    /**
     * Retrieves the intro slides (remote with local mock fallback).
     */
    suspend fun getIntroSlides(): Result<List<IntroSlide>>

    /**
     * Checks whether the user has already seen and completed the intro flow.
     */
    suspend fun isIntroCompleted(): Boolean

    /**
     * Marks the intro flow as completed in persistent storage.
     */
    suspend fun setIntroCompleted(completed: Boolean)
}
