plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.apollo)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.anilist.aniexplorer.graphql"
    compileSdk = 35

    defaultConfig {
        minSdk = 35
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        buildConfig = true
    }

    flavorDimensions += "environment"
    productFlavors {
        create("development") {
            dimension = "environment"
            buildConfigField("boolean", "IS_MOCK", "true")
            buildConfigField("boolean", "SHOULD_MOCK_HTTP_ERROR", "false")
            buildConfigField("boolean", "SHOULD_MOCK_NETWORK_EXCEPTION", "false")
        }
        create("production") {
            dimension = "environment"
            buildConfigField("boolean", "IS_MOCK", "false")
            buildConfigField("boolean", "SHOULD_MOCK_HTTP_ERROR", "false")
            buildConfigField("boolean", "SHOULD_MOCK_NETWORK_EXCEPTION", "false")
        }
    }
}

kotlin {
    jvmToolchain(17)
}

apollo {
    service("service") {
        packageName.set("com.anilist.aniexplorer.graphql")
        introspection {
            endpointUrl.set("https://graphql.anilist.co")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}

dependencies {
    implementation(libs.apollo.runtime)
    implementation(libs.apollo.normalized.cache)
    implementation(libs.apollo.normalized.cache.sqlite)
    implementation(libs.apollo.mockserver)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    implementation(libs.kotlinx.coroutines.core)
}
