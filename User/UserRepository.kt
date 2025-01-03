package User

import Observer.Observable
import Observer.Observer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class UserRepository private constructor() : Observable<List<User>> {

    init {
        println("Repository is created")
    }

    private val _users: MutableList<User> = loadAllUsers()

    private fun loadAllUsers(): MutableList<User> = Json.decodeFromString<MutableList<User>>(file.readText().trim())

    private val file = File("users.json")

    /* Коллекция наблюдателей */
    private val _observers = mutableListOf<Observer<List<User>>>()

    override val observers
        get() = _observers.toList()

    override val currentValue: List<User>
        get() = _users.toList()

    override fun registerObserver(observer: Observer<List<User>>) {
        _observers.add(observer)
        observer.onChanged(currentValue)
    }

    override fun unregisterObserver(observer: Observer<List<User>>) {
        _observers.remove(observer)
    }

    fun addOnUserChangedListener(observer: Observer<List<User>>) {
        registerObserver(observer)
    }

    fun addUser(name: String, secondName: String, age: Int) {
        val id = currentValue.maxOf { it.id } + 1
        val user = User(id, name, secondName, age)
        _users.add(user)
        notifyObserver()
    }

    fun removeUser(id: Int) {
        _users.removeIf { it.id == id }
        notifyObserver()
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


