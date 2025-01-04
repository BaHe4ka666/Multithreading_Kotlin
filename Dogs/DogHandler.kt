package Dogs

class DogHandler {

    val repository = DogsRepository.getInstance("qwerty")

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
                    DogsInvoker.addCommand(DogHandlerCommands.SaveChanges(repository))
                    break
                }
                TypeOfOperation.ADD_NEW_DOG -> addNewDog()
                TypeOfOperation.DELETE_DOG -> removeDog()
            }
        }
    }

    fun addNewDog() {
        println("Breed: ")
        val breed = readln()
        println("Name: ")
        val name = readln()
        println("Weight: ")
        val weight = readln().toDouble()
        DogsInvoker.addCommand(DogHandlerCommands.AddDog(repository, breed, name, weight))
    }

    fun removeDog() {
        println("Enter user id: ")
        val id = readln().toInt()
        DogsInvoker.addCommand(DogHandlerCommands.RemoveDog(repository, id))
    }
}