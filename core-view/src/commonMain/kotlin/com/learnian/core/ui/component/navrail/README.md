# ClayNavRail & ClayNavRailItem

Adaptive vertical navigation rail dock implementing **Surface 1 • Resting Float** specs for larger screens (Tablets, Foldables, Landscape orientation, and Desktop).

## Features
- **Adaptive Screen Orientation**: Replaces `ClayBottomBar` when screen width >= 600.dp or device is in landscape mode.
- **RTL & LTR Automatic Positioning**: Positioned on the Right in RTL (Persian) and Left in LTR (English).
- **Floating Pill Dock**: 3D rounded vertical container (`AppCorners.XXL` 32.dp) floating with soft clay drop shadow.
- **Bouncy Selected Indicator**: Active 3D raised pill (`ClayTheme.colors.primary`) with spring physics animation (`spring(dampingRatio = MediumBouncy)`).
- **Scrollable Safety**: The navigation items column scrolls gracefully on smaller landscape phone displays, preventing vertical clipping.

## Code Example

```kotlin
ClayNavRail(
    header = {
        BaseImage(
            resource = Res.drawable.ic_learnian_eye_logo,
            contentDescription = null,
            modifier = Modifier.size(42.dp)
        )
    }
) {
    tabs.forEachIndexed { index, tab ->
        ClayNavRailItem(
            selected = selectedTab == index,
            onClick = { selectedTab = index },
            icon = tab.icon,
            label = tab.title
        )
    }
}
```
