package effectivejava.chapter7.item44;

/**
 * 
 * ------------------------------------------------------------------------
 * Function
 * ------------------------------------------------------------------------
 * Function<T,R> : Represents a function that accepts one argument and produces
 * a result (reference type) -- R apply(T t);
 * 
 * DoubleFunction<R> : Accepts a double-valued argument and produces a result --
 * R apply(double value);
 * 
 * IntFunction<R> : Accepts an int-valued argument and produces a result ------
 * R apply(int value);
 * 
 * LongFunction<R> : Accepts a long-valued argument and produces a result -----
 * R apply(long value);
 * 
 * DoubleToIntFunction : Accepts a double-valued argument and produces an
 * int-valued result -- int applyAsInt(double value);
 * 
 * DoubleToLongFunction : Accepts a double-valued argument and produces a
 * long-valued result -- long applyAsLong(double value);
 * 
 * IntToDoubleFunction : Accepts an int-valued argument and produces a
 * double-valued result -- double applyAsDouble(int value);
 * 
 * IntToLongFunction : Accepts an int-valued argument and produces a long-valued
 * result -- long applyAsLong(int value);
 * 
 * LongToIntFunction : Accepts a long-valued argument and produces an int-valued
 * result -- int applyAsInt(long value);
 * 
 * LongToDoubleFunction : Accepts a long-valued argument and produces a
 * double-valued result. -- double applyAsDouble(long value);
 * 
 * ToDoubleFunction<T> : Accepts a reference type and produces an int-valued
 * result -- double applyAsDouble(T value);
 * 
 * ToIntFunction<T> : Accepts a reference type and produces an int-valued result
 * -- int applyAsInt(T value);
 * 
 * ToLongFunction<T> : Accepts a reference type and produces a long-valued
 * result. -- long applyAsLong(T value);
 * 
 * BiFunction<T,U,R> : Represents a function that accepts two arguments and
 * produces a result (reference type) -- R apply(T t, U u);
 * 
 * ToDoubleBiFunction<T,U> : Accepts two reference type arguments and produces a
 * double-valued result -- double applyAsDouble(T t, U u);
 * 
 * ToIntBiFunction<T,U> : Accepts two reference type arguments and produces an
 * int-valued result -- int applyAsInt(T t, U u);
 * 
 * ToLongBiFunction<T,U> : Accepts two reference type arguments and produces a
 * long-valued result -- long applyAsLong(T t, U u);
 *
 */
public interface Functions<T, U, R> {

	/** Function<T,R> */
	R apply(T t);

	/** DoubleFunction<R> */
	R apply(double value);

	/** IntFunction<R> */
	R apply(int value);

	/** LongFunction<R> */
	R apply(long value);

	/** DoubleToIntFunction */
	int applyAsInt(double value);

	/** DoubleToLongFunction */
	long applyAsLong(double value);

	/** IntToDoubleFunction */
	double applyAsDouble(int value);

	/** IntToLongFunction */
	long applyAsLong(int value);

	/** LongToIntFunction */
	int applyAsInt(long value);

	/** LongToDoubleFunction */
	double applyAsDouble(long value);

	/** ToDoubleFunction<T> */
	double applyAsDouble(T value);

	/** ToIntFunction<T> */
	int applyAsInt(T value);

	/** ToLongFunction<T> */
	long applyAsLong(T value);

	/** BiFunction<T,U,R> */
	R apply(T t, U u);

	/** ToDoubleBiFunction<T,U> */
	double applyAsDouble(T t, U u);

	/** ToIntBiFunction<T,U> */
	int applyAsInt(T t, U u);

	/** ToLongBiFunction<T,U> */
	long applyAsLong(T t, U u);

}
