plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}


android {
    compileSdkVersion(30)
    buildToolsVersion = "29.0.3"

    buildFeatures {
        dataBinding = true
    }

    defaultConfig {
        applicationId = "tm.mr.relaxingsounds"
        minSdkVersion(19)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")
    implementation("androidx.core:core-ktx:${Versions.core_ktx}")
    implementation("androidx.appcompat:appcompat:${Versions.appcompat}")
    implementation("androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}")
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.nav_version}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.nav_version}")

    implementation("com.google.dagger:hilt-android:${Versions.hilt}")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:${Versions.androidx_hilt}")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.hilt}")
    kapt("androidx.hilt:hilt-compiler:${Versions.androidx_hilt}")

    implementation("com.squareup.retrofit2:converter-gson:${Versions.converter_gson}")
    implementation("com.squareup.okhttp3:logging-interceptor:${Versions.logging_interceptor}")

    implementation("androidx.room:room-runtime:${Versions.room_version}")
    kapt("androidx.room:room-compiler:${Versions.room_version}")
    implementation("androidx.room:room-ktx:${Versions.room_version}")

    implementation("androidx.paging:paging-runtime:${Versions.paging_version}")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}")

    implementation(project(mapOf("path" to ":audioplayer")))

    testImplementation("junit:junit:${Versions.junit}")
    testImplementation("android.arch.core:core-testing:${Versions.core_testing}")
    testImplementation("io.mockk:mockk:${Versions.mockk}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}")

    androidTestImplementation("androidx.test.ext:junit:${Versions.androidx_junit}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.espresso_core}")

}