package effectivejava.chapter5.item31;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * 
 * Item 31: Use bounded wildcards to increase API flexibility.
 * 
 * As noted in Item 28, parameterized types are invariant. In other words, for
 * any two distinct types Type1 and Type2, List<Type1> is neither a subtype nor
 * a supertype of List<Type2>. Although it is counterintuitive that List<String>
 * is not a subtype of List<Object>, it really does make sense. You can put any
 * object into a List<Object>, but you can put only strings into a List<String>.
 * Since a List<String> can’t do everything a List<Object> can, it isn’t a
 * subtype (by the Liskov substitution principal, Item 10).
 * 
 * In summary, using wildcard types in your APIs, while tricky, makes the APIs
 * far more flexible. If you write a library that will be widely used, the
 * proper use of wildcard types should be considered mandatory. Remember the
 * basic rule: producer-extends, consumer-super (PECS). Also remember that all
 * comparables and comparators are consumers.
 *
 */
// Wildcard type for parameter that serves as an T producer (page 141)
public class Chooser<T> {
	private final List<T> choiceList;
	private final Random rnd = new Random();

	public Chooser(Collection<? extends T> choices) {
		choiceList = new ArrayList<>(choices);
	}

	public T choose() {
		return choiceList.get(rnd.nextInt(choiceList.size()));
	}

	public static void main(String[] args) {
		List<Integer> intList = List.of(1, 2, 3, 4, 5, 6);
		Chooser<Number> chooser = new Chooser<>(intList);
		for (int i = 0; i < 10; i++) {
			Number choice = chooser.choose();
			System.out.println(choice);
		}
	}
}
