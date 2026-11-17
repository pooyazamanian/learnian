package com.learnian.core.ui.component.toast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.image.BaseImage
import com.learnian.core.ui.modifiers.clayPillRaised
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppPaddings
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.fire
import com.learnian.core.view.resources.ic_gem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * Toast severity / category.
 */
enum class ClayToastType {
    Success,
    Error,
    Warning,
    Info,
}

/**
 * Toast payload holding message and visual configuration.
 */
data class ClayToastData(
    val id: Long = Random.nextLong(),
    val message: String,
    val type: ClayToastType = ClayToastType.Info,
    val durationMs: Long = 3200L,
)

/**
 * State holder for dispatching and dismissing [ClayToast] notifications.
 */
@Stable
class ClayToastHostState {
    var currentToast by mutableStateOf<ClayToastData?>(null)
        private set

    /**
     * Suspends and shows a toast for [durationMs].
     */
    suspend fun showToast(
        message: String,
        type: ClayToastType = ClayToastType.Info,
        durationMs: Long = 3200L,
    ) {
        val toast = ClayToastData(message = message, type = type, durationMs = durationMs)
        currentToast = toast
        delay(durationMs)
        if (currentToast?.id == toast.id) {
            currentToast = null
        }
    }

    /**
     * Non-suspending helper that launches [showToast] within [scope].
     */
    fun show(
        scope: CoroutineScope,
        message: String,
        type: ClayToastType = ClayToastType.Info,
        durationMs: Long = 3200L,
    ) {
        scope.launch {
            showToast(message = message, type = type, durationMs = durationMs)
        }
    }

    suspend fun showSuccess(message: String) = showToast(message, ClayToastType.Success)
    suspend fun showError(message: String) = showToast(message, ClayToastType.Error)
    suspend fun showWarning(message: String) = showToast(message, ClayToastType.Warning)
    suspend fun showInfo(message: String) = showToast(message, ClayToastType.Info)

    fun showSuccess(scope: CoroutineScope, message: String) = show(scope, message, ClayToastType.Success)
    fun showError(scope: CoroutineScope, message: String) = show(scope, message, ClayToastType.Error)
    fun showWarning(scope: CoroutineScope, message: String) = show(scope, message, ClayToastType.Warning)
    fun showInfo(scope: CoroutineScope, message: String) = show(scope, message, ClayToastType.Info)

    fun dismiss() {
        currentToast = null
    }
}

@Composable
fun rememberClayToastHostState(): ClayToastHostState {
    return remember { ClayToastHostState() }
}

/**
 * CompositionLocal providing global or screen-level access to the active [ClayToastHostState].
 */
val LocalClayToastHost = staticCompositionLocalOf<ClayToastHostState> {
    ClayToastHostState()
}

/**
 * Floating container presenting active toasts with smooth spring animations and claymorphic styling.
 */
@Composable
fun ClayToastHost(
    hostState: ClayToastHostState,
    modifier: Modifier = Modifier,
) {
    val toast = hostState.currentToast

    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        AnimatedVisibility(
            visible = toast != null,
            enter = slideInVertically(
                initialOffsetY = { -it },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMediumLow,
                ),
            ) + fadeIn() + scaleIn(initialScale = 0.88f),
            exit = slideOutVertically(
                targetOffsetY = { -it },
                animationSpec = spring(stiffness = Spring.StiffnessMedium),
            ) + fadeOut() + scaleOut(targetScale = 0.9f),
        ) {
            if (toast != null) {
                ClayToast(data = toast)
            }
        }
    }
}

/**
 * Individual Claymorphic 3D Toast Pill.
 */
@Composable
fun ClayToast(
    data: ClayToastData,
    modifier: Modifier = Modifier,
) {
    val (primaryColor, containerColor, borderColor) = when (data.type) {
        ClayToastType.Success -> Triple(
            ClayTheme.colors.secondary,
            ClayTheme.colors.secondaryContainer,
            ClayTheme.colors.secondary.copy(alpha = 0.25f),
        )
        ClayToastType.Error -> Triple(
            ClayTheme.colors.error,
            ClayTheme.colors.errorContainer,
            ClayTheme.colors.error.copy(alpha = 0.25f),
        )
        ClayToastType.Warning -> Triple(
            ClayTheme.colors.tertiary,
            ClayTheme.colors.tertiaryContainer,
            ClayTheme.colors.tertiary.copy(alpha = 0.25f),
        )
        ClayToastType.Info -> Triple(
            ClayTheme.colors.primary,
            ClayTheme.colors.primaryContainer,
            ClayTheme.colors.primary.copy(alpha = 0.25f),
        )
    }

    Box(
        modifier = modifier
            .clayPillRaised(
                backgroundColor = containerColor,
                cornerRadius = AppCorners.XL,
                borderColor = borderColor,
                borderWidth = 1.dp,
                shadowColor = ClayTheme.colors.shadowOuter,
            )
            .padding(horizontal = AppPaddings.CardPadding, vertical = Spacing.Standard),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.Standard),
        ) {
            // Indicator Dot or Icon
            when (data.type) {
                ClayToastType.Warning -> {
                    BaseImage(
                        resource = Res.drawable.fire,
                        contentDescription = "هشدار",
                        modifier = Modifier.size(18.dp),
                    )
                }
                ClayToastType.Success -> {
                    BaseImage(
                        resource = Res.drawable.ic_gem,
                        contentDescription = "موفق",
                        modifier = Modifier.size(18.dp),
                    )
                }
                ClayToastType.Info, ClayToastType.Error -> {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(primaryColor, CircleShape)
                            .border(1.5.dp, Color.White.copy(alpha = 0.8f), CircleShape),
                    )
                }
            }

            Text(
                text = data.message,
                style = AppTypography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.5.sp,
                ),
                color = ClayTheme.colors.textPrimary,
            )
        }
    }
}
