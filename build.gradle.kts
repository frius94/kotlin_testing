import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val junit_version: String by project
val mockk_version: String by project

plugins {
    kotlin("jvm") version "1.6.21"
    idea
    eclipse
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:$junit_version")
    testImplementation("io.mockk:mockk:$mockk_version")
}


tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}