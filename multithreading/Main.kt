package org.example.multithreading

import kotlin.concurrent.thread

/* Race condition - состояние гонки */

fun main() {
    val counter = Counter()
    val thread1 = thread {
        repeat(1_000_000) {
            counter.increment()
        }
    }

    val thread2 = thread {
        repeat(1_000_000) {
            counter.increment()
        }
    }

    /* Сначала дождемся пока 2 потока выполнятся, а затем перейдем к функции println() */

    thread1.join()
    thread2.join()
    println(counter.number)
}

class Counter {

    /* Mutex или замок принимает значение "занято", когда в него попадает поток и "свободен", когда поток выходит.
    Такой замок прикреплен к каждому объекту в Kotlin, поэтому в качестве параметра в () после ключевого слова
     synchronized мы можем передавать любой объект */

    private val lock = Any()

    var number: Int = 0

    fun increment() {

        /* Сначала зайдет первый поток, после того, как состояние замка измениться, то зайдет второй поток, чтобы
        исключить из кода race condition */

        /* Участок кода в котором одновременно может находиться один поток, называется критическая секция.
        Вся это конструкция называется блок синхронизации. */

        synchronized(lock) {
            number++
        }
    }
}