# ClayCard

Claymorphism floating card component implementing **Surface 1 • Resting Float (`clay-card`)** specs.

## Features
- **Ergonomic Flashcard Radius**: Defaults to `24.dp` (`AppCorners.XL`).
- **Resting Depth**: Features top inset white highlight with soft shadow spread underneath.
- **Interactive Option**: Pass `onClick` callback to enable active spring press scaling (`0.96`).

## Code Example

```kotlin
ClayCard(
    onClick = { /* navigate or flip card */ }
) {
    Text(
        text = "کارت لغت شناور خمیری",
        style = AppTypography.titleLarge
    )
}
```
