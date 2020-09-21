package effectivejava.chapters12.item88;

import java.util.Date;

/**
 * 
 * Item 88: Write readObject methods defensively.
 * 
 * Item 50 contains an immutable date-range class with mutable private Date
 * fields. The class goes to great lengths to preserve its invariants and
 * immutability by defensively copying Date objects in its constructor and
 * accessors.
 * 
 * Suppose you decide that you want this class to be serializable. Because the
 * physical representation of a Period object exactly mirrors its logical data
 * content, it is not unreasonable to use the default serialized form (Item 87).
 * Therefore, it might seem that all you have to do to make the class
 * serializable is to add the words implements Serializable to the class
 * declaration. If you did so, however, the class would no longer guarantee its
 * critical invariants.
 * 
 * The problem is that the readObject method is effectively another public
 * constructor, and it demands all of the same care as any other constructor.
 * Just as a constructor must check its arguments for validity (Item 49) and
 * make defensive copies of parameters where appropriate (Item 50), so must a
 * readObject method. If a readObject method fails to do either of these things,
 * it is a relatively simple matter for an attacker to violate the class’s
 * invariants.
 * 
 * To summarize, anytime you write a readObject method, adopt the mind-set that
 * you are writing a public constructor that must produce a valid instance
 * regardless of what byte stream it is given. Do not assume that the byte
 * stream represents an actual serialized instance. While the examples in this
 * item concern a class that uses the default serialized form, all of the issues
 * that were raised apply equally to classes with custom serialized forms. Here,
 * in summary form, are the guidelines for writing a readObject method:
 * 
 * • For classes with object reference fields that must remain private,
 * defensively copy each object in such a field. Mutable components of immutable
 * classes fall into this category.
 * 
 * • Check any invariants and throw an InvalidObjectException if a check fails.
 * The checks should follow any defensive copying.
 * 
 * • If an entire object graph must be validated after it is deserialized, use
 * the ObjectInputValidation interface (not discussed in this book).
 * 
 * • Do not invoke any overridable methods in the class, directly or indirectly.
 *
 */

public class DefensiveReadObject {

	// To see the attack in action, run the following program:
	public static void main(String[] args) {
		MutablePeriod mp = new MutablePeriod();
		Period p = mp.period;
		Date pEnd = mp.end;
		// Let's turn back the clock
		pEnd.setYear(78);
		System.out.println(p);
		// Bring back the 60s!
		pEnd.setYear(69);
		System.out.println(p);
	}

}
