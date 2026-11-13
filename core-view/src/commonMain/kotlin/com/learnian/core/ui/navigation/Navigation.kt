package com.learnian.core.ui.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

/**
 * Unified App Routes & Nested Graphs connected to rootNavController:
 * 0 - Splash
 * 1 - MainApp (Nested: Map, Services, Wallet, Profile, Settings)
 * 2 - Intro (Nested: Welcome, Features, GetStarted)
 * 3 - Auth (Nested: PhoneInput, OtpVerify, Register)
 * 4 - MainLiteApp (Nested: LiteHome, LiteServices, LiteSettings)
 */
sealed class AppRoute {
    // 0: Splash Screen
    object Splash : AppRoute()

    // 1: Main App Nested Graph
    sealed class MainApp(val routeName: String, val title: String) : AppRoute() {
        object Home : MainApp("main_home", "خانه")
        object Map : MainApp("main_map", "نقشه")
        object Services : MainApp("main_services", "خدمات شهری")
        object Wallet : MainApp("main_wallet", "کیف پول")
        object Profile : MainApp("main_profile", "پروفایل")
        object Settings : MainApp("main_settings", "تنظیمات")
    }

    // 2: Intro Nested Graph
    sealed class Intro(val routeName: String) : AppRoute() {
        object Welcome : Intro("intro_welcome")
        object Features : Intro("intro_features")
        object GetStarted : Intro("intro_get_started")
    }

    // 3: Auth Nested Graph
    sealed class Auth(val routeName: String) : AppRoute() {
        object PhoneInput : Auth("auth_phone")
        object OtpVerify : Auth("auth_otp")
        object Register : Auth("auth_register")
    }

}

val AppRoute.isSplash: Boolean get() = this is AppRoute.Splash
val AppRoute.isMainApp: Boolean get() = this is AppRoute.MainApp
val AppRoute.isIntro: Boolean get() = this is AppRoute.Intro
val AppRoute.isAuth: Boolean get() = this is AppRoute.Auth

/**
 * Type-Safe State-Driven NavController for Compose Multiplatform
 */
@Stable
class NavController<T>(initialRoute: T) {
    private val backStack = mutableStateListOf<T>(initialRoute)

    var currentRoute by mutableStateOf(initialRoute)
        private set

    val stack: List<T>
        get() = backStack.toList()

    fun navigate(destination: T) {
        backStack.add(destination)
        currentRoute = destination
    }

    fun pop(): Boolean {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
            currentRoute = backStack.last()
            return true
        }
        return false
    }

    fun replace(destination: T) {
        if (backStack.isNotEmpty()) {
            backStack.removeAt(backStack.lastIndex)
        }
        backStack.add(destination)
        currentRoute = destination
    }

    fun popToRoot(destination: T) {
        backStack.clear()
        backStack.add(destination)
        currentRoute = destination
    }
}

@Composable
fun <T> rememberNavController(initialRoute: T): NavController<T> {
    return remember { NavController(initialRoute) }
}

/**
 * Animated Content Container for screen transitions
 */
@Composable
fun <T> AnimatedNavHost(
    currentRoute: T,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit
) {
    AnimatedContent(
        targetState = currentRoute,
        modifier = modifier,
        transitionSpec = {
            (slideInHorizontally { width -> width } + fadeIn()) togetherWith
                    (slideOutHorizontally { width -> -width } + fadeOut())
        },
        label = "NavTransition"
    ) { targetRoute ->
        content(targetRoute)
    }
}
