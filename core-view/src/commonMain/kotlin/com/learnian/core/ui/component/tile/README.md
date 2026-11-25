# ClaySelectableTile

کاشی خمیری فشرده و قابل انتخاب برای ردیف گزینه‌ها؛ جایی که `ClayRadioCard` تمام‌عرض جا نمی‌شود (سهمیه‌ی واژه‌ی روزانه، درصد سهم، پلن پیشنهادی).

## مشخصات
- سطوح: `ClayCard` با `AppCorners.Medium` و پدینگ `Spacing.Small`.
- وضعیت انتخاب‌شده: پس‌زمینه `primary` + متن `onPrimary` + سایه‌ی رنگی `primary.copy(alpha = 0.30f)`؛ حالت عادی: `surface` + `textPrimary` + `shadowOuter`.
- عنوان `12.sp` / `ExtraBold` و زیرعنوان اختیاری `9.sp`؛ هر دو وسط‌چین.
- بدون شاخه‌ی `if (isDark)`: هر دو تم فقط از توکن‌های `ClayTheme.colors` تغذیه می‌شوند.
- `onClick` از طریق `ClayCard` به `clayPressable` وصل است؛ بازخورد فشردن لمسی دارد.

## پارامترها
| پارامتر | نوع | پیش‌فرض | توضیح |
|---|---|---|---|
| `title` | `String` | — | برچسب اصلی کاشی |
| `selected` | `Boolean` | — | وضعیت انتخاب |
| `onClick` | `() -> Unit` | — | انتخاب گزینه |
| `modifier` | `Modifier` | `Modifier` | برای `weight`/ارتفاع در ردیف |
| `subtitle` | `String?` | `null` | توضیح کوچک (مثلاً تعداد واژه یا روز) |

## نمونه استفاده
```kotlin
Row(horizontalArrangement = Arrangement.spacedBy(Spacing.Small)) {
    QUOTA_PRESETS.forEach { preset ->
        ClaySelectableTile(
            title = stringResource(Res.string.learning_settings_percent, preset),
            subtitle = stringResource(Res.string.learning_settings_option_words, preset * capacity / 100),
            selected = preset == selectedQuota,
            onClick = { onQuotaChange(preset) },
            modifier = Modifier.weight(1f).height(62.dp),
        )
    }
}
```

## کجا استفاده نکنیم
- گزینه‌های تمام‌عرض با توضیح بلند → `ClayRadioCard`.
- انتخاب پیوسته/طیفی (مثلاً انتخاب حالت تمرین) → `ClaySegmentedSlider`.