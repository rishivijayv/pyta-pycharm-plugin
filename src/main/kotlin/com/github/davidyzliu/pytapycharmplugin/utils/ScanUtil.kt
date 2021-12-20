package com.github.davidyzliu.pytapycharmplugin.utils

import java.io.IOException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors

object ScanUtil {
    @Throws(IOException::class)
    fun scan(pytaPath: String, filePath: String): String {
        println(pytaPath)
        val builder = ProcessBuilder(pytaPath, filePath, "--output-format", "python_ta.reporters.JSONReporter")
        var p: Process? = null
        try {
            p = builder.start()
        } catch (e: IOException) {
            println(e)
        }
        println(p)
        println(p!!.inputStream)
        val stdInput = BufferedReader(InputStreamReader(p.inputStream))
        // val stdError = BufferedReader(InputStreamReader(p.errorStream))
        val result = stdInput.lines().collect(Collectors.joining(System.lineSeparator()))


        return result
    }
}