# Claymorphic Text Inputs (`com.learnian.core.ui.component.textfield`)

Claymorphism text inputs implementing **Surface 0 • Inset Grooved (`clay-sunken` / Deep Inset Trench)** style.

## Components

### 1. `ClayTextField` (General-Purpose Base Input)
Fully generalized 3D sunken text input trench. Instead of creating single-purpose component wrappers (e.g. `ClayPhoneTextField`), customize `ClayTextField` using its flexible composable slots:
- `leadingContent: (@Composable () -> Unit)?`: Custom badges (e.g. Country Flag & Dialing Code `+98 🇮🇷`), prefix chips, or icons.
- `trailingContent: (@Composable () -> Unit)?`: Action icons (e.g. clear button, visibility toggle, contact icon).
- `leadingIcon` / `trailingIcon`: Convenience icon slots.
- `textStyle` & `placeholderStyle`: Custom typography and digit sizing.

#### Standard Usage:
```kotlin
var username by remember { mutableStateOf("") }

ClayTextField(
    value = username,
    onValueChange = { username = it },
    label = "نام کاربری",
    placeholder = "نام کاربری خود را وارد کنید..."
)
```

#### Phone Number / Custom Badged Field:
```kotlin
var phone by remember { mutableStateOf("") }

ClayTextField(
    value = phone,
    onValueChange = { phone = it },
    label = "شماره تماس شما",
    placeholder = "912 345 6789",
    leadingContent = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(text = "+98", style = AppTypography.labelMedium)
            Text(text = "🇮🇷")
        }
    },
    trailingContent = {
        BaseIcon(
            resource = Res.drawable.ic_phone_outline,
            contentDescription = null,
            tint = ClayTheme.colors.textSecondary,
        )
    },
    errorMessage = errorMessage,
)
```

---

### 2. `ClayOtpInputField`
3D sunken digit cells for SMS verification / OTP codes. Always maintains strict **Left-to-Right (LTR)** input progression across both Persian and English locales, with primary highlight border on the active cell and automatic `onComplete` trigger.

```kotlin
var otpCode by remember { mutableStateOf("") }

ClayOtpInputField(
    value = otpCode,
    onValueChange = { otpCode = it },
    length = 5,
    onComplete = { viewModel.submitOtp() }
)
```
