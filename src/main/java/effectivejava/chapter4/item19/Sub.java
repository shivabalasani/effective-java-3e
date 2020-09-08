package effectivejava.chapter4.item19;

import java.time.Instant;

/**
 * 
 * Item 19: Design and document for inheritance or else prohibit it.
 * 
 * First, the class must document precisely the effects of overriding any
 * method. In other words, the class must document its self-use of overridable
 * methods.
 * 
 * The @implSpec tag was added in Java 8 and used heavily in Java 9. This tag
 * should be enabled by default, but as of Java 9, the Javadoc utility still
 * ignores it unless you pass the command line switch -tag "apiNote:a:API
 * Note:". Designing for inheritance involves more than just documenting
 * patterns of self-use. To allow programmers to write efficient subclasses
 * without undue pain, a class may have to provide hooks into its internal
 * workings in the form of judiciously chosen protected methods or, in rare
 * instances, protected fields.
 * 
 * The only way to test a class designed for inheritance is to write
 * subclasses.Therefore, you must test your class by writing subclasses before
 * you release it.There are a few more restrictions that a class must obey to
 * allow inheritance. Constructors must not invoke overridable methods, directly
 * or indirectly.
 * 
 * If you do decide to implement either Cloneable or Serializable in a class
 * that is designed for inheritance, you should be aware that because the clone
 * and readObject methods behave a lot like constructors, a similar restriction
 * applies: neither clone nor readObject may invoke an overridable method,
 * directly or indirectly.
 * 
 * Designing a class for inheritance requires great effort and places
 * substantial limitations on the class. This is not a decision to be undertaken
 * lightly. There are some situations where it is clearly the right thing to do,
 * such as abstract classes, including skeletal implementations of interfaces
 * (Item 20). There are other situations where it is clearly the wrong thing to
 * do, such as immutable classes (Item 17).
 * 
 * Prohibit subclassing in classes that are not designed and documented to be
 * safely subclassed. There are two ways to prohibit subclassing. The easier of
 * the two is to declare the class final. The alternative is to make all the
 * constructors private or package-private and to add public static factories in
 * place of the constructors. This alternative, which provides the flexibility
 * to use subclasses internally, is discussed in Item 17. Either approach is
 * acceptable.
 * 
 * In summary, designing a class for inheritance is hard work. You must document
 * all of its self-use patterns, and once you’ve documented them, you must
 * commit to them for the life of the class. If you fail to do this, subclasses
 * may become dependent on implementation details of the superclass and may
 * break if the implementation of the superclass changes. To allow others to
 * write efficient subclasses, you may also have to export one or more protected
 * methods. Unless you know there is a real need for subclasses, you are
 * probably better off prohibiting inheritance by declaring your class final or
 * ensuring that there are no accessible constructors.
 *
 */
// Demonstration of what can go wrong when you override a method  called from constructor (Page 96)
public final class Sub extends Super {
	// Blank final, set by constructor
	private final Instant instant;

	Sub() {
		instant = Instant.now();
	}

	// Overriding method invoked by superclass constructor
	@Override
	public void overrideMe() {
		System.out.println(instant);
	}

	public static void main(String[] args) {
		Sub sub = new Sub();
		sub.overrideMe();
	}
}
