package User

import Command.Command
import Command.Invoker
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

object UsersInvoker : Invoker<AdministratorCommands> {

    private val commands = LinkedBlockingQueue<Command>()

    override fun addCommand(command: AdministratorCommands) {
        println("New command: $command")
        commands.add(command)
    }

    init {
        thread {
            while (true) {
                println("Waiting...")
                val command = commands.take()
                println("Executing $command")
                command.execute()
                println("Executed $command")
            }
        }
    }
}