package com.github.davidyzliu.pytapycharmplugin.toolwindow

import com.github.davidyzliu.pytapycharmplugin.utils.PytaIssue
import com.github.davidyzliu.pytapycharmplugin.utils.PytaMessage
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.layout.panel
import com.intellij.ui.treeStructure.Tree
import javax.swing.tree.DefaultMutableTreeNode


class ReportToolWindow {

    private var toolWindowPanel = getPanel()

    private fun getPanel(): DialogPanel {

        return panel {
            row {
                tree("No report loaded...") {}
            }
        }
    }

    private fun parseMessage(msg: PytaMessage): String {
        return "[${msg.symbol}] ${msg.msg}"
    }

    fun getContent(): DialogPanel {
        return toolWindowPanel
    }

    private fun tree(rootContent: String, init: NodeBuilder.() -> Unit): Tree {
        val builder = NodeBuilder(rootContent, init)
        return Tree(builder.build())
    }


    fun replaceContent(issues: List<PytaIssue>) {
        toolWindowPanel.removeAll()
        toolWindowPanel.add(getParsedTree(issues))
    }

    private fun getParsedTree(issues: List<PytaIssue>): Tree {
        return tree("PyTA Report: report summary") {
            for (issue: PytaIssue in issues)
                node(issue.filename) {
                    for (message: PytaMessage in issue.msgs)
                        leaf(parseMessage(message))
                }
        }
    }

}

interface Builder {
    fun build(): DefaultMutableTreeNode
}

class NodeBuilder(nodeContent: String, init: NodeBuilder.() -> Unit) : Builder {

    private var root: DefaultMutableTreeNode = DefaultMutableTreeNode(nodeContent)

    init {
        init()
    }

    private fun add(buildable: Builder) {
        root.add(buildable.build())
    }

    override fun build(): DefaultMutableTreeNode {
        return root
    }



    fun leaf(content: String) {
        add(LeafBuilder(content))
    }

    fun node(content: String, init: NodeBuilder.() -> Unit) {
        val node = NodeBuilder(content, init)
        add(node)
    }

}


class LeafBuilder(private val leafContent: String) : Builder {
    override fun build(): DefaultMutableTreeNode {
        return DefaultMutableTreeNode(leafContent)
    }
}