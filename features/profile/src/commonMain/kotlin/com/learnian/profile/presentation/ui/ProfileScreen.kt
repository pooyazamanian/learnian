package com.learnian.profile.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.learnian.core.ui.component.icon.BaseIcon
import com.learnian.core.ui.theme.AppTypography
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.view.resources.Res
import com.learnian.core.view.resources.ic_palette
import com.learnian.core.view.resources.ic_tune
import com.learnian.core.view.resources.profile_learning_settings_title
import com.learnian.core.view.resources.settings_appearance
import com.learnian.profile.presentation.component.ProfileAppearanceCard
import com.learnian.profile.presentation.component.ProfileFooterSection
import com.learnian.profile.presentation.component.ProfileHeaderCard
import com.learnian.profile.presentation.component.ProfileLearningSettingsCard
import com.learnian.profile.presentation.component.ProfileProBannerCard
import com.learnian.profile.presentation.component.ProfileQuickActionsCard
import com.learnian.profile.presentation.mvi.SettingsIntent
import com.learnian.profile.presentation.viewmodel.SettingsViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

/**
 * Claymorphic Profile & Settings Screen.
 */
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val settings = state.userSettings

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(contentPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // 1. Gamified Profile Overview Header Card
        ProfileHeaderCard(
            modifier = Modifier.fillMaxWidth(),
            onEditAvatarClick = { /* Handle edit avatar / change photo */ },
        )

        // 2. Pro Membership Upgrade Banner Card
        ProfileProBannerCard(
            modifier = Modifier.fillMaxWidth(),
            onUpgradeClick = { /* Handle Pro upgrade / subscription */ },
        )

        // 3. Learning & Spaced Repetition Settings Section Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            BaseIcon(
                resource = Res.drawable.ic_tune,
                contentDescription = null,
                tint = ClayTheme.colors.primary,
                modifier = Modifier.size(20.dp),
            )
            Text(
                text = stringResource(Res.string.profile_learning_settings_title),
                style = AppTypography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                color = ClayTheme.colors.textPrimary,
            )
        }

        // 4. Learning & Repetition Settings Card
        ProfileLearningSettingsCard(
            dailyWordGoal = settings.dailyWordGoal,
            autoFsrsEnabled = settings.autoFsrsEnabled,
            audioAccent = settings.audioAccent,
            dailyReminderTime = settings.dailyReminderTime,
            onDailyWordGoalChange = { viewModel.dispatch(SettingsIntent.SetDailyWordGoal(it)) },
            onAutoFsrsChange = { viewModel.dispatch(SettingsIntent.SetAutoFsrsEnabled(it)) },
            onAudioAccentChange = { viewModel.dispatch(SettingsIntent.SetAudioAccent(it)) },
            onDailyReminderClick = { /* Handle reminder time picker */ },
            modifier = Modifier.fillMaxWidth(),
        )

        // 5. Appearance & System Settings Section Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            BaseIcon(
                resource = Res.drawable.ic_palette,
                contentDescription = null,
                tint = ClayTheme.colors.primary,
                modifier = Modifier.size(20.dp),
            )
            Text(
                text = stringResource(Res.string.settings_appearance),
                style = AppTypography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                color = ClayTheme.colors.textPrimary,
            )
        }

        // 6. Unified Appearance and Customization Card
        ProfileAppearanceCard(
            settings = settings,
            onThemeModeChange = { viewModel.dispatch(SettingsIntent.SetThemeMode(it)) },
            onLanguageChange = { viewModel.dispatch(SettingsIntent.SetLanguage(it)) },
            onFontSizeScaleChange = { viewModel.dispatch(SettingsIntent.SetFontSizeScale(it)) },
            onFontChange = { viewModel.dispatch(SettingsIntent.SetFont(it)) },
            modifier = Modifier.fillMaxWidth(),
        )

        // 7. Cloud Sync, Support & Update Quick Actions Card
        ProfileQuickActionsCard(
            modifier = Modifier.fillMaxWidth(),
            onCloudSyncClick = { /* Handle vocabulary cloud sync */ },
            onSupportClick = { /* Handle voice guide and support */ },
            onCheckUpdateClick = { /* Handle check for app updates */ },
        )

        // 8. Logout Button & Version Footer
        ProfileFooterSection(
            modifier = Modifier.fillMaxWidth(),
            onLogoutClick = { /* Handle user logout */ },
        )
    }
}
