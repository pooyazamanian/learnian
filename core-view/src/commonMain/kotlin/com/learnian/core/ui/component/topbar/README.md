# ClayTopBar

Claymorphism floating top bar component implementing **Resting Float Surface** specs.

## Features
- **Floating Top Bar**: Soft elevation with light top highlight.
- **Title & Actions**: Centered vertical layout for navigation icon, title text, and action buttons.

## Code Example

```kotlin
ClayTopBar(
    title = "صفحه اصلی",
    navigationIcon = {
        ClayIconButton(onClick = { /* back */ }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
    }
)
```
