package com.learnian.intro.domain.model

import com.learnian.base.UiText
import org.jetbrains.compose.resources.DrawableResource

/**
 * Visual color variant for feature badges in Intro slides.
 */
enum class IntroBadgeVariant {
    Mint,
    Amber,
    Lavender,
}

/**
 * Individual feature card/bullet inside an Intro slide.
 */
data class IntroFeatureItem(
    val id: String,
    val iconRes: DrawableResource,
    val badgeVariant: IntroBadgeVariant,
    val title: UiText,
    val description: UiText,
)

/**
 * Full page/slide data model for the onboarding flow.
 */
data class IntroSlide(
    val id: Int,
    val tag: UiText,
    val title: UiText,
    val subtitle: UiText,
    val heroImageRes: DrawableResource?,
    val features: List<IntroFeatureItem>,
)
