package com.learnian.home.domain.repository

import com.learnian.base.BaseRepository
import com.learnian.home.domain.model.HomeDashboard

interface HomeRepository : BaseRepository<HomeDashboard> {
    suspend fun getHomeDashboard(): HomeDashboard
}
