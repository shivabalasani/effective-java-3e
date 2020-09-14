package effectivejava.chapter2.item6;

/**
 * Hideously slow program! Can you spot the object creation? (Page 24) “Don’t
 * create a new object when you should reuse an existing one,” while Item 50
 * says, “Don’t reuse an existing object when you should create a new one.”
 *
 */
public class Sum {

	private static long sum() {
		// The variable sum is declared as a Long instead of a long, which means that
		// the program constructs about 2^31 unnecessary Long instances (roughly one
		// for each time the long i is added to the Long sum).

		// Changing the declaration of sum from Long to long reduces the runtime from
		// 6.3 seconds to 0.59 seconds on my machine. The lesson is clear: prefer
		// primitives to boxed primitives, and watch out for unintentional autoboxing.
		Long sum = 0L;
		for (long i = 0; i <= Integer.MAX_VALUE; i++)
			sum += i;
		return sum;
	}

	public static void main(String[] args) {
		int numSets = Integer.parseInt(args[0]);
		long x = 0;

		for (int i = 0; i < numSets; i++) {
			long start = System.nanoTime();
			x += sum();
			long end = System.nanoTime();
			System.out.println((end - start) / 1_000_000. + " ms.");
		}

		// Prevents VM from optimizing away everything.
		if (x == 42)
			System.out.println();
	}
}