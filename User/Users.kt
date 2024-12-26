package User


fun main() {
    UserRepository.getInstance("qwerty").users.forEach(::println)
    UserRepository.getInstance("qwerty").users.forEach(::println)
}