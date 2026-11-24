package com.learnian.stats.data.mapper

import com.learnian.stats.data.dto.AccuracySummaryDto
import com.learnian.stats.data.dto.DailyGoalDto
import com.learnian.stats.data.dto.DayActivityDto
import com.learnian.stats.data.dto.MasterySegmentDto
import com.learnian.stats.data.dto.MasterySummaryDto
import com.learnian.stats.data.dto.MemorySummaryDto
import com.learnian.stats.data.dto.StabilitySummaryDto
import com.learnian.stats.data.dto.StatsDashboardDto
import com.learnian.stats.data.dto.StreakSummaryDto
import com.learnian.stats.data.dto.VocabularySummaryDto
import com.learnian.stats.data.dto.WeeklyActivityDto
import com.learnian.stats.domain.model.AccuracySummary
import com.learnian.stats.domain.model.DailyGoalSummary
import com.learnian.stats.domain.model.DayActivity
import com.learnian.stats.domain.model.LearningMomentum
import com.learnian.stats.domain.model.MasterySegment
import com.learnian.stats.domain.model.MasterySummary
import com.learnian.stats.domain.model.MasteryTier
import com.learnian.stats.domain.model.MemorySecurity
import com.learnian.stats.domain.model.MemorySummary
import com.learnian.stats.domain.model.StabilitySummary
import com.learnian.stats.domain.model.StatsDashboard
import com.learnian.stats.domain.model.StreakSummary
import com.learnian.stats.domain.model.VocabularySummary
import com.learnian.stats.domain.model.Weekday
import com.learnian.stats.domain.model.WeeklyActivitySummary

/**
 * Maps the wire snapshot into the domain model.
 *
 * Returns null when a section the screen cannot render without is missing, which lets
 * the repository fall back to the bundled catalog instead of showing an empty tab.
 */
fun StatsDashboardDto.toDomain(): StatsDashboard? {
    val memoryDto = memory ?: return null
    val vocabularyDto = vocabulary ?: return null
    val accuracyDto = accuracy ?: return null
    val streakDto = streak ?: return null
    val weeklyDto = weeklyActivity ?: return null
    val masteryDto = mastery ?: return null
    val stabilityDto = stability ?: return null
    val goalDto = dailyGoal ?: return null

    val days = weeklyDto.days.mapNotNull { it.toDomain() }
    if (days.isEmpty()) return null

    val segments = masteryDto.segments
        .mapNotNull { it.toDomain() }
        .sortedBy { it.tier.ordinal }
    if (segments.isEmpty()) return null

    return StatsDashboard(
        memory = memoryDto.toDomain(),
        vocabulary = vocabularyDto.toDomain(),
        accuracy = AccuracySummary(
            correctPercent = accuracyDto.correctPercent,
            targetPercent = accuracyDto.targetPercent,
        ),
        streak = streakDto.toDomain(),
        weeklyActivity = weeklyDto.toDomain(days = days),
        mastery = MasterySummary(
            totalWords = masteryDto.totalWords,
            segments = segments,
        ),
        stability = stabilityDto.toDomain(),
        dailyGoal = goalDto.toDomain(),
    )
}

private fun MemorySummaryDto.toDomain() = MemorySummary(
    promotedThisWeek = promotedThisWeek,
    momentum = momentum.toMomentum(),
)

private fun VocabularySummaryDto.toDomain() = VocabularySummary(
    totalWords = totalWords,
    addedThisWeek = addedThisWeek,
    masteredWords = masteredWords,
    masteredPercent = masteredPercent,
)

private fun StreakSummaryDto.toDomain() = StreakSummary(
    currentDays = currentDays,
    recordDays = recordDays,
)

private fun WeeklyActivityDto.toDomain(days: List<DayActivity>) = WeeklyActivitySummary(
    totalMinutes = totalMinutes,
    averageMinutes = averageMinutes,
    days = days,
)

private fun DayActivityDto.toDomain(): DayActivity? {
    val weekday = day.toWeekday() ?: return null
    return DayActivity(
        day = weekday,
        minutes = minutes,
        isToday = isToday,
    )
}

private fun MasterySegmentDto.toDomain(): MasterySegment? {
    val masteryTier = tier.toMasteryTier() ?: return null
    return MasterySegment(
        tier = masteryTier,
        count = count,
        percent = percent,
    )
}

private fun StabilitySummaryDto.toDomain() = StabilitySummary(
    retrievabilityPercent = retrievabilityPercent,
    stabilityDays = stabilityDays,
    stabilityTargetDays = stabilityTargetDays,
    security = security.toSecurity(),
)

private fun DailyGoalDto.toDomain() = DailyGoalSummary(
    isAchieved = isAchieved,
    queuedNewCards = queuedNewCards,
    progressPercent = progressPercent,
)

private fun String?.toMomentum(): LearningMomentum = when (this?.lowercase()) {
    MOMENTUM_EXCELLENT -> LearningMomentum.EXCELLENT
    MOMENTUM_SLOW -> LearningMomentum.SLOW
    else -> LearningMomentum.GOOD
}

private fun String?.toWeekday(): Weekday? = when (this?.lowercase()) {
    DAY_SAT -> Weekday.SAT
    DAY_SUN -> Weekday.SUN
    DAY_MON -> Weekday.MON
    DAY_TUE -> Weekday.TUE
    DAY_WED -> Weekday.WED
    DAY_THU -> Weekday.THU
    DAY_FRI -> Weekday.FRI
    else -> null
}

private fun String?.toMasteryTier(): MasteryTier? = when (this?.lowercase()) {
    TIER_MASTERED -> MasteryTier.MASTERED
    TIER_REVIEWING -> MasteryTier.REVIEWING
    TIER_NEW -> MasteryTier.NEW
    else -> null
}

private fun String?.toSecurity(): MemorySecurity = when (this?.lowercase()) {
    SECURITY_SECURE -> MemorySecurity.SECURE
    SECURITY_AT_RISK -> MemorySecurity.AT_RISK
    else -> MemorySecurity.MODERATE
}

private const val MOMENTUM_EXCELLENT = "excellent"
private const val MOMENTUM_SLOW = "slow"
private const val DAY_SAT = "sat"
private const val DAY_SUN = "sun"
private const val DAY_MON = "mon"
private const val DAY_TUE = "tue"
private const val DAY_WED = "wed"
private const val DAY_THU = "thu"
private const val DAY_FRI = "fri"
private const val TIER_MASTERED = "mastered"
private const val TIER_REVIEWING = "reviewing"
private const val TIER_NEW = "new"
private const val SECURITY_SECURE = "secure"
private const val SECURITY_AT_RISK = "at_risk"
