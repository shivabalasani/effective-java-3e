package effectivejava.chapter8.item50;

import java.util.*;

/**
 * 
 * With the new constructor in place, the previous attack will have no effect on
 * the Period instance. Note that defensive copies are made before checking the
 * validity of the parameters (Item 49), and the validity check is performed on
 * the copies rather than on the originals. While this may seem unnatural, it is
 * necessary. It protects the class against changes to the parameters from
 * another thread during the window of vulnerability between the time the
 * parameters are checked and the time they are copied. In the computer security
 * community, this is known as a time-of-check/time-of-use or TOCTOU attack
 * [Viega01].
 * 
 * Note also that we did not use Date’s clone method to make the defensive
 * copies. Because Date is nonfinal, the clone method is not guaranteed to
 * return an object whose class is java.util.Date: it could return an instance
 * of an untrusted subclass that is specifically designed for malicious
 * mischief. Such a subclass could, for example, record a reference to each
 * instance in a private static list at the time of its creation and allow the
 * attacker to access this list. This would give the attacker free rein over all
 * instances. To prevent this sort of attack, do not use the clone method to
 * make a defensive copy of a parameter whose type is subclassable by untrusted
 * parties.
 *
 */
// Fixed "immutable" time period class (Pages 231-3)
public final class PeriodDefensive {
	private final Date start;
	private final Date end;

	/**
	 * @param start the beginning of the period
	 * @param end   the end of the period; must not precede start
	 * @throws IllegalArgumentException if start is after end
	 * @throws NullPointerException     if start or end is null
	 */
	// Repaired constructor - makes defensive copies of parameters (Page 232)
	public PeriodDefensive(Date start, Date end) {
		this.start = new Date(start.getTime());
		this.end = new Date(end.getTime());

		if (this.start.compareTo(this.end) > 0)
			throw new IllegalArgumentException(this.start + " after " + this.end);
	}

	// Repaired accessors - make defensive copies of internal fields (Page 233)
	public Date start() {
		return new Date(start.getTime());
	}

	public Date end() {
		// return end;
		return new Date(end.getTime());
	}

	public String toString() {
		return start + " - " + end;
	}
	// Remainder omitted
}