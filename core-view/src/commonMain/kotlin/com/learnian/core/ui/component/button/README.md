# ClayButton & ClayIconButton

Claymorphism button components implementing **Surface 2 • Action Raised (`clay-btn`)** specs.

## Features
- **Ergonomic Radius**: Defaults to `20.dp` (`AppCorners.Large`) or `AppCorners.Pill` (for rounded action pills).
- **Interactive Active Scaling**: Automatically scales to `0.96` on touch press (`clayPressable`).
- **Dual Surface Depth**: Top-left inset highlight combined with soft spread outer drop shadow.
- **Variants (`ClayButtonVariant`)**:
  - `Primary`: Brand lavender button with white text.
  - `Secondary`: Mint green button for success/confirmations.
  - `Tertiary`: Amber button for rewards and gamification.
  - `Error`: Peach red button for destructive actions.
  - `Surface`: White/light 3D tactile surface button with dark text, ideal for Google SSO or neutral secondary actions.

## Code Examples

### 1. Simple Text & Icon Overload (Preferred):
```kotlin
ClayButton(
    text = "دریافت کد تایید",
    onClick = { viewModel.submit() },
    variant = ClayButtonVariant.Primary,
    leadingIcon = {
        BaseIcon(
            resource = Res.drawable.ic_arrow_left,
            contentDescription = null,
            tint = Color.White,
        )
    },
)

// Google SSO Surface Variant:
ClayButton(
    text = "ورود با حساب گوگل",
    onClick = { viewModel.signInWithGoogle() },
    variant = ClayButtonVariant.Surface,
    leadingIcon = {
        BaseIcon(
            resource = Res.drawable.ic_google_logo,
            contentDescription = "Google",
        )
    },
)
```

### 2. Custom Content Slot:
```kotlin
ClayButton(
    onClick = { /* handle click */ },
    variant = ClayButtonVariant.Primary
) {
    Text("دکمه با محتوای سفارشی")
}

ClayIconButton(
    onClick = { /* handle action */ }
) {
    BaseIcon(resource = Res.drawable.ic_mic, contentDescription = "Voice Action")
}
```
