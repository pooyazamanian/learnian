package com.learnian.intro.data.repository

import com.learnian.base.ResourceKey
import com.learnian.base.UiText
import com.learnian.core.datastore.SettingsWrapper
import com.learnian.core.preferences.AppPreferenceKeys
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.cards
import com.learnian.core.view.resources.ic_brain_ai
import com.learnian.core.view.resources.ic_clock
import com.learnian.core.view.resources.ic_cloud_sync
import com.learnian.core.view.resources.ic_lightning
import com.learnian.core.view.resources.ic_ribbon_medal
import com.learnian.core.view.resources.ic_target
import com.learnian.core.view.resources.ic_tune
import com.learnian.core.view.resources.ic_volume_up
import com.learnian.core.view.resources.intro_slide1_feat1_desc
import com.learnian.core.view.resources.intro_slide1_feat1_title
import com.learnian.core.view.resources.intro_slide1_feat2_desc
import com.learnian.core.view.resources.intro_slide1_feat2_title
import com.learnian.core.view.resources.intro_slide1_feat3_desc
import com.learnian.core.view.resources.intro_slide1_feat3_title
import com.learnian.core.view.resources.intro_slide1_subtitle
import com.learnian.core.view.resources.intro_slide1_tag
import com.learnian.core.view.resources.intro_slide1_title
import com.learnian.core.view.resources.intro_slide2_feat1_desc
import com.learnian.core.view.resources.intro_slide2_feat1_title
import com.learnian.core.view.resources.intro_slide2_feat2_desc
import com.learnian.core.view.resources.intro_slide2_feat2_title
import com.learnian.core.view.resources.intro_slide2_feat3_desc
import com.learnian.core.view.resources.intro_slide2_feat3_title
import com.learnian.core.view.resources.intro_slide2_subtitle
import com.learnian.core.view.resources.intro_slide2_tag
import com.learnian.core.view.resources.intro_slide2_title
import com.learnian.core.view.resources.intro_slide3_feat1_desc
import com.learnian.core.view.resources.intro_slide3_feat1_title
import com.learnian.core.view.resources.intro_slide3_feat2_desc
import com.learnian.core.view.resources.intro_slide3_feat2_title
import com.learnian.core.view.resources.intro_slide3_feat3_desc
import com.learnian.core.view.resources.intro_slide3_feat3_title
import com.learnian.core.view.resources.intro_slide3_subtitle
import com.learnian.core.view.resources.intro_slide3_tag
import com.learnian.core.view.resources.intro_slide3_title
import com.learnian.core.view.resources.owl
import com.learnian.intro.domain.model.IntroBadgeVariant
import com.learnian.intro.domain.model.IntroFeatureItem
import com.learnian.intro.domain.model.IntroSlide
import com.learnian.intro.domain.repository.IntroRepository
import kotlinx.coroutines.delay

/**
 * Implementation of [IntroRepository] managing intro slides and completion flags.
 *
 * Prepared for remote server fetching while providing instantaneous local clay data.
 */
class IntroRepositoryImpl(
    private val settingsWrapper: SettingsWrapper,
) : IntroRepository {

    override val resourceKey: ResourceKey = ResourceKey("intro_slides")

    override suspend fun getIntroSlides(): Result<List<IntroSlide>> {
        return runCatching {
            // Simulated network latency hook for future backend endpoint
            delay(150)
            createDefaultSlides()
        }
    }

    override suspend fun isIntroCompleted(): Boolean {
        return settingsWrapper.getBoolean(AppPreferenceKeys.KEY_INTRO_COMPLETED) ?: false
    }

    override suspend fun setIntroCompleted(completed: Boolean) {
        settingsWrapper.saveBoolean(AppPreferenceKeys.KEY_INTRO_COMPLETED, completed)
    }

    private fun createDefaultSlides(): List<IntroSlide> = listOf(
        // Slide 1: FSRS Scientific Repetition (matching reference design)
        IntroSlide(
            id = 1,
            tag = UiText.Resource(Res.string.intro_slide1_tag),
            title = UiText.Resource(Res.string.intro_slide1_title),
            subtitle = UiText.Resource(Res.string.intro_slide1_subtitle),
            heroImageRes = Res.drawable.cards,
            features = listOf(
                IntroFeatureItem(
                    id = "feat_fsrs",
                    iconRes = Res.drawable.ic_lightning,
                    badgeVariant = IntroBadgeVariant.Mint,
                    title = UiText.Resource(Res.string.intro_slide1_feat1_title),
                    description = UiText.Resource(Res.string.intro_slide1_feat1_desc),
                ),
                IntroFeatureItem(
                    id = "feat_forgetting",
                    iconRes = Res.drawable.ic_clock,
                    badgeVariant = IntroBadgeVariant.Amber,
                    title = UiText.Resource(Res.string.intro_slide1_feat2_title),
                    description = UiText.Resource(Res.string.intro_slide1_feat2_desc),
                ),
                IntroFeatureItem(
                    id = "feat_gamification",
                    iconRes = Res.drawable.ic_ribbon_medal,
                    badgeVariant = IntroBadgeVariant.Lavender,
                    title = UiText.Resource(Res.string.intro_slide1_feat3_title),
                    description = UiText.Resource(Res.string.intro_slide1_feat3_desc),
                ),
            ),
        ),

        // Slide 2: Smart AI Coach & Deep Solidification
        IntroSlide(
            id = 2,
            tag = UiText.Resource(Res.string.intro_slide2_tag),
            title = UiText.Resource(Res.string.intro_slide2_title),
            subtitle = UiText.Resource(Res.string.intro_slide2_subtitle),
            heroImageRes = Res.drawable.owl,
            features = listOf(
                IntroFeatureItem(
                    id = "feat_roots",
                    iconRes = Res.drawable.ic_brain_ai,
                    badgeVariant = IntroBadgeVariant.Mint,
                    title = UiText.Resource(Res.string.intro_slide2_feat1_title),
                    description = UiText.Resource(Res.string.intro_slide2_feat1_desc),
                ),
                IntroFeatureItem(
                    id = "feat_audio",
                    iconRes = Res.drawable.ic_volume_up,
                    badgeVariant = IntroBadgeVariant.Amber,
                    title = UiText.Resource(Res.string.intro_slide2_feat2_title),
                    description = UiText.Resource(Res.string.intro_slide2_feat2_desc),
                ),
                IntroFeatureItem(
                    id = "feat_quiz",
                    iconRes = Res.drawable.ic_target,
                    badgeVariant = IntroBadgeVariant.Lavender,
                    title = UiText.Resource(Res.string.intro_slide2_feat3_title),
                    description = UiText.Resource(Res.string.intro_slide2_feat3_desc),
                ),
            ),
        ),

        // Slide 3: Tailored & Adaptive Journey
        IntroSlide(
            id = 3,
            tag = UiText.Resource(Res.string.intro_slide3_tag),
            title = UiText.Resource(Res.string.intro_slide3_title),
            subtitle = UiText.Resource(Res.string.intro_slide3_subtitle),
            heroImageRes = Res.drawable.cards,
            features = listOf(
                IntroFeatureItem(
                    id = "feat_placement",
                    iconRes = Res.drawable.ic_target,
                    badgeVariant = IntroBadgeVariant.Mint,
                    title = UiText.Resource(Res.string.intro_slide3_feat1_title),
                    description = UiText.Resource(Res.string.intro_slide3_feat1_desc),
                ),
                IntroFeatureItem(
                    id = "feat_goal",
                    iconRes = Res.drawable.ic_tune,
                    badgeVariant = IntroBadgeVariant.Amber,
                    title = UiText.Resource(Res.string.intro_slide3_feat2_title),
                    description = UiText.Resource(Res.string.intro_slide3_feat2_desc),
                ),
                IntroFeatureItem(
                    id = "feat_sync",
                    iconRes = Res.drawable.ic_cloud_sync,
                    badgeVariant = IntroBadgeVariant.Lavender,
                    title = UiText.Resource(Res.string.intro_slide3_feat3_title),
                    description = UiText.Resource(Res.string.intro_slide3_feat3_desc),
                ),
            ),
        ),
    )
}
