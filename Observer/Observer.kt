package Observer

fun interface Observer<T> {
    fun onChanged(items: T)
}