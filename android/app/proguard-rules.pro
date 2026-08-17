# ProGuard / R8 rules for DIVO777 release builds.
# minify is currently disabled in build.gradle.kts; these rules are kept for
# future enablement and to satisfy the proguardFiles reference.

# Kotlin serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.**
-keepclassmembers class **$$serializer { *; }
-keepclasseswithmembers class * {
    kotlinx.serialization.KSerializer serializer(...);
}

# Ktor
-dontwarn io.ktor.**
-keep class io.ktor.** { *; }

# Coil
-dontwarn coil.**

# Koin
-keep class org.koin.** { *; }

# Keep Compose runtime
-keep class androidx.compose.** { *; }
