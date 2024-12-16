plugins {
    kotlin("multiplatform") version "2.1.0"
}

repositories {
    mavenCentral()
}

kotlin {
    macosX64 {
        binaries {
            executable("main", listOf(RELEASE))
        }
        compilations.getByName("main") {
            val allegro by cinterops.creating {
                defFile(project.file("src/nativeInterop/cinterop/liballegro.def"))
                packageName("org.liballeg")
            }
        }

        compilerOptions {
            freeCompilerArgs.add("-nomain")
        }
    }
}
