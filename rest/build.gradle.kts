import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.springframework.boot") version "2.1.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.7.RELEASE"
}

configurations {
    implementation.get().exclude(module = "spring-boot-starter-tomcat")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":core"))
    implementation("org.springframework.boot:spring-boot-starter-web:2.1.4.RELEASE")
    implementation("org.springframework.boot:spring-boot-configuration-processor:2.1.4.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-jetty:2.1.4.RELEASE")
    implementation("org.apache.logging.log4j:log4j-api:2.11.2")
    implementation("org.apache.logging.log4j:log4j-core:2.11.2")

    testImplementation("junit:junit:4.12")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.jayway.jsonpath:json-path:0.8.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnit()
}

springBoot {
    mainClassName = "gq.rest.Application"
}

tasks.jar {
    archiveBaseName.set("gq-rest")
}
