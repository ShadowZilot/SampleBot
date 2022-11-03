import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins {
    kotlin("jvm") version "1.7.10"
    application
    `java-library`
    `maven-publish`
    signing
    groovy
    id("com.gradle.plugin-publish") version "0.9.5"
}

apply(plugin = "java-gradle-plugin")
apply(plugin = "maven-publish")
apply(plugin = "groovy")

group = "io.github.shadowzilot"
version = "1.0"

repositories {
    mavenCentral()
    maven {
        url = URI.create("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {

        create<MavenPublication>("mavenJava") {

            groupId = "io.github.shadowzilot"
            artifactId = "kbotlib"
            version = "1.0.0"
            from(project.components["java"])

            pom {
                name.set("KBotLib")
                description.set("KBotLib is a library that allows to create telegram bot on Kotlin programming language")
                url.set("https://github.com/ShadowZilot/SampleBot")
                inceptionYear.set("2022")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("ShadowZilot")
                        name.set("Egor Ponomarev")
                        email.set("humanvectoringsoft@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:git:github.com/ShadowZilot/SampleBot.git")
                    developerConnection.set("scm:git:ssh://github.com/ShadowZilot/SampleBot.git")
                    url.set("https://github.com/ShadowZilot/SampleBot")
                }
            }
        }
    }

    repositories {
        maven {
            name = "OSSRH"
            url = URI.create("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = project.properties["ossrhUsername"] as String?
                password = project.properties["ossrhPassword"] as String?
            }
            credentials(PasswordCredentials::class)
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("org.json:json:20220320")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    implementation("com.opencsv:opencsv:5.6")
    implementation("commons-codec:commons-codec:1.15")
    implementation("mysql:mysql-connector-java:8.0.30")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}