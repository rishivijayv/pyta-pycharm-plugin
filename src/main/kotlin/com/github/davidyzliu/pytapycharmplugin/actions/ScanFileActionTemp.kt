package com.github.davidyzliu.pytapycharmplugin.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class ScanFileActionTemp : BaseGlobalAction() {
    override fun actionPerformed(e: AnActionEvent) {
    }

    override fun update(e: AnActionEvent) {
        super.update(e)

        val project: Project? = e.project
        if (project == null) {
            e.presentation.isEnabled = false
        }

        val selectedEditor: Editor? = project?.let { FileEditorManager.getInstance(it).selectedTextEditor }
        val selectedFile: VirtualFile? = selectedEditor?.document?.let { FileDocumentManager.getInstance().getFile(it) }

        if (selectedFile != null) {
            println(selectedFile.extension)
        }

        e.presentation.isVisible = (selectedFile != null) && (selectedFile.extension != null && selectedFile.extension == "py")
    }
}
