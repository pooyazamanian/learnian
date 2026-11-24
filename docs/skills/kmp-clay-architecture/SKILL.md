---
name: kmp-clay-architecture
description: Comprehensive architecture, design system, and clean code guidelines for Kotlin Multiplatform (KMP) with Compose Multiplatform. Covers strict modularization, MVI with LoadableUiState and cache retention, 3D Claymorphism design system rules, zero conditional branching in UI (no if (ClayTheme.isDark)), ClayToast notifications, BaseViewModel, BaseScreen, BaseRepository data synchronization, and cross-platform best practices.
license: Complete terms in LICENSE.txt
metadata:
  author: Learnyan Engineering
  last-updated: '2026-11-17'
  keywords:
  - kmp
  - compose-multiplatform
  - clean-architecture
  - mvi
  - claymorphism
  - base-screen
  - base-repository
  - loadable-ui-state
  - toast
  - modularization
---

# KMP Claymorphic Clean Architecture Skill

This skill defines the architectural rules, state management standards, UI design constraints, and engineering patterns for building ultra-clean, scalable, and responsive applications with **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**.

---

## 1. Core Principles & Clean Architecture Philosophy

1. **Unidirectional Data Flow (MVI)**: State flows DOWN, Events/Intents flow UP. Side effects flow as one-off Channels/Flows.
2. **Zero Conditional Logic in UI**: Never use `if (ClayTheme.isDark)` or platform branching inside Composable functions. All colors, metrics, and tokens MUST be provided by the theme layer.
3. **No Screen Data Loss (Cache Retention)**: Reloading, refreshing, or transient network errors MUST NOT wipe existing screen content. Previous data remains visible while errors are surfaced non-destructively.
4. **Strict Modularization**: Feature modules are completely isolated from each other. Shared capabilities live in `:core-*` modules.
5. **Ergonomic Tactile UI (Claymorphism)**: Every visual element possesses 3D soft volume (ambient + contact drop shadows, top inner highlights, smooth rounded corners without sharp edges).
6. **Generalized Base Components in `:core-view` (NO Disposable Feature Wrappers)**:
   - Base components in `:core-view` (such as `ClayTextField`, `ClayButton`, `ClayOtpInputField`) MUST be generalized, highly parameter-driven, and slot-based (providing `leadingContent: (@Composable () -> Unit)?`, `trailingContent: (@Composable () -> Unit)?`, semantic variants, typography, and padding parameters).
   - Screens and feature modules MUST configure and consume these base components directly.
   - **STRICT ANTI-PATTERN**: NEVER create disposable, single-use, screen-specific component wrappers in `:core-view` or feature packages (e.g. `ClayPhoneTextField`, `AuthGoogleSsoButton`, `ProfileLogoutButton`).
   - **Single Source of Truth**: When a core design token, corner radius, haptic press animation, or elevation shadow is updated in `:core-view`, that improvement MUST propagate automatically to every screen across the app without manual edits.
7. **Strict Left-to-Right (LTR) for OTP and Numeric Input Progression**:
   - OTP digit cells, PIN entries, and numeric progression fields MUST strictly fill, animate, and navigate from Left to Right (LTR) regardless of active app language or layout direction (both in Persian/RTL and English/LTR), enforced via `CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr)`.
8. **Scaffold & Inset Protection in `App.kt`**: Never revert or override manual adjustments to `App.kt` root `Scaffold` modifiers (e.g. `.imePadding()`, `.fillMaxSize()`, `.navigationBarsPadding()`, `.statusBarsPadding()`, `.windowInsetsPadding(...)`).

---

## 2. Multi-Module Project Structure

```text
├── androidApp/               # Android platform entry (edge-to-edge, MainActivity)
├── iosApp/                   # iOS platform entry (SwiftUI wrapper, ViewController)
├── shared/                   # Cross-feature routing, Root App.kt, Koin entrypoint
├── core-view/                # UI Design System: ClayTheme, Tokens, BaseScreen, Components, Modifiers
├── core-datastore/           # Key-value storage (UserSettings, DataStore preferences)
├── core-network/             # Ktor client, HTTP engines, Auth tokens, Base DTOs
├── core-database/            # Offline SQLDelight / Room KMP database
├── features/
│   ├── intro/                # Presentation, domain & data for Onboarding Intro flow
│   ├── auth/                 # Presentation, domain & data for Phone OTP authentication
│   ├── home/                 # Presentation, domain & data for Home dashboard
│   ├── profile/              # Presentation, domain & data for Profile & Settings
│   └── review/               # Presentation, domain & data for FSRS Review
```

