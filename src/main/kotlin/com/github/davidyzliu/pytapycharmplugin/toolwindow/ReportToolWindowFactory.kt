package com.github.davidyzliu.pytapycharmplugin.toolwindow

import com.github.davidyzliu.pytapycharmplugin.utils.PytaIssue
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.ui.content.ContentFactory

class ReportToolWindowFactory : ToolWindowFactory {

//    private var report: List<PytaIssue>? = null
//    private var parentToolWindow: ToolWindow? = null
//    private var providedProject: Project? = null

    companion object {
        const val TOOL_WINDOW_ID = "PytaReport"
    }



//    fun setReport(report: List<PytaIssue>) {
//        println("Setting to a new report")
//        this.report = report;
//        createToolWindowContent(providedProject!!, parentToolWindow!!)
////        parentToolWindow!!.contentManager.remove
//    }

//    private fun setProjectAndParentToolWindow(project: Project, toolWindow: ToolWindow) {
//        if (providedProject == null) {
//            providedProject = project
//        }
//        if (parentToolWindow == null) {
//            parentToolWindow = toolWindow
//        }
//    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        println("Calling create tool window content")
        val tw = ReportToolWindow()
//        if (report != null)
//            tw.replaceContent(report!!)
//        val contentFactory = ContentFactory.SERVICE.getInstance()

//        setProjectAndParentToolWindow(project, toolWindow)

        val content = toolWindow
            .contentManager
            .factory
            .createContent(tw.getContent(), "Some Test Text", false)

        toolWindow.contentManager.addContent(content)
    }
}