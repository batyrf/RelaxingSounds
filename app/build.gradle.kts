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

    defaultConfig {
        applicationId = "tm.mr.relaxingsounds"
        minSdkVersion(15)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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
    kapt("com.google.dagger:hilt-android-compiler:${Versions.hilt}")

    implementation("io.reactivex.rxjava2:rxandroid:${Versions.rxandroid}")
    implementation("io.reactivex.rxjava2:rxkotlin:${Versions.rxkotlin}")
    implementation("com.squareup.retrofit2:converter-gson:${Versions.converter_gson}")
    implementation("com.squareup.okhttp3:logging-interceptor:${Versions.logging_interceptor}")
    implementation("com.squareup.retrofit2:adapter-rxjava2:${Versions.adapter_rxjava2}")

    implementation("androidx.room:room-runtime:${Versions.room_version}")
    kapt("androidx.room:room-compiler:${Versions.room_version}")
    implementation("androidx.room:room-ktx:${Versions.room_version}")
    implementation("androidx.room:room-rxjava2:${Versions.room_version}")

    implementation("androidx.paging:paging-runtime:${Versions.paging_version}")
    implementation("androidx.paging:paging-rxjava2:${Versions.paging_version}")

    testImplementation("junit:junit:${Versions.junit}")
    androidTestImplementation("androidx.test.ext:junit:${Versions.androidx_junit}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.espresso_core}")

}