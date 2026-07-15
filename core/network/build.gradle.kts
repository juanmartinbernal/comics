plugins {
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    api(libs.retrofit.core)
    api(libs.retrofit.converter.moshi)
    api(libs.moshi)
    api(libs.moshi.kotlin)
    api(libs.okhttp.core)
    implementation(libs.okhttp.logging.interceptor)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.junit)
}
