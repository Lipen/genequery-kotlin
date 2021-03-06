package gq.console.commands

import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.MissingArgumentException
import org.apache.commons.cli.MissingOptionException
import org.apache.commons.cli.Options
import org.apache.commons.cli.ParseException
import org.apache.commons.cli.UnrecognizedOptionException

val BASE_COMMAND_NAME = "java -jar gqcmd.jar"

interface Command {
    fun execute(cmdLine: CommandLine)
    fun options(): Options
    fun name(): String
    fun briefDescription(): String
    fun detailedDescription(): String

    fun printHelp() {
        val options = getOptionsWithHelp()
        val formatter = HelpFormatter()
        formatter.printHelp("$BASE_COMMAND_NAME ${name()} [arguments]", detailedDescription(), options, null)
    }

    fun getOptionsWithHelp(): Options {
        val options = options()
        options.addOption("h", "help", false, "Print this help message")
        return options
    }
}

fun runCommand(cmd: Command, args: Array<String>) {
    if (args.isEmpty() || args.first() == "-h" || args.first() == "--help") {
        cmd.printHelp()
        return
    }
    val options = cmd.getOptionsWithHelp()
    try {
        val cmdLine = DefaultParser().parse(options, args)
        cmd.execute(cmdLine)
    } catch (e: MissingOptionException) {
        println("Missing options: ${e.missingOptions.joinToString(",")}")
        cmd.printHelp()
    } catch (e: MissingArgumentException) {
        println("Missing argument for option: ${e.option}")
        cmd.printHelp()
    } catch (e: UnrecognizedOptionException) {
        println("Unknown option: ${e.option}")
        cmd.printHelp()
    } catch (e: ParseException) {
        cmd.printHelp()
        e.printStackTrace()
    }
}
