plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("org.jetbrains.kotlin.plugin.allopen")
}

dependencies {
    implementation("org.yaml:snakeyaml:1.30")
    implementation("io.micronaut:micronaut-http-client:3.2.4")
    implementation("io.micronaut:micronaut-inject-java:3.2.4")
    kapt("io.micronaut:micronaut-inject-java:3.2.4")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
}