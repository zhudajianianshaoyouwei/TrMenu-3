import io.izzel.taboolib.gradle.*

plugins {
    java
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
    id("io.izzel.taboolib") version "2.0.9"
}

subprojects {
    apply<JavaPlugin>()
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
            taboolib = "6.1.1-beta7"
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

}