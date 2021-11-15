import java.io.*
import kotlin.Throws
import kotlin.jvm.JvmStatic
import java.lang.ProcessBuilder
import java.lang.Process

object ScanFile {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val builder = ProcessBuilder("/bin/bash")
        var p: Process? = null
        try {
            p = builder.start()
        } catch (e: IOException) {
            println(e)
        }
        //get stdin of shell
        val p_stdin = BufferedWriter(OutputStreamWriter(p!!.outputStream))

        // List of commands
        //TODO: get PyTA path
        val commands = arrayOf("cd ..", "cd pyta", "cd pyta", "cd python_ta", "python3 __main__.py -h")
        for (command in commands) {
            try {
                //single execution
                p_stdin.write(command)
                p_stdin.newLine()
                p_stdin.flush()
            } catch (e: IOException) {
                println(e)
            }
        }

        // close the shell by execution exit command
        try {
            p_stdin.write("exit")
            p_stdin.newLine()
            p_stdin.flush()
        } catch (e: IOException) {
            println(e)
        }
        val stdInput = BufferedReader(InputStreamReader(p.inputStream))
        val stdError = BufferedReader(InputStreamReader(p.errorStream))

        // Read the output from the command
        var s: String? = null
        while (stdInput.readLine().also { s = it } != null) {
            println(s)
        }
    }
}