import io.izzel.taboolib.gradle.*

plugins {
    java
    `maven-publish`
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
    id("io.izzel.taboolib") version "2.0.9"
}

tasks.jar {
    onlyIf { false }
}

tasks.build {
    doLast {
        val plugin = project(":plugin")
        val file = file("${plugin.layout.buildDirectory.get()}/libs").listFiles()?.find { it.endsWith("plugin-$version.jar") }

        file?.copyTo(file("${project.layout.buildDirectory.get()}/libs/${project.name}-$version.jar"), true)
    }
    dependsOn(project(":plugin").tasks.build)
}

subprojects {
    apply<JavaPlugin>()
    apply(plugin = "maven-publish")
    apply(plugin = "io.izzel.taboolib")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    taboolib {
        env {
            install(
                UNIVERSAL, DATABASE, KETHER, METRICS, NMS, NMS_UTIL, UI,
                EXPANSION_REDIS, EXPANSION_JAVASCRIPT, EXPANSION_PLAYER_DATABASE,
                BUKKIT_ALL
            )
        }
        version {
            taboolib = "6.1.1-beta4"
            coroutines = null
        }
    }

    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/groups/public/")
        maven("https://repo.tabooproject.org/repository/releases")
        maven("https://repo.codemc.io/repository/nms/")
        maven("https://hub.spigotmc.org/nexus/content/groups/public/")
        maven("https://repo.opencollab.dev/main/")
    }

    dependencies {
        compileOnly(kotlin("stdlib"))
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    // Java 版本设置
    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    val archiveName = if (project == rootProject)
        rootProject.name.lowercase()
    else "${rootProject.name.lowercase()}-${project.name.lowercase()}"

    val sourceSets = extensions.getByName("sourceSets") as SourceSetContainer

    task<Jar>("sourcesJar") {
        from(sourceSets.named("main").get().allSource)
        archiveClassifier.set("sources")
    }

    tasks.jar {
        exclude("taboolib")
    }

    publishing {
        repositories {
            maven {
                url = uri("https://repo.mcage.cn/repository/trplugins/")
                credentials {
                    username = project.findProperty("user").toString()
                    password = project.findProperty("password").toString()
                }
                authentication {
                    create<BasicAuthentication>("basic")
                }
            }
        }
        publications {
            create<MavenPublication>("library") {
                from(components["java"])
                artifactId = archiveName

                artifact(tasks["sourcesJar"])

                pom {
                    allprojects.forEach {
                        repositories.addAll(it.repositories)
                    }
                }
            }
        }
    }
}
