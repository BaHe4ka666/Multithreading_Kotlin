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

    /* Такая реализация паттерна Singleton называется double check */

    companion object {

        private val lock = Any()
        private var instance: UserRepository? = null

        fun getInstance(password: String): UserRepository {

            val correctPassword = File("password_users.txt").readText().trim()
            if (correctPassword != password) throw IllegalArgumentException("Wrong password")


            /* Если экземпляр уже создан, то нет смысла, чтобы все потоки ждали открытия замка, они
            могут забрать уже существующий репозиторий */

            instance?.let { return it }

            /*  Необходимо чтобы данная проверка на null и создание нового объекта репозитория находились в
            критической секции и доступ к ней был только у одного потока. */

            synchronized(lock) {
                /* Если был создан экземпляр репозитория, то возвращаем его */
                instance?.let { return it }
                /* Если экземпляр не был создан, то возвращаем экземпляр репозитория и в блоке also присваиваем
                переменной instance значение экземпляра класса репозитория */

                return UserRepository().also {
                    instance = it
                }
//                return UserRepository().also {
//                    instance = it
//                }
            }
        }
    }
}