### Dependency Rules:
- `features:*` depends ONLY on `:core-view`, `:core-datastore`, `:core-network` (or database).
- **NEVER** depend from one feature module to another feature module (`:features:home` MUST NOT depend on `:features:profile`).
- `:shared` coordinates navigation across features.

---

## 3. MVI State Layer (`UiState`, `UiIntent`, `UiEffect`)

### 3.1. Contract Definition
Every screen feature defines a Contract file with typed MVI boundaries:

```kotlin
// 1. UI Intent (Actions sent from UI to ViewModel)
sealed interface HomeIntent : UiIntent {
    data object Refresh : HomeIntent
    data object StartReview : HomeIntent
}

// 2. UI State (Immutable state rendered by UI)
data class HomeState(
    override val isLoading: Boolean = false,
    override val data: HomeDashboard? = null,
    override val cachedData: HomeDashboard? = null,
    override val errorMessage: String? = null,
    override val supportsCache: Boolean = true,
    override val isRefreshing: Boolean = false,
) : LoadableUiState<HomeDashboard>

// 3. UI Effect (One-off transient events like Toasts or navigation)
sealed interface HomeEffect : UiEffect {
    data class ShowToast(val message: String, val type: ClayToastType = ClayToastType.Info) : HomeEffect
    data class NavigateToReview(val deckId: String) : HomeEffect
}
```

### 3.2. `LoadableUiState<T>` Contract
All loadable states implement `LoadableUiState<T>` to guarantee safe data extraction and background cache preservation:

```kotlin
interface LoadableUiState<out T> : UiState {
    val isLoading: Boolean
    val data: T?
    val cachedData: T?
    val errorMessage: String?
    val supportsCache: Boolean
    val isRefreshing: Boolean

    /** Safely returns active data, falling back to cachedData if available. */
    val safeData: T? get() = data ?: if (supportsCache) cachedData else null

    /** True only on initial load when NO data (live or cache) exists. */
    val isInitialLoading: Boolean get() = isLoading && safeData == null

    /** True when content is available to render. */
    val hasData: Boolean get() = safeData != null
}
```

---

## 4. Data & Repository Synchronization Layer (`BaseRepository` & `ResourceKey`)

To support robust offline-first synchronization, selective cache invalidation, and bandwidth optimization (HTTP 304 Not Modified / ETag / Last-Modified), feature repositories implement `BaseRepository<T>`:

```kotlin
interface BaseRepository<T> {
    /** Unique type-safe identifier for the resource managed by this repository. */
    val resourceKey: ResourceKey

    /** Optional extension hook to normalize/filter API data before local DB save. */
    suspend fun processBeforeDatabaseSave(data: T): T = data

    /** Optional extension hook to format Last-Modified/ETag before DataStore save. */
    suspend fun processBeforeLastModifiedSave(lastModified: String): String = lastModified
}
```

### 4.1. Purpose of `val resourceKey: ResourceKey`
- **Type-Safe Domain Identity**: Uniquely identifies the domain resource managed by the repository (e.g., `ResourceKey("home_dashboard")`, `ResourceKey("user_profile")`).
- **Decoupled Cache Management**: Allows generic memory registries, local SQLite databases, or DataStore metadata stores to clear, validate, or invalidate caches for specific resources without coupling to concrete repository types.
- **Multi-Tenant / Scoped Invalidation**: Enables namespacing cached entities by user session or tenant and performing targeted partial cache evictions.

### 4.2. Purpose of `processBeforeDatabaseSave(data: T): T`
- **Pre-Persistence Normalization Hook**: Intercepts raw DTO/domain payloads arriving from remote APIs before writing to local persistence (SQLDelight/Room).
- **Use Cases**:
  - Filtering or sanitizing corrupted, malformed, or duplicate items.
  - Computing derived properties (e.g. expiration timestamps, local sort orders).
  - Merging incoming server state with pending offline local mutations.
