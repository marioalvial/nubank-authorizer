plugins {
    application
    kotlin("jvm") version "1.4.10"
    id("jacoco")
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("io.gitlab.arturbosch.detekt") version "1.14.2"
    id("org.jmailen.kotlinter") version "2.1.2"
    id("com.adarshr.test-logger") version "2.0.0"
}

repositories {
    jcenter()
    mavenCentral()
}

val jacksonVersion by extra { "2.10.3" }

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.google.guava:guava:29.0-jre")

    // Serialization
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    // Mock Framework
    testImplementation("io.mockk:mockk:1.10.2")

    // Assert Framework
    testImplementation("org.assertj:assertj-core:3.15.0")

    //Test Engine
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.1")
    testImplementation("org.testcontainers:junit-jupiter:1.12.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.1")
}

tasks {
    assemble { dependsOn(shadowJar) }
    assemble { dependsOn(formatKotlin) }

    application {
        mainClassName = "nubank.authorizer.ApplicationKt"
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    detekt {
        failFast = true
        buildUponDefaultConfig = true
        input = files("src/main/kotlin", "src/test/kotlin")
        config = files("$projectDir/detekt/config.yml")

        reports {
            xml.enabled = true
            html.enabled = false
            txt.enabled = false
        }
    }

    jacocoTestReport {
        reports {
            xml.isEnabled = true
            csv.isEnabled = false
        }
    }

    testlogger {
        showStackTraces = false
        showCauses = false
        showSimpleNames = true
        showExceptions = true
        showPassed = true
        showStandardStreams = false
    }

    test { useJUnitPlatform() }
}
