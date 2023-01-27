plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}


repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    mavenLocal()
}

dependencies {
    // This dependency is used by the application.
    implementation("com.google.guava:guava:31.1-jre")
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT") // The Spigot API with no shadowing. Requires the OSS repo.
    // compileOnly("io.papermc.paper:paper-api:1.8.8-R0.1-SNAPSHOT")
}


application {
    // Define the main class for the application.
    mainClass.set("me.nixuge.BlockSumo")
}
