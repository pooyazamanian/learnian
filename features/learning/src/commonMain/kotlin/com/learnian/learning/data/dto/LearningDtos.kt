package com.learnian.learning.data.dto

import com.learnian.learning.domain.model.DEFAULT_DAILY_CAPACITY
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Response of the learning catalog endpoint. */
@Serializable
data class LearningCatalogDto(
    val packages: List<PackageDto> = emptyList(),
    val enrollments: List<EnrollmentDto> = emptyList(),
    @SerialName("study_capacity") val studyCapacity: Int = DEFAULT_DAILY_CAPACITY,
)

/** A vocabulary package as delivered by the catalog endpoint. */
@Serializable
data class PackageDto(
    val id: String,
    val title: String = "",
    val description: String = "",
    val category: String = "general",
    @SerialName("cefr_band") val cefrBand: String = "b1",
    @SerialName("level_label") val levelLabel: String = "",
    @SerialName("audience_label") val audienceLabel: String = "",
    @SerialName("updated_label") val updatedLabel: String = "",
    val version: String = "1.0",
    @SerialName("total_words") val totalWords: Int = 0,
    @SerialName("lesson_count") val lessonCount: Int = 0,
    @SerialName("audio_coverage") val audioCoveragePercent: Int = 0,
    @SerialName("fsrs_version") val fsrsVersion: String = "FSRS v5",
    val rating: Float = 0f,
    @SerialName("rating_count") val ratingCount: Int = 0,
    @SerialName("student_count") val studentCount: Int = 0,
    @SerialName("recommended_daily_words") val recommendedDailyWords: Int = 20,
    @SerialName("why_study") val whyStudy: String = "",
    val highlights: List<String> = emptyList(),
    val testimonial: TestimonialDto? = null,
)

/** Social proof attached to a package. */
@Serializable
data class TestimonialDto(
    val quote: String = "",
    val name: String = "",
    val rank: String = "",
    val badge: String = "",
    val initials: String = "",
)

/** Server view of one enrollment (study settings + reading progress). */
@Serializable
data class EnrollmentDto(
    @SerialName("package_id") val packageId: String,
    @SerialName("daily_quota") val dailyQuota: Int = 20,
    @SerialName("practice_mode") val practiceMode: String = "flashcards",
    @SerialName("is_active") val isActive: Boolean = true,
    @SerialName("learned_words") val learnedWords: Int = 0,
    @SerialName("due_today") val dueToday: Int = 0,
    @SerialName("learned_today") val learnedToday: Int = 0,
)

/** Body of the study-plan endpoint (`PUT learning/packages/{id}/plan`). */
@Serializable
data class StudyPlanRequest(
    @SerialName("daily_quota") val dailyQuota: Int,
    @SerialName("practice_mode") val practiceMode: String,
    @SerialName("is_active") val isActive: Boolean,
)

/** Minimal acknowledgement payload of the write endpoints. */
@Serializable
data class SimpleResponse(
    val success: Boolean = true,
    val message: String? = null,
)