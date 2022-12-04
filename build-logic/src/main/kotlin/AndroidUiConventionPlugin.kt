import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

class AndroidUiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            pluginManager.apply {
                apply("kychan.android.library")
                apply("kychan.android.hilt")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner =
                        "androidx.test.runner.AndroidJUnitRunner"
                }
            }

            dependencies {
                add("implementation", project(":core:design"))

                add("testImplementation", kotlin("test"))
                add("androidTestImplementation", kotlin("test"))

                add("implementation", libs.findLibrary("material3.compose").get())
                add("implementation", libs.findLibrary("material2.compose").get())
                add("implementation", libs.findLibrary("foundation.compose").get())
                add("implementation", libs.findLibrary("ui.compose").get())
                add("implementation", libs.findLibrary("ui.tooling.preview.compose").get())
                add("debugImplementation", libs.findLibrary("ui.tooling.compose").get())
                add("androidTestImplementation", libs.findLibrary("ui.test.junit4.compose").get())
                add("debugImplementation", libs.findLibrary("ui.test.manifest.compose").get())

                add("implementation", libs.findLibrary("material.icons.core.compose").get())
                add("implementation", libs.findLibrary("material.icons.extended.compose").get())
                add("implementation", libs.findLibrary("material3.window.size.compose").get())
                add("implementation", libs.findLibrary("activity.compose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewModel.compose").get())

                add("implementation", libs.findLibrary("coil.kt.compose").get())

                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.runtime.compose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewModel.compose").get())

                add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
            }
        }
    }
}
