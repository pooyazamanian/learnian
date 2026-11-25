# ClayToast

سیستم پیام شناور خمیری با چهار نوع پیام (`Success`, `Error`, `Warning`, `Info`) و انیمیشن فنری ورود/خروج.

## اجزا
- `ClayToastHostState`: نگهدارنده‌ی وضعیت (سوسپند/غیرسوسپند، `dismiss`).
- `rememberClayToastHostState()`: ساخت و به‌خاطر سپردن state در Composable.
- `ClayToastHost(hostState)`: کانتینر شناور بالای صفحه (با `statusBarsPadding`) که توست فعال را رندر می‌کند.
- `ClayToast(data)`: پیل خمیری تک‌توست.
- `LocalClayToastHost`: CompositionLocal برای دسترسی سراسری.
- `ClayToastData`: `message`, `type`, `durationMs` (پیش‌فرض ۳۲۰۰ms) و `id` تصادفی.

## استفاده
```kotlin
@Composable
fun MyScreen(viewModel: MyViewModel = koinViewModel()) {
    val state by viewModel.uiState.collectAsState()
    val toastHostState = rememberClayToastHostState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MyEffect.ShowToast -> toastHostState.show(
                    scope = scope,
                    message = effect.message.asStringAsync(), // بیرون از updateState
                    type = effect.type,
                )
            }
        }
    }

    BaseScreen(state = state, toastHostState = toastHostState) { data -> /* ... */ }
}
```

## قواعد
- پیام همیشه از `UiText` می‌آید؛ `asStringAsync()` را قبل از `updateState` در یک `val` بگیرید و داخل ردیوسر سوسپند فراخوانی نکنید.
- `BaseScreen` خودش `ClayToastHost` را می‌چسباند؛ فقط برای صفحاتی که به `BaseScreen` دسترسی ندارند میزبان را دستی اضافه کنید.
- رنگ‌ها از `ClayTheme.colors` (جعبه‌ی هر نوع پیام) و سایه/حاشیه‌ها از `AppCorners`/`Spacing` می‌آیند.