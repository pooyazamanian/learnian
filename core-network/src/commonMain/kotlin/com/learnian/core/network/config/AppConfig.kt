package com.learnian.core.network.config

enum class NetworkEnvironment {
    TEST,
    FINAL
}

object AppConfig {
    var environment: NetworkEnvironment = NetworkEnvironment.TEST

    const val FINAL_URL_API = "https://api.learnian.com/api/"
    const val TEST_URL_API = "https://api-test.learnian.com/api/"

    const val BASE_URL_ICON = "https://learnian.com/"
    const val BASE_URL = "https://learnian.com"

    val currentBaseUrl: String
        get() = when (environment) {
            NetworkEnvironment.TEST -> TEST_URL_API
            NetworkEnvironment.FINAL -> FINAL_URL_API
        }
}
