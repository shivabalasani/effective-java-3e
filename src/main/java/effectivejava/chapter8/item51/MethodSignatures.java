package effectivejava.chapter8.item51;

/**
 * 
 * Item 51: Design method signatures carefully.
 * 
 * Choose method names carefully. Names should always obey the standard naming
 * conventions (Item 68). Your primary goal should be to choose names that are
 * understandable and consistent with other names in the same package. Your
 * secondary goal should be to choose names consistent with the broader
 * consensus, where it exists.
 * 
 * Don’t go overboard in providing convenience methods.
 * 
 * Avoid long parameter lists. Aim for four parameters or fewer. Most
 * programmers can’t remember longer parameter lists.Long sequences of
 * identically typed parameters are especially harmful.
 * 
 * There are three techniques for shortening overly long parameter lists. One is
 * to break the method up into multiple methods, each of which requires only a
 * subset of the parameters. If done carelessly, this can lead to too many
 * methods, but it can also help reduce the method count by increasing
 * orthogonality.
 * 
 * A second technique for shortening long parameter lists is to create helper
 * classes to hold groups of parameters. Typically these helper classes are
 * static member classes (Item 24). This technique is recommended if a
 * frequently occurring sequence of parameters is seen to represent some
 * distinct entity.
 * 
 * A third technique that combines aspects of the first two is to adapt the
 * Builder pattern (Item 2) from object construction to method invocation.
 * 
 * For parameter types, favor interfaces over classes (Item 64). If there is an
 * appropriate interface to define a parameter, use it in favor of a class that
 * implements the interface. For example, there is no reason to ever write a
 * method that takes HashMap on input—use Map instead.
 *
 */
public class MethodSignatures {

	// Prefer two-element enum types to boolean parameters,
	public enum TemperatureScale {
		FAHRENHEIT, CELSIUS
	};

	public static void main(String[] args) {
		// Not only does Thermometer.newInstance(TemperatureScale.CELSIUS) make
		// a lot more sense than Thermometer.newInstance(true), but you can add KELVIN
		// to TemperatureScale in a future release without having to add a new static
		// factory to Thermometer.
	}

}
