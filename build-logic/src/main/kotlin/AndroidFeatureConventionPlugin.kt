import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

class AndroidFeatureConventionPlugin : Plugin<Project> {
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
                add("implementation", project(":core:common"))
                add("implementation", project(":core:domain"))
                add("implementation", project(":core:entity"))
                add("testImplementation", project(":core:testing"))

                add("testImplementation", kotlin("test"))
                add("androidTestImplementation", kotlin("test"))

                add("implementation", libs.findLibrary("androidx.compose.material3").get())
                add("implementation", libs.findLibrary("androidx.compose.material2").get())
                add("implementation", libs.findLibrary("androidx.compose.foundation").get())
                add("implementation", libs.findLibrary("androidx.compose.ui").get())
                add("implementation", libs.findLibrary("androidx.compose.ui.tooling.preview").get())
                add("debugImplementation", libs.findLibrary("androidx.compose.ui.tooling").get())
                add("androidTestImplementation", libs.findLibrary("androidx.compose.ui.test.junit4").get())
                add("debugImplementation", libs.findLibrary("androidx.compose.ui.test.manifest").get())

                add("implementation", libs.findLibrary("androidx.compose.material.icons.core").get())
                add("implementation", libs.findLibrary("androidx.compose.material.icons.extended").get())
                add("implementation", libs.findLibrary("androidx.compose.material3.window.size").get())
                add("implementation", libs.findLibrary("androidx.compose.activity").get())
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
