package effectivejava.chapter7.item44;

/**
 * --------------------------------------------------------------------------------
 * Consumer
 * --------------------------------------------------------------------------------
 * Consumer<T> : Represents an operation that accepts a single (reference type)
 * input argument and returns no result -- void accept(T t);
 * 
 * DoubleConsumer : Accepts a single double-valued argument and returns no
 * result -- void accept(double value);
 * 
 * IntConsumer : Accepts a single int-valued argument and returns no result --
 * void accept(int value);
 * 
 * LongConsumer : Accepts a single long-valued argument and returns no result --
 * void accept(long value);
 * 
 * BiConsumer<T,U> : Represents an operation that accepts two (reference type)
 * input arguments and returns no result -- void accept(T t, U u);
 * 
 * ObjDoubleConsumer<T> : Accepts an object-valued and a double-valued argument,
 * and returns no result -- void accept(T t, double value);
 * 
 * ObjIntConsumer<T> : Accepts an object-valued and an int-valued argument, and
 * returns no result -- void accept(T t, int value);
 * 
 * ObjLongConsumer<T> : Accepts an object-valued and a long-valued argument, and
 * returns no result -- void accept(T t, long value);
 *
 */
public interface Consumers<T, U> {

	/** Consumer<T> */
	void accept(T t);

	/** DoubleConsumer */
	void accept(double value);

	/** IntConsumer */
	void accept(int value);

	/** LongConsumer */
	void accept(long value);

	/** BiConsumer<T,U> */
	void accept(T t, U u);

	/** ObjDoubleConsumer<T> */
	void accept(T t, double value);

	/** ObjIntConsumer<T> */
	void accept(T t, int value);

	/** ObjLongConsumer<T> */
	void accept(T t, long value);

}
