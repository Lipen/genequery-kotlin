import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    application
    id("com.github.johnrengelman.shadow")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":core"))
    implementation("commons-cli:commons-cli:1.4")

    testImplementation("junit:junit:4.12")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnit()
}

application {
    mainClassName = "gq.console.MainKt"
}

tasks.shadowJar {
    archiveBaseName.set("gqcmd")
    archiveClassifier.set(null as String?)
    archiveVersion.set(null as String?)
}
