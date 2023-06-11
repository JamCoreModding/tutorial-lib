plugins {
    id("fabric-loom") version "1.2-SNAPSHOT"
    id("io.github.juuxel.loom-quiltflower") version "1.+"
    id("io.github.p03w.machete") version "1.+"
    id("org.cadixdev.licenser") version "0.6.+"
}

apply(from = "https://raw.githubusercontent.com/JamCoreModding/Gronk/main/publishing.gradle.kts")
apply(from = "https://raw.githubusercontent.com/JamCoreModding/Gronk/main/misc.gradle.kts")

val mod_version: String by project

group = "io.github.jamalam360"

version = mod_version

repositories {
    val mavenUrls =
        mapOf(
            Pair("https://maven.terraformersmc.com/releases", listOf("com.terraformersmc")),
            Pair("https://api.modrinth.com/maven", listOf("maven.modrinth")),
            Pair("https://maven.jamalam.tech/releases", listOf("io.github.jamalam360")),
            Pair("https://maven.quiltmc.org/repository/release", listOf("org.quiltmc")),
        )

    for (mavenPair in mavenUrls) {
        maven {
            url = uri(mavenPair.key)
            content {
                for (group in mavenPair.value) {
                    includeGroup(group)
                }
            }
        }
    }
}

dependencies {
    minecraft(libs.minecraft)
    mappings(variantOf(libs.quilt.mappings) { classifier("intermediary-v2") })

    modImplementation(libs.bundles.fabric)
    modApi(libs.bundles.required)
    modLocalRuntime(libs.bundles.runtime)
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks {
    withType(Javadoc::class.java) {
        exclude("**/mixin/**")
    }

    getByName("generateMetadataFileForMavenPublication") {
        dependsOn("optimizeOutputsOfRemapJar")
    }
}

sourceSets {
    val main = this.getByName("main")

    create("testmod") {
        this.compileClasspath += main.compileClasspath
        this.compileClasspath += main.output
        this.runtimeClasspath += main.runtimeClasspath
        this.runtimeClasspath += main.output

        java {
            // This is required for some reason
            resources.srcDir("src/testmod")
        }
    }
}

loom {
    runtimeOnlyLog4j.set(true)
    accessWidenerPath.set(project.file("src/main/resources/tutoriallib.accesswidener"))

    runs {
        create("testClient") {
            client()
            name("Testmod Client")
            source(sourceSets.getByName("testmod"))
        }
    }
}
