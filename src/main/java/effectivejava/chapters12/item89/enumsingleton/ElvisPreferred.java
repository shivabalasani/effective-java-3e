package effectivejava.chapters12.item89.enumsingleton;

import java.util.*;

/**
 * The use of readResolve for instance control is not obsolete. If you have to
 * write a serializable instance-controlled class whose instances are not known
 * at compile time, you will not be able to represent the class as an enum type.
 * 
 * The accessibility of readResolve is significant. If you place a readResolve
 * method on a final class, it should be private. If you place a readResolve
 * method on a nonfinal class, you must carefully consider its accessibility. If
 * it is private, it will not apply to any subclasses. If it is package-private,
 * it will apply only to subclasses in the same package. If it is protected or
 * public, it will apply to all subclasses that do not override it. If a
 * readResolve method is protected or public and a subclass does not override
 * it, deserializing a subclass instance will produce a superclass instance,
 * which is likely to cause a ClassCastException.
 * 
 * To summarize, use enum types to enforce instance control invariants wherever
 * possible. If this is not possible and you need a class to be both
 * serializable and instance-controlled, you must provide a readResolve method
 * and ensure that all of the class’s instance fields are either primitive or
 * transient.
 *
 */
// Enum singleton - the preferred approach - Page 311
public enum ElvisPreferred {
	INSTANCE;

	private String[] favoriteSongs = { "Hound Dog", "Heartbreak Hotel" };

	public void printFavorites() {
		System.out.println(Arrays.toString(favoriteSongs));
	}
}