package Dogs

import Command.Command
import Command.Invoker
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

object DogsInvoker : Invoker {

    private val commands = LinkedBlockingQueue<Command>()
    override fun addCommand(command: Command) {
        commands.add(command)
    }

    init {
        thread {
            while (true) {
                val command = commands.take()
                command.execute()
            }
        }
    }
}