package effectivejava.chapter9.item57;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * 
 * Item 57: Minimize the scope of local variables.
 * 
 * By minimizing the scope of local variables, you increase the readability and
 * maintainability of your code and reduce the likelihood of error.Older
 * programming languages, such as C, mandated that local variables must be
 * declared at the head of a block, and some programmers continue to do this out
 * of habit. It’s a habit worth breaking. As a gentle reminder, Java lets you
 * declare variables anywhere a statement is legal (as does C, since C99).
 * 
 * The most powerful technique for minimizing the scope of a local variable is
 * to declare it where it is first used.
 * 
 * Nearly every local variable declaration should contain an initializer. If you
 * don’t yet have enough information to initialize a variable sensibly, you
 * should postpone the declaration until you do. One exception to this rule
 * concerns trycatch statements.
 * 
 * Prefer for loops to while loops, assuming the contents of the loop variable
 * aren’t needed after the loop terminates.
 * 
 * The for loop has one more advantage over the while loop: it is shorter, which
 * enhances readability. Here is another loop idiom that minimizes the scope of
 * local variables:
 * 
 * for (int i = 0, n = expensiveComputation(); i < n; i++) { ... // Do something
 * with i; }
 * 
 * The important thing to notice about this idiom is that it has two loop
 * variables, i and n, both of which have exactly the right scope. The second
 * variable, n, is used to store the limit of the first, thus avoiding the cost
 * of a redundant computation in every iteration. As a rule, you should use this
 * idiom if the loop test involves a method invocation that is guaranteed to
 * return the same result on each iteration.
 *
 * A final technique to minimize the scope of local variables is to keep methods
 * small and focused. If you combine two activities in the same method, local
 * variables relevant to one activity may be in the scope of the code performing
 * the other activity. To prevent this from happening, simply separate the
 * method into two: one for each activity.
 */
public class MinimizeLocalVarScope {

	enum Element {
		HYDROGEN, HELIUM
	}

	static Collection<Element> elements = Arrays.asList(Element.values());

	public static void main(String[] args) {
		// Preferred idiom for iterating over a collection or array
		for (Element e : elements) {
			System.out.println(e);
			// ... Do Something with e
		}

		System.out.println("-----------------------------");
		// Idiom for iterating when you need the iterator
		for (Iterator<Element> i = elements.iterator(); i.hasNext();) {
			Element e = i.next();
			System.out.println(e);
			// ... Do something with e and i
		}

		System.out.println("-----------------------------");
		// To see why these for loops are preferable to a while loop, consider the
		// following code fragment, which contains two while loops and one bug:
		Iterator<Element> i = elements.iterator();
		while (i.hasNext()) {
			System.out.println(i.next());
		}
		System.out.println("-----------------------------");
		// The second loop contains a copy-and-paste error: it initializes a new loop
		// variable, i2, but uses the old one, i, which is, unfortunately, still in
		// scope. the second loop terminates immediately,giving the false impression
		// that c2 is empty
		Iterator<Element> i2 = elements.iterator();
		while (i.hasNext()) { // BUG!
			System.out.println(i2.next());
		}
	}

}
