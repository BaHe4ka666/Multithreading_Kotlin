package User

class Administrator {

    val repository = UserRepository.getInstance("qwerty")

    fun work() {
        while (true) {
            print("Enter the type of operation. ")
            val operations = TypeOfOperation.entries
            for ((index, operation) in operations.withIndex()) {
                if (index == operations.lastIndex - 1) {
                    print("$index - ${operation.title}, ")
                } else {
                    print("$index - ${operation.title}: ")
                }
            }

            val index = readln().toInt()
            val code = operations[index]

            when (code) {
                TypeOfOperation.EXIT -> {
                    UsersInvoker.addCommand(AdministratorCommands.SaveChanges(repository))
                    break
                }

                TypeOfOperation.ADD_NEW_USER -> addNewUser()
                TypeOfOperation.DELETE_USER -> removeUser()
            }
        }
    }

    fun addNewUser() {
        println("Name: ")
        val name = readln()
        println("Second name: ")
        val secondName = readln()
        println("Age: ")
        val age = readln().toInt()
        UsersInvoker.addCommand(AdministratorCommands.AddUser(repository, name, secondName, age))
    }

    fun removeUser() {
        println("Enter user id: ")
        val id = readln().toInt()
        UsersInvoker.addCommand(AdministratorCommands.RemoveUser(repository, id))
    }
}