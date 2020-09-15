package effectivejava.chapter7.item44;

/**
 * --------------------------------------------------------------------------
 * Binary Operator
 * --------------------------------------------------------------------------
 * BinaryOperator<T> : Represents an operation upon two operands of the same
 * type, producing a result of the same type as the operands (reference type) T
 * apply(T t1, T t2);
 * 
 * DoubleBinaryOperator : Accepts two double-valued operands and produces a
 * double-valued result -- double applyAsDouble(double left, double right);
 * 
 * IntBinaryOperator : Accepts two int-valued operands and produces an
 * int-valued result -- int applyAsInt(int left, int right);
 * 
 * LongBinaryOperator : Accepts two long-valued operands and produces a
 * long-valued result. -- long applyAsLong(long left, long right);
 *
 */
public interface BinaryOperators<T> {

	/** BinaryOperator<T> extends BiFunction<T,T,T> */
	T apply(T t1, T t2);

	/** DoubleBinaryOperator */
	double applyAsDouble(double left, double right);

	/** IntBinaryOperator */
	int applyAsInt(int left, int right);

	/** LongBinaryOperator */
	long applyAsLong(long left, long right);

}
