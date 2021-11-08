package com.github.davidyzliu.pytapycharmplugin.utils

data class PytaIssue(val filename: String, val msgs: Array<PytaMessage>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PytaIssue

        if (filename != other.filename) return false
        if (!msgs.contentEquals(other.msgs)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = filename.hashCode()
        result = 31 * result + msgs.contentHashCode()
        return result
    }

}


data class PytaMessage(
    val symbol: String,
    val msg: String,
    val category: String,
    val path: String,
    val module: String,
    val line: Int,
    val column: Int
)
