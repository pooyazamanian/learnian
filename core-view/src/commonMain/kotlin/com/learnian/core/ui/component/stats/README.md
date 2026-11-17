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
