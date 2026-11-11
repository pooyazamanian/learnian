package com.learnian.core.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.learnian.core.ui.component.scaffold.ClayScaffold

@Deprecated(
    message = "Use ClayScaffold instead for standard Claymorphism layout.",
    replaceWith = ReplaceWith("ClayScaffold(modifier, topBar, bottomBar, content = content)", "com.learnian.core.ui.component.scaffold.ClayScaffold")
)
@Composable
fun VoiceScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    ClayScaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        content = content
    )
}
