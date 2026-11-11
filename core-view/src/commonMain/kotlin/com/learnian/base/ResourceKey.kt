package com.learnian.base

/**
 * Type-safe unique identifier for a domain resource managed by a repository.
 * Used for identifying resources across future caching layers (Memory Registry, Database, Last-Modified DataStore).
 *
 * @property value The unique string identifier for the resource (e.g. "services", "news", "profile").
 */
data class ResourceKey(val value: String) {
    init {
        require(value.isNotBlank()) { "ResourceKey value must not be blank" }
    }

    override fun toString(): String = value
}
