package effectivejava.chapters11.item79;

/**
 * 
 * This interface is structurally identical to BiConsumer<ObservableSet<E>,E>.
 * We chose to define a custom functional interface because the interface and
 * method names make the code more readable and because the interface could
 * evolve to incorporate multiple callbacks. That said, a reasonable argument
 * could also be made for using BiConsumer (Item 44).
 *
 * @param <E>
 */
// Set obeserver callback interface - Page 266
public interface SetObserver<E> {
    // Invoked when an element is added to the observable set
    void added(ObservableSet<E> set, E element);
}
