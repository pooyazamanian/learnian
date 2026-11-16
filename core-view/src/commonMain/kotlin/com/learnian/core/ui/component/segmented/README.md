# ClaySegmentedSlider

اسلایدر چندگزینه‌ای سگمنتد خمیری با نوار فرورفته شیاردار و زبانه لغزان ۳بعدی برآمده (**Surface 0 Inset Groove + Surface 2 Action Raised**).

## Features
- **شیار فرورفته خمیری (`clayInsetGrooved`)**: شیار ملایم با پس‌زمینه عمیق‌تر که حس حفر شدن در سطح را تداعی می‌کند.
- **زبانه شناور متحرک (`clayActionRaised`)**: زبانه برجسته خمیری با سایه دوبل و فیزیک جهشی فنری (`spring(dampingRatio = 0.72f, stiffness = 380f)`).
- **پشتیبانی کامل RTL و LTR**: محاسبه دقیق جهت جابجایی بر اساس LayoutDirection صفحه.
- **پشتیبانی از انواع محتوا**: قابلیت استفاده به صورت فقط متنی، متن همراه آیکون (`BaseIcon`)، یا محتوای بصری دلخواه (`ClaySegment.CustomContent`).

## Code Example

```kotlin
val options = listOf(
    ClaySegment(value = 10, label = "۱۰ لغت"),
    ClaySegment(value = 20, label = "۲۰ لغت"),
    ClaySegment(value = 30, label = "۳۰ لغت")
)

ClaySegmentedSlider(
    segments = options,
    selectedValue = selectedOption,
    onValueChange = { selectedOption = it }
)
```
