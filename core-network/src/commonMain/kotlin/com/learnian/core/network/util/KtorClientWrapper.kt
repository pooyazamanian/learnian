package com.learnian.core.network.util

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorClientWrapper(val client: HttpClient) {

    suspend inline fun <reified T> get(
        urlString: String,
        params: Map<String, Any?> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): Result<T> = safeApiCall {
        client.get(urlString) {
            headers {
                headers.forEach { (key, value) -> append(key, value) }
            }
            params.forEach { (key, value) ->
                value?.let { parameter(key, it) }
            }
        }.body()
    }

    suspend inline fun <reified T> post(
        urlString: String,
        body: Any? = null,
        params: Map<String, Any?> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): Result<T> = safeApiCall {
        client.post(urlString) {
            contentType(ContentType.Application.Json)
            headers {
                headers.forEach { (key, value) -> append(key, value) }
            }
            params.forEach { (key, value) ->
                value?.let { parameter(key, it) }
            }
            body?.let { setBody(it) }
        }.body()
    }

    suspend inline fun <reified T> put(
        urlString: String,
        body: Any? = null,
        params: Map<String, Any?> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): Result<T> = safeApiCall {
        client.put(urlString) {
            contentType(ContentType.Application.Json)
            headers {
                headers.forEach { (key, value) -> append(key, value) }
            }
            params.forEach { (key, value) ->
                value?.let { parameter(key, it) }
            }
            body?.let { setBody(it) }
        }.body()
    }

    suspend inline fun <reified T> delete(
        urlString: String,
        params: Map<String, Any?> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): Result<T> = safeApiCall {
        client.delete(urlString) {
            headers {
                headers.forEach { (key, value) -> append(key, value) }
            }
            params.forEach { (key, value) ->
                value?.let { parameter(key, it) }
            }
        }.body()
    }

    suspend inline fun <T> safeApiCall(block: () -> T): Result<T> {
        return try {
            Result.Success(block())
        } catch (e: Exception) {
            Result.Error(e.message ?: "An unexpected error occurred", e)
        }
    }
}
