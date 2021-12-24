package com.github.davidyzliu.pytapycharmplugin.actions

import com.github.davidyzliu.pytapycharmplugin.services.MyProjectService
import com.github.davidyzliu.pytapycharmplugin.utils.PytaPluginUtils
import com.github.davidyzliu.pytapycharmplugin.utils.ScanUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.components.service
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.annotations.NonNls

/**
 * Represents an IDE action in which a *single* file is scanned using PythonTA
 * */
class ScanFileAction : AnAction() {

    /**
     * Contains the scanning logic that is executed when the user clicks on the button
     * representing this action.
     * @param e Contains contextual data about the action performed by the user
     * */
    override fun actionPerformed(e: AnActionEvent) {

        var selectedFile: VirtualFile? = null
        var selectedFilePath: @NonNls String? = null

        // Project guaranteed to exist because of update()
        val selectedProject: Project = PlatformDataKeys.PROJECT.getData(e.dataContext)!!
        val pytaPath: String = selectedProject.service<MyProjectService>().getPythonSDKPath()
        val selectedTextEditor = selectedProject.let { FileEditorManager.getInstance(it).selectedTextEditor }

        if (selectedTextEditor != null) {
            selectedFile = FileDocumentManager.getInstance().getFile(selectedTextEditor.document)
        }

        if (selectedFile != null) {
            selectedFilePath = selectedFile.path
        } else {
            println("No selected file to scan")
        }

        if (selectedFilePath != null) {
            val result: String = ScanUtil.scan(pytaPath, selectedFilePath)
            println(PytaPluginUtils.parsePytaOutputString(result))
        }
    }

    /**
     * Contains the code that determines the *state* of the action (ie, whether it is enabled
     * or disabled, whether it is visible or not, etc.) based on user interactions with the IDE.
     * @param e Contains contextual data about the action performed by the user
     * */
    override fun update(e: AnActionEvent) {
        super.update(e)

        val project: Project? = e.project
        if (project == null) {
            e.presentation.isEnabled = false
        }

        val selectedFile: VirtualFile? = PlatformDataKeys.VIRTUAL_FILE.getData(e.dataContext)

        e.presentation.isVisible =
            (selectedFile != null) && (selectedFile.extension != null && selectedFile.extension == "py")
    }
}
