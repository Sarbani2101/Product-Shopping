plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.productshopping"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.productshopping"
        minSdk = 24
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //noinspection UseTomlInstead
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    //noinspection UseTomlInstead
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    //noinspection UseTomlInstead
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
    //noinspection UseTomlInstead
    implementation ("com.github.bumptech.glide:glide:4.15.1")

    //noinspection UseTomlInstead
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    //noinspection GradleDependency,UseTomlInstead
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    //noinspection UseTomlInstead,GradleDependency
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    //noinspection UseTomlInstead,GradleDependency
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    //noinspection UseTomlInstead,GradleDependency
    implementation ("com.google.android.material:material:1.9.0")
    //noinspection GradleDependency,UseTomlInstead
    implementation ("com.google.code.gson:gson:2.8.8")
}