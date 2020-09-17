package effectivejava.chapter8.item52;

import java.util.*;
import java.math.*;

/**
 * 
 * Item 52: Use overloading judiciously.
 * 
 * It prints Unknown Collection three times. Why does this happen? Because the
 * classify method is overloaded, and the choice of which overloading to invoke
 * is made at compile time. For all three iterations of the loop, the
 * compile-time type of the parameter is the same: Collection<?>.
 * 
 * The behavior of this program is counterintuitive because selection among
 * overloaded methods is static, while selection among overridden methods is
 * dynamic. The correct version of an overridden method is chosen at
 * runtime,based on the runtime type of the object on which the method is
 * invoked.
 * 
 * To summarize, just because you can overload methods doesn’t mean you should.
 * It is generally best to refrain from overloading methods with multiple
 * signatures that have the same number of parameters. In some cases, especially
 * where constructors are involved, it may be impossible to follow this advice.
 * In these cases, you should at least avoid situations where the same set of
 * parameters can be passed to different overloadings by the addition of casts.
 * If this cannot be avoided, for example, because you are retrofitting an
 * existing class to implement a new interface, you should ensure that all
 * overloadings behave identically when passed the same parameters. If you fail
 * to do this, programmers will be hard pressed to make effective use of the
 * overloaded method or constructor, and they won’t understand why it doesn’t
 * work.
 *
 */
// Broken! - What does this program print?  (Page 238)
public class CollectionClassifier {
	public static String classify(Set<?> s) {
		return "Set";
	}

	public static String classify(List<?> lst) {
		return "List";
	}

	public static String classify(Collection<?> c) {
		return "Unknown Collection";
	}

	// Repaired static classifier method. (Page 240)
	public static String classifyFixed(Collection<?> c) {
		return c instanceof Set ? "Set" : c instanceof List ? "List" : "Unknown Collection";
	}

	public static void main(String[] args) {
		Collection<?>[] collections = { new HashSet<String>(), new ArrayList<BigInteger>(),
				new HashMap<String, String>().values() };

		for (Collection<?> c : collections)
			System.out.println(classify(c));

		System.out.println("-----------------------------");
		for (Collection<?> c : collections)
			System.out.println(classifyFixed(c));
	}
}
