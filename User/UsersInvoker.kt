package User

import Command.Command
import Command.Invoker
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

object UsersInvoker : Invoker {

    /* Эта коллекция работает следующим образом: в нее можно так же добавлять объекты при помощи метода add, но кроме
    этого у неё есть дополнительные методы, такие как take(), метод take() получает первый элемент из этой коллекции
    и удалит его оттуда.*/
    private val commands = LinkedBlockingQueue<Command>()

    override fun addCommand(command: Command) {
        println("New command: $command")
        commands.add(command)
    }

    init {
        /* Поток с бесконечным циклом в котором мы будем проверять есть ли в этой коллекции какие-то объекты, и если
        они есть, то мы достаем объект, выполняем команду и удаляем её, чтобы она не была выполнена повторно. Все это
        происходит по системе очереди.
         */
        thread {
            while (true) {
                /* Если в коллекции есть объекты, то метод take() получит первый элемент, положит его в переменную
                command и дальше с ним можно работать, а если в коллекции ничего нет, то он усыпит поток до тех пор,
                пока в коллекции не появятся новые объекты.*/
                println("Waiting...")
                val command = commands.take()
                println("Executing $command")
                command.execute()
                println("Executed $command")
            }
        }
    }
}