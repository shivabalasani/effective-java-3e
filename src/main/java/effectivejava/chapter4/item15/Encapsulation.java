package effectivejava.chapter4.item15;

/**
 * 
 * Item 15: Minimize the accessibility of classes and members
 * 
 * The access control mechanism [JLS, 6.6] specifies the accessibility of
 * classes, interfaces, and members. The accessibility of an entity is
 * determined by the location of its declaration and by which, if any, of the
 * access modifiers (private, protected, and public) is present on the
 * declaration. Proper use of these modifiers is essential to information
 * hiding. The rule of thumb is simple: make each class or member as
 * inaccessible as possible.
 * 
 * For top-level (non-nested) classes and interfaces, there are only two
 * possible access levels: package-private and public.If a package-private
 * top-level class or interface is used by only one class, consider making the
 * top-level class a private static nested class of the sole class that uses it
 * (Item 24).
 * 
 * For members (fields, methods, nested classes, and nested interfaces), there
 * are four possible access levels, listed here in order of increasing
 * accessibility:
 * 
 * • private—The member is accessible only from the top-level class where it is
 * declared.
 * 
 * • package-private—The member is accessible from any class in the package
 * where it is declared. Technically known as default access, this is the access
 * level you get if no access modifier is specified (except for interface
 * members, which are public by default).
 * 
 * • protected—The member is accessible from subclasses of the class where it is
 * declared (subject to a few restrictions [JLS, 6.6.2]) and from any class in
 * the package where it is declared.
 * 
 * • public—The member is accessible from anywhere
 *
 * There is a key rule that restricts your ability to reduce the accessibility
 * of methods. If a method overrides a superclass method, it cannot have a more
 * restrictive access level in the subclass than in the superclass [JLS,
 * 8.4.8.3]. This is necessary to ensure that an instance of the subclass is
 * usable anywhere that an instance of the superclass is usable (the Liskov
 * substitution principle, see Item 15).
 * 
 * Instance fields of public classes should rarely be public. Classes with
 * public mutable fields are not generally thread-safe.Ensure that objects
 * referenced by public static final fields are immutable.
 */
public class Encapsulation {

    public static void main(String[] args) {

	// Note that a nonzero-length array is always mutable, so it is wrong for a
	// class to have a public static final array field, or an accessor that returns
	// such a field.

	// Potential security hole!
	// public static final Thing[] VALUES = { ... };

	// There are two ways to fix the problem.

	// 1. You can make the public array private and add a public immutable list:

	// private static final Thing[] PRIVATE_VALUES = { ... };
	// public static final List<Thing> VALUES =
	// Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));

	// 2. Alternatively, you can make the array private and add a public method that
	// returns a copy of a private array:

	// private static final Thing[] PRIVATE_VALUES = { ... };
	// public static final Thing[] values() {
	// return PRIVATE_VALUES.clone();
	// }
    }
}
