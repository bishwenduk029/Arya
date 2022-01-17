import com.moowork.gradle.node.npm.NpmInstallTask
import com.moowork.gradle.node.npm.NpxTask
import com.moowork.gradle.node.yarn.YarnInstallTask
import com.moowork.gradle.node.yarn.YarnTask

plugins {
    id("com.github.node-gradle.node") version "2.2.3"
}

node {
    version = "12.16.2"
    npmVersion = ""
    yarnVersion = ""
    npmInstallCommand = "install"
    distBaseUrl = "https://nodejs.org/dist"
    download = false
    workDir = file("${project.buildDir}/nodejs")
    npmWorkDir = file("${project.buildDir}/npm")
    yarnWorkDir = file("${project.buildDir}/yarn")
    nodeModulesDir = file("${project.projectDir}")
}

val yarnInstallTask = tasks.named<YarnInstallTask>("yarn") {
    nodeModulesOutputFilter = closureOf<ConfigurableFileTree> {
        exclude("notExistingFile")
    }
}

val buildTaskUsingYarn = tasks.register<YarnTask>("buildFrontend") {
    dependsOn(yarnInstallTask)
    setYarnCommand("run", "build")
    inputs.dir(".")
}