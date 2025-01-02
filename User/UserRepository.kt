package User

import Observer.Observer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class UserRepository private constructor() {

    init {
        println("Repository is created")
    }

    private val file = File("users.json")

    /* Коллекция наблюдателей */
    private val observers = mutableListOf<Observer<List<User>>>()

    private val _users: MutableList<User> = loadAllUsers()
    val users
        get() = _users.toList()

    private fun loadAllUsers(): MutableList<User> = Json.decodeFromString<MutableList<User>>(file.readText().trim())


    /* Обновление каждого наблюдателя в коллекции */
    private fun notifyObservers() {
        for (observer in observers) {
            observer.onChanged(users)
        }
    }

    fun registerObserver(observer: Observer<List<User>>) {
        observers.add(observer)
        observer.onChanged(users)
    }

    fun addUser(name: String, secondName: String, age: Int) {
        val id = users.maxOf { it.id } + 1
        val user = User(id, name, secondName, age)
        _users.add(user)
        notifyObservers()
    }

    fun removeUser(id: Int) {
        _users.removeIf { it.id == id }
        notifyObservers()
    }

    fun saveChanges() {
        val content = Json.encodeToString(_users)
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


