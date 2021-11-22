import java.io.IOException
import java.io.BufferedReader
import java.io.InputStreamReader

class Scan(val pytaPath: String, val filePath: String) {
    @Throws(IOException::class)
    fun scan(): String {
        val builder = ProcessBuilder(pytaPath, filePath, "--output-format", "python_ta.reporters.JSONReporter")
        var p: Process? = null
        try {
            p = builder.start()
        } catch (e: IOException) {
            println(e)
        }
        val stdInput = BufferedReader(InputStreamReader(p!!.inputStream))
        val stdError = BufferedReader(InputStreamReader(p.errorStream))

        // Read the output from the command
        var s: String? = null
        while (stdInput.readLine().also { s = it } != null) {
            println(s)
        }
        var errors: String? = null
        while (stdError.readLine().also { errors = it } != null) {
            println(errors)
        }
        return stdInput.readLine()
    }
}