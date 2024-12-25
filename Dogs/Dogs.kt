package Dogs

fun main() {
    DogsRepository.getInstance("qwerty").dogs.forEach(::println)
}