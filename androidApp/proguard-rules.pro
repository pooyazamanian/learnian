# Project-Specific R8 / ProGuard Keep Rules for Learnian

# ------------------------------------------------------------------------------
# General Attributes & Optimization Settings
# ------------------------------------------------------------------------------
-keepattributes *Annotation*, Signature, InnerClasses, EnclosingMethod, SourceFile, LineNumberTable
-keepattributes RuntimeVisibleAnnotations, RuntimeInvisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations, RuntimeInvisibleParameterAnnotations
-renamesourcefileattribute SourceFile

# ------------------------------------------------------------------------------
# App Data Models, DTOs & Domain Objects
# ------------------------------------------------------------------------------
-keep class com.learnian.**.dto.** { *; }
-keep class com.learnian.**.model.** { *; }
-keep class com.learnian.core.database.** { *; }

# ------------------------------------------------------------------------------
# Kotlin & Coroutines
# ------------------------------------------------------------------------------
-keepclassmembers class * {
    @kotlin.jvm.Volatile <fields>;
}
-keep class kotlinx.coroutines.android.AndroidDispatcherFactory { *; }
-keep class kotlinx.coroutines.CoroutineExceptionHandler { *; }
-keepclassmembers class * extends kotlinx.coroutines.internal.MainDispatcherFactory { *; }

# ------------------------------------------------------------------------------
# Kotlinx Serialization
# ------------------------------------------------------------------------------
-keepclassmembers class * {
    @kotlinx.serialization.SerialName <fields>;
    @kotlinx.serialization.Serializable <fields>;
}
-keepclassmembers class *$$serializer { *; }
-keepclassmembers class * {
    *** Companion;
}
-keepclassmembers class *$Companion {
    *** serializer(...);
}

# ------------------------------------------------------------------------------
# Ktor Client & OkHttp (Network)
# ------------------------------------------------------------------------------
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**
-dontwarn io.netty.**
-dontwarn org.slf4j.**

-keepattributes Signature, Annotation
-keep class **.PublicSuffixDatabase { *; }
-dontwarn okhttp3.internal.platform.**
-dontwarn okhttp3.internal.publicsuffix.**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

# ------------------------------------------------------------------------------
# Koin (Dependency Injection)
# ------------------------------------------------------------------------------
-keep class io.insertkoin.** { *; }
-keepclassmembers class * {
    @io.insertkoin.** *;
}
-dontwarn io.insertkoin.**

# ------------------------------------------------------------------------------
# SQLDelight (Database Driver & Generated Queries)
# ------------------------------------------------------------------------------
-keep class app.cash.sqldelight.** { *; }
-keepclassmembers class * implements **.ColumnAdapter { *; }
-keep class **.AppDatabaseImpl { *; }
-keep class **.AppDatabase { *; }

# ------------------------------------------------------------------------------
# Multiplatform Settings & AndroidX Security Crypto
# ------------------------------------------------------------------------------
-keep class androidx.security.crypto.** { *; }
-dontwarn androidx.security.crypto.**
-keep class com.russhwolf.settings.** { *; }

# ------------------------------------------------------------------------------
# Jetpack Compose / Compose Multiplatform
# ------------------------------------------------------------------------------
-keepclassmembers class * {
    @androidx.compose.runtime.Composable *;
}
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**
