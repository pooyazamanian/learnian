# Learnian Claymorphism Design System & Component Library

> [!IMPORTANT]
> **قانون اصلی طراحی UI در پروژه:**
> تمام صفحات پروژه (Screens) بر اساس سیستم **Claymorphism** پیاده‌سازی می‌شوند.
> **هرگز المان‌های پایه (Button, Text Field, Card, Progress Bar, TopBar, BottomBar) را از نو طراحی نکنید.**
> قبل از پیاده‌سازی هر صفحه جدید، حتماً این سند و پوشه مربوط به هر Component در `com.learnian.core.ui.component` را بررسی کنید و از المان‌های موجود استفاده کنید.

---

## 🎨 ۱. سیستم رنگ‌ها (Color Spec)

### Light Clay Spec
- **Primary Lavender (`ClayPrimaryLavender`)**: `#5949C0` - رنگ اصلی برند، اکشن‌های اصلی، تگ‌های فعال.
- **Secondary Mint (`ClaySecondaryMint`)**: `#006B5C` - نشان‌دهنده یادگیری کامل، پاداش‌ها و تیک‌های موفقیت.
- **Tertiary Warm Amber (`ClayTertiaryAmber`)**: `#F3BE67` - نماد استریک روزانه، صندوقچه‌ها و دستاوردهای گیمفیکیشن.
- **Error / Again Peach (`ClayErrorPeach`)**: `#BA1A1A` - دکمه‌های تصحیح، اشتباهات و پیام‌های خطا.
- **Surface Cream (`ClayLightBackground`)**: `#F6F4FA` - پس‌زمینه نرم خمیری.
- **Container (`ClayLightContainer`)**: `#FFFFFF` - کارت‌ها و کانتینرهای شناور.
- **Sunken Groove (`ClayLightSunken`)**: `#EDE9F5` - شیارهای فرورفته اینپوت‌ها و ترک‌های پیشرفت.

### Dark Clay Spec
- **Surface**: `#1C1A29`
- **Container**: `#2A2739`
- **Vibrant Neon Clay (`ClayDarkVibrantNeon`)**: `#7666E3` - یاسی شبانه تنظیم‌شده برای کنتراست تیره.
- **Secondary Mint**: `#00A896`
- **Deep Inset Trench (`ClayDarkDeepTrench`)**: `#151320` - شیار گود شبانه برای ورودی‌ها.
- **Subtle Elevation Highlight**: `8%` opacity white reflex (`0x1AFFFFFF`).

---

## 📐 ۲. ماتریس رادیوس‌ها و فواصل (Ergonomic Radius Matrix)

شبکه پایه: **8pt Grid** (هیچ زاویه ۹۰ درجه‌ای در سیستم وجود ندارد):
- `AppCorners.XS` (8.dp) - کپ آیکون‌ها و تگ‌های بسیار کوچک
- `AppCorners.Small` (12.dp) - پدینگ تگ‌ها و بج‌ها
- `AppCorners.Medium` (16.dp) - پدینگ کارت‌های کوچک
- `AppCorners.Large` (20.dp) - دکمه‌های اصلی (`clay-btn`) و اینپوت‌ها (`clay-sunken`)
- `AppCorners.XL` (24.dp) - رادیوس کارت فلش‌کارت‌ها (`clay-card`)
- `AppCorners.XXL` (32.dp) - رادیوس مودال‌ها و داک پایینی (`clay-modal`)
- `AppCorners.Pill` (9999.dp) - چیپ‌ها و تگ‌های قرصی شکل

---

## 🧱 ۳. کاتالوگ المان‌ها (Component Catalog)

