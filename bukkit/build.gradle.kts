plugins {
    id("java")
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
    compileOnly(project(":abstract"))

    // Java Dependencies
    compileOnly("org.jetbrains:annotations:24.0.1")

    // Spigot API
    val spigotVersion = findProperty("spigot.version")
    compileOnly("org.spigotmc:spigot-api:$spigotVersion")
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-Xlint:deprecation")
    }
}
