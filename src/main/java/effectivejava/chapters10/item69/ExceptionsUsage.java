package effectivejava.chapters10.item69;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * Item 69: Use exceptions only for exceptional conditions.
 * 
 * What does Version 1 code do? It’s not at all obvious from inspection, and
 * that’s reason enough not to use it (Item 67). It turns out to be a horribly
 * ill-conceived idiom for looping through the elements of an array. The
 * infinite loop terminates by throwing, catching, and ignoring an
 * ArrayIndexOutOfBoundsException when it attempts to access the first array
 * element outside the bounds of the array. It’s supposed to be equivalent to
 * the standard idiom for looping through an array, which is instantly
 * recognizable to any Java programmer: Version 2
 * 
 * So why would anyone use the exception-based loop in preference to the tried
 * and true? It’s a misguided attempt to improve performance based on the faulty
 * reasoning that, since the VM checks the bounds of all array accesses, the
 * normal loop termination test—hidden by the compiler but still present in the
 * for-each loop—is redundant and should be avoided. There are three things
 * wrong with this reasoning:
 * 
 * • Because exceptions are designed for exceptional circumstances, there is
 * little incentive for JVM implementors to make them as fast as explicit tests.
 * 
 * • Placing code inside a try-catch block inhibits certain optimizations that
 * JVM implementations might otherwise perform.
 * 
 * • The standard idiom for looping through an array doesn’t necessarily result
 * in redundant checks. Many JVM implementations optimize them away.
 *
 * In fact, the exception-based idiom is far slower than the standard one. On my
 * machine, the exception-based idiom is about twice as slow as the standard one
 * for arrays of one hundred elements.
 * 
 * The moral of this story is simple: Exceptions are, as their name implies, to
 * be used only for exceptional conditions; they should never be used for
 * ordinary control flow.
 * 
 * This principle also has implications for API design. A well-designed API must
 * not force its clients to use exceptions for ordinary control flow.A class
 * with a “state-dependent” method that can be invoked only under certain
 * unpredictable conditions should generally have a separate “state-testing”
 * method indicating whether it is appropriate to invoke the state-dependent
 * method. For example, the Iterator interface has the state-dependent method
 * next and the corresponding state-testing method hasNext. This enables the
 * standard idiom for iterating over a collection with a traditional for loop
 * (as well as the for-each loop, where the hasNext method is used internally):
 * see Version 3
 * 
 * Version 4 should look very familiar after the array iteration example that
 * began this item. In addition to being wordy and misleading, the
 * exception-based loop is likely to perform poorly and can mask bugs in
 * unrelated parts of the system.
 * 
 * In summary, exceptions are designed for exceptional conditions. Don’t use
 * them for ordinary control flow, and don’t write APIs that force others to do
 * so.
 * 
 */

public class ExceptionsUsage {

    public enum Exception {
	NORMAL, ABNORMAL
    }

    static Collection<Exception> exceptions = Arrays.asList(Exception.values());

    public static void main(String[] args) {

	// Version 1 : Horrible abuse of exceptions. Don't ever do this!
	try {
	    int i = 0;
	    // while (true)
	    // range[i++].climb();
	} catch (ArrayIndexOutOfBoundsException e) {
	}

	// Version 2
	// for (Mountain m : range)
	// m.climb();

	// Version 3
	for (Iterator<Exception> i = exceptions.iterator(); i.hasNext();) {
	    Exception foo = i.next();
	}

	// Version 4
	// If Iterator lacked the hasNext method, clients would be forced to do this
	// instead: Do not use this hideous code for iteration over a collection!
	try {
	    Iterator<Exception> i = exceptions.iterator();
	    while (true) {
		Exception foo = i.next();
	    }
	} catch (NoSuchElementException e) {
	}
    }
}
