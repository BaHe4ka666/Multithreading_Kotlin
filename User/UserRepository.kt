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


    /* Способ создания репозитория через использование делегатов.
    Делегаты нужны, чтобы передать ответственность за создание объекта кому-то другому. */

//    companion object {
//
//        /*  Создание делегата: мы вызываем функцию lazy, которая в качестве параметра принимает другую функцию,
//        которая умеет создавать объект репозитория. Внутри этой функции мы говорим как необходимо создать объект.
//         В данном случае просто вызвать его конструктор. Под капотом у этого делегата реализована ленивая инициализация
//         с использованием паттерна Singleton. Но в качестве параметров ничего передать нельзя.*/

//        private val instance: UserRepository by lazy { UserRepository() }
//
//        fun getInstance(password: String): UserRepository {
//            val correctPassword = File("password_users.txt").readText().trim()
//            if (correctPassword != password) throw IllegalArgumentException("Wrong password")
//            return instance
//        }
//    }


    /* Все, что мы объявили внутри companion object будет относиться к классу репозитория, а не к его
    объекту. Получается, что для всего класса будет создан единственный экземпляр репозитория и с помощью метода
    getInstance() мы сможем его получить. Этот способ часто встречается на практике, но у него есть минусы:
    1. Если в конструктор класса нужно передавать аргумент, то использовать этот способ не получится, потому что
    мы создаем объект класса репозитория до вызова метода getInstance(), куда передаем значения.
    2. Репозиторий будет создан при первом обращении к классу репозитория, независимо, что это будет за обращение
    (отсутствие ленивой инициализации)
     */

//    companion object {
//
//        private val instance: UserRepository = UserRepository()
//
//        fun getInstance(password: String): UserRepository {
//            val correctPassword = File("password_users.txt").readText().trim()
//            if (correctPassword != password) throw IllegalArgumentException("Wrong password")
//            return instance
//        }
//    }

    /* Используется при ИНЬЕКЦИИ ЗАВИСИМОСТЕЙ - когда инициализацией переменных занимается библиотека.
    Такой способ называется ленивой инициализацией. Это значит, что экземпляр репозитория будет создан в тот момент,
    когда мы первый раз вызовем метод getInstance(). В момент создания репозитория у нас идет загрузка данных из файла
    и если файл будет слишком большим, то этот момент займет много времени. Чтобы программа не зависала, можно метод
    getInstance() вызывать в другом потоке.
    */

//    companion object {
//
    /* Такая запись осзначает, что я не хочу сейчас класть значение в эту переменную, но в будущем
        оно обязательно там появится */

//        private lateinit var instance: UserRepository
//
//        fun getInstance(password: String): UserRepository {
//            val correctPassword = File("password_users.txt").readText().trim()
//            if (correctPassword != password) throw IllegalArgumentException("Wrong password")

    /* Проверка на то, была ли переменная проинициализирована */

//            if (!::instance.isInitialized) {
//                instance = UserRepository()
//            }
//            return instance
//        }
//    }

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

