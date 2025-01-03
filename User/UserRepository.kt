package User

import Command.Command
import Observer.MutableObservable
import Observer.Observable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

class UserRepository private constructor() {

    init {
        println("Repository is created")
    }

    private val file = File("users.json")

    private val usersList: MutableList<User> = loadAllUsers()

    private val _users = MutableObservable<List<User>>(usersList.toList())
    val users: Observable<List<User>>
        get() = _users

    private val _oldestUser = MutableObservable(usersList.maxBy { it.age })
    val oldestUser: Observable<User>
        get() = _oldestUser

    private fun loadAllUsers(): MutableList<User> = Json.decodeFromString<MutableList<User>>(file.readText().trim())


    fun addUser(name: String, secondName: String, age: Int) {
        Thread.sleep(10_000)
        val id = usersList.maxOf { it.id } + 1
        val user = User(id, name, secondName, age)
        usersList.add(user)
        _users.currentValue = usersList.toList()

        if (age > oldestUser.currentValue.age) {
            _oldestUser.currentValue = user
        }
    }

    fun removeUser(id: Int) {
        usersList.removeIf { it.id == id }
        _users.currentValue = usersList.toList()

        val newOldest = usersList.maxBy { it.age }

        if (newOldest != oldestUser.currentValue) {
            _oldestUser.currentValue = newOldest
        }
    }

    fun saveChanges() {
        val content = Json.encodeToString(usersList)
        file.writeText(content)
    }

    companion object {

        private val lock = Any()
        private var instance: UserRepository? = null

        fun getInstance(password: String): UserRepository {

            val correctPassword = File("password_users.txt").readText().trim()
            if (correctPassword != password) throw IllegalArgumentException("Wrong password")

            instance?.let { return it }

            synchronized(lock) {
                instance?.let { return it }
                return UserRepository().also {
                    instance = it
                }
            }
        }
    }
}


