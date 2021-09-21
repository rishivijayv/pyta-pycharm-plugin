package com.github.davidyzliu.pytapycharmplugin.services

import com.intellij.openapi.project.Project
import com.github.davidyzliu.pytapycharmplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
