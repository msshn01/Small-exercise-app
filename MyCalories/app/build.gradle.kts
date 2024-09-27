plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.mycalories"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mycalories"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)



    val room_version = "2.6.1"

    implementation ("androidx.room:room-runtime:$room_version")
    annotationProcessor ("androidx.room:room-compiler:$room_version")







    // optional - RxJava3 support for Room
    implementation("androidx.room:room-rxjava3:$room_version")




    // optional - Paging 3 Integration
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.0")


    val lottieVersion = "3.4.0"
    implementation ("com.airbnb.android:lottie:$lottieVersion")


    



}