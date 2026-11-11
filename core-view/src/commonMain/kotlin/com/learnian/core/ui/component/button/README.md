# ClayButton & ClayIconButton

Claymorphism button components implementing **Surface 2 • Action Raised (`clay-btn`)** specs.

## Features
- **Ergonomic Radius**: Defaults to `20.dp` (`AppCorners.Large`).
- **Interactive Active Scaling**: Automatically scales to `0.96` on touch press (`clayPressable`).
- **Dual Surface Depth**: Top-left inset highlight combined with soft spread outer drop shadow.
- **Variants**: `Primary`, `Secondary`, `Tertiary`, `Error`.

## Code Example

```kotlin
ClayButton(
    onClick = { /* handle click */ },
    variant = ClayButtonVariant.Primary
) {
    Text("دکمه قابل کلیک (تست کنید)")
}

ClayIconButton(
    onClick = { /* handle action */ }
) {
    Icon(Icons.Default.Mic, contentDescription = "Voice Action")
}
```
