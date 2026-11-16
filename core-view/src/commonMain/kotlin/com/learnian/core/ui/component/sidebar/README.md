# ClaySidebar & ClaySidebarItem

Claymorphic solid dashboard sidebar drawer panel for tablets, landscape phones, and desktop form factors.

## Features
- **Solid Dashboard Panel**: Flush against the screen edges without floating margins, separating from the content with a crisp divider.
- **Automatic Language Positioning**: Positioned on the Right in RTL (Persian) and Left in LTR (English).
- **Horizontal Row Items**: Wide horizontal row with icon and title side-by-side.
- **Active 3D Raised Pill**: Plump claymorphic indicator (`ClayTheme.colors.primary`) with white text and icon on selection.
- **Scrollable Safety**: Internal scroll state ensures the menu never overflows vertically on shorter landscape screens.

## Code Example

```kotlin
ClaySidebar(
    width = 250.dp,
    header = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            BaseImage(Res.drawable.ic_learnian_eye_logo, modifier = Modifier.size(36.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text("لرنیان", style = AppTypography.titleLarge)
        }
    }
) {
    tabs.forEachIndexed { index, tab ->
        ClaySidebarItem(
            selected = selectedTab == index,
            onClick = { selectedTab = index },
            icon = tab.icon,
            label = tab.title
        )
    }
}
```
