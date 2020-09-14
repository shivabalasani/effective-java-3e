package effectivejava.chapter6.item40;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Luckily, the compiler can help you find this error, but only if you help it
 * by telling it that you intend to override Object.equals. To do this, annotate
 * Bigram.equals with @Override, as shown here:
 * 
 * -@Override public boolean equals(Bigram b) { return b.first == first &&
 * b.second == second; }
 * 
 * If you insert this annotation and try to recompile the program, the compiler
 * will generate an error message like this:
 * 
 * Bigram.java:10: method does not override or implement a method from a
 * supertype -@Override public boolean equals(Bigram b) {
 * 
 * You will immediately realize what you did wrong, slap yourself on the
 * forehead, and replace the broken equals implementation with a correct one
 * (Item 10):
 * 
 * Therefore, you should use the Override annotation on every method declaration
 * that you believe to override a superclass declaration. There is one minor
 * exception to this rule. If you are writing a class that is not labeled
 * abstract and you believe that it overrides an abstract method in its
 * superclass, you needn’t bother putting the Override annotation on that
 * method.
 * 
 * The Override annotation may be used on method declarations that override
 * declarations from interfaces as well as classes. With the advent of default
 * methods, it is good practice to use Override on concrete implementations of
 * interface methods to ensure that the signature is correct. If you know that
 * an interface does not have default methods, you may choose to omit Override
 * annotations on concrete implementations of interface methods to reduce
 * clutter.
 * 
 * In an abstract class or an interface, however, it is worth annotating all
 * methods that you believe to override superclass or superinterface methods,
 * whether concrete or abstract. For example, the Set interface adds no new
 * methods to the Collection interface, so it should include Override
 * annotations on all of its method declarations to ensure that it does not
 * accidentally add any new methods to the Collection interface.
 *
 */

// Fixed Bigram class (Page 189)
public class Bigram2 {
	private final char first;
	private final char second;

	public Bigram2(char first, char second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Bigram2))
			return false;
		Bigram2 b = (Bigram2) o;
		return b.first == first && b.second == second;
	}

	public int hashCode() {
		return 31 * first + second;
	}

	public static void main(String[] args) {
		Set<Bigram2> s = new HashSet<>();
		for (int i = 0; i < 10; i++)
			for (char ch = 'a'; ch <= 'z'; ch++)
				s.add(new Bigram2(ch, ch));
		System.out.println(s.size());
	}
}
