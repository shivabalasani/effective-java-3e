package effectivejava.chapters12.item88;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * The source of the problem is that Period’s readObject method is not doing
 * enough defensive copying. When an object is deserialized, it is critical to
 * defensively copy any field containing an object reference that a client must
 * not possess. Therefore, every serializable immutable class containing private
 * mutable components must defensively copy these components in its readObject
 * method. The following readObject method suffices to ensure Period’s
 * invariants and to maintain its immutability:
 * 
 * Note that the defensive copy is performed prior to the validity check and
 * that we did not use Date’s clone method to perform the defensive copy. Both
 * of these details are required to protect Period against attack (Item 50).
 * Note also that defensive copying is not possible for final fields. To use the
 * readObject method, we must make the start and end fields nonfinal. This is
 * unfortunate, but it is the lesser of two evils. With the new readObject
 * method in place and the final modifier removed from the start and end fields,
 * the MutablePeriod class is rendered ineffective. The above attack program now
 * generates this output:
 *
 */
// Immutable class that uses defensive copying
public class Period implements Serializable {
	private static final long serialVersionUID = -1296925599569337059L;
	private Date start;
	private Date end;

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

	// readObject method with validity checking - insufficient!
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		// Check that our invariants are satisfied
		if (start.compareTo(end) > 0)
			throw new InvalidObjectException(start + " after " + end);
	}

	// readObject method with defensive copying and validity checking
	private void readObjectSafe(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		// Defensively copy our mutable components
		start = new Date(start.getTime());
		end = new Date(end.getTime());
		// Check that our invariants are satisfied
		if (start.compareTo(end) > 0)
			throw new InvalidObjectException(start + " after " + end);
	}

	public String toString() {
		return start + " - " + end;
	} // Remainder omitted
}
