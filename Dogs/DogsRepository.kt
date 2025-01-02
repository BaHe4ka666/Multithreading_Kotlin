package Dogs

import Observer.Observer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File


class DogsRepository private constructor() {

    private val file = File("dogs.json")

    private val _dogs = loadAllDogs()
    val dogs
        get() = _dogs.toList()

    private val observers = mutableListOf<Observer<List<Dog>>>()

    private fun loadAllDogs() = Json.decodeFromString<MutableList<Dog>>(file.readText().trim())

    fun notifyObserver() {
        for (observer in observers) {
            observer.onChanged(dogs)
        }
    }

    fun registerObserver(observer: Observer<List<Dog>>) {
        observers.add(observer)
        observer.onChanged(dogs)
    }

    fun addDog(breed: String, name: String, weight: Double) {
        val id = dogs.maxOf { it.id } + 1
        val dog = Dog(id, breed, name, weight)
        _dogs.add(dog)
        notifyObserver()
    }

    fun removeDog(id: Int) {
        _dogs.removeIf { it.id == id }
        notifyObserver()
    }

    fun saveChanges() {
        val content = Json.encodeToString(dogs)
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