# BaseImage

The unified, foundational image component for the Learnian Design System.

## Architecture Rule
**All image displays in the application MUST use `BaseImage`.**
Never use `androidx.compose.foundation.Image` directly. Centralizing all images via `BaseImage` ensures consistent scaling, placeholder support, accessibility, and centralized modifiers.

## Overloads Supported
- `Painter`
- `ImageVector`
- `DrawableResource`

## Code Example

```kotlin
BaseImage(
    resource = Res.drawable.ic_logo,
    contentDescription = "لوگوی برنامه",
    modifier = Modifier.size(32.dp),
    contentScale = ContentScale.Fit
)
```
