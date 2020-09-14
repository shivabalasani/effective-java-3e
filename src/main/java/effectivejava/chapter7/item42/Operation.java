package effectivejava.chapter7.item42;

import java.util.function.DoubleBinaryOperator;

/**
 * 
 * Item 34 says that enum instance fields are preferable to constant-specific
 * class bodies. Lambdas make it easy to implement constant-specific behavior
 * using the former instead of the latter. Merely pass a lambda implementing
 * each enum constant’s behavior to its constructor. The constructor stores the
 * lambda in an instance field, and the apply method forwards invocations to the
 * lambda. The resulting code is simpler and clearer than the original version:
 * 
 * Note that we’re using the DoubleBinaryOperator interface for the lambdas that
 * represent the enum constant’s behavior. This is one of the many predefined
 * functional interfaces in java.util.function (Item 44). It represents a
 * function that takes two double arguments and returns a double result.
 * 
 * Looking at the lambda-based Operation enum, you might think constant specific
 * method bodies have outlived their usefulness, but this is not the case.
 * Unlike methods and classes, lambdas lack names and documentation; if a
 * computation isn’t self-explanatory, or exceeds a few lines, don’t put it in a
 * lambda. One line is ideal for a lambda, and three lines is a reasonable
 * maximum. If you violate this rule, it can cause serious harm to the
 * readability of your programs. If a lambda is long or difficult to read,
 * either find a way to simplify it or refactor your program to eliminate it.
 * Also, the arguments passed to enum constructors are evaluated in a static
 * context. Thus, lambdas in enum constructors can’t access instance members of
 * the enum. Constant-specific class bodies are still the way to go if an enum
 * type has constant-specific behavior that is difficult to understand, that
 * can’t be implemented in a few lines, or that requires access to instance
 * fields or methods.
 * 
 * Likewise, you might think that anonymous classes are obsolete in the era of
 * lambdas. This is closer to the truth, but there are a few things you can do
 * with anonymous classes that you can’t do with lambdas. Lambdas are limited to
 * functional interfaces. If you want to create an instance of an abstract
 * class, you can do it with an anonymous class, but not a lambda. Similarly,
 * you can use anonymous classes to create instances of interfaces with multiple
 * abstract methods. Finally, a lambda cannot obtain a reference to itself. In a
 * lambda, the this keyword refers to the enclosing instance, which is typically
 * what you want. In an anonymous class, the this keyword refers to the
 * anonymous class instance. If you need access to the function object from
 * within its body, then you must use an anonymous class.
 * 
 * Lambdas share with anonymous classes the property that you can’t reliably
 * serialize and deserialize them across implementations. Therefore, you should
 * rarely, if ever, serialize a lambda (or an anonymous class instance). If you
 * have a function object that you want to make serializable, such as a
 * Comparator, use an instance of a private static nested class (Item 24).
 *
 */
// Enum with function object fields & constant-specific behavior (Page 195)
public enum Operation {
	PLUS("+", (x, y) -> x + y), MINUS("-", (x, y) -> x - y), TIMES("*", (x, y) -> x * y), DIVIDE("/", (x, y) -> x / y);

	private final String symbol;
	private final DoubleBinaryOperator op;

	Operation(String symbol, DoubleBinaryOperator op) {
		this.symbol = symbol;
		this.op = op;
	}

	@Override
	public String toString() {
		return symbol;
	}

	public double apply(double x, double y) {
		return op.applyAsDouble(x, y);
	}

	// Main method from Item 34 (Page 163)
	public static void main(String[] args) {
		double x = Double.parseDouble(args[0]);
		double y = Double.parseDouble(args[1]);
		for (Operation op : Operation.values())
			System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
	}
}
