package com.learnian.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
