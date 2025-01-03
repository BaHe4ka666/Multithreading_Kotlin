package Dogs

import Observer.Observable
import Observer.Observer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File


class DogsRepository private constructor() : Observable<List<Dog>> {

    private val file = File("dogs.json")

    private val _dogs = loadAllDogs()

    private val _observers = mutableListOf<Observer<List<Dog>>>()

    override val currentValue
        get() = _dogs.toList()

    override val observers
        get() = _observers.toList()


    override fun registerObserver(observer: Observer<List<Dog>>) {
        _observers.add(observer)
        observer.onChanged(currentValue)
    }

    override fun unregisterObserver(observer: Observer<List<Dog>>) {
        _observers.remove(observer)
    }

    private fun loadAllDogs() = Json.decodeFromString<MutableList<Dog>>(file.readText().trim())


    fun addDog(breed: String, name: String, weight: Double) {
        val id = currentValue.maxOf { it.id } + 1
        val dog = Dog(id, breed, name, weight)
        _dogs.add(dog)
        notifyObserver()
    }

    fun removeDog(id: Int) {
        _dogs.removeIf { it.id == id }
        notifyObserver()
    }

    fun saveChanges() {
        val content = Json.encodeToString(currentValue)
        file.writeText(content)
    }

    companion object {

        val lock = Any()
        var instance: DogsRepository? = null

        fun getInstance(password: String): DogsRepository {

            val correctPassword = File("dogs_password.txt").readText().trim()
            if (password != correctPassword) throw IllegalArgumentException("Wrong password")

            instance?.let { return it }

            synchronized(lock) {
                instance?.let { return it }
                return DogsRepository().also {
                    instance = it
                }
            }
        }
    }
}