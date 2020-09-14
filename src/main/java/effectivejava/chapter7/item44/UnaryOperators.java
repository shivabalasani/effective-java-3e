package effectivejava.chapter7.item44;

import java.util.function.Function;

/**
 * 
 * Unary Operator
 * -----------------------------------------------------------------------------
 * UnaryOperator<T> : Represents an operation on a single operand that produces
 * a result of the same type as its operand (reference type)
 * 
 * DoubleUnaryOperator : Accepts single double-valued operand and produces a
 * double-valued result -- double applyAsDouble(double operand);
 * 
 * IntUnaryOperator : Accepts a single int-valued operand and produces an
 * int-valued result -- int applyAsInt(int operand);
 * 
 * LongUnaryOperator : Accepts a single long-valued operand and produces a
 * long-valued result -- long applyAsLong(long operand);
 *
 */
public interface UnaryOperators<T> {

	/** UnaryOperator<T> extends Function<T, T> */
	T apply(T t);

	/** DoubleUnaryOperator */
	double applyAsDouble(double operand);

	/** IntUnaryOperator */
	int applyAsInt(int operand);

	/** LongUnaryOperator */
	long applyAsLong(long operand);

}