### ۱. دکمه‌ها (`com.learnian.core.ui.component.button`)
- **کلاس‌ها**: `ClayButton`, `ClayIconButton`
- **توضیحات**: پیاده‌سازی حالت `Surface 2 • Action Raised`. دارای انیمیشن فشردن لمسی (`scale = 0.96f`).
- **تغییرات**: `Primary`, `Secondary`, `Tertiary`, `Error`.
- **مستندات اختصاصی**: [`component/button/README.md`](file:///Users/pooya/StudioProjects/learnian/core-view/src/commonMain/kotlin/com/learnian/core/ui/component/button/README.md)

### ۲. ورودی‌های متنی (`com.learnian.core.ui.component.textfield`)
- **کلاس**: `ClayTextField`
- **توضیحات**: پیاده‌سازی حالت `Surface 0 • Inset Grooved` (شیار گود فرورفته Deep Inset Trench). پشتیبانی از آیکون‌های ابتدا/انتها، لیبل و پیام خطا.
- **مستندات اختصاصی**: [`component/textfield/README.md`](file:///Users/pooya/StudioProjects/learnian/core-view/src/commonMain/kotlin/com/learnian/core/ui/component/textfield/README.md)

### ۳. کارت‌ها (`com.learnian.core.ui.component.card`)
- **کلاس**: `ClayCard`
- **توضیحات**: پیاده‌سازی حالت `Surface 1 • Resting Float`. دارای سایه شناور نرم و هایلایت سفید بالای کارت.
- **مستندات اختصاصی**: [`component/card/README.md`](file:///Users/pooya/StudioProjects/learnian/core-view/src/commonMain/kotlin/com/learnian/core/ui/component/card/README.md)

### ۴. نوار پیشرفت (`com.learnian.core.ui.component.progress`)
- **کلاس**: `ClayProgressBar`
- **توضیحات**: نوار پیشرفت خمیری با شیار فرورفته و شناور خمیری متحرک.
- **مستندات اختصاصی**: [`component/progress/README.md`](file:///Users/pooya/StudioProjects/learnian/core-view/src/commonMain/kotlin/com/learnian/core/ui/component/progress/README.md)

### ۵. نوار بالایی (`com.learnian.core.ui.component.topbar`)
- **کلاس**: `ClayTopBar`
- **توضیحات**: نوار عنوان شناور بالای صفحه با پشتیبانی از دکمه بازگشت و اکشن‌ها.
- **مستندات اختصاصی**: [`component/topbar/README.md`](file:///Users/pooya/StudioProjects/learnian/core-view/src/commonMain/kotlin/com/learnian/core/ui/component/topbar/README.md)

### ۶. نوار پایینی (`com.learnian.core.ui.component.bottombar`)
- **کلاس‌ها**: `ClayBottomBar`, `ClayBottomNavItem`
- **توضیحات**: داک پایینی فرورفته خمیری با آیتم‌های انتخابی برجسته و انیمیشن مقیاس.
- **مستندات اختصاصی**: [`component/bottombar/README.md`](file:///Users/pooya/StudioProjects/learnian/core-view/src/commonMain/kotlin/com/learnian/core/ui/component/bottombar/README.md)

### ۷. کانتینر اصلی صفحه (`com.learnian.core.ui.component.scaffold`)
- **کلاس**: `ClayScaffold`
- **توضیحات**: کانتینر اصلی صفحات اپلیکیشن بر اساس پالت رنگی و استانداردهای Claymorphism.
- **مستندات اختصاصی**: [`component/scaffold/README.md`](file:///Users/pooya/StudioProjects/learnian/core-view/src/commonMain/kotlin/com/learnian/core/ui/component/scaffold/README.md)

---

## 🛠 نحوه استفاده در صفحات جدید

```kotlin
import com.learnian.core.ui.theme.AppPlatformTheme
import com.learnian.core.ui.theme.ClayTheme
import com.learnian.core.ui.component.button.ClayButton
import com.learnian.core.ui.component.button.ClayButtonVariant
import com.learnian.core.ui.component.card.ClayCard
import com.learnian.core.ui.component.textfield.ClayTextField

@Composable
fun SampleScreen() {
    AppPlatformTheme {
        ClayCard {
            Column {
                ClayTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = "نام کاربری"
                )
                ClayButton(
                    onClick = { /* action */ },
                    variant = ClayButtonVariant.Primary
                ) {
                    Text("تایید")
                }
            }
        }
    }
}
```
