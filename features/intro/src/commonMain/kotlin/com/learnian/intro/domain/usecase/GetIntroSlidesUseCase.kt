package com.learnian.intro.domain.usecase

import com.learnian.base.BaseNoParamsUseCase
import com.learnian.intro.domain.model.IntroSlide
import com.learnian.intro.domain.repository.IntroRepository

class GetIntroSlidesUseCase(
    private val introRepository: IntroRepository,
) : BaseNoParamsUseCase<Result<List<IntroSlide>>> {
    override suspend fun invoke(): Result<List<IntroSlide>> {
        return introRepository.getIntroSlides()
    }
}
