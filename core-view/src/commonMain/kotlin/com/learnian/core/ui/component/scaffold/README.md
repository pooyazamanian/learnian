# ClayScaffold

Claymorphism main screen container component providing standardized screen background and padding layout.

## Features
- **Clay Background**: Uses `ClayTheme.colors.background` by default (`#F6F4FA` light / `#1C1A29` dark).
- **Layout Integration**: Integrates `ClayTopBar` and `ClayBottomBar` via Material 3 Scaffold slots.

## Code Example

```kotlin
ClayScaffold(
    topBar = { ClayTopBar(title = "عنوان صفحه") },
    bottomBar = { ClayBottomBar { /* navigation items */ } }
) { paddingValues ->
    Column(modifier = Modifier.padding(paddingValues)) {
        // Screen content
    }
}
```
