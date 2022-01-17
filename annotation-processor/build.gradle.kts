plugins {
    kotlin("jvm")
    kotlin("kapt")
}

repositories {
    mavenCentral()

}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    kapt("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    annotationProcessor("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation((project(":nlu")))
}