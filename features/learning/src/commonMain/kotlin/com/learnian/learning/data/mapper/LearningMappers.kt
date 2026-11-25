package com.learnian.learning.data.mapper

import com.learnian.learning.data.dto.EnrollmentDto
import com.learnian.learning.data.dto.LearningCatalogDto
import com.learnian.learning.data.dto.PackageDto
import com.learnian.learning.data.dto.TestimonialDto
import com.learnian.learning.domain.model.CefrBand
import com.learnian.learning.domain.model.LearningCatalog
import com.learnian.learning.domain.model.LearningPackage
import com.learnian.learning.domain.model.PackageCategory
import com.learnian.learning.domain.model.PackageEnrollment
import com.learnian.learning.domain.model.PackageProgress
import com.learnian.learning.domain.model.PackageTestimonial
import com.learnian.learning.domain.model.PracticeMode
import com.learnian.learning.domain.model.buildLearningCatalog

/** Maps the catalog payload to the domain snapshot the screens consume. */
internal fun LearningCatalogDto.toDomain(): LearningCatalog = buildLearningCatalog(
    packages = packages.map(PackageDto::toDomain),
    enrollments = enrollments.mapNotNull { enrollment ->
        val packageInfo = packages.firstOrNull { it.id == enrollment.packageId }
            ?: return@mapNotNull null
        enrollment.toDomain(totalWords = packageInfo.totalWords)
    },
    studyCapacity = studyCapacity,
)

internal fun EnrollmentDto.toDomain(totalWords: Int): PackageEnrollment = PackageEnrollment(
    packageId = packageId,
    dailyQuota = dailyQuota,
    practiceMode = practiceMode.toPracticeMode(),
    isActive = isActive,
    progress = PackageProgress(
        learnedWords = learnedWords,
        totalWords = totalWords,
        dueToday = dueToday,
        learnedToday = learnedToday,
    ),
)

internal fun PackageDto.toDomain(): LearningPackage = LearningPackage(
    id = id,
    title = title,
    description = description,
    category = category.toCategory(),
    cefrBand = cefrBand.toCefrBand(),
    levelLabel = levelLabel,
    audienceLabel = audienceLabel,
    updatedLabel = updatedLabel,
    version = version,
    totalWords = totalWords,
    lessonCount = lessonCount,
    audioCoveragePercent = audioCoveragePercent,
    fsrsVersion = fsrsVersion,
    rating = rating,
    ratingCount = ratingCount,
    studentCount = studentCount,
    recommendedDailyWords = recommendedDailyWords,
    whyStudy = whyStudy,
    highlights = highlights,
    testimonial = testimonial?.toDomain(),
)

internal fun TestimonialDto.toDomain(): PackageTestimonial = PackageTestimonial(
    quote = quote,
    authorName = name,
    authorRank = rank,
    authorBadge = badge,
    authorInitials = initials,
)

internal fun String.toCategory(): PackageCategory = when (lowercase()) {
    "exams", "exam", "university" -> PackageCategory.EXAMS
    "migration", "ielts" -> PackageCategory.MIGRATION
    else -> PackageCategory.GENERAL
}

internal fun String.toCefrBand(): CefrBand = when (lowercase()) {
    "a1_a2", "a1", "a2" -> CefrBand.A1_A2
    "b1" -> CefrBand.B1
    "b2" -> CefrBand.B2
    "c1_c2", "c1", "c2" -> CefrBand.C1_C2
    else -> CefrBand.B1
}

internal fun String.toPracticeMode(): PracticeMode =
    if (lowercase() == "quiz") PracticeMode.QUIZ else PracticeMode.FLASHCARDS

/** Wire representation of a practice mode. */
internal val PracticeMode.wireValue: String
    get() = when (this) {
        PracticeMode.FLASHCARDS -> "flashcards"
        PracticeMode.QUIZ -> "quiz"
    }