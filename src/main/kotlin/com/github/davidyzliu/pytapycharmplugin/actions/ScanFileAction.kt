package com.github.davidyzliu.pytapycharmplugin.actions

import com.github.davidyzliu.pytapycharmplugin.services.MyProjectService
import com.github.davidyzliu.pytapycharmplugin.toolwindow.ReportToolWindow
import com.github.davidyzliu.pytapycharmplugin.toolwindow.ReportToolWindowFactory
import com.github.davidyzliu.pytapycharmplugin.utils.ScanUtil
import com.github.davidyzliu.pytapycharmplugin.utils.PytaPluginUtils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.components.service
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.ui.content.ContentManager
import org.jetbrains.annotations.NonNls


class ScanAction: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {

        var selectedFile: VirtualFile? = null
        var selectedFilePath: @NonNls String? = null

        // Project guaranteed to exist because of update()
        val selectedProject: Project = PlatformDataKeys.PROJECT.getData(e.dataContext)!!
        val pytaPath: String = selectedProject.service<MyProjectService>().getPythonSDKPath()
        val selectedTextEditor = selectedProject.let { FileEditorManager.getInstance(it).selectedTextEditor };

        // scan disable
        if (selectedTextEditor != null) {
            selectedFile = FileDocumentManager.getInstance().getFile(selectedTextEditor.document)
        }
        if (selectedFile != null) {
            selectedFilePath = selectedFile.path
//            println(selectedFilePath)
        }
        else{
            println("No selected file to scan")
            // terminate
        }

        var result: String? = null


        if (selectedFilePath != null) {
            result = ScanUtil.scan(pytaPath, selectedFilePath)
            println(result)
        }


        if (result != null) {
            val toolWindow: ToolWindow? = ToolWindowManager
                .getInstance(selectedProject)
                .getToolWindow("PythonTA")

            val toolWindowContentManager: ContentManager = toolWindow!!.contentManager
            toolWindowContentManager.removeAllContents(true)

            val reportToolWindow = ReportToolWindow()
            reportToolWindow.replaceContent(PytaPluginUtils.parsePytaOutputString(result))
            val issueContent = toolWindowContentManager
                .factory
                .createContent(reportToolWindow.getContent(), "Scan Results", false)

            toolWindowContentManager.addContent(issueContent)


            println(PytaPluginUtils.parsePytaOutputString(result))
        }
    }

    override fun update(e: AnActionEvent) {
        super.update(e)


        val project: Project? = e.project
        if (project == null) {
            e.presentation.isEnabled = false
        }


//        val selectedEditor: Editor? = project?.let { FileEditorManager.getInstance(it).selectedTextEditor }
//        val selectedFile: VirtualFile? = selectedEditor?.document?.let { FileDocumentManager.getInstance().getFile(it) }
        PlatformDataKeys.VIRTUAL_FILE.getData(e.dataContext)
        val selectedFile: VirtualFile? = PlatformDataKeys.VIRTUAL_FILE.getData(e.dataContext)
//        val selectedFileOther: VirtualFile? = project!!.workspaceFile

        println(selectedFile)
//        println(selectedFileOther)

        if (selectedFile != null) {
            println(selectedFile.extension)
        }

        e.presentation.isVisible = (selectedFile != null) && (selectedFile.extension != null && selectedFile.extension == "py")
    }


}