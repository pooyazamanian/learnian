# ClayTopBar

Claymorphism floating top bar component implementing **Resting Float Surface** specs.

## Features
- **Floating Top Bar**: Soft elevation with light top highlight and rounded corners (`AppCorners.XL`).
- **Brand Logo & Title**: Displays the Learnian brand logo with animated vertical slide and fade transitions for title updates.
- **Gamification Status Badges**: Integrated pill chips for Streak Days (`🔥`) and Gems/XP (`💎`), matching language learning gamification patterns.
- **More Settings Button**: Integrated 3-dots overflow button (`ic_more_vert`) for settings and extra actions.
- **Customizable Actions**: Full support for custom navigation icon and trailing action buttons.

## Code Example

```kotlin
ClayTopBar(
    title = "خانه",
    streakText = "۱۲ روز",
    gemText = "۴۸۰",
    onStreakClick = { /* open streak dialog */ },
    onGemClick = { /* open shop/wallet */ },
    onMoreClick = { /* open more options */ }
)
```
