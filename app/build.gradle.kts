plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.androidx.room)
}

android {
    namespace = "com.example.CoingeckoAPI"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.CoingeckoAPI"
        minSdk = 29
        targetSdk = 37
        versionCode = 38
        versionName = "37.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    // 1. Base Platform & Android Extensions (Duplicates cleaned up)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // 2. Visual Extended Icons & Core Navigation
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)

    // 3. Clean Setup via Your Custom Bundles
    implementation(libs.bundles.compose)
    implementation(libs.bundles.room)
    implementation(libs.bundles.hilt)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.coil)

    // 4. Annotation Processors via KSP
    ksp(libs.hilt.compiler)
    ksp(libs.androidx.room.compiler)

    // 5. Unit Testing Frameworks
    testImplementation(libs.bundles.unitTest)
    testImplementation(libs.room.testing)

    // 6. UI Instrumented Testing & Debugging Tools (Duplicates cleaned up)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // 7. DataStore
    implementation(libs.bundles.datastore)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    jvmArgs("-XX:+EnableDynamicAgentLoading")

}