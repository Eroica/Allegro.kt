# Allegro.kt

A Kotlin/Native Gradle project that sets up [Allegro](https://liballeg.org/) in Kotlin on macOS!

This setup is for Allegro version 5.

## Requirements

Compile Allegro using CMake or by using one of your platform's package managers. This guide assumes that Allegro has been installed to `/opt/local`.

On macOS, both `entryPoint` and linking with `allegro_main` is required due to https://www.allegro.cc/manual/5/al_run_main.

This `.def` file contains enough to compile the sample application. If you use other Allegro functions, you need to adapt `headers` and `linkerOpts` accordingly.

## Compile sample program

You can now use Allegro functions in Kotlin! Note that `cinterop` is not able to create definitions for everything, especially not for C macros. `al_init` is a macro that wraps `al_install_system`, so in your Kotlin code you need to initialize Allegro like this:

```kotlin
import platform.posix.*
import org.liballeg.*

fun main(args: Array<String>) {
    al_install_system(ALLEGRO_VERSION_INT, staticCFunction(::atexit))
    // ...
}

```
