package effectivejava.chapter8.item53;

/**
 * 
 * Item 53: Use varargs judiciously.
 * 
 * Varargs methods, formally known as variable arity methods [JLS, 8.4.1],
 * accept zero or more arguments of a specified type. The varargs facility works
 * by first creating an array whose size is the number of arguments passed at
 * the call site, then putting the argument values into the array, and finally
 * passing the array to the method.
 * 
 * Varargs are effective in circumstances where you want a method with a
 * variable number of arguments. Varargs were designed for printf, which was
 * added to the platform at the same time as varargs, and for the core
 * reflection facility (Item 65), which was retrofitted. Both printf and
 * reflection benefited enormously from varargs.
 *
 * Exercise care when using varargs in performance-critical situations. Every
 * invocation of a varargs method causes an array allocation and initialization.
 * If you have determined empirically that you can’t afford this cost but you
 * need the flexibility of varargs, there is a pattern that lets you have your
 * cake and eat it too. Suppose you’ve determined that 95 percent of the calls
 * to a method have three or fewer parameters. Then declare five overloadings of
 * the method, one each with zero through three ordinary parameters, and a
 * single varargs method for use when the number of arguments exceeds three:
 * 
 * Now you know that you’ll pay the cost of the array creation only in the 5
 * percent of all invocations where the number of parameters exceeds three. Like
 * most performance optimizations, this technique usually isn’t appropriate, but
 * when it is, it’s a lifesaver.
 * 
 * The static factories for EnumSet use this technique to reduce the cost of
 * creating enum sets to a minimum. This was appropriate because it was critical
 * that enum sets provide a performance-competitive replacement for bit fields
 * (Item 36).
 * 
 * In summary, varargs are invaluable when you need to define methods with a
 * variable number of arguments. Precede the varargs parameter with any required
 * parameters, and be aware of the performance consequences of using varargs.
 */

// Sample uses of varargs (Pages 245-6)
public class Varargs {
	// Simple use of varargs (Page 245)
	static int sum(int... args) {
		int sum = 0;
		for (int arg : args)
			sum += arg;
		return sum;
	}

//    // The WRONG way to use varargs to pass one or more arguments! (Page 245)
	static int minWrong(int... args) {
		if (args.length == 0)
			throw new IllegalArgumentException("Too few arguments");
		int min = args[0];
		for (int i = 1; i < args.length; i++)
			if (args[i] < min)
				min = args[i];
		return min;
	}

	// The right way to use varargs to pass one or more arguments (Page 246)
	static int min(int firstArg, int... remainingArgs) {
		int min = firstArg;
		for (int arg : remainingArgs)
			if (arg < min)
				min = arg;
		return min;
	}

	public void foo() {
	}

	public void foo(int a1) {
	}

	public void foo(int a1, int a2) {
	}

	public void foo(int a1, int a2, int a3) {
	}

	public void foo(int a1, int a2, int a3, int... rest) {
	}

	public static void main(String[] args) {
		System.out.println(sum());
		System.out.println(sum(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

		// System.out.println(min());
		System.out.println(min(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
	}
}
