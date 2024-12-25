package Dogs

import kotlinx.serialization.json.Json
import java.io.File
import javax.swing.text.PasswordView

class DogsRepository private constructor() {
    private val file = File("dogs.json")
    private val _dogs = loadAllDogs()
    val dogs
        get() = _dogs.toList()

    private fun loadAllDogs() = Json.decodeFromString<MutableList<Dog>>(file.readText().trim())

    companion object {

        var instance: DogsRepository? = null

        fun getInstance(password: String): DogsRepository {
            val correctPassword = File("dogs_password.txt").readText().trim()
            if (password != correctPassword) throw IllegalArgumentException("Wrong password")
            if (instance == null) {
                instance = DogsRepository()
            }
            return instance!!
        }
    }

}