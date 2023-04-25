plugins {
    id("java")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withSourcesJar()
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/") // PaperMC Repository
}

dependencies {
    // Local Dependencies
    compileOnly(project(":abstract"))

    // Java Dependencies
    compileOnly("org.jetbrains:annotations:24.0.1")

    // Folia API
    val foliaVersion = findProperty("folia.version")
    compileOnly("dev.folia:folia-api:$foliaVersion")
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-Xlint:deprecation")
    }
}
