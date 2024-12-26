package Dogs

fun main() {
    DogsRepository.getInstance("qwerty").dogs.forEach(::println)
    val d = DogsRepository.instance
    println(d.toString())
}