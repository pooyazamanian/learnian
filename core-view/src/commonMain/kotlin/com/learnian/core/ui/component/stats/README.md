# ClayStatCard & ClayStatsRow Component

ماژول کارت‌های آماری و متریک خمیری ۳بعدی جهت نمایش خلاصه وضعیت، اهداف و داده‌های یادگیری.

## مشخصات طراحی
- **پالت‌ها**: `Purple` (مرور)، `Mint` (واژگان جدید/یادگیری)، `Amber` (اهداف روزانه/استریک)، `Custom`.
- **قابلیت‌ها**: آیکون دایره‌ای هدر، مقدار عددی بزرگ (`headlineLarge`), زیرعنوان واحد، بج درصدی انتها، و مینی نوار پیشرفت خمیری (`ClayProgressBar`).
- **اسکیل‌پذیری**: استفاده در صفحه اصلی (`HomeScreen`)، صفحه مرور و آمار (`ReviewScreen`), جزئیات دسته‌ها (`DeckDetails`).

## نحوه استفاده
```kotlin
ClayStatsRow {
    ClayStatCard(
        title = "مرور امروز",
        value = "18",
        subtitle = "لغت در نوبت",
        variant = ClayStatCardVariant.Purple,
        modifier = Modifier.weight(1f),
    )
    ClayStatCard(
        title = "واژه جدید",
        value = "10",
        subtitle = "آماده یادگیری",
        variant = ClayStatCardVariant.Mint,
        modifier = Modifier.weight(1f),
    )
    ClayStatCard(
        title = "هدف روزانه",
        trailingBadge = { Text("65%") },
        subtitle = "۱۳ از ۲۰ لغت",
        progress = 0.65f,
        variant = ClayStatCardVariant.Amber,
        modifier = Modifier.weight(1f),
    )
}
```

## واحد کنار مقدار
`valueSuffix` یک واحد کوچک (مثل «واژه» یا «روز») را کنار عدد بزرگ و هم‌تراز پایین آن رندر می‌کند؛ برای کارت‌هایی که مقدار از یک عدد به‌تنهایی بیشتر است.
```kotlin
ClayStatCard(
    title = "لغات",
    value = "1240",
    valueSuffix = "واژه",
    subtitle = "420 مورد در این هفته",
    variant = ClayStatCardVariant.Purple,
)
```

# ClayDonutChart & ClayBarChart
نمودارهای خمیری پایه برای صفحات آمار. هر دو بدون وابستگی به فیچر هستند و فقط توکن‌های تم را می‌خوانند.

هر دو از سطوح خود clay استفاده می‌کنند: بدنه‌ها `clayPillRaised` (گرادیان روشن، لبه‌ی هایلایت بالایی و سایه‌ی تماسی) و شیارها `clayInsetGrooved` هستند؛ هیچ نمودار مسطح یا سافت‌شده‌ای اینجا مجاز نیست.

## ClayDonutChart
- `segments`: لیست `ClayDonutSegment(value, color)` — مقدار خام (تعداد واژه و…)؛ زاویه‌ها از نسبت مجموع محاسبه می‌شوند.
- `strokeWidth` / `gapDegrees`: ضخامت حلقه و فاصله‌ی بصری بین قوس‌ها.
- `trackColor`: ترک گود پشت حلقه (اختیاری)؛ خود قوس‌ها سایه‌ی رنگی نرم، گرادیان روشن‌به‌تیره و ریم براق داخلی دارند.
- `centerContent`: اسلات داخل حلقه (مثلاً مجموع + برچسب).
```kotlin
ClayDonutChart(
    segments = listOf(
        ClayDonutSegment(value = 840f, color = ClayTheme.colors.secondary),
        ClayDonutSegment(value = 276f, color = ClayTheme.colors.primary),
        ClayDonutSegment(value = 124f, color = ClayTheme.colors.tertiary),
    ),
    modifier = Modifier.size(190.dp),
) {
    Text("1240")
}
```

## ClayBarChart
- `bars`: لیست `ClayBarChartBar(label, value, isHighlighted)`.
- `trackHeight` / `barWidth`: ابعاد ستون‌ها؛ ارتفاع پرشدنسبی است.
- ستون `isHighlighted` با رنگ تاکید و برچسب پررنگ (معمولاً «امروز») رندر می‌شود.
```kotlin
ClayBarChart(
    bars = days.map { ClayBarChartBar(it.label, it.minutes.toFloat(), it.isToday) },
    trackHeight = 120.dp,
)
```
