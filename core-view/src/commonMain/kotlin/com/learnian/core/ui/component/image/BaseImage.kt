package com.learnian.core.ui.component.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.jetbrains.compose.resources.painterResource

/**
 * BaseImage is the foundational image component for the Learnian Design System.
 *
 * ALL image displays across the application MUST use BaseImage.
 * This guarantees uniform scaling, placeholder rendering, accessibility,
 * and enables global styling and image caching rules from a single entry point.
 */
@Composable
fun BaseImage(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
    )
}

@Composable
fun BaseImage(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
) {
    Image(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
    )
}

@Composable
fun BaseImage(
    resource: DrawableResource,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
) {
    Image(
        painter = painterResource(resource),
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
    )
}

/**
 * BaseImage overload supporting network URL with fallback DrawableResource.
 *
 * If [url] is provided, attempts to asynchronously fetch and decode the remote image.
 * If [url] is null, empty, or fails to load, gracefully falls back to [fallbackResource].
 */
@Composable
fun BaseImage(
    url: String?,
    fallbackResource: DrawableResource,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
) {
    var loadedBitmap by remember(url) { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(url) {
        if (!url.isNullOrBlank()) {
            try {
                val bytes = withContext(Dispatchers.Default) {
                    val client = HttpClient()
                    try {
                        client.get(url).body<ByteArray>()
                    } finally {
                        client.close()
                    }
                }
                loadedBitmap = bytes.decodeToImageBitmap()
            } catch (_: Exception) {
                loadedBitmap = null
            }
        } else {
            loadedBitmap = null
        }
    }

    val bitmap = loadedBitmap
    if (bitmap != null) {
        Image(
            bitmap = bitmap,
            contentDescription = contentDescription,
            modifier = modifier,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter,
        )
    } else {
        BaseImage(
            resource = fallbackResource,
            contentDescription = contentDescription,
            modifier = modifier,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter,
        )
    }
}
