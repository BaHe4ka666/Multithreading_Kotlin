package User

import Command.Command

sealed interface AdministratorCommands : Command {


    data class AddUser(
        val userRepository: UserRepository,
        val firstName: String,
        val secondName: String,
        val age: Int
    ) : AdministratorCommands {
        override fun execute() {
            userRepository.addUser(firstName, secondName, age)
        }
    }

    data class RemoveUser(
        val userRepository: UserRepository,
        val id: Int
    ) : AdministratorCommands {
        override fun execute() {
            userRepository.removeUser(id)
        }
    }

    data class SaveChanges(
        val userRepository: UserRepository
    ) : AdministratorCommands {
        override fun execute() {
            userRepository.saveChanges()
        }
    }
}