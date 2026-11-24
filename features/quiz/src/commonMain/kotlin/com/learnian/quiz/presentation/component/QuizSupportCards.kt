package com.learnian.quiz.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.theme.Spacing
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_brain_ai
import com.learnian.core.view.resources.ic_lightbulb
import com.learnian.core.view.resources.ic_link
import com.learnian.core.view.resources.quiz_ai_hint_title
import com.learnian.core.view.resources.quiz_etymology_title
import com.learnian.core.view.resources.quiz_mnemonic_title
import com.learnian.quiz.domain.model.QuizWord
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource

/**
 * Learning support cards (etymology, mnemonic, AI hint) shared by every format that
 * ships this metadata. Empty sections are skipped instead of rendering blank cards.
 */
@Composable
fun QuizSupportCards(
    word: QuizWord,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Spacing.Small),
    ) {
        if (word.etymology.isNotBlank()) {
            QuizInfoCard(
                icon = Res.drawable.ic_link,
                title = stringResource(Res.string.quiz_etymology_title),
                body = word.etymology,
                accentColor = ClayTheme.colors.tertiary,
            )
        }

        if (word.mnemonic.isNotBlank()) {
            QuizInfoCard(
                icon = Res.drawable.ic_lightbulb,
                title = stringResource(Res.string.quiz_mnemonic_title),
                body = word.mnemonic,
                accentColor = ClayTheme.colors.primary,
            )
        }

        if (word.aiHint.isNotBlank()) {
            QuizInfoCard(
                icon = Res.drawable.ic_brain_ai,
                title = stringResource(Res.string.quiz_ai_hint_title),
                body = word.aiHint,
                accentColor = ClayTheme.colors.secondary,
            )
        }
    }
}

/** Small clay info card with an icon badge, a title and an optional body. */
@Composable
fun QuizInfoCard(
    icon: DrawableResource,
    title: String,
    body: String,
    modifier: Modifier = Modifier,
    accentColor: Color = ClayTheme.colors.tertiary,
) {
    ClayCard(
        modifier = modifier.fillMaxWidth(),
        padding = Spacing.Comfortable,
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(accentColor.copy(alpha = 0.14f), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                BaseIcon(
                    resource = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(16.dp),
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spacing.Tiny),
            ) {
                Text(
                    text = title,
                    style = AppTypography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = accentColor,
                )

                if (body.isNotBlank()) {
                    Text(
                        text = body,
                        style = AppTypography.bodySmall,
                        color = ClayTheme.colors.textSecondary,
                    )
                }
            }
        }
    }
}
