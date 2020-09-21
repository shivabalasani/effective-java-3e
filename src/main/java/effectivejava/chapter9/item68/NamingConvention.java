package effectivejava.chapter9.item68;

/**
 * 
 * Item 68: Adhere to generally accepted naming conventions.
 * 
 * The Java platform has a well-established set of naming conventions, many of
 * which are contained in The Java Language Specification [JLS, 6.1]. Loosely
 * speaking, naming conventions fall into two categories: typographical and
 * grammatical.
 * 
 * There are only a handful of typographical naming conventions, covering
 * packages, classes, interfaces, methods, fields, and type variables. You
 * should rarely violate them and never without a very good reason. If an API
 * violates these conventions, it may be difficult to use. If an implementation
 * violates them, it may be difficult to maintain. In both cases, violations
 * have the potential to confuse and irritate other programmers who work with
 * the code and can cause faulty assumptions that lead to errors.
 * 
 * Package and module names should be hierarchical with the components separated
 * by periods. Components should consist of lowercase alphabetic characters and,
 * rarely, digits. The name of any package that will be used outside your
 * organization should begin with your organization’s Internet domain name with
 * the components reversed, for example, edu.cmu, com.google, org.eff. The
 * standard libraries and optional packages, whose names begin with java and
 * javax, are exceptions to this rule. Users must not create packages or modules
 * whose names begin with java or javax. Detailed rules for converting Internet
 * domain names to package name prefixes can be found in the JLS [JLS, 6.1].
 * 
 * The remainder of a package name should consist of one or more components
 * describing the package. Components should be short, generally eight or fewer
 * characters. Meaningful abbreviations are encouraged, for example, util rather
 * than utilities. Acronyms are acceptable, for example, awt. Components should
 * generally consist of a single word or abbreviation.
 * 
 * Class and interface names, including enum and annotation type names, should
 * consist of one or more words, with the first letter of each word capitalized,
 * for example, List or FutureTask.
 * 
 * Method and field names follow the same typographical conventions as class and
 * interface names, except that the first letter of a method or field name
 * should be lowercase, for example, remove or ensureCapacity. If an acronym
 * occurs as the first word of a method or field name, it should be lowercase.
 * 
 * The sole exception to the previous rule concerns “constant fields,” whose
 * names should consist of one or more uppercase words separated by the
 * underscore character, for example, VALUES or NEGATIVE_INFINITY. A constant
 * field is a static final field whose value is immutable. If a static final
 * field has a primitive type or an immutable reference type (Item 17), then it
 * is a constant field.
 * 
 * Local variable names have similar typographical naming conventions to member
 * names, except that abbreviations are permitted, as are individual characters
 * and short sequences of characters whose meaning depends on the context in
 * which they occur, for example, i, denom, houseNum.
 * 
 * Type parameter names usually consist of a single letter. Most commonly it is
 * one of these five: T for an arbitrary type, E for the element type of a
 * collection, K and V for the key and value types of a map, and X for an
 * exception. The return type of a function is usually R. A sequence of
 * arbitrary types can be T, U, V or T1, T2, T3. For quick reference, the
 * following table shows examples of typographical conventions.
 * 
 * -------------------------------------------------------------------------------
 * Identifier Type ---------- Examples
 * -------------------------------------------------------------------------------
 * Package or module ------- org.junit.jupiter.api, com.google.common.collect
 * 
 * Class or Interface ------ Stream, FutureTask, LinkedHashMap, HttpClient
 * 
 * Method or Field --------- remove, groupingBy, getCrc
 * 
 * Constant Field ---------- MIN_VALUE, NEGATIVE_INFINITY
 * 
 * Local Variable ---------- i, denom, houseNum
 * 
 * Type Parameter ---------- T, E, K, V, X, R, U, V, T1, T2
 *
 * ------------------------------------------------------------------------------
 * 
 * Grammatical naming conventions are more flexible and more controversial than
 * typographical conventions. There are no grammatical naming conventions to
 * speak of for packages. Instantiable classes, including enum types, are
 * generally named with a singular noun or noun phrase, such as Thread,
 * PriorityQueue, or ChessPiece. Non-instantiable utility classes (Item 4) are
 * often named with a plural noun, such as Collectors or Collections. Interfaces
 * are named like classes, for example, Collection or Comparator, or with an
 * adjective ending in able or ible, for example, Runnable, Iterable, or
 * Accessible. Because annotation types have so many uses, no part of speech
 * predominates. Nouns, verbs, prepositions, and adjectives are all common, for
 * example, BindingAnnotation, Inject, ImplementedBy, or Singleton.
 * 
 * Methods that perform some action are generally named with a verb or verb
 * phrase (including object), for example, append or drawImage. Methods that
 * return a boolean value usually have names that begin with the word is or,
 * less commonly, has, followed by a noun, noun phrase, or any word or phrase
 * that functions as an adjective, for example, isDigit, isProbablePrime,
 * isEmpty, isEnabled, or hasSiblings.
 * 
 * Methods that return a non-boolean function or attribute of the object on
 * which they’re invoked are usually named with a noun, a noun phrase, or a verb
 * phrase beginning with the verb get, for example, size, hashCode, or getTime.
 * 
 * The form beginning with get has its roots in the largely obsolete Java Beans
 * specification, which formed the basis of an early reusable component
 * architecture. There are modern tools that continue to rely on the Beans
 * naming convention, and you should feel free to use it in any code that is to
 * be used in conjunction with these tools. There is also a strong precedent for
 * following this naming convention if a class contains both a setter and a
 * getter for the same attribute. In this case, the two methods are typically
 * named getAttribute and setAttribute.
 * 
 * A few method names deserve special mention. Instance methods that convert the
 * type of an object, returning an independent object of a different type, are
 * often called toType, for example, toString or toArray. Methods that return a
 * view (Item 6) whose type differs from that of the receiving object are often
 * called asType, for example, asList. Methods that return a primitive with the
 * same value as the object on which they’re invoked are often called typeValue,
 * for example, intValue. Common names for static factories include from, of,
 * valueOf, instance, getInstance, newInstance, getType, and newType (Item 1,
 * page 9).
 * 
 * To summarize, internalize the standard naming conventions and learn to use
 * them as second nature. The typographical conventions are straightforward and
 * largely unambiguous; the grammatical conventions are more complex and looser.
 * To quote from The Java Language Specification [JLS, 6.1], “These conventions
 * should not be followed slavishly if long-held conventional usage dictates
 * otherwise.” Use common sense.
 * 
 * 
 */

public class NamingConvention {

	private static final int SPEED_LIMIT = 60;

	private static int speed() {
		return 130;
	}

	private static void generateAudibleAlert(String msg) {
		System.out.println(msg);
	}

	public static void main(String[] args) {
		if (speed() > 2 * SPEED_LIMIT)
			generateAudibleAlert("Watch out for cops!");
	}

}
