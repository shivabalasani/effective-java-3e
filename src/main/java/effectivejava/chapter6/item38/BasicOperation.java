package effectivejava.chapter6.item38;

/**
 * 
 * Item 38: Emulate extensible enums with interfaces
 * 
 * Extensibility of enums turns out to be a bad idea. It is confusing that
 * elements of an extension type are instances of the base type and not vice
 * versa. There is no good way to enumerate over all of the elements of a base
 * type and its extensions. Finally, extensibility would complicate many aspects
 * of the design and implementation.
 *
 * That said, there is at least one compelling use case for extensible
 * enumerated types, which is operation codes, also known as opcodes. An opcode
 * is an enumerated type whose elements represent operations on some machine,
 * such as the Operation type in Item 34, which represents the functions on a
 * simple calculator. Sometimes it is desirable to let the users of an API
 * provide their own operations, effectively extending the set of operations
 * provided by the API.
 * 
 * Luckily, there is a nice way to achieve this effect using enum types. The
 * basic idea is to take advantage of the fact that enum types can implement
 * arbitrary interfaces by defining an interface for the opcode type and an enum
 * that is the standard implementation of the interface.
 * 
 * The pattern described in this item is used in the Java libraries. For
 * example, the java.nio.file.LinkOption enum type implements the CopyOption and
 * OpenOption interfaces.
 * 
 * In summary, while you cannot write an extensible enum type, you can emulate
 * it by writing an interface to accompany a basic enum type that implements the
 * interface. This allows clients to write their own enums (or other types) that
 * implement the interface. Instances of these types can then be used wherever
 * instances of the basic enum type can be used, assuming APIs are written in
 * terms of the interface.
 *
 */

// Emulated extensible enum using an interface - Basic implementation (Page 176)
public enum BasicOperation implements Operation {
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

	BasicOperation(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		return symbol;
	}
}
