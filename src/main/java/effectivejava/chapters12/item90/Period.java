package effectivejava.chapters12.item90;

// Period class with serialization proxy - Pages 363-364

import java.util.*;
import java.io.*;

/**
 * 
 * Item 90: Consider serialization proxies instead of serialized instances.
 * 
 * As mentioned in Items 85 and 86 and discussed throughout this chapter, the
 * decision to implement Serializable increases the likelihood of bugs and
 * security problems as it allows instances to be created using an
 * extralinguistic mechanism in place of ordinary constructors. There is,
 * however, a technique that greatly reduces these risks. This technique is
 * known as the serialization proxy pattern.
 * 
 * The presence of writeReplace method on the enclosing class causes the
 * serialization system to emit a SerializationProxy instance instead of an
 * instance of the enclosing class. In other words, the writeReplace method
 * translates an instance of the enclosing class to its serialization proxy
 * prior to serialization.
 * 
 * With this writeReplace method in place, the serialization system will never
 * generate a serialized instance of the enclosing class, but an attacker might
 * fabricate one in an attempt to violate the class’s invariants. To guarantee
 * that such an attack would fail, merely add this readObject method to the
 * enclosing class:
 * 
 * Finally, provide a readResolve method on the SerializationProxy class that
 * returns a logically equivalent instance of the enclosing class. The presence
 * of this method causes the serialization system to translate the serialization
 * proxy back into an instance of the enclosing class upon deserialization.
 * 
 * This readResolve method creates an instance of the enclosing class using only
 * its public API and therein lies the beauty of the pattern. It largely
 * eliminates the extralinguistic character of serialization, because the
 * deserialized instance is created using the same constructors, static
 * factories, and methods as any other instance. This frees you from having to
 * separately ensure that deserialized instances obey the class’s invariants. If
 * the class’s static factories or constructors establish these invariants and
 * its instance methods maintain them, you’ve ensured that the invariants will
 * be maintained by serialization as well.
 * 
 * Like the defensive copying approach (page 357), the serialization proxy
 * approach stops the bogus byte-stream attack (page 354) and the internal field
 * theft attack (page 356) dead in their tracks. Unlike the two previous
 * approaches, this one allows the fields of Period to be final, which is
 * required in order for the Period class to be truly immutable (Item 17). And
 * unlike the two previous approaches, this one doesn’t involve a great deal of
 * thought. You don’t have to figure out which fields might be compromised by
 * devious serialization attacks, nor do you have to explicitly perform validity
 * checking as part of deserialization.
 * 
 * There is another way in which the serialization proxy pattern is more
 * powerful than defensive copying in readObject. The serialization proxy
 * pattern allows the deserialized instance to have a different class from the
 * originally serialized instance. You might not think that this would be useful
 * in practice, but it is.
 *
 * In summary, consider the serialization proxy pattern whenever you find
 * yourself having to write a readObject or writeObject method on a class that
 * is not extendable by its clients. This pattern is perhaps the easiest way to
 * robustly serialize objects with nontrivial invariants.
 */

// Immutable class that uses defensive copying
public final class Period implements Serializable {
	private final Date start;
	private final Date end;

	/**
	 * @param start the beginning of the period
	 * @param end   the end of the period; must not precede start
	 * @throws IllegalArgumentException if start is after end
	 * @throws NullPointerException     if start or end is null
	 */
	public Period(Date start, Date end) {
		this.start = new Date(start.getTime());
		this.end = new Date(end.getTime());
		if (this.start.compareTo(this.end) > 0)
			throw new IllegalArgumentException(start + " after " + end);
	}

	public Date start() {
		return new Date(start.getTime());
	}

	public Date end() {
		return new Date(end.getTime());
	}

	public String toString() {
		return start + " - " + end;
	}

	// Serialization proxy for Period class
	private static class SerializationProxy implements Serializable {
		private final Date start;
		private final Date end;

		SerializationProxy(Period p) {
			this.start = p.start;
			this.end = p.end;
		}

		// readResolve method for Period.SerializationProxy
		private Object readResolve() {
			return new Period(start, end); // Uses public constructor
		}

		private static final long serialVersionUID = 234098243823485285L; // Any number will do (Item 87)
	}

	// writeReplace method for the serialization proxy pattern
	private Object writeReplace() {
		return new SerializationProxy(this);
	}

	// readObject method for the serialization proxy pattern
	private void readObject(ObjectInputStream stream) throws InvalidObjectException {
		throw new InvalidObjectException("Proxy required");
	}
}