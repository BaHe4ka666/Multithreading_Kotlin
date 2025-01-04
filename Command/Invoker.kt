package Command

/* Такая запись означает, что в качестве типа <T> можно указать тип Command, либо любого из его наследников.*/
interface Invoker<T : Command> {
    fun addCommand(command: T)
}