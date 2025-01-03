package Dogs

import Observer.MutableObservable
import Observer.Observable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File


class DogsRepository private constructor() {

    private val file = File("dogs.json")

    private val dogsList = loadAllDogs()

    private fun loadAllDogs() = Json.decodeFromString<MutableList<Dog>>(file.readText().trim())

    private val _dogs = MutableObservable<List<Dog>>(dogsList.toList())
    val dogs: Observable<List<Dog>>
        get() = _dogs


    fun addDog(breed: String, name: String, weight: Double) {
        val id = dogsList.maxOf { it.id } + 1
        val dog = Dog(id, breed, name, weight)
        dogsList.add(dog)

        _dogs.currentValue = dogsList.toList()

    }

    fun removeDog(id: Int) {
        dogsList.removeIf { it.id == id }

        _dogs.currentValue = dogsList.toList()
    }

    fun saveChanges() {
        val content = Json.encodeToString(dogsList)
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