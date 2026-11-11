package com.learnian.splash.presentation.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.progress.ClayProgressBar
import com.learnian.core.ui.component.scaffold.ClayScaffold
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.splash.presentation.mvi.SplashEffect
import com.learnian.splash.presentation.viewmodel.SplashViewModel
import org.koin.compose.viewmodel.koinViewModel

/**
 * Claymorphism Splash Screen connected to MVI Architecture.
 */
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onSplashFinished: (() -> Unit)? = null,
    viewModel: SplashViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SplashEffect.NavigateToNext -> {
                    onSplashFinished?.invoke()
                }
                is SplashEffect.ShowErrorToast -> {
                    // Handle error toast if needed
                }
            }
        }
    }

    val animatedScale by animateFloatAsState(
        targetValue = if (uiState.progress > 0.1f) 1.0f else 0.8f,
        animationSpec = tween(durationMillis = 800)
    )

    ClayScaffold(modifier = modifier) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                // Claymorphism Animated Logo Container
                ClayCard(
                    modifier = Modifier
                        .size(120.dp)
                        .graphicsLayer {
                            scaleX = animatedScale
                            scaleY = animatedScale
                        },
                    cornerRadius = AppCorners.XXL,
                    backgroundColor = ClayTheme.colors.surface
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(ClayTheme.colors.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "L",
                                style = AppTypography.headlineLarge.copy(
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = ClayTheme.colors.highlightTop
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // App Title
                Text(
                    text = "Learnian",
                    style = AppTypography.headlineLarge.copy(
                        color = ClayTheme.colors.primary,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // App Subtitle
                Text(
                    text = "سیستم هوشمند یادگیری زبان با جعبه لایتنر",
                    style = AppTypography.bodyMedium,
                    color = ClayTheme.colors.textSecondary
                )

                Spacer(modifier = Modifier.height(64.dp))

                // Claymorphism Progress Indicator
                ClayProgressBar(
                    progress = uiState.progress,
                    modifier = Modifier.width(200.dp),
                    progressColor = ClayTheme.colors.primary
                )
            }
        }
    }
}
