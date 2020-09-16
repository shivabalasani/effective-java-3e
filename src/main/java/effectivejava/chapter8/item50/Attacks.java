package effectivejava.chapter8.item50;

import java.util.*;

/**
 * 
 * Item 50: Make defensive copies when needed.
 * 
 * One thing that makes Java a pleasure to use is that it is a safe language.
 * This means that in the absence of native methods it is immune to buffer
 * overruns, array overruns, wild pointers, and other memory corruption errors
 * that plague unsafe languages such as C and C++. In a safe language, it is
 * possible to write classes and to know with certainty that their invariants
 * will hold, no matter what happens in any other part of the system. This is
 * not possible in languages that treat all of memory as one giant array.
 * 
 * Even in a safe language, you aren’t insulated from other classes without some
 * effort on your part. You must program defensively, with the assumption that
 * clients of your class will do their best to destroy its invariants. This is
 * increasingly true as people try harder to break the security of systems, but
 * more commonly, your class will have to cope with unexpected behavior
 * resulting from the honest mistakes of well-intentioned programmers. Either
 * way, it is worth taking the time to write classes that are robust in the face
 * of ill-behaved clients.
 * 
 * As of Java 8, the obvious way to fix this problem is to use Instant (or
 * Local- DateTime or ZonedDateTime) in place of a Date because Instant (and the
 * other java.time classes) are immutable (Item 17). Date is obsolete and should
 * no longer be used in new code.
 * 
 * To protect the internals of a Period instance from this sort of attack, it is
 * essential to make a defensive copy of each mutable parameter to the
 * constructor and to use the copies as components of the Period instance in
 * place of the originals:
 * 
 * Remember that nonzero-length arrays are always mutable. Therefore, you should
 * always make a defensive copy of an internal array before returning it to a
 * client. Alternatively, you could return an immutable view of the array. Both
 * of these techniques are shown in Item 15.
 * 
 * In summary, if a class has mutable components that it gets from or returns to
 * its clients, the class must defensively copy these components. If the cost of
 * the copy would be prohibitive and the class trusts its clients not to modify
 * the components inappropriately, then the defensive copy may be replaced by
 * documentation outlining the client’s responsibility not to modify the
 * affected components
 *
 */

// Two attacks on the internals of an "immutable" period (232-3)
public class Attacks {
	public static void main(String[] args) {
		// Attack the internals of a Period instance (Page 232)
		Date start = new Date();
		Date end = new Date();
		Period p = new Period(start, end);
		// Can violate by exploiting the fact that Date is mutable:
		end.setYear(78); // Modifies internals of p!
		System.out.println(p);

		// Second attack on the internals of a Period instance (Page 233)
		start = new Date();
		end = new Date();
		p = new Period(start, end);
		p.end().setYear(78); // Modifies internals of p!
		System.out.println(p);

		// Can be fixed by making defensive copies
		start = new Date();
		end = new Date();
		PeriodDefensive p1 = new PeriodDefensive(start, end);
		end.setYear(78);
		p1.end().setYear(78); // Cannot modify internals of p1
		System.out.println(p1);
	}
}
