package com.learnian.review.presentation.component.hub

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnian.core.ui.component.chip.ClayChipStyle
import com.learnian.core.ui.component.chip.ClayStatusChip
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.component.image.BaseImage
import com.learnian.core.ui.component.textfield.ClayTextField
import com.learnian.core.ui.modifiers.clayInsetGrooved
import com.learnian.core.ui.modifiers.clayRestingFloat
import com.learnian.core.ui.theme.AppCorners
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_search
import com.learnian.core.view.resources.review_filter_count_format
import com.learnian.core.view.resources.review_percent_format
import com.learnian.review.presentation.mvi.ReviewSection
import org.jetbrains.compose.resources.stringResource

/** Header of one hub section: clay illustration tile plus title and subtitle. */
@Composable
fun HubSectionHeader(
    section: ReviewSection,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.Comfortable),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .clayRestingFloat(
                    backgroundColor = section.tileColor(),
                    cornerRadius = AppCorners.Medium,
                    shadowColor = section.accentColor().copy(alpha = 0.22f),
                ),
            contentAlignment = Alignment.Center,
        ) {
            BaseImage(
                resource = section.illustration(),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Spacing.Tiny),
        ) {
            Text(
                text = section.title(),
                style = AppTypography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 17.sp,
                ),
                color = ClayTheme.colors.textPrimary,
            )
            Text(
                text = subtitle,
                style = AppTypography.bodySmall.copy(fontSize = 11.sp, lineHeight = 16.sp),
                color = ClayTheme.colors.textSecondary,
            )
        }
    }
}

/** One value/label pair of a sunken performance strip. */
@Immutable
data class HubStat(
    val value: String,
    val label: String,
    val valueColor: Color? = null,
)

/** Deep sunken strip holding up to four performance numbers. */
@Composable
fun HubStatStrip(
    stats: List<HubStat>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clayInsetGrooved(
                backgroundColor = ClayTheme.colors.surfaceSunken,
                cornerRadius = AppCorners.Large,
            )
            .padding(vertical = Spacing.Comfortable),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        stats.forEachIndexed { index, stat ->
            if (index > 0) {
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(24.dp)
                        .background(ClayTheme.colors.textSecondary.copy(alpha = 0.18f)),
                )
            }

            Column(
                modifier = Modifier.weight(1f).padding(horizontal = Spacing.Tiny),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Spacing.Tiny),
            ) {
                Text(
                    text = stat.value,
                    style = AppTypography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                    ),
                    color = stat.valueColor ?: ClayTheme.colors.textPrimary,
                    maxLines = 1,
                )
                Text(
                    text = stat.label,
                    style = statLabelStyle(),
                    color = ClayTheme.colors.textSecondary,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    lineHeight = 13.sp,
                )
            }
        }
    }
}

/** One selectable quick filter of the hub. */
@Immutable
data class HubChipOption(
    val key: String,
    val label: String,
    val count: Int? = null,
)

/** Scrollable clay chip row used for quick filters and tags. */
@Composable
fun HubFilterRow(
    options: List<HubChipOption>,
    selectedKey: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
    ) {
        options.forEach { option ->
            val isSelected = option.key == selectedKey

            ClayStatusChip(
                text = option.count?.let { count ->
                    stringResource(Res.string.review_filter_count_format, option.label, count)
                } ?: option.label,
                style = if (isSelected) ClayChipStyle.Embossed else ClayChipStyle.Resting,
                backgroundColor = if (isSelected) {
                    ClayTheme.colors.primary
                } else {
                    ClayTheme.colors.surface
                },
                textColor = if (isSelected) {
                    ClayTheme.colors.onPrimary
                } else {
                    ClayTheme.colors.textPrimary
                },
                shadowColor = if (isSelected) {
                    ClayTheme.colors.primary.copy(alpha = 0.35f)
                } else {
                    ClayTheme.colors.shadowOuter
                },
                textStyle = AppTypography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                ),
                contentPadding = PaddingValues(
                    horizontal = Spacing.Comfortable,
                    vertical = Spacing.Compact,
                ),
                onClick = { onSelect(option.key) },
            )
        }
    }
}

/** Pill-shaped sunken search field with a clay magnifier. */
@Composable
fun HubSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    ClayTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = placeholder,
        leadingIcon = {
            BaseIcon(
                resource = Res.drawable.ic_search,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = ClayTheme.colors.textSecondary,
            )
        },
        cornerRadius = AppCorners.Pill,
        textStyle = AppTypography.bodyMedium,
        placeholderStyle = AppTypography.bodyMedium,
        contentPadding = PaddingValues(
            horizontal = Spacing.Medium,
            vertical = Spacing.Comfortable,
        ),
    )
}

/** Embossed percent badge tinted with the mastery state. */
@Composable
fun HubPercentBadge(
    percent: Int,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
) {
    ClayStatusChip(
        text = stringResource(Res.string.review_percent_format, percent.toString()),
        modifier = modifier,
        style = ClayChipStyle.Resting,
        backgroundColor = containerColor,
        textColor = contentColor,
        shadowColor = contentColor.copy(alpha = 0.18f),
        textStyle = AppTypography.labelMedium.copy(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 11.sp,
        ),
        contentPadding = PaddingValues(
            horizontal = Spacing.Small,
            vertical = Spacing.ExtraSmall,
        ),
    )
}

/** Sunken placeholder shown when a filter or a search has no results. */
@Composable
fun HubEmptyState(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clayInsetGrooved(
                backgroundColor = ClayTheme.colors.surfaceSunken,
                cornerRadius = AppCorners.Large,
            )
            .padding(horizontal = Spacing.Medium, vertical = Spacing.Section),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = AppTypography.bodySmall,
            color = ClayTheme.colors.textSecondary,
            textAlign = TextAlign.Center,
        )
    }
}
