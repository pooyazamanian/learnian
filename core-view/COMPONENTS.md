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
- **توضیحات**: نوار عنوان شناور بالای صفحه بر اساس استانداردهای گیمفیکیشن و لرنینگ. شامل نمایش لوگوی اختصاصی لرنیان، انیمیشن عمودی تغییر عنوان، بج‌های قرصی استریک روزانه (`🔥`) و الماس‌ها (`💎`)، و دکمه سه‌نقطه تنظیمات بیشتر.
- **مستندات اختصاصی**: [`component/topbar/README.md`](file:///Volumes/pooya/project/learnian/core-view/src/commonMain/kotlin/com/learnian/core/ui/component/topbar/README.md)

### ۶. نوار پایینی (`com.learnian.core.ui.component.bottombar`)
- **کلاس‌ها**: `ClayBottomBar`, `ClayBottomNavItem`
- **توضیحات**: داک پایینی فرورفته خمیری با آیتم‌های انتخابی برجسته و انیمیشن مقیاس.
- **مستندات اختصاصی**: [`component/bottombar/README.md`](file:///Volumes/pooya/project/learnian/core-view/src/commonMain/kotlin/com/learnian/core/ui/component/bottombar/README.md)

### ۷. کانتینر اصلی صفحه (`com.learnian.core.ui.component.scaffold`)
- **کلاس**: `ClayScaffold`
- **توضیحات**: کانتینر اصلی صفحات اپلیکیشن بر اساس پالت رنگی و استانداردهای Claymorphism.
- **مستندات اختصاصی**: [`component/scaffold/README.md`](file:///Volumes/pooya/project/learnian/core-view/src/commonMain/kotlin/com/learnian/core/ui/component/scaffold/README.md)

### ۸. چیپ‌های وضعیت و بج‌ها (`com.learnian.core.ui.component.chip`)
- **کلاس**: `ClayStatusChip`
- **توضیحات**: چیپ‌های قرصی خمیری چندکاره (`clay-pill`) با پشتیبانی از دو استایل مجزا:
  - **`ClayChipStyle.Resting` (حالت پیش‌فرض تاپ‌بار)**: کپسول‌های شناور نرم و لطیف با سایه ملایم و گسترده (`clayRestingFloat`) که برای نشان‌های خلوت مانند الماس و استریک تاپ‌بار استفاده می‌شود.
  - **`ClayChipStyle.Embossed` (حالت سه‌بعدی برجسته)**: کپسول‌های برآمده و حجیم خمیری (`clayPillRaised`) دارای سایه عمیق دوبل، گرادیان نوری بدنه، هایلایت بازتابی لبه بالا و کادر متناسب برای بج‌های عضویت و لول‌های پیشرفت در کارت‌ها و پروفایل.
- **مستندات اختصاصی**: [`component/chip/README.md`](file:///Volumes/pooya/project/learnian/core-view/src/commonMain/kotlin/com/learnian/core/ui/component/chip/README.md)

### ۹. آیکون پایه (`com.learnian.core.ui.component.icon`)
- **کلاس**: `BaseIcon`
- **توضیحات**: مولفه مرکزی و پایه برای تمام آیکون‌های اپلیکیشن. پشتیبانی از `DrawableResource`, `ImageVector`, `Painter`. الزامی است تمامی آیکون‌های پروژه از طریق این کامپوننت فراخوانی شوند.
- **مستندات اختصاصی**: [`component/icon/README.md`](file:///Volumes/pooya/project/learnian/core-view/src/commonMain/kotlin/com/learnian/core/ui/component/icon/README.md)

### ۱۰. تصویر پایه (`com.learnian.core.ui.component.image`)
- **کلاس**: `BaseImage`
- **توضیحات**: مولفه مرکزی و پایه برای تمام تصاویر اپلیکیشن با امکان اعمال تنظیمات، مودیفایرها، بارگذاری ناهمگام آدرس شبکه (Server Image URL) با Fallback خودکار به DrawableResource و کشینگ سراسری.
- **مستندات اختصاصی**: [`component/image/README.md`](file:///Volumes/pooya/project/learnian/core-view/src/commonMain/kotlin/com/learnian/core/ui/component/image/README.md)

### ۱۱. دکمه رادیویی و کارت انتخابی (`com.learnian.core.ui.component.radio`)
- **کلاس‌ها**: `ClayRadioButton`, `ClayRadioCard`
- **توضیحات**: کنترل‌های انتخاب تک‌گزینه‌ای خمیری با انیمیشن فنری مقیاس و رنگ، به همراه کارت تعاملی کامل برای لیست‌های گزینه‌ای (لهجه صوتی، انتخاب پلن و...).
- **مستندات اختصاصی**: [`component/radio/README.md`](file:///Volumes/pooya/project/learnian/core-view/src/commonMain/kotlin/com/learnian/core/ui/component/radio/README.md)

### ۱۲. کلید تغییر وضعیت خمیری (`com.learnian.core.ui.component.switch`)
- **کلاس**: `ClaySwitch`
- **توضیحات**: کلید تاگل خمیری ۳بعدی با شیار فرورفته (`clayInsetGrooved`) و کلید لغزنده شناور (`clay-thumb`) با انیمیشن روان تغییر وضعیت.
- **مستندات اختصاصی**: [`component/switch/README.md`](file:///Volumes/pooya/project/learnian/core-view/src/commonMain/kotlin/com/learnian/core/ui/component/switch/README.md)

### ۱۳. اسلایدر چندگزینه‌ای سگمنتد (`com.learnian.core.ui.component.segmented`)
- **کلاس‌ها**: `ClaySegmentedSlider`, `ClaySegment`
- **توضیحات**: اسلایدر تعاملی چندگزینه‌ای خمیری با شیار فرورفته (`clayInsetGrooved`) و نشانگر لغزان ۳بعدی برآمده (`clayActionRaised`) با فیزیک جهشی فنری (`spring`). پشتیبانی کامل از جهت‌های RTL و LTR، متن، آیکون و محتوای بصری دلخواه (نظیر پیش‌نمایش اندازه قلم یا تم‌های سیستم).
- **مستندات اختصاصی**: [`component/segmented/README.md`](file:///Volumes/pooya/project/learnian/core-view/src/commonMain/kotlin/com/learnian/core/ui/component/segmented/README.md)

### ۱۴. نوار ناوبری ستونی تبلت و دسکتاپ (`com.learnian.core.ui.component.navrail`)
- **کلاس‌ها**: `ClayNavRail`, `ClayNavRailItem`
- **توضیحات**: داک ستونی عمودی خمیری شناور برای تبلت‌ها، حالت افقی گوشی (Landscape)، تاشوها و دسکتاپ. دارای چیدمان خودکار در سمت راست در زبان‌های RTL (فارسی) و سمت چپ در زبان‌های LTR (انگلیسی)، پشتیبانی از هدر لوگو، اسکرول ایمن در ارتفاع‌های کم، و آیتم‌های ۳بعدی خمیری با انیمیشن فنری.
- **مستندات اختصاصی**: [`component/navrail/README.md`](file:///Volumes/pooya/project/learnian/core-view/src/commonMain/kotlin/com/learnian/core/ui/component/navrail/README.md)

### ۱۵. سایدبار ناوبری داشبورد تبلت و دسکتاپ (`com.learnian.core.ui.component.sidebar`)
- **کلاس‌ها**: `ClaySidebar`, `ClaySidebarItem`
- **توضیحات**: پنل سایدبار تخت و یکدست (Flat Dashboard Drawer) متصل به لبه صفحه (سمت راست در فارسی / RTL و سمت چپ در انگلیسی / LTR) برای تبلت‌ها، حالت افقی گوشی‌ها و دسکتاپ به سبک داشبوردهای مدرن. شامل خط جداکننده ستونی ظریف، آیتم‌های عریض تعاملی افقی (آیکون + عنوان کنار هم) با پیل برجسته سه‌بعدی بنفش هنگام انتخاب، انیمیشن لمسی، و بخش هدر و فوتر اختصاصی.
- **مستندات اختصاصی**: [`component/sidebar/README.md`](file:///Volumes/pooya/project/learnian/core-view/src/commonMain/kotlin/com/learnian/core/ui/component/sidebar/README.md)

### ۱۶. کارت‌های آماری و متریک خمیری (`com.learnian.core.ui.component.stats`)
- **کلاس‌ها**: `ClayStatCard`, `ClayStatsRow`, `ClayStatCardVariant`
- **توضیحات**: کارت‌های متریک و آماری خمیری سه‌بعدی و ماژولار مناسب برای صفحه اصلی، صفحه مرور، جزئیات دسته‌ها و جعبه لایتنر. پشتیبانی از سه تم رنگی اصلی (بنفش، نعنایی و کهربایی)، آیکون اختصاصی در هدر، مقادیر عددی بزرگ برجسته، زیرعنوان واحد، بج درصدی انتها، و مینی نوار پیشرفت خمیری گود رفته (`ClayProgressBar`).
- **مستندات اختصاصی**: [`component/stats/README.md`](file:///Volumes/pooya/project/learnian/core-view/src/commonMain/kotlin/com/learnian/core/ui/component/stats/README.md)

### ۱۷. بنر و کارت قهرمان مرور هوشمند FSRS (`com.learnian.core.ui.component.review`)
- **کلاس**: `ClayReviewBannerCard`
- **توضیحات**: کارت بنر تعاملی خمیری ۳بعدی جهت شروع مرورهای مبتنی بر الگوریتم‌های تکرار فاصله‌دار (FSRS v5). دارای بج تگ وضعیت در بالا، عنوان و متن توضیحی انگیزشی، تصویر سه‌بعدی زاویه‌دار دفترچه در حاشیه، و دکمه اکشن بزرگ خمیری ۳بعدی شامل نشان زمان تخمینی، متن شروع و آیکون موشک.
- **مستندات اختصاصی**: [`component/review/README.md`](file:///Volumes/pooya/project/learnian/core-view/src/commonMain/kotlin/com/learnian/core/ui/component/review/README.md)

---

## 🎨 ۴. مدیریت تم داینامیک، فونت، مقیاس و زبان (`AppPlatformTheme`)

کامپوننت `AppPlatformTheme` در `com.learnian.core.ui.theme` به طور کامل به `UserSettings` و لایه DataStore متصل است:
- **حالت‌های تم (ThemeMode)**:
  - `SYSTEM`: هماهنگ با تم تاریک/روشن سیستم عامل
  - `LIGHT`: تم روشن خمیری
  - `DARK`: تم تیره شبانه نئونی
  - **پشتیبانی از تم‌های سفارشی (Custom Themes)**: از طریق `CustomThemeRegistry` برای اضافه کردن تم‌های رنگی در آینده.
- **زبان و جهت صفحه (AppLanguage & LayoutDirection)**:
  - `FA`: راست‌چین (RTL)
  - `EN`: چپ‌چین (LTR)
- **مقیاس داینامیک فونت (FontSizeScale)**:
  - `SMALL` (0.88x)
  - `MEDIUM` (1.0x پیش‌فرض)
  - `LARGE` (1.15x)
- **فونت‌های پشتیبانی‌شده (AppFont)**:
  - `DEFAULT`, `VAZIR`, `IRANYEKAN`

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

---

## 18. ClayToast & BaseScreen (مدیریت امن داده و تست پیام‌های ۳بعدی)

### ClayToast (توست ۳بعدی متحرک)
سیستم پیام شناور کلیمورفیک با ۴ نوع پیام (`Success`, `Error`, `Warning`, `Info`) و افکت ورود/خروج فنری (`spring`):

```kotlin
val toastHostState = rememberClayToastHostState()

// درون Coroutine / LaunchedEffect:
toastHostState.showSuccess("تنظیمات با موفقیت ذخیره شد!")
toastHostState.showError("خطا در برقراری ارتباط")
toastHostState.showWarning("هشدار اتصال اینترنت")
toastHostState.showInfo("نسخه جدید در دسترس است")

// دسترسی سراسری از طریق LocalClayToastHost:
val toastHost = LocalClayToastHost.current
```

### BaseScreen & LoadableUiState (حفظ کش و عدم پرش صفحه)
کلاس پایه صفحات با مدیریت وضعیت MVI بدون پاک شدن داده‌های قبلی در زمان رفرش یا خطای شبکه:

```kotlin
data class MyState(
    override val isLoading: Boolean = false,
    override val data: MyData? = null,
    override val cachedData: MyData? = null,
    override val errorMessage: String? = null,
    override val supportsCache: Boolean = true,
    override val isRefreshing: Boolean = false,
) : LoadableUiState<MyData>

@Composable
fun MyScreen(viewModel: MyViewModel = koinViewModel()) {
    val state by viewModel.uiState.collectAsState()
    val toastHost = rememberClayToastHostState()

    BaseScreen(
        state = state,
        isRefreshing = state.isRefreshing,
        toastHostState = toastHost,
        onRefresh = { viewModel.dispatch(MyIntent.Refresh) },
        onRetry = { viewModel.dispatch(MyIntent.Retry) }
    ) { data ->
        // رندر صفحه با امنیت کامل و حفظ کش در زمان رفرش
    }
}
```

