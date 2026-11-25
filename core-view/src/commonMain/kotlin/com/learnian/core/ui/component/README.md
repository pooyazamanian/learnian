# کامپوننت‌های صوتی (Voice)

دو رپر باریک روی کامپوننت‌های پایه که در جریان «تلفظ/صدا» استفاده می‌شوند. در صورت نیاز، مستقیم از کامپوننت پایه هم می‌توان استفاده کرد؛ این فایل‌ها فقط میان‌بر هستند.

## VoiceIconButton
`ClayIconButton` را با اندازه‌ی پیش‌فرض بزرگ‌تر (۵۶dp) و پس‌زمینه‌ی قابل انتخاب (`surface` یا `primary`) می‌پیچد. محتوای آیکون از طریق اسلات `content` می‌آید، پس رنگ آیکون همان‌جا ست می‌شود.

| پارامتر | نوع | پیش‌فرض | توضیح |
|---|---|---|---|
| `onClick` | `() -> Unit` | — | اکشن دکمه |
| `modifier` | `Modifier` | `Modifier` | — |
| `size` | `Dp` | `56.dp` | اندازه دکمه |
| `useSurface` | `Boolean` | `true` | `true`: پس‌زمینه `surface`؛ `false`: پس‌زمینه `primary` |
| `content` | `@Composable () -> Unit` | — | آیکون/محتوای داخل دکمه |

```kotlin
VoiceIconButton(onClick = onPlayAudio) {
    BaseIcon(
        resource = Res.drawable.ic_volume_up,
        contentDescription = stringResource(Res.string.quiz_audio_icon_desc),
        modifier = Modifier.size(22.dp),
        tint = ClayTheme.colors.textPrimary,
    )
}
```

## VoiceScaffold
منسوخ (deprecated): فقط `ClayScaffold` را صدا می‌زند. برای صفحه‌های جدید از `ClayScaffold` استفاده کنید (مستندات: [`component/scaffold/README.md`](../scaffold/README.md)).