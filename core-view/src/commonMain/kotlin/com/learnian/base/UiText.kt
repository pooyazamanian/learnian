package com.learnian.base

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

/**
 * Universal localized string wrapper for KMP.
 *
 * Allows UseCases, Repositories, and ViewModels to produce localized messages
 * (e.g. client-side validation errors) without leaking UI/Context dependencies.
 * Automatically resolves to the user's selected language in Compose UI or Coroutine scopes.
 */
sealed interface UiText {
    data class DynamicString(val value: String) : UiText

    class Resource(
        val res: StringResource,
        val args: List<Any> = emptyList(),
    ) : UiText {
        constructor(res: StringResource, vararg args: Any) : this(res, args.toList())
    }

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
