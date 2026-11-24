package com.learnian.intro.domain.usecase

import com.learnian.base.BaseNoParamsUseCase
import com.learnian.intro.domain.repository.IntroRepository

class CompleteIntroUseCase(
    private val introRepository: IntroRepository,
) : BaseNoParamsUseCase<Unit> {
    override suspend fun invoke() {
        introRepository.setIntroCompleted(true)
    }
}
