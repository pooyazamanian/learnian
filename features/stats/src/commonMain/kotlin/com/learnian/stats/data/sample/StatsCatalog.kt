package com.learnian.stats.data.sample

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
 * Bundled dashboard used while the stats endpoint is not deployed yet.
 *
 * Mirrors the shape a real FSRS backend returns, so swapping in the server only means
 * dropping this fallback — no UI or domain change.
 */
class StatsCatalog {

    fun dashboard(): StatsDashboard = StatsDashboard(
        memory = MemorySummary(
            promotedThisWeek = 184,
            momentum = LearningMomentum.EXCELLENT,
        ),
        vocabulary = VocabularySummary(
            totalWords = 1_240,
            addedThisWeek = 420,
            masteredWords = 840,
            masteredPercent = 67,
        ),
        accuracy = AccuracySummary(
            correctPercent = 91,
            targetPercent = 90,
        ),
        streak = StreakSummary(
            currentDays = 12,
            recordDays = 18,
        ),
        weeklyActivity = WeeklyActivitySummary(
            totalMinutes = 1_684,
            averageMinutes = 24,
            days = listOf(
                DayActivity(day = Weekday.SAT, minutes = 26),
                DayActivity(day = Weekday.SUN, minutes = 42),
                DayActivity(day = Weekday.MON, minutes = 34),
                DayActivity(day = Weekday.TUE, minutes = 51),
                DayActivity(day = Weekday.WED, minutes = 30),
                DayActivity(day = Weekday.THU, minutes = 45),
                DayActivity(day = Weekday.FRI, minutes = 12, isToday = true),
            ),
        ),
        mastery = MasterySummary(
            totalWords = 1_240,
            segments = listOf(
                MasterySegment(tier = MasteryTier.MASTERED, count = 840, percent = 68),
                MasterySegment(tier = MasteryTier.REVIEWING, count = 276, percent = 22),
                MasterySegment(tier = MasteryTier.NEW, count = 124, percent = 10),
            ),
        ),
        stability = StabilitySummary(
            retrievabilityPercent = 93.4f,
            stabilityDays = 28.6f,
            stabilityTargetDays = 30,
            security = MemorySecurity.SECURE,
        ),
        dailyGoal = DailyGoalSummary(
            isAchieved = true,
            queuedNewCards = 30,
            progressPercent = 100,
        ),
    )
}
