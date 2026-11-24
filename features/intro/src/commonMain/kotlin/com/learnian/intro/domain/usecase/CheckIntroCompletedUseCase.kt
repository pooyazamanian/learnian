package com.learnian.intro.domain.usecase

import com.learnian.base.BaseNoParamsUseCase
import com.learnian.intro.domain.repository.IntroRepository

class CheckIntroCompletedUseCase(
    private val introRepository: IntroRepository,
) : BaseNoParamsUseCase<Boolean> {
    override suspend fun invoke(): Boolean {
        return introRepository.isIntroCompleted()
    }
}
