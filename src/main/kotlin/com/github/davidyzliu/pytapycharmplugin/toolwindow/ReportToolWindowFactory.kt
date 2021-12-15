package com.github.davidyzliu.pytapycharmplugin.toolwindow

import com.github.davidyzliu.pytapycharmplugin.utils.PytaIssue
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class ReportToolWindowFactory : ToolWindowFactory {

    private var report: List<PytaIssue>? = null

    fun setReport(report: List<PytaIssue>) {
        this.report = report;
    }

    override fun createToolWindowContent(p0: Project, p1: ToolWindow) {
        val tw = ReportToolWindow()
        if (report != null)
            tw.replaceContent(report!!)
        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(tw.getContent(), "Some Test Text", false)
        p1.contentManager.addContent(content)
    }
}