package com.learnian.learning.domain.model

import com.learnian.base.UiText

/** A domain rule was violated (invalid quota for the learner's capacity, ...). */
class LearningValidationException(
    val uiMessage: UiText,
) : Exception("Learning validation failed")