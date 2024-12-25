package org.example.multithreading

import kotlin.concurrent.thread

fun main() {

    thread {
        repeat(100_000) {
            print(" 0 ")
            Thread.sleep(1000)
        }

    }

    repeat(100_000) {
        print(" * ")
        Thread.sleep(1000)
    }

}