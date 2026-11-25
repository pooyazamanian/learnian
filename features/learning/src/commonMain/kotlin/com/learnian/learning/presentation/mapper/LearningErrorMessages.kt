package com.learnian.learning.presentation.mapper

import com.learnian.base.UiText
import com.learnian.learning.domain.model.LearningValidationException
import org.jetbrains.compose.resources.StringResource

/**
 * Maps a domain failure to the message the learner sees: validation errors keep their
 * own localized text, everything else falls back to the screen's generic message.
 */
internal fun Throwable.toUiMessage(fallback: StringResource): UiText =
    (this as? LearningValidationException)?.uiMessage ?: UiText.Resource(fallback)