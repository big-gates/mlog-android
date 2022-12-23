// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.gms.google.service) apply false
}

//task clean(type: Delete) {
//    delete rootProject.buildDir
//}
//
//task copyGitHooks(type: Copy) {
//    from("$rootDir/teamConfig/git/gitHooks/") {
//        include '**/*'
//        rename '(.*)', '$1'
//    }
//    into "$rootDir/.git/hooks"
//}
//
//task installGitHooks(type: Exec) {
//    group 'git hooks'
//    commandLine 'chmod'
//    args '-R', '+x', "$rootDir/.git/hooks/"
//    dependsOn copyGitHooks
//}
//
//task autoKtlintFormat(type: Exec) {
//    executable "$rootDir/teamConfig/script/autoKtlintFormat"
//}