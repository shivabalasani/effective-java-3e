package effectivejava.chapter6.item34;

/**
 * 
 * If switch statements on enums are not a good choice for implementing
 * constant-specific behavior on enums, what are they good for? Switches on
 * enums are good for augmenting enum types with constant-specific behavior. For
 * example, suppose the Operation enum is not under your control and you wish it
 * had an instance method to return the inverse of each operation.
 * 
 * You should also use this technique on enum types that are under your control
 * if a method simply doesn’t belong in the enum type. The method may be
 * required for some use but is not generally useful enough to merit inclusion
 * in the enum type.
 *
 */

// Switch on an enum to simulate a missing method (Page 167)
public class Inverse {
	public static Operation inverse(Operation op) {
		switch (op) {
		case PLUS:
			return Operation.MINUS;
		case MINUS:
			return Operation.PLUS;
		case TIMES:
			return Operation.DIVIDE;
		case DIVIDE:
			return Operation.TIMES;

		default:
			throw new AssertionError("Unknown op: " + op);
		}
	}

	public static void main(String[] args) {
		double x = Double.parseDouble(args[0]);
		double y = Double.parseDouble(args[1]);
		for (Operation op : Operation.values()) {
			Operation invOp = inverse(op);
			System.out.printf("%f %s %f %s %f = %f%n", x, op, y, invOp, y, invOp.apply(op.apply(x, y), y));
		}
	}
}
