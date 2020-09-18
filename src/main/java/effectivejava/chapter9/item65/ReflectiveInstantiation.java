package effectivejava.chapter9.item65;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;

/**
 * 
 * Item 65: Prefer interfaces to reflection.
 * 
 * The core reflection facility, java.lang.reflect, offers programmatic access
 * to arbitrary classes. Given a Class object, you can obtain Constructor,
 * Method, and Field instances representing the constructors, methods, and
 * fields of the class represented by the Class instance. These objects provide
 * programmatic access to the class�s member names, field types, method
 * signatures, and so on.
 * 
 * Moreover, Constructor, Method, and Field instances let you manipulate their
 * underlying counterparts reflectively: you can construct instances, invoke
 * methods, and access fields of the underlying class by invoking methods on the
 * Constructor, Method, and Field instances. For example, Method.invoke lets you
 * invoke any method on any object of any class (subject to the usual security
 * constraints). Reflection allows one class to use another, even if the latter
 * class did not exist when the former was compiled. This power, however, comes
 * at a price:
 * 
 * � You lose all the benefits of compile-time type checking, including
 * exception checking.
 * 
 * � The code required to perform reflective access is clumsy and verbose. It is
 * tedious to write and difficult to read.
 * 
 * � Performance suffers. Reflective method invocation is much slower than
 * normal method invocation.
 *
 * There are a few sophisticated applications that require reflection. Examples
 * include code analysis tools and dependency injection frameworks. Even such
 * tools have been moving away from reflection of late, as its disadvantages
 * become clearer. If you have any doubts as to whether your application
 * requires reflection, it probably doesn�t.
 * 
 * You can obtain many of the benefits of reflection while incurring few of its
 * costs by using it only in a very limited form. For many programs that must
 * use a class that is unavailable at compile time, there exists at compile time
 * an appropriate interface or superclass by which to refer to the class (Item
 * 64). If this is the case, you can create instances reflectively and access
 * them normally via their interface or superclass.
 * 
 * While this program is just a toy, the technique it demonstrates is quite
 * powerful. The toy program could easily be turned into a generic set tester
 * that validates the specified Set implementation by aggressively manipulating
 * one or more instances and checking that they obey the Set contract.
 * Similarly, it could be turned into a generic set performance analysis tool.
 * In fact, this technique is sufficiently powerful to implement a full-blown
 * service provider framework (Item 1). Usually, this technique is all that you
 * need in the way of reflection.
 * 
 * This example demonstrates two disadvantages of reflection. First, the example
 * can generate six different exceptions at runtime, all of which would have
 * been compile-time errors if reflective instantiation were not used.The second
 * disadvantage is that it takes twenty-five lines of tedious code to generate
 * an instance of the class from its name, whereas a constructor invocation
 * would fit neatly on a single line.The length of the program could be reduced
 * by catching ReflectiveOperationException, a superclass of the various
 * reflective exceptions that was introduced in Java 7.Both disadvantages are
 * restricted to the part of the program that instantiates the object. Once
 * instantiated, the set is indistinguishable from any other Set instance. In a
 * real program, the great bulk of the code is thus unaffected by this limited
 * use of reflection.
 * 
 * In summary, reflection is a powerful facility that is required for certain
 * sophisticated system programming tasks, but it has many disadvantages. If you
 * are writing a program that has to work with classes unknown at compile time,
 * you should, if at all possible, use reflection only to instantiate objects,
 * and access the objects using some interface or superclass that is known at
 * compile time.
 */

// Reflective instantiaion demo (Page 283)
public class ReflectiveInstantiation {
	// Reflective instantiation with interface access
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// Translate the class name into a Class object
		Class<? extends Set<String>> cl = null;
		try {
			cl = (Class<? extends Set<String>>) // Unchecked cast!
			Class.forName("java.util.HashSet");
		} catch (ClassNotFoundException e) {
			fatalError("Class not found.");
		}

		// Get the constructor
		Constructor<? extends Set<String>> cons = null;
		try {
			cons = cl.getDeclaredConstructor();
		} catch (NoSuchMethodException e) {
			fatalError("No parameterless constructor");
		}

		// Instantiate the set
		Set<String> s = null;
		try {
			s = cons.newInstance();
		} catch (IllegalAccessException e) {
			fatalError("Constructor not accessible");
		} catch (InstantiationException e) {
			fatalError("Class not instantiable.");
		} catch (InvocationTargetException e) {
			fatalError("Constructor threw " + e.getCause());
		} catch (ClassCastException e) {
			fatalError("Class doesn't implement Set");
		}

		// Exercise the set
		s.addAll(Arrays.asList("w", "b", "c", "z", "a").subList(0, 5));
		System.out.println(s);
	}

	private static void fatalError(String msg) {
		System.err.println(msg);
		System.exit(1);
	}
}
