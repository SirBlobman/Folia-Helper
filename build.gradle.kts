import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

version = fetchProperty("version.api", "invalid")
val mavenUsername = fetchEnv("MAVEN_DEPLOY_USR", "mavenUsernameSirBlobman", "")
val mavenPassword = fetchEnv("MAVEN_DEPLOY_PSW", "mavenPasswordSirBlobman", "")

fun fetchProperty(propertyName: String, defaultValue: String): String {
    val found = findProperty(propertyName)
    if (found != null) {
        return found.toString()
    }

    return defaultValue
}

fun fetchEnv(envName: String, propertyName: String?, defaultValue: String): String {
    val found = System.getenv(envName)
    if (found != null) {
        return found
    }

    if (propertyName != null) {
        return fetchProperty(propertyName, defaultValue)
    }

    return defaultValue
}

plugins {
    id("java")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") // SpigotMC Repository
    maven("https://oss.sonatype.org/content/repositories/snapshots/") // OSS Sonatype Snapshots
}

dependencies {
    // Local Dependencies
    implementation(project(path = ":abstract", configuration = "archives"))
    implementation(project(path = ":bukkit", configuration = "archives"))
    implementation(project(path = ":folia", configuration = "archives"))

    // Java Dependencies
    compileOnly("org.jetbrains:annotations:24.0.1")

    // Spigot API
    val spigotVersion = findProperty("spigot.version")
    compileOnly("org.spigotmc:spigot-api:$spigotVersion")
}

publishing {
    repositories {
        maven("https://nexus.sirblobman.xyz/public/") {
            credentials {
                username = mavenUsername
                password = mavenPassword
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.sirblobman.api"
            artifactId = "folia-helper"
            artifact(tasks["shadowJar"])
            artifact(tasks["sourcesJar"])
        }
    }
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-Xlint:deprecation")
    }

    withType<Javadoc> {
        val standardOptions = options as StandardJavadocDocletOptions
        standardOptions.addStringOption("Xdoclint:none", "-quiet")
    }

    named("jar") {
        enabled = false
    }

    named<Jar>("sourcesJar") {
        archiveBaseName.set("Folia-Helper")
        from(files("abstract/src/main/java"))
    }

    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("Folia-Helper")
        archiveClassifier.set(null as String?)
    }

    named("build") {
        dependsOn("shadowJar")
    }
}
