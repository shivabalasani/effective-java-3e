package effectivejava.chapter2.item5;

import java.util.List;
import java.util.Objects;

/**
 * 
 * Item 5: Prefer dependency injection to hard wiring resources.
 * 
 * Static utility classes and singletons are inappropriate for classes whose behavior is parameterized by an underlying resource.
 * 
 * A useful variant of the pattern is to pass a resource factory to the constructor. The Supplier<T> interface, introduced in Java 8, is perfect for representing factories.
 * Here is a method that makes a mosaic using a client-provided factory to produce each tile:
 * Mosaic create(Supplier<? extends Tile> tileFactory) { ... }
 *
 */
//Dependency injection provides flexibility and testability
public class SpellChecker {
	//private final Lexicon dictionary
	private final List dictionary;

	public SpellChecker(List dictionary) {
		this.dictionary = Objects.requireNonNull(dictionary);
	}

	public boolean isValid(String word) {
		return true;
	}

	public List<String> suggestions(String typo) {
		return null;
	}
}

//Inappropriate use of static utility - inflexible & untestable!
class SpellChecker1 {
	// private static final Lexicon dictionary = ...;

	private SpellChecker1() {
	} // Noninstantiable

	public static boolean isValid(String word) {
		return true;
	}

	public static List<String> suggestions(String typo) {
		return null;
	}
}

//Inappropriate use of singleton - inflexible & untestable!
class SpellChecker2 {
	// private final Lexicon dictionary = ...;
	private SpellChecker2() {
	}

	public static SpellChecker2 INSTANCE = new SpellChecker2();

	public boolean isValid(String word) {
		return true;
	}

	public List<String> suggestions(String typo) {
		return null;
	}
}
