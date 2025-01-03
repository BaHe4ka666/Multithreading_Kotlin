package Observer

class MutableObservable<T>(initialValue: T) : Observable<T> {

    override var currentValue: T = initialValue
        set(value) {
            field = value
            notifyObserver()
        }

    private val _observers = mutableListOf<Observer<T>>()
    override val observers: List<Observer<T>>
        get() = _observers.toList()


    override fun registerObserver(observer: Observer<T>) {
        _observers.add(observer)
        observer.onChanged(currentValue)
    }

    override fun unregisterObserver(observer: Observer<T>) {
        _observers.remove(observer)
    }
}