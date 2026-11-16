package com.learnian.profile.di

import com.learnian.profile.presentation.viewmodel.SettingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val profileModule = module {
    viewModelOf(::SettingsViewModel)
}