- **Default Behavior**: Identity function (`value -> value`), requiring override only when custom transformation is needed.

### 4.3. Purpose of `processBeforeLastModifiedSave(lastModified: String): String`
- **HTTP Conditional Requests & Cache Validation (304 Not Modified)**: Prepares timestamp or ETag values for headers like `If-Modified-Since` / `If-None-Match`.
- **Use Cases**:
  - Normalizing date formats across different server endpoints (e.g. RFC 1123, ISO 8601).
  - Stripping weak ETag prefixes (`W/`) or adjusting timezones before saving to DataStore metadata.
- **Default Behavior**: Identity function (`value -> value`).

---

## 5. ViewModel Layer (`BaseViewModel` & `BaseSharedViewModel`)

### 5.1. Standard Feature ViewModels (`BaseViewModel`)
ViewModels extend `BaseViewModel<S, I, E>` and manage updates through `updateState` and `emitEffect`:

```kotlin
class HomeViewModel(
    private val repository: HomeRepository,
) : BaseViewModel<HomeState, HomeIntent, HomeEffect>(HomeState()) {

    init {
        dispatch(HomeIntent.Refresh)
    }

    override fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.Refresh -> loadDashboard(isRefresh = true)
            is HomeIntent.StartReview -> emitEffect(HomeEffect.NavigateToReview("daily"))
        }
    }
}
```

### 5.2. Multi-Step & Pager Shared ViewModels (`BaseSharedViewModel`)
For multi-step flows, horizontal onboarding pagers, or nested sub-flows spanning multiple child screens, ViewModels extend `BaseSharedViewModel<S, I, E>`.
- Allows sharing a single state machine, step index, and data payload across sibling pages.
- Provides `broadcastEvent(event: E)` to dispatch one-off effects (such as scrolling to a page or completing a flow) to all active subscribers simultaneously.

```kotlin
class IntroSharedViewModel(
    private val getIntroSlidesUseCase: GetIntroSlidesUseCase,
    private val completeIntroUseCase: CompleteIntroUseCase,
) : BaseSharedViewModel<IntroState, IntroIntent, IntroEffect>(IntroState())
```

    private fun loadDashboard(isRefresh: Boolean) {
        viewModelScope.launch {
            // Retain existing data as cache to prevent screen jump
            val currentData = currentState.safeData
            updateState {
                copy(
                    isLoading = !isRefresh,
                    isRefreshing = isRefresh,
                    cachedData = currentData,
                    errorMessage = null,
                )
            }

            repository.getDashboard()
                .onSuccess { result ->
                    updateState {
                        copy(
                            isLoading = false,
                            isRefreshing = false,
                            data = result,
                            cachedData = result,
                            errorMessage = null,
                        )
                    }
                }
                .onFailure { error ->
                    updateState {
                        copy(
                            isLoading = false,
                            isRefreshing = false,
                            errorMessage = error.message ?: "Network error",
                        )
                    }
                }
        }
    }
}
```

---

## 6. UI Screen Architecture (`BaseScreen`)

Every Composable screen MUST be wrapped with `BaseScreen`.

### Key Behaviors of `BaseScreen`:
1. **Initial Loading**: Displays `BaseLoadingIndicator` ONLY if `safeData == null`.
2. **Initial Failure**: Displays `BaseErrorView` with Retry button ONLY if `safeData == null`.
3. **Background Reload / Refresh**: Retains existing UI content while showing pull-to-refresh spinner.
4. **Transient Error with Existing Cache**: Renders the screen normally with cached data and surfaces the error non-destructively as a floating 3D `ClayToast` at the top.
5. **Toast Host**: Automatically provides `LocalClayToastHost` via `CompositionLocalProvider`.

```kotlin
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(AppPaddings.ScreenHorizontal),
    viewModel: HomeViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val toastHost = rememberClayToastHostState()

    // Listen to one-off ViewModel effects
    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.ShowToast -> toastHost.showToast(effect.message, effect.type)
                is HomeEffect.NavigateToReview -> { /* Handle navigation */ }
            }
        }
    }

    BaseScreen(
        state = uiState,
        isRefreshing = uiState.isRefreshing,
        toastHostState = toastHost,
        onRefresh = { viewModel.dispatch(HomeIntent.Refresh) },
        onRetry = { viewModel.dispatch(HomeIntent.Refresh) },
        modifier = modifier.fillMaxSize(),
    ) { dashboard ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
        ) {
            HomeGreetingHeader(userName = dashboard.userName, streakDays = dashboard.streakDays)
            HomeOwlCoachCard(dueCount = dashboard.dueReviewsCount)
            ClayReviewBannerCard(
                title = "مرور واژگان",
                description = "الگوریتم تکرار هوشمند",
                onActionClick = { viewModel.dispatch(HomeIntent.StartReview) },
            )
        }
    }
}
```

---

## 7. Claymorphic Design System Rules

### 7.1. Design Tokens Only (No Magic Numbers)
All visual parameters MUST use tokens from `:core-view`:
- **Paddings**: `AppPaddings.ScreenHorizontal`, `AppPaddings.CardPadding`, `AppPaddings.ItemPadding`, `AppPaddings.BadgePaddingHorizontal`.
- **Spacings**: `Spacing.Tiny` (2dp), `Spacing.Small` (8dp), `Spacing.Medium` (16dp), `Spacing.Large` (24dp).
- **Corners**: `AppCorners.XS` (8dp), `AppCorners.Small` (12dp), `AppCorners.Medium` (16dp), `AppCorners.Large` (20dp), `AppCorners.XL` (24dp), `AppCorners.Pill` (9999dp). **NEVER use sharp corners (< 8dp).**

### 7.2. Theme Color Architecture (`ClayColors`)
Never write `if (ClayTheme.isDark)` or `if (isSystemInDarkTheme())` in UI components. Add semantic tokens to `ClayColors` instead:

```kotlin
data class ClayColors(
    val primary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val tertiary: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color,
    val error: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,
    val background: Color,
    val surface: Color,
    val surfaceSunken: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val highlightTop: Color,
    val shadowOuter: Color,
    val shadowInner: Color,
    val isDark: Boolean = false,
)
```

In UI code, consume only through `ClayTheme.colors`:
```kotlin
// RIGHT:
backgroundColor = ClayTheme.colors.primaryContainer
textColor = ClayTheme.colors.onPrimaryContainer
shadowColor = ClayTheme.colors.shadowOuter

// WRONG:
backgroundColor = if (ClayTheme.isDark) Color(0xFF272147) else Color(0xFFE8E2F8)
```

---

## 8. ClayToast Floating Notification System

`ClayToast` is a tactile, spring-animated 3D notification pill.

### Usage Options:
1. **Local State inside Composable**:
   ```kotlin
   val toastHost = rememberClayToastHostState()
   toastHost.showSuccess("با موفقیت ثبت شد!")
   toastHost.showError("خطا در برقراری ارتباط")
   toastHost.showWarning("اتصال اینترنت ضعیف است")
   toastHost.showInfo("نسخه جدید در دسترس است")
   ```
2. **Global Access via CompositionLocal**:
   ```kotlin
   val toastHost = LocalClayToastHost.current
   LaunchedEffect(Unit) {
       toastHost.showSuccess("خوش آمدید")
   }
   ```

---

## 9. Adaptive Layouts (Phone, Tablet, Landscape, Desktop)

Ensure full layout responsiveness across form factors:
- **Phone (Portrait)**: Floating `ClayBottomBar` + floating `ClayTopBar`.
- **Tablet / Landscape / Desktop**: Docked `ClaySidebar` or `ClayNavRail` on Start edge (Right in RTL, Left in LTR). TopBar and BottomBar adapt to avoid overlapping content.
- **Bi-directional (RTL / LTR)**: Layout automatically adapts to Persian (RTL) and English (LTR) using `LocalLayoutDirection`.

---

## 10. String Resources & Multi-Language Localization Rules

> [!CRITICAL]
> **STRICT PROHIBITION ON HARDCODED UI STRINGS:**
> NEVER hardcode any Persian, English, or localized string literals directly inside Composable functions or ViewModels.
> Every single user-visible text MUST be resolved through Compose Multiplatform StringResources (`Res.string.*`).

### 10.1. String Definition in All Locales
Every resource key added to `composeResources` MUST be defined across all supported locale bundles:
- `values/strings.xml` (Default locale fallback)
- `values-fa/strings.xml` (Persian RTL)
- `values-en/strings.xml` (English LTR)

### 10.2. Client-Side Errors via `UiText`
When errors are generated on the client (e.g. phone number validation, password length checks, offline warnings), **never** pass raw String literals from UseCases/ViewModels.
Use `UiText`:

```kotlin
sealed interface UiText {
    data class DynamicString(val value: String) : UiText
    class Resource(val res: StringResource, val args: List<Any> = emptyList()) : UiText

    @Composable
    fun asString(): String = when (this) {
        is DynamicString -> value
        is Resource -> if (args.isEmpty()) stringResource(res) else stringResource(res, *args.toTypedArray())
    }

    suspend fun asStringAsync(): String = when (this) {
        is DynamicString -> value
        is Resource -> if (args.isEmpty()) getString(res) else getString(res, *args.toTypedArray())
    }
}
```

In UseCases:
```kotlin
return PhoneValidationResult.Invalid(UiText.Resource(Res.string.auth_error_phone_invalid_prefix))
```

In Compose UI:
```kotlin
if (errorText != null) {
    Text(
        text = errorText.asString(),
        color = ClayTheme.colors.error,
        style = AppTypography.bodySmall,
    )
}
```

---

## 11. Domain UseCase Layer (`BaseUseCase`)

UseCases encapsulate business logic, input validation, and multi-repository orchestration between the ViewModel and Repository.

```kotlin
interface BaseUseCase<in Params, out ReturnType> {
    suspend operator fun invoke(params: Params): ReturnType
}

interface BaseNoParamsUseCase<out ReturnType> {
    suspend operator fun invoke(): ReturnType
}

interface BaseFlowUseCase<in Params, out ReturnType> {
    operator fun invoke(params: Params): Flow<ReturnType>
}
```

### When to Use a UseCase vs. Direct Repository:
- **USE a UseCase when**:
  - Validating user inputs (e.g. phone numbers, OTP lengths, credential formats) and producing localized `UiText` errors.
  - Combining, coordinating, or sequencing multiple repositories.
  - Applying non-trivial domain calculation or transformation (e.g. FSRS scheduling computations).
- **DO NOT create artificial UseCases when**:
  - The ViewModel is only performing a simple pass-through fetch from a repository (e.g. fetching a dashboard or profile without extra client business logic). Direct repository calls prevent unnecessary boilerplate.

---

## 12. Feature Development Checklist

Before completing any new KMP feature or screen, verify:

- [ ] Does the repository implement `BaseRepository<T>` with a unique `ResourceKey`?
- [ ] Does the state implement `LoadableUiState<T>` with `cachedData` support?
- [ ] Is the Screen wrapped in `BaseScreen` with `isRefreshing` and `onRefresh`?
- [ ] Are transient errors displayed via `ClayToast` without resetting UI content?
- [ ] Are ALL colors retrieved from `ClayTheme.colors` without any `if (isDark)` checks?
- [ ] Are ALL paddings and spacings using `Spacing.*` and `AppPaddings.*`?
- [ ] Are ALL corner radiuses using `AppCorners.*` (minimum 8dp)?
- [ ] Are ALL UI strings loaded via `stringResource(Res.string.*)` (ZERO hardcoded strings)?
- [ ] Are client-side validation errors modeled with `UiText.Resource` so language changes dynamically localize errors?
- [ ] Are OTP cells, PIN entries, and numeric progression fields strictly forced to Left-to-Right (LTR)?
- [ ] Are validation rules and business error logic cleanly encapsulated in `BaseUseCase`?
- [ ] Are base components in `:core-view` (like `ClayTextField`, `ClayButton`, `ClayOtpInputField`) generalized and reused directly WITHOUT creating disposable single-use feature wrappers (e.g. `ClayPhoneTextField`)?
- [ ] Are window insets and Scaffold modifiers in `App.kt` preserved without unwanted overrides?
- [ ] Does the feature module remain completely isolated from other feature modules?
- [ ] Does the project compile cleanly via `:androidApp:assembleDebug`?

