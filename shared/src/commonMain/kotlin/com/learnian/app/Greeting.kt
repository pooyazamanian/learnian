package com.learnian.app

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Welcome to Learnian on ${platform.name}!"
    }
}
