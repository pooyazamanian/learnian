# ClayTextField

Claymorphism text field implementing **Surface 0 • Inset Grooved (`clay-sunken` / Deep Inset Trench)** style.

## Features
- **Deep Inset Trench**: Gives input fields a dug-in carved feeling into the clay base.
- **Ergonomic Radius**: Defaults to `20.dp` (`AppCorners.Large`).
- **Icons & Error Handling**: Supports leading/trailing icons, label, placeholder, and error messages.

## Code Example

```kotlin
var text by remember { mutableStateOf("") }

ClayTextField(
    value = text,
    onValueChange = { text = it },
    label = "نام کاربری",
    placeholder = "نام کاربری خود را وارد کنید..."
)
```
