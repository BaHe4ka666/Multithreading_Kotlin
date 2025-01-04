package Builder

fun main() {
    val drink = Drink.Builder()
        .type("Tea")
        .temperature("Hot")
        .build()

    println(drink)
}