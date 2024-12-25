package multithreading

import kotlin.concurrent.thread
import kotlin.random.Random

fun main() {
    println("Enter the number from 1 to 1_000_000_000")
    val number = readln().toInt()
    var win = false

    thread {
        var sec = 0
        while (!win) {
            sec++
            println(sec)
            Thread.sleep(1000)
        }
    }

    thread {
        while (!win) {
            val random = Random.nextInt(1, 1_000_000_000)
            if (random == number) {
                println("I win. Your number is $number")
                win = true
            }
        }
    }


}