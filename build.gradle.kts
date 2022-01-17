import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage

plugins {
    kotlin("jvm") version "1.5.10"
    kotlin("kapt") version "1.6.10"
    kotlin("plugin.serialization") version "1.5.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("io.micronaut.application") version "1.5.0"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.4.32"
}

group = "com.arya"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.arya.server.ApplicationKt")
}

dependencies {
    implementation("io.micronaut:micronaut-core:3.2.4")
    implementation("io.micronaut:micronaut-http:3.2.4")
    implementation("io.micronaut:micronaut-http-server:3.2.4")
    implementation("io.micronaut:micronaut-runtime:3.2.4")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime:3.0.0")
    implementation(project(":nlu"))
    kapt(project(":nlu"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    kapt("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    annotationProcessor("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation(project("annotation-processor"))
    implementation("net.java.dev.jna:jna:5.9.0")
    implementation("com.alphacephei:vosk:0.3.33")
    implementation("ws.schild:jave-all-deps:3.2.0")
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")

    runtimeOnly("io.micronaut:micronaut-http-server-netty:3.2.4")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.0")
    implementation("com.lordcodes.turtle:turtle:0.6.0")
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    repositories {
        mavenCentral()

    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.0")
    }
}

tasks.create("buildNLUImage", DockerBuildImage::class.java){
    dependsOn("build")
    inputDir.set(file("./rasa"))
    images.add("arya/nlu:v0.0.1")
}

tasks.create("buildAryaBot", DockerBuildImage::class.java){
    dependsOn(":frontend:buildFrontend")
    inputDir.set(file("./"))
    images.add("arya/bot:v0.0.1")
}

tasks {
    named<DockerBuildImage>("buildAryaBot") {
        dependsOn("buildNLUImage")
    }
}