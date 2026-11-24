package com.learnian.stats.presentation.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.stats_day_fri
import com.learnian.core.view.resources.stats_day_mon
import com.learnian.core.view.resources.stats_day_sat
import com.learnian.core.view.resources.stats_day_sun
import com.learnian.core.view.resources.stats_day_thu
import com.learnian.core.view.resources.stats_day_today_format
import com.learnian.core.view.resources.stats_day_tue
import com.learnian.core.view.resources.stats_day_wed
import com.learnian.core.view.resources.stats_mastery_tier_mastered
import com.learnian.core.view.resources.stats_mastery_tier_new
import com.learnian.core.view.resources.stats_mastery_tier_reviewing
import com.learnian.core.view.resources.stats_momentum_excellent
import com.learnian.core.view.resources.stats_momentum_good
import com.learnian.core.view.resources.stats_momentum_slow
import com.learnian.core.view.resources.stats_security_at_risk
import com.learnian.core.view.resources.stats_security_moderate
import com.learnian.core.view.resources.stats_security_secure
import com.learnian.stats.domain.model.DayActivity
import com.learnian.stats.domain.model.LearningMomentum
import com.learnian.stats.domain.model.MasteryTier
import com.learnian.stats.domain.model.MemorySecurity
import com.learnian.stats.domain.model.Weekday
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/** Localized label of the FSRS momentum badge. */
@Composable
internal fun LearningMomentum.label(): String = stringResource(
    when (this) {
        LearningMomentum.EXCELLENT -> Res.string.stats_momentum_excellent
        LearningMomentum.GOOD -> Res.string.stats_momentum_good
        LearningMomentum.SLOW -> Res.string.stats_momentum_slow
    },
)

/** Localized short weekday used under the weekly bars. */
internal fun Weekday.labelRes(): StringResource = when (this) {
    Weekday.SAT -> Res.string.stats_day_sat
    Weekday.SUN -> Res.string.stats_day_sun
    Weekday.MON -> Res.string.stats_day_mon
    Weekday.TUE -> Res.string.stats_day_tue
    Weekday.WED -> Res.string.stats_day_wed
    Weekday.THU -> Res.string.stats_day_thu
    Weekday.FRI -> Res.string.stats_day_fri
}

/** Localized bar label, marking the active day as today. */
@Composable
internal fun DayActivity.label(): String {
    val dayLabel = stringResource(this.day.labelRes())
    return if (isToday) stringResource(Res.string.stats_day_today_format, dayLabel) else dayLabel
}

/** Localized label of one mastery bucket. */
@Composable
internal fun MasteryTier.label(): String = stringResource(
    when (this) {
        MasteryTier.MASTERED -> Res.string.stats_mastery_tier_mastered
        MasteryTier.REVIEWING -> Res.string.stats_mastery_tier_reviewing
        MasteryTier.NEW -> Res.string.stats_mastery_tier_new
    },
)

/** Localized label of the memory security badge. */
@Composable
internal fun MemorySecurity.label(): String = stringResource(
    when (this) {
        MemorySecurity.SECURE -> Res.string.stats_security_secure
        MemorySecurity.MODERATE -> Res.string.stats_security_moderate
        MemorySecurity.AT_RISK -> Res.string.stats_security_at_risk
    },
)

/** Semantic color of a mastery bucket, shared by the donut and its legend. */
@Composable
internal fun MasteryTier.color(): Color = when (this) {
    MasteryTier.MASTERED -> ClayTheme.colors.secondary
    MasteryTier.REVIEWING -> ClayTheme.colors.primary
    MasteryTier.NEW -> ClayTheme.colors.tertiary
}
