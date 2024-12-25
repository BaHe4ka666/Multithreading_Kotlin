package User

import kotlinx.serialization.json.Json
import java.io.File

class UserRepository private constructor() {

    init {
        println("Repository is created")
    }

    private val file = File("users.json")

    private val _users: MutableList<User> = loadAllUsers()
    val users
        get() = _users.toList()

    private fun loadAllUsers(): MutableList<User> = Json.decodeFromString<MutableList<User>>(file.readText().trim())

    companion object {

        private var instance: UserRepository? = null

        fun getInstance(password: String): UserRepository {
            val correctPassword = File("password_users.txt").readText().trim()
            if (correctPassword != password) throw IllegalArgumentException("Wrong password")
            if (instance == null) {
                instance = UserRepository()
            }
            return instance!!
        }
    }


}

