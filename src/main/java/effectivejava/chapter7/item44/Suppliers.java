package effectivejava.chapter7.item44;

/**
 * -------------------------------------------------------------------------------
 * Supplier
 * -------------------------------------------------------------------------------
 * Supplier<T> : Represents a supplier of results (reference type) -- T get();
 * 
 * DoubleSupplier : A supplier of double-valued results -- double getAsDouble();
 * 
 * IntSupplier : A supplier of int-valued results -- int getAsInt();
 * 
 * LongSupplier : A supplier of long-valued results -- long getAsLong();
 * 
 * BooleanSupplier : A supplier of boolean-valued results -- boolean
 * getAsBoolean();
 *
 */
public interface Suppliers<T> {

	/** Supplier<T> */
	T get();

	/** DoubleSupplier */
	double getAsDouble();

	/** IntSupplier */
	int getAsInt();

	/** LongSupplier */
	long getAsLong();

	/** BooleanSupplier */
	boolean getAsBoolean();

}
