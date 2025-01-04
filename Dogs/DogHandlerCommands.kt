package Dogs

import Command.Command

sealed interface DogHandlerCommands : Command {

    data class AddDog(
        val dogsRepository: DogsRepository,
        val breed: String,
        val name: String,
        val weight: Double
    ) : DogHandlerCommands {
        override fun execute() {
            dogsRepository.addDog(breed, name, weight)
        }
    }

    data class RemoveDog(
        val dogsRepository: DogsRepository,
        val id: Int
    ) : DogHandlerCommands {
        override fun execute() {
            dogsRepository.removeDog(id)
        }
    }

    data class SaveChanges(
        val dogsRepository: DogsRepository,
    ) : DogHandlerCommands {
        override fun execute() {
            dogsRepository.saveChanges()
        }
    }
}