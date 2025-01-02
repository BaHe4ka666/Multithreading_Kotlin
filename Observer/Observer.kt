package Observer

interface Observer<T> {
    fun onChanged(items: T)
}