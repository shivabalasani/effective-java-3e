package effectivejava.chapter6.item34;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * 
 * Just as with other classes, unless you have a compelling reason to expose an
 * enum method to its clients, declare it private or, if need be,
 * package-private (Item 15).
 * 
 * If an enum is generally useful, it should be a top-level class; if its use is
 * tied to a specific top-level class, it should be a member class of that
 * top-level class (Item 24). For example, the java.math.RoundingMode enum
 * represents a rounding mode for decimal fractions. These rounding modes are
 * used by the BigDecimal class, but they provide a useful abstraction that is
 * not fundamentally tied to BigDecimal. By making RoundingMode a top-level
 * enum, the library designers encourage any programmer who needs rounding modes
 * to reuse this enum, leading to increased consistency across APIs.
 * 
 * The techniques demonstrated in the Planet example are sufficient for most
 * enum types, but sometimes you need more. There is different data associated
 * with each Planet constant, but sometimes you need to associate fundamentally
 * different behavior with each constant.
 * 
 * Luckily, there is a better way to associate a different behavior with each
 * enum constant: declare an abstract apply method in the enum type, and
 * override it with a concrete method for each constant in a constant-specific
 * class body. Such methods are known as constant-specific method
 * implementations:
 * 
 * Enum types have an automatically generated valueOf(String) method that
 * translates a constant’s name into the constant itself. If you override the
 * toString method in an enum type, consider writing a fromString method to
 * translate the custom string representation back to the corresponding enum.
 *
 */
// Enum type with constant-specific class bodies and data (Pages 163-4)
public enum Operation {
	PLUS("+") {
		public double apply(double x, double y) {
			return x + y;
		}
	},
	MINUS("-") {
		public double apply(double x, double y) {
			return x - y;
		}
	},
	TIMES("*") {
		public double apply(double x, double y) {
			return x * y;
		}
	},

	DIVIDE("/") {
		public double apply(double x, double y) {
			return x / y;
		}
	};

	private final String symbol;

	Operation(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		return symbol;
	}

	public abstract double apply(double x, double y);

	// Implementing a fromString method on an enum type (Page 164)
	private static final Map<String, Operation> stringToEnum = Stream.of(values())
			.collect(toMap(Object::toString, e -> e));

	// Returns Operation for string, if any
	public static Optional<Operation> fromString(String symbol) {
		return Optional.ofNullable(stringToEnum.get(symbol));
	}

	public static void main(String[] args) {
		double x = Double.parseDouble("25.0");
		double y = Double.parseDouble("25.0");

		System.out.println(stringToEnum);
		System.out.println(fromString("+"));

		for (Operation op : Operation.values()) {
			// System.out.println(op.toString());
			System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
		}

	}
}
