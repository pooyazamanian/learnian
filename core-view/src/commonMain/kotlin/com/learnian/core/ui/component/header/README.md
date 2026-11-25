# ClayScreenHeader & ClaySectionHeader

هدرهای مشترک اپلیکیشن؛ همه‌ی صفحات و بخش‌ها باید از همین دو کامپوننت استفاده کنند و هیچ صفحه‌ای نباید ردیف بازگشت یا عنوان اختصاصی خودش را بسازد.

- `ClayScreenHeader`: هدر بالای **صفحه‌های فرزند** (صفحه‌هایی که روی تب‌بار هل داده می‌شوند و باتم‌بار سراسری ندارند: جزئیات بسته، تنظیمات بسته، مرور، آزمون، جزئیات واژه).
- `ClaySectionHeader`: عنوان یک بخش **درون** صفحه (لیست بسته‌ها، «بسته‌های در حال یادگیری»، ...).

## ClayScreenHeader

### ساختار
- دکمه بازگشت خمیری ۴۲dp: `ClayIconButton` + `AppCorners.Medium` + پس‌زمینه `surface` + آیکون `ic_arrow_left` و توضیح دسترس‌پذیری `common_back`.
- عنوان `17.sp` با وزن `ExtraBold` و رنگ `textPrimary`.
- زیرعنوان اختیاری `11.sp` (lineHeight = 16.sp) با رنگ `textSecondary` — برای بافت صفحه مثل «درس ۳ · واژگان» یا «سوال ۲ از ۱۰».
- اسلات `trailing` برای بج یا اکشن سمت مقابل (استریک، شمارنده، ...).
- چیدمان RTL-first: دکمه بازگشت در ابتدای ردیف و عنوان با `weight(1f)` بقیه فضا را می‌گیرد.

### پارامترها
| پارامتر | نوع | پیش‌فرض | توضیح |
|---|---|---|---|
| `title` | `String` | — | عنوان صفحه (باید از `stringResource` بیاید) |
| `onBackClick` | `() -> Unit` | — | رویداد بازگشت؛ در ViewModel به `NavigateBack` نگاشت می‌شود |
| `modifier` | `Modifier` | `Modifier` | برای پدینگ افقی/عمودی صفحه |
| `subtitle` | `String?` | `null` | زیرعنوان اختیاری |
| `trailing` | `(@Composable () -> Unit)?` | `null` | اسلات اکشن/بج |

### نمونه استفاده
```kotlin
ClayScreenHeader(
    title = stringResource(Res.string.learning_details_title),
    subtitle = stringResource(Res.string.review_level_lesson_format, level, lesson),
    onBackClick = { viewModel.dispatch(Intent.NavigateBack) },
    trailing = {
        ClayStatusChip(text = "۳ روز", style = ClayChipStyle.Resting)
    },
)
```

## ClaySectionHeader

### ساختار
- ردیف عنوان با فاصله‌ی `Spacing.Small` و تراز عمودی وسط.
- عنوان `17.sp` / `ExtraBold` + زیرعنوان اختیاری `11.sp`.
- اسلات `trailing` برای چیپ شمارنده یا اکشن (مثلاً تعداد بسته‌های فعال).

### پارامترها
| پارامتر | نوع | پیش‌فرض | توضیح |
|---|---|---|---|
| `title` | `String` | — | عنوان بخش |
| `modifier` | `Modifier` | `Modifier` | — |
| `subtitle` | `String?` | `null` | توضیح کوتاه زیر عنوان |
| `trailing` | `(@Composable () -> Unit)?` | `null` | بج/اکشن انتهای ردیف |

### نمونه استفاده
```kotlin
ClaySectionHeader(
    title = stringResource(Res.string.learning_discover_title),
    trailing = {
        ClayStatusChip(text = count.toString(), style = ClayChipStyle.Resting)
    },
)
```

## قواعد
- فقط توکن: `AppTypography`, `ClayTheme.colors`, `Spacing`, `AppCorners`. هیچ رنگ یا فونت هاردکد.
- متن‌ها همیشه از `stringResource(Res.string.*)`؛ دکمه بازگشت از `common_back` استفاده می‌کند تا همه‌ی صفحات یک برچسب دسترس‌پذیری داشته باشند.
- عنوان تب‌های اصلی (خانه، مرور، آمار، ...) همچنان در هدر سراسری اسکافولد می‌ماند و این کامپوننت جای آن را نمی‌گیرد.