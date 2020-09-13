package effectivejava.chapter6.item38;

import java.util.*;

/**
 * 
 * While the enum type (BasicOperation) is not extensible, the interface type
 * (Operation) is, and it is the interface type that is used to represent
 * operations in APIs. You can define another enum type that implements this
 * interface and use instances of this new type in place of the base type. For
 * example, suppose you want to define an extension to the operation type shown
 * earlier, consisting of the exponentiation and remainder operations. All you
 * have to do is write an enum type that implements the Operation interface:
 * 
 * You can now use your new operations anywhere you could use the basic
 * operations, provided that APIs are written to take the interface type
 * (Operation), not the implementation (BasicOperation). Note that you don’t
 * have to declare theabstract apply method in the enum as you do in a
 * nonextensible enum with instance-specific method implementations (page 162).
 * This is because the abstract method (apply) is a member of the interface
 * (Operation).
 * 
 * Not only is it possible to pass a single instance of an “extension enum”
 * anywhere a “base enum” is expected, but it is possible to pass in an entire
 * extension enum type and use its elements in addition to or instead of those
 * of the base type.
 * 
 * Version 2 is a bit less complex, and the test method is a bit more flexible:
 * it allows the caller to combine operations from multiple implementation
 * types. On the other hand, you forgo the ability to use EnumSet (Item 36) and
 * EnumMap (Item 37) on the specified operations.
 * 
 * A minor disadvantage of the use of interfaces to emulate extensible enums is
 * that implementations cannot be inherited from one enum type to another. If
 * the implementation code does not rely on any state, it can be placed in the
 * interface, using default implementations (Item 20). In the case of our
 * Operation example, the logic to store and retrieve the symbol associated with
 * an operation must be duplicated in BasicOperation and ExtendedOperation. In
 * this case it doesn’t matter because very little code is duplicated. If there
 * were a larger amount of shared functionality, you could encapsulate it in a
 * helper class or a static helper method to eliminate the code duplication.
 *
 */

// Emulated extensible enum (Pages 176-9)
public enum ExtendedOperation implements Operation {
    EXP("^") {
	public double apply(double x, double y) {
	    return Math.pow(x, y);
	}
    },
    REMAINDER("%") {
	public double apply(double x, double y) {
	    return x % y;
	}
    };
    private final String symbol;

    ExtendedOperation(String symbol) {
	this.symbol = symbol;
    }

    @Override
    public String toString() {
	return symbol;
    }

    public static void main(String[] args) {
	double x = Double.parseDouble("5.0");
	double y = Double.parseDouble("2.0");

	// Note class literal for the extended operation type (ExtendedOperation.class)
	// is passed from main to test to describe the set of extended operations.The
	// class literal serves as a bounded type token (Item 33).
	test(ExtendedOperation.class, x, y);

	// A second alternative is to pass a Collection<? extends Operation>, which
	// is a bounded wildcard type (Item 31), instead of passing a class object:
	test(Arrays.asList(ExtendedOperation.values()), x, y);
    }

    // Version 1 : Using an enum class object to represent a collection of extended
    // enums (page 178)
    private static <T extends Enum<T> & Operation> void test(Class<T> opEnumType, double x, double y) {
	for (Operation op : opEnumType.getEnumConstants())
	    System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
    }

    // Version 2 : Using a collection instance to represent a collection of extended
    // enums (page 178)
    private static void test(Collection<? extends Operation> opSet, double x, double y) {
	for (Operation op : opSet)
	    System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
    }
}
