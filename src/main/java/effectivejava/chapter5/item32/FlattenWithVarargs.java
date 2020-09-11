package effectivejava.chapter5.item32;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Prior to Java 7, there was nothing the author of a method with a generic
 * varargs parameter could do about the warnings at the call sites. This made
 * these APIs unpleasant to use. Users had to put up with the warnings or,
 * preferably, to eliminate them with @SuppressWarnings("unchecked") annotations
 * at every call site (Item 27). This was tedious, harmed readability, and hid
 * warnings that flagged real issues.
 * 
 * In Java 7, the SafeVarargs annotation was added to the platform, to allow the
 * author of a method with a generic varargs parameter to suppress client
 * warnings automatically. In essence, the SafeVarargs annotation constitutes a
 * promise by the author of a method that it is typesafe. In exchange for this
 * promise, the compiler agrees not to warn the users of the method that calls
 * may be unsafe.
 * 
 * The rule for deciding when to use the SafeVarargs annotation is simple: Use
 * SafeVarargs on every method with a varargs parameter of a generic or
 * parameterized type, so its users won’t be burdened by needless and confusing
 * compiler warnings.
 * 
 * Note that the SafeVarargs annotation is legal only on methods that can’t be
 * overridden, because it is impossible to guarantee that every possible
 * overriding method will be safe. In Java 8, the annotation was legal only on
 * static methods and final instance methods; in Java 9, it became legal on
 * private instance methods as well.
 *
 */
// Safe method with a generic varargs parameter (page 149)
public class FlattenWithVarargs {

	@SafeVarargs
	static <T> List<T> flatten(List<? extends T>... lists) {
		List<T> result = new ArrayList<>();
		for (List<? extends T> list : lists)
			result.addAll(list);
		return result;
	}

	public static void main(String[] args) {
		List<Integer> flatList = flatten(List.of(1, 2), List.of(3, 4, 5), List.of(6, 7));
		System.out.println(flatList);
	}
}
