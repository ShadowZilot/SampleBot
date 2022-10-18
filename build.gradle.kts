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

//val sourcesJar by tasks.registering(Jar::class) {
//    classifier = "sources"
//    from(sourceSets.main.get().allSource)
//}

publishing {
    publications {

        register("mavenJava", MavenPublication::class.java) {

            groupId = "io.github.shadowzilot"
            artifactId = "kbotlib"
            version = "1.0.0"
            from(project.components["java"])
            //artifact(sourcesJar.get())

            pom {
                name.value("KBotLib")
                description.value("KBotLib is a library that allows to create telegram bot on Kotlin programming language")
                url.value("https://github.com/ShadowZilot/SampleBot")
                licenses {
                    license {
                        name.value("The Apache License, Version 2.0")
                        url.value("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.value("ShadowZilot")
                        name.value("Egor Ponomarev")
                        email.value("humanvectoringsoft@gmail.com")
                    }
                }

                scm {
                    connection.value("scm:git:git:github.com/ShadowZilot/SampleBot.git")
                    developerConnection.value("scm:git:ssh://github.com/ShadowZilot/SampleBot.git")
                    url.value("https://github.com/ShadowZilot/SampleBot")
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
        }
    }
}

signing {
    sign(publishing.publications.getByName("mavenJava"))
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

//javadoc {
//    if(JavaVersion.current().isJava9Compatible()) {
//        options.addBooleanOption('html5', true)
//    }
//}

application {
    mainClass.set("MainKt")
}