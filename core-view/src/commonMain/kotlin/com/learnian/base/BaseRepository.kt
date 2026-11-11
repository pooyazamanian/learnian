package com.learnian.base

/**
 * Generic BaseRepository infrastructure abstraction for data resources.
 *
 * Designed as a foundation for future data synchronization capabilities
 * (Memory Registry, Database persistence, Last-Modified DataStore, HTTP 304).
 *
 * @param T The domain/data payload type managed by this repository.
 */
interface BaseRepository<T> {

    /**
     * The unique [ResourceKey] identifying the domain resource owned by this repository
     * (e.g. [ResourceKey]("services"), [ResourceKey]("news")).
     */
    val resourceKey: ResourceKey

    /**
     * Optional extension hook to process, map, or normalize API data before database persistence.
     *
     * Default behavior is identity: value -> value.
     *
     * @param data The raw or incoming data payload.
     * @return The processed data ready for persistence.
     */
    suspend fun processBeforeDatabaseSave(data: T): T = data

    /**
     * Optional extension hook to process or format Last-Modified metadata string before DataStore persistence.
     *
     * Default behavior is identity: value -> value.
     *
     * @param lastModified The raw Last-Modified header or timestamp.
     * @return The processed Last-Modified string ready for persistence.
     */
    suspend fun processBeforeLastModifiedSave(lastModified: String): String = lastModified
}
