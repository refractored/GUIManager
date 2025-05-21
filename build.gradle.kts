plugins {
    java
    `java-library`
    `maven-publish`
    kotlin("jvm") version "2.1.0"
    id("com.gradleup.shadow") version "8.3.5"
    id("org.jlleitschuh.gradle.ktlint") version "12.2.0"
}

group = "net.refractored"
version = "1.4.1"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")
//    implementation(kotlin("stdlib", version = "2.1.0"))
    testCompileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")
}

java {
    withSourcesJar()
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
