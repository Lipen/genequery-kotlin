import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.21"
    id("com.github.johnrengelman.shadow") version "5.0.0" apply false
}

allprojects {
    group = "ru.ifmo.genequery"
    version = "1.0-SNAPSHOT"
    repositories {
        jcenter()
        mavenCentral()
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.wrapper {
    gradleVersion = "5.3.1"
    distributionType = Wrapper.DistributionType.ALL
}
