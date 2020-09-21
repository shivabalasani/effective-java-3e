package effectivejava.chapters11.item79;

import java.util.*;

/**
 * 
 * Observers subscribe to notifications by invoking the addObserver method and
 * unsubscribe by invoking the removeObserver method. In both cases, an instance
 * of this callback interface is passed to the method.
 * 
 * On cursory inspection, ObservableSet appears to work fine. For example, the
 * following program prints the numbers from 0 through 99:
 *
 */

// Simple test of ObservableSet - Page 318
public class Test1 {
	public static void main(String[] args) {
		ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());

		set.addObserver2((s, e) -> System.out.println(e));

		for (int i = 0; i < 100; i++)
			set.add(i);
	}
}
