package effectivejava.chapter6.item34;

/**
 * 
 * Item 34: Use enums instead of int constants
 *
 * JAVA supports two special-purpose families of reference types: a kind of
 * class called an enum type, and a kind of interface called an annotation type.
 * 
 * An enumerated type is a type whose legal values consist of a fixed set of
 * constants, such as the seasons of the year, the planets in the solar system,
 * or the suits in a deck of playing cards. Before enum types were added to the
 * language, a common pattern for representing enumerated types was to declare a
 * group of named int constants, one for each member of the type:
 * 
 * Programs that use int enums are brittle. Because int enums are constant
 * variables [JLS, 4.12.4], their int values are compiled into the clients that
 * use them [JLS, 13.1]. If the value associated with an int enum is changed,
 * its clients must be recompiled. If not, the clients will still run, but their
 * behavior will be incorrect.
 * 
 * There is no easy way to translate int enum constants into printable strings.
 * If you print such a constant or display it from a debugger, all you see is a
 * number, which isn’t very helpful. There is no reliable way to iterate over
 * all the int enum constants in a group, or even to obtain the size of an int
 * enum group.
 * 
 * You may encounter a variant of this pattern in which String constants are
 * used in place of int constants. This variant, known as the String enum
 * pattern, is even less desirable. While it does provide printable strings for
 * its constants, it can lead naive users to hard-code string constants into
 * client code instead of using field names. If such a hard-coded string
 * constant contains a typographical error, it will escape detection at compile
 * time and result in bugs at runtime. Also, it might lead to performance
 * problems, because it relies on string comparisons.
 * 
 * The basic idea behind Java’s enum types is simple: they are classes that
 * export one instance for each enumeration constant via a public static final
 * field. Enum types are effectively final, by virtue of having no accessible
 * constructors. Because clients can neither create instances of an enum type
 * nor extend it, there can be no instances but the declared enum constants. In
 * other words, enum types are instance-controlled (page 6). They are a
 * generalization of singletons (Item 3), which are essentially single-element
 * enums.
 * 
 * Enums provide compile-time type safety. If you declare a parameter to be of
 * type Apple, you are guaranteed that any non-null object reference passed to
 * the parameter is one of the three valid Apple values. Attempts to pass values
 * of the wrong type will result in compile-time errors, as will attempts to
 * assign an expression of one enum type to a variable of another, or to use the
 * == operator to compare values of different enum types.
 * 
 * Enums are, generally speaking, comparable in performance to int constants. A
 * minor performance disadvantage of enums is that there is a space and time
 * cost to load and initialize enum types, but it is unlikely to be noticeable
 * in practice.
 * 
 * So when should you use enums? Use enums any time you need a set of constants
 * whose members are known at compile time. Of course, this includes “natural
 * enumerated types,” such as the planets, the days of the week, and the chess
 * pieces. But it also includes other sets for which you know all the possible
 * values at compile time, such as choices on a menu, operation codes, and
 * command line flags. It is not necessary that the set of constants in an enum
 * type stay fixed for all time. The enum feature was specifically designed to
 * allow for binary compatible evolution of enum types.
 * 
 * In summary, the advantages of enum types over int constants are compelling.
 * Enums are more readable, safer, and more powerful. Many enums require no
 * explicit constructors or members, but others benefit from associating data
 * with each constant and providing methods whose behavior is affected by this
 * data. Fewer enums benefit from associating multiple behaviors with a single
 * method. In this relatively rare case, prefer constant-specific methods to
 * enums that switch on their own values. Consider the strategy enum pattern if
 * some, but not all, enum constants share common behaviors.
 */
public class IntEnumPattern {

    // The int enum pattern - severely deficient!
    public static final int APPLE_FUJI = 0;
    public static final int APPLE_PIPPIN = 1;
    public static final int APPLE_GRANNY_SMITH = 2;

    public static final int ORANGE_NAVEL = 0;
    public static final int ORANGE_TEMPLE = 1;
    public static final int ORANGE_BLOOD = 2;

    public static void main(String[] args) {
	// This technique, known as the int enum pattern, has many shortcomings. It
	// provides nothing in the way of type safety and little in the way of
	// expressive power. The compiler won’t complain if you pass an apple to a
	// method that expects an orange, compare apples to oranges with the ==
	// operator, or worse:

	// Tasty citrus flavored applesauce!
	int i = (APPLE_FUJI - ORANGE_TEMPLE) / APPLE_PIPPIN;

    }

    // Luckily, Java provides an alternative that avoids all the shortcomings of the
    // int and string enum patterns and provides many added benefits. It is the enum
    // type [JLS, 8.9]. Here’s how it looks in its simplest form:
    public enum Apple {
	FUJI, PIPPIN, GRANNY_SMITH
    }

    public enum Orange {
	NAVEL, TEMPLE, BLOOD
    }
}
