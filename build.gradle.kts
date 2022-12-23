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
    // 이거 테스트 주석 : 동작확인 용도 나중에 발견하면 이거 지워도 됨
//    id("com.android.library") version "7.3.1" apply false
//    id("org.jetbrains.kotlin.android") version "1.7.20" apply fals
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