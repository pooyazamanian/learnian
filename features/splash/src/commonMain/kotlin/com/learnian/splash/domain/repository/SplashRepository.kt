package com.learnian.splash.domain.repository

import com.learnian.base.BaseRepository
import com.learnian.splash.domain.model.SplashConfig

interface SplashRepository : BaseRepository<SplashConfig> {
    suspend fun getSplashConfig(): SplashConfig
}
