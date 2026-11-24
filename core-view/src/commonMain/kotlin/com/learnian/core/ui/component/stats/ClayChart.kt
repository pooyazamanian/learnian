package com.learnian.core.ui.component.stats

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.modifiers.clayInsetGrooved
import com.learnian.core.ui.modifiers.clayPillRaised
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing

/**
 * One colored slice of a [ClayDonutChart].
 *
 * [value] is a raw magnitude (words, minutes, ...): the chart normalizes the slices by
 * their sum, so callers never have to pre-compute angles.
 */
data class ClayDonutSegment(
    val value: Float,
    val color: Color,
)

/**
 * Claymorphic donut chart (clay-donut-chart).
 *
 * Every slice is a plump rounded clay tube: a sunken groove track behind it, a diffused
 * colored shadow underneath, a lit gradient body and a glossy rim on the inner edge.
 * That is what makes the ring read as pressed/raised clay instead of a flat stroke.
 *
 * @param segments colored slices, drawn in order from the top and clockwise.
 * @param trackColor optional sunken track behind the slices; pass null to hide it.
 * @param gapDegrees visual gap between two neighboring slices.
 * @param centerContent content rendered inside the ring (e.g. total value + label).
 */
@Composable
fun ClayDonutChart(
    segments: List<ClayDonutSegment>,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 26.dp,
    gapDegrees: Float = 8f,
    trackColor: Color? = null,
    centerContent: @Composable BoxScope.() -> Unit = {},
) {
    val total = segments.sumOf { it.value.toDouble() }.toFloat()
    val isDark = ClayTheme.colors.isDark

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val stroke = strokeWidth.toPx()
            // Keeps room for the soft shadow that bleeds outside the ring.
            val radius = ((size.minDimension - stroke) / 2f - stroke * 0.55f).coerceAtLeast(0f)
            val arcSize = Size(radius * 2f, radius * 2f)
            val arcTopLeft = Offset(center.x - radius, center.y - radius)

            if (trackColor != null) {
                drawCircle(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            trackColor.darken(0.05f),
                            trackColor.lighten(0.06f),
                        ),
                        startY = center.y - radius,
                        endY = center.y + radius,
                    ),
                    radius = radius,
                    style = Stroke(width = stroke),
                )
            }

            if (total <= 0f) return@Canvas

            // Pass 1: the diffused colored shadow of every slice, under all bodies.
            var startAngle = START_ANGLE
            segments.forEach { segment ->
                val sweep = (segment.value / total) * 360f
                val gap = gapDegrees.coerceAtMost(sweep / 2f)
                if (sweep > gap) {
                    SOFT_SHADOW_LAYERS.forEach { layer ->
                        drawArc(
                            color = segment.color.copy(alpha = layer.alpha),
                            startAngle = startAngle + gap / 2f,
                            sweepAngle = sweep - gap,
                            useCenter = false,
                            topLeft = arcTopLeft + Offset(0f, layer.width.toPx() * 0.6f),
                            size = arcSize,
                            style = Stroke(
                                width = stroke + layer.width.toPx(),
                                cap = StrokeCap.Round,
                            ),
                        )
                    }
                }
                startAngle += sweep
            }

            // Pass 2: the lit clay bodies with their glossy inner rim.
            val highlightRadius = radius - stroke * 0.18f
            startAngle = START_ANGLE
            segments.forEach { segment ->
                val sweep = (segment.value / total) * 360f
                val gap = gapDegrees.coerceAtMost(sweep / 2f)
                val arcStart = startAngle + gap / 2f
                val arcSweep = sweep - gap

                if (sweep > gap) {
                    drawArc(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                segment.color.lighten(0.22f),
                                segment.color,
                                segment.color.darken(0.12f),
                            ),
                            start = Offset(arcTopLeft.x, arcTopLeft.y),
                            end = Offset(
                                arcTopLeft.x + arcSize.width,
                                arcTopLeft.y + arcSize.height,
                            ),
                        ),
                        startAngle = arcStart,
                        sweepAngle = arcSweep,
                        useCenter = false,
                        topLeft = arcTopLeft,
                        size = arcSize,
                        style = Stroke(width = stroke, cap = StrokeCap.Round),
                    )

                    if (arcSweep > RIM_TRIM_DEGREES * 2f) {
                        drawArc(
                            color = Color.White.copy(alpha = if (isDark) 0.22f else 0.35f),
                            startAngle = arcStart + RIM_TRIM_DEGREES,
                            sweepAngle = arcSweep - RIM_TRIM_DEGREES * 2f,
                            useCenter = false,
                            topLeft = Offset(
                                center.x - highlightRadius,
                                center.y - highlightRadius,
                            ),
                            size = Size(highlightRadius * 2f, highlightRadius * 2f),
                            style = Stroke(width = stroke * 0.24f, cap = StrokeCap.Round),
                        )
                    }
                }

                startAngle += sweep
            }

        }

        centerContent()
    }
}

