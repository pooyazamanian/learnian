package com.learnian.ui.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.button.ClayButton
import com.learnian.core.ui.component.button.ClayButtonVariant
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme

/**
 * Reusable default claymorphic loading indicator for BaseScreen.
 */
@Composable
fun BaseLoadingIndicator(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = ClayTheme.colors.primary,
            strokeWidth = 3.5.dp,
            modifier = Modifier.size(42.dp),
        )
    }
}

/**
 * Reusable default claymorphic error view for BaseScreen.
 */
@Composable
fun BaseErrorView(
    message: String?,
    onRetry: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        ClayCard(
            cornerRadius = AppCorners.XL,
            padding = 20.dp,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = message ?: "خطایی رخ داد. لطفاً مجدداً تلاش کنید.",
                    style = AppTypography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                    ),
                    color = ClayTheme.colors.error,
                    textAlign = TextAlign.Center,
                )
                if (onRetry != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    ClayButton(
                        onClick = onRetry,
                        variant = ClayButtonVariant.Primary,
                    ) {
                        Text("تلاش مجدد")
                    }
                }
            }
        }
    }
}

/**
 * Reusable default empty state view for BaseScreen.
 */
@Composable
fun BaseEmptyView(
    modifier: Modifier = Modifier,
    message: String = "اطلاعاتی یافت نشد.",
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        ClayCard(
            cornerRadius = AppCorners.XL,
            padding = 20.dp,
        ) {
            Text(
                text = message,
                style = AppTypography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                ),
                color = ClayTheme.colors.textSecondary,
                textAlign = TextAlign.Center,
            )
        }
    }
}
