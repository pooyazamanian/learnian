# ClayStatusChip

Claymorphism pill-shaped status badge and chip implementing **Surface 1 • Resting Float** specs.

## Features
- **Pill Shape**: `AppCorners.Pill` (fully rounded capsule).
- **Gamification Badges**: Displays numeric stats alongside an icon (Streak, Gems, XP).
- **Pressable**: Supports `onClick` with tactile clay press feedback.

## Code Example

```kotlin
ClayStatusChip(
    text = "۱۲ روز",
    textColor = Color(0xFFB5700A),
    icon = {
        BaseIcon(
            resource = Res.drawable.ic_streak_flame,
            contentDescription = "استریک",
            tint = Color.Unspecified
        )
    },
    onClick = { /* show streak details */ }
)
```