private const val START_ANGLE = -90f
private const val RIM_TRIM_DEGREES = 4f

/** One fake-blur ring of the donut shadow: wider rings carry less alpha. */
private data class SoftShadowLayer(val width: Dp, val alpha: Float)

private val SOFT_SHADOW_LAYERS = listOf(
    SoftShadowLayer(10.dp, 0.055f),
    SoftShadowLayer(6.dp, 0.075f),
    SoftShadowLayer(2.dp, 0.09f),
)

private fun Color.lighten(fraction: Float): Color = lerp(this, Color.White, fraction)

private fun Color.darken(fraction: Float): Color = lerp(this, Color.Black, fraction)

/**
 * One pillar of a [ClayBarChart].
 *
 * [isHighlighted] marks the active pillar (usually "today") with the accent color.
 */
data class ClayBarChartBar(
    val label: String,
    val value: Float,
    val isHighlighted: Boolean = false,
)

/**
 * Claymorphic vertical bar chart (clay-bar-chart).
 *
 * Each pillar is a sunken clay groove with a floating rounded fill inside it: the fill is
 * an embossed pill, so it casts a shadow on the groove walls and catches light on top.
 * Labels stay below the track so RTL/LTR layouts read the same.
 */
@Composable
fun ClayBarChart(
    bars: List<ClayBarChartBar>,
    modifier: Modifier = Modifier,
    trackHeight: Dp = 124.dp,
    barWidth: Dp = 24.dp,
    trackColor: Color = ClayTheme.colors.surfaceSunken,
    barColor: Color = ClayTheme.colors.primary,
    highlightColor: Color = ClayTheme.colors.tertiary,
    labelStyle: TextStyle = AppTypography.labelSmall,
    labelColor: Color = ClayTheme.colors.textSecondary,
    highlightLabelColor: Color = ClayTheme.colors.tertiary,
) {
    val peak = bars.maxOfOrNull { it.value }?.coerceAtLeast(1f) ?: 1f

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    ) {
        bars.forEach { bar ->
            val fillColor = if (bar.isHighlighted) highlightColor else barColor

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .width(barWidth)
                        .height(trackHeight)
                        .clayInsetGrooved(
                            backgroundColor = trackColor,
                            cornerRadius = AppCorners.Pill,
                        ),
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .fillMaxHeight((bar.value / peak).coerceIn(MIN_FILL_RATIO, 1f))
                            .padding(horizontal = GROOVE_INSET, vertical = GROOVE_INSET)
                            .clayPillRaised(
                                backgroundColor = fillColor,
                                cornerRadius = AppCorners.Pill,
                                shadowColor = fillColor.copy(alpha = 0.45f),
                                highlightColor = Color.White.copy(alpha = 0.55f),
                            ),
                    )
                }

                Spacer(modifier = Modifier.height(Spacing.Small))

                Text(
                    text = bar.label,
                    style = labelStyle.copy(
                        fontWeight = if (bar.isHighlighted) FontWeight.ExtraBold else FontWeight.Medium,
                    ),
                    color = if (bar.isHighlighted) highlightLabelColor else labelColor,
                    maxLines = 1,
                )
            }
        }
    }
}

/** Keeps very small values visible instead of rendering an empty track. */
private const val MIN_FILL_RATIO = 0.12f

/** Gap between the floating fill and the groove wall, so its shadow stays visible. */
private val GROOVE_INSET = 3.dp
