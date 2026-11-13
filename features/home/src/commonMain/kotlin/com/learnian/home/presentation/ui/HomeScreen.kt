package com.learnian.home.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.component.button.ClayButton
import com.learnian.core.ui.component.button.ClayButtonVariant
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.progress.ClayProgressBar
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.home.presentation.mvi.HomeIntent
import com.learnian.home.presentation.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

/**
 * Claymorphism Home Screen
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val dashboard = uiState.dashboard

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Level Progress Card
        if (dashboard != null) {
            ClayCard(
                modifier = Modifier.fillMaxWidth(),
                cornerRadius = AppCorners.XL
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "پیشرفت تا لول ${dashboard.currentLevel}",
                            style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = ClayTheme.colors.textPrimary
                        )

                        Text(
                            text = "XP ${dashboard.targetLevelXp} / ${dashboard.xpPoints}",
                            style = AppTypography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = ClayTheme.colors.primary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    val progressRatio = (dashboard.xpPoints.toFloat() / dashboard.targetLevelXp.toFloat()).coerceIn(0f, 1f)
                    ClayProgressBar(
                        progress = progressRatio,
                        height = 22.dp,
                        progressColor = ClayTheme.colors.primary
                    )
                }
            }
        }

        // Daily Streak Card (Tertiary Amber Spec)
        if (dashboard != null) {
            ClayCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = ClayTheme.colors.tertiary.copy(alpha = 0.15f),
                cornerRadius = AppCorners.XL
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "استریک روزانه 🔥",
                            style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = ClayTheme.colors.textPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${dashboard.streakDays} روز متوالی یادگیری منظم",
                            style = AppTypography.bodyMedium,
                            color = ClayTheme.colors.textSecondary
                        )
                    }

                    ClayButton(
                        onClick = { },
                        variant = ClayButtonVariant.Tertiary,
                        cornerRadius = AppCorners.Large
                    ) {
                        Text(
                            text = "${dashboard.streakDays} روز",
                            style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }

        // Flashcard Review Card
        ClayCard(
            modifier = Modifier.fillMaxWidth(),
            cornerRadius = AppCorners.XL
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "مرور کارت‌های امروز (FSRS)",
                    style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = ClayTheme.colors.textPrimary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "${dashboard?.dueReviewsCount ?: 0} کارت آماده مرور در جعبه لایتنر شماست.",
                    style = AppTypography.bodyLarge,
                    color = ClayTheme.colors.textSecondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                ClayButton(
                    onClick = { viewModel.dispatch(HomeIntent.StartReview) },
                    modifier = Modifier.fillMaxWidth(),
                    variant = ClayButtonVariant.Primary
                ) {
                    Text("شروع مرور کارت‌ها")
                }
            }
        }
    }
}
