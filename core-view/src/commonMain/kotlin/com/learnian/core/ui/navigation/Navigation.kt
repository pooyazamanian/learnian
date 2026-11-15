package com.learnian.core.ui.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
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
 * 1 - MainApp (Nested: Home, Services, Wallet, Profile, Settings)
 * 2 - Intro (Nested: Welcome, Features, GetStarted)
 * 3 - Auth (Nested: PhoneInput, OtpVerify, Register)
 */
sealed class AppRoute {
    // 0: Splash Screen
    object Splash : AppRoute()

    // 1: Main App Nested Graph
    sealed class MainApp(val routeName: String, val title: String) : AppRoute() {
        object Home : MainApp("main_home", "خانه")
        object Map : MainApp("main_map", "نقشه")
        object Services : MainApp("main_services", "یادگیری")
        object Wallet : MainApp("main_wallet", "مرور")
        object Profile : MainApp("main_profile", "آمار")
        object Settings : MainApp("main_settings", "پروفایل")
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
 * Polished transition specifications for root and non-bottom-bar screen transitions.
 * - Splash -> Main/Intro/Auth: Cinematic fade & scale reveal.
 * - Auth: Bottom modal sheet entry/exit.
 * - Hierarchical Push/Pop: Smooth parallax slide with subtle scale & fade.
 */
val appRouteTransitionSpec: AnimatedContentTransitionScope<AppRoute>.() -> ContentTransform = {
    when {
        initialState is AppRoute.Splash -> {
            // Splash reveal: cinematic fade & scale
            (fadeIn(animationSpec = tween(450, delayMillis = 80, easing = LinearOutSlowInEasing)) +
                    scaleIn(initialScale = 0.93f, animationSpec = tween(450, delayMillis = 80, easing = LinearOutSlowInEasing)))
                .togetherWith(
                    fadeOut(animationSpec = tween(350)) +
                            scaleOut(targetScale = 1.05f, animationSpec = tween(350)),
                )
        }
        (targetState is AppRoute.Auth) || (targetState is AppRoute.Intro && initialState !is AppRoute.Intro) -> {
            // Modal entry
            (slideInVertically(animationSpec = tween(420, easing = FastOutSlowInEasing)) { height -> (height * 0.35f).toInt() } +
                    fadeIn(animationSpec = tween(320)) +
                    scaleIn(initialScale = 0.95f, animationSpec = tween(420, easing = FastOutSlowInEasing)))
                .togetherWith(
                    fadeOut(animationSpec = tween(280)) +
                            scaleOut(targetScale = 0.94f, animationSpec = tween(380))
                )
        }
        initialState is AppRoute.Auth && targetState !is AppRoute.Auth -> {
            // Modal exit
            (fadeIn(animationSpec = tween(320)) +
                    scaleIn(initialScale = 0.94f, animationSpec = tween(350)))
                .togetherWith(
                    slideOutVertically(animationSpec = tween(380, easing = FastOutSlowInEasing)) { height -> (height * 0.35f).toInt() } +
                            fadeOut(animationSpec = tween(280))
                )
        }
        else -> {
            // Modern Parallax Slide + Fade + Scale (Push / Pop)
            (slideInHorizontally(animationSpec = tween(380, easing = FastOutSlowInEasing)) { width -> (width * 0.45f).toInt() } +
                    fadeIn(animationSpec = tween(300)) +
                    scaleIn(initialScale = 0.96f, animationSpec = tween(380, easing = FastOutSlowInEasing)))
                .togetherWith(
                    slideOutHorizontally(animationSpec = tween(380, easing = FastOutSlowInEasing)) { width -> -(width * 0.25f).toInt() } +
                            fadeOut(animationSpec = tween(280)) +
                            scaleOut(targetScale = 0.94f, animationSpec = tween(380, easing = FastOutSlowInEasing))
                )
        }
    }
}

/**
 * Animated Content Container for screen transitions
 */
@Composable
fun <T> AnimatedNavHost(
    currentRoute: T,
    modifier: Modifier = Modifier,
    transitionSpec: (AnimatedContentTransitionScope<T>.() -> ContentTransform)? = null,
    content: @Composable (T) -> Unit
) {
    val defaultSpec: AnimatedContentTransitionScope<T>.() -> ContentTransform = {
        (slideInHorizontally(animationSpec = tween(380, easing = FastOutSlowInEasing)) { width -> (width * 0.4f).toInt() } +
                fadeIn(animationSpec = tween(300)) +
                scaleIn(initialScale = 0.96f, animationSpec = tween(380, easing = FastOutSlowInEasing)))
            .togetherWith(
                slideOutHorizontally(animationSpec = tween(380, easing = FastOutSlowInEasing)) { width -> -(width * 0.25f).toInt() } +
                        fadeOut(animationSpec = tween(280)) +
                        scaleOut(targetScale = 0.95f, animationSpec = tween(380, easing = FastOutSlowInEasing))
            )
    }

    AnimatedContent(
        targetState = currentRoute,
        modifier = modifier,
        transitionSpec = transitionSpec ?: defaultSpec,
        label = "NavTransition"
    ) { targetRoute ->
        content(targetRoute)
    }
}

