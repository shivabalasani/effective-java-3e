package effectivejava.chapter4.item18;

import java.util.*;

/**
 * 
 * Item 18: Favor composition over inheritance
 * 
 * Unlike method invocation, inheritance violates encapsulation [Snyder86]. In
 * other words, a subclass depends on the implementation details of its
 * superclass for its proper function. The superclass’s implementation may
 * change from release to release, and if it does, the subclass may break, even
 * though its code has not been touched. As a consequence, a subclass must
 * evolve in tandem with its superclass.
 * 
 * Inheritance is appropriate only in circumstances where the subclass really is
 * a subtype of the superclass. In other words, a class B should extend a class
 * A only if an “is-a” relationship exists between the two classes.
 * 
 * Inheritance propagates any flaws in the superclass’s API, while composition
 * lets you design a new API that hides these flaws.
 * 
 * To summarize, inheritance is powerful, but it is problematic because it
 * violates encapsulation. It is appropriate only when a genuine subtype
 * relationship exists between the subclass and the superclass. Even then,
 * inheritance may lead to fragility if the subclass is in a different package
 * from the superclass and the superclass is not designed for inheritance. To
 * avoid this fragility, use composition and forwarding instead of inheritance,
 *
 */
// Wrapper class - uses composition in place of inheritance (Page 90)
public class InstrumentedSet<E> extends ForwardingSet<E> {
	private int addCount = 0;

	public InstrumentedSet(Set<E> s) {
		super(s);
	}

	@Override
	public boolean add(E e) {
		addCount++;
		return super.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		addCount += c.size();
		return super.addAll(c);
	}

	public int getAddCount() {
		return addCount;
	}

	public static void main(String[] args) {
		InstrumentedSet<String> s = new InstrumentedSet<>(new HashSet<>());
		s.addAll(List.of("Snap", "Crackle", "Pop"));
		System.out.println(s.getAddCount());
	}
}
