package effectivejava.chapters11.item79;

import java.util.*;

/**
 * Now let’s try something a bit fancier. Suppose we replace the addObserver
 * call with one that passes an observer that prints the Integer value that was
 * added to the set and removes itself if the value is 23:
 * 
 * Note that this call uses an anonymous class instance in place of the lambda
 * used in the previous call. That is because the function object needs to pass
 * itself to s.removeObserver, and lambdas cannot access themselves (Item 42).
 * 
 * You might expect the program to print the numbers 0 through 23, after which
 * the observer would unsubscribe and the program would terminate silently. In
 * fact, it prints these numbers and then throws a
 * ConcurrentModificationException. The problem is that notifyElementAdded is in
 * the process of iterating over the observers list when it invokes the
 * observer’s added method. The added method calls the observable set’s
 * removeObserver method, which in turn calls the method observers.remove. Now
 * we’re in trouble. We are trying to remove an element from a list in the midst
 * of iterating over it, which is illegal. The iteration in the
 * notifyElementAdded method is in a synchronized block to prevent concurrent
 * modification, but it doesn’t prevent the iterating thread itself from calling
 * back into the observable set and modifying its observers list.
 *
 */
// More complex test of ObservableSet - Page 318-9
public class Test2 {
    public static void main(String[] args) {
	ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());

	set.addObserver2(new SetObserver<>() {
	    public void added(ObservableSet<Integer> s, Integer e) {
		System.out.println(e);
		if (e == 23)
		    s.removeObserver2(this);
	    }
	});

	for (int i = 0; i < 100; i++)
	    set.add(i);
    }
}
