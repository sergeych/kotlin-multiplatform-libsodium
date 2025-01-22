/*
 *    Copyright 2019 Ugljesa Jovanovic
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeTest

plugins {
    kotlin(PluginsDeps.multiplatform)
    id(PluginsDeps.mavenPublish)
    id(PluginsDeps.signing)
}

repositories {
    mavenCentral()
}
group = ReleaseInfo.group
version = "0.1" //Irrelevant

val ideaActive = System.getProperty("idea.active") == "true"



kotlin {
    val hostOsName = getHostOsName()
    runningOnLinuxx86_64 {
        jvm()

        // TODO: wasm тут копирует не апишный, если поменялся тот, то поменять и тут
        @OptIn(ExperimentalWasmDsl::class)
        wasmJs {
            browser {
                testTask {
                    useKarma {
                        useChromeHeadless()
                    }
                }
            }
        }

        js(IR) {
           browser {
                testTask {
                    enabled = false //Until I sort out testing on travis
                    useKarma {
                        useChrome()
                    }
                }
            }
            nodejs {
                testTask {
                    useMocha() {
                        timeout = "10s"
                    }
                }
            }

        }
        linuxX64("linux") {
            binaries {
                staticLib {
                    optimized = true
                }
            }
        }

        linuxArm64() {
            binaries {
                staticLib {
                }
            }
        }


    }

    runningOnMacos {
        iosX64()
        iosArm64()
        iosSimulatorArm64()

        macosX64()
        macosArm64()

        tvosX64()
        tvosArm64()
        tvosSimulatorArm64()

        watchosArm64()
        watchosArm32()
        watchosSimulatorArm64()

    }
    runningOnWindows {
        mingwX64()
    }




    println(targets.names)

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin(Deps.Common.stdLib))
                implementation(kotlin(Deps.Common.test))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin(Deps.Common.test))
                implementation(kotlin(Deps.Common.testAnnotation))
            }
        }

        runningOnLinuxx86_64 {
            val jvmMain by getting {
                dependencies {
                    implementation(kotlin(Deps.Jvm.stdLib))
                    implementation(kotlin(Deps.Jvm.test))
                    implementation(kotlin(Deps.Jvm.testJUnit))
                }
            }
            val jvmTest by getting {
                dependencies {
                    implementation(kotlin(Deps.Jvm.test))
                    implementation(kotlin(Deps.Jvm.testJUnit))
                    implementation(kotlin(Deps.Jvm.reflection))
                }
            }
            val jsMain by getting {
                dependencies {
                    implementation(kotlin(Deps.Js.stdLib))
                }
            }
            val jsTest by getting {
                dependencies {
                    implementation(kotlin(Deps.Js.test))
                }
            }
        }

        // TODO: может сунуть обратно в ранинг онг линукс, как и то, что выше
        val wasmJsMain by getting {
            dependencies {
                // implementation(kotlin(Deps.wasmJs.stdLib))
                implementation(npm(Deps.wasmJs.Npm.libsodiumWrappers.first, Deps.wasmJs.Npm.libsodiumWrappers.second))
            }
        }
        val wasmJsTest by getting {
            dependencies {
                implementation(npm(Deps.wasmJs.Npm.libsodiumWrappers.first, Deps.wasmJs.Npm.libsodiumWrappers.second))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")

                implementation(kotlin("test"))
            }
        }

        runningOnMacos {
            val tvosX64Main by getting {
                dependsOn(commonMain)
            }

            val tvosArm64Main by getting {
                dependsOn(commonMain)
            }

            val watchosArm64Main by getting {
                dependsOn(commonMain)
            }

            val watchosArm32Main by getting {
                dependsOn(commonMain)
            }


        }

        all {
            languageSettings.enableLanguageFeature("InlineClasses")
            languageSettings.optIn("kotlin.ExperimentalUnsignedTypes")
            languageSettings.optIn("kotlin.ExperimentalStdlibApi")
        }
    }


}

tasks {

    if (getHostOsName() == "linux" && getHostArchitecture() == "x86-64") {

        val jvmTest by getting(Test::class) {
            testLogging {
                events("PASSED", "FAILED", "SKIPPED")
            }
        }

        val linuxTest by getting(KotlinNativeTest::class) {

            testLogging {
                events("PASSED", "FAILED", "SKIPPED")
                // showStandardStreams = true
            }
        }
    }

    if (getHostOsName() == "windows") {
        val mingwX64Test by getting(KotlinNativeTest::class) {

            testLogging {
                events("PASSED", "FAILED", "SKIPPED")
                showStandardStreams = true
            }
        }
    }

}






