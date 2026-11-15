# BaseIcon

The unified, foundational icon component for the Learnian Design System.

## Architecture Rule
**All icon displays in the application MUST use `BaseIcon`.**
Never use `androidx.compose.material3.Icon` directly. Centralizing all icons via `BaseIcon` ensures uniform tinting, accessibility support, and allows global modifiers or styling to be applied across the entire app from a single place.

## Overloads Supported
- `Painter`
- `ImageVector`
- `DrawableResource`

## Code Example

```kotlin
// Using Compose Multiplatform DrawableResource
BaseIcon(
    resource = Res.drawable.ic_streak_flame,
    contentDescription = "استریک",
    tint = Color.Unspecified
)

// Using Material ImageVector
BaseIcon(
    imageVector = Icons.Default.ArrowBack,
    contentDescription = "بازگشت"
)
```
