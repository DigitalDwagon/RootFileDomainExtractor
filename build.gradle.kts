import kotlin.text.Charsets.UTF_8

plugins {
    `java-library`
    `maven-publish`
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"

}

group = "dev.digitaldragon"
version = "1.0-SNAPSHOT"
description = "RootFileDomainExtractor"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    compileJava {
        options.encoding = UTF_8.name()

        // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
        // See https://openjdk.java.net/jeps/247 for more information.
        options.release.set(17)
    }
    shadowJar {
        archiveFileName.set("RootFileDomainExtractor-shadow.jar")
        manifest.attributes["Main-Class"] = "dev.digitaldragon.RootFileDomainExtractor"
    }
    javadoc {
        options.encoding = UTF_8.name() // We want UTF-8 for everything
    }
    processResources {
        filteringCharset = UTF_8.name() // We want UTF-8 for everything
    }
}