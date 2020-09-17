package effectivejava.chapter8.item52;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * While the Thread constructor invocation and the submit method invocation look
 * similar, the former compiles while the latter does not. The arguments are
 * identical (System.out::println), and both the constructor and the method have
 * an overloading that takes a Runnable. What’s going on here? The surprising
 * answer is that the submit method has an overloading that takes a Callable<T>,
 * while the Thread constructor does not. You might think that this shouldn’t
 * make any difference because all overloadings of println return void, so the
 * method reference couldn’t possibly be a Callable. This makes perfect sense,
 * but it’s not the way the overload resolution algorithm works. Perhaps equally
 * surprising is that the submit method invocation would be legal if the println
 * method weren’t also overloaded. It is the combination of the overloading of
 * the referenced method (println) and the invoked method (submit) that prevents
 * the overload resolution algorithm from behaving as you’d expect.
 * 
 * Technically speaking, the problem is that System.out::println is an inexact
 * method reference [JLS, 15.13.1] and that “certain argument expressions that
 * contain implicitly typed lambda expressions or inexact method references are
 * ignored by the applicability tests, because their meaning cannot be
 * determined until a target type is selected [JLS, 15.12.2].” Don’t worry if
 * you don’t understand this passage; it is aimed at compiler writers. The key
 * point is that overloading methods or constructors with different functional
 * interfaces in the same argument position causes confusion. Therefore, do not
 * overload methods to take different functional interfaces in the same argument
 * position. In the parlance of this item, different functional interfaces are
 * not radically different. The Java compiler will warn you about this sort of
 * problematic overload if you pass the command line switch -Xlint:overloads
 * 
 * Array types and class types other than Object are radically different. Also,
 * array types and interface types other than Serializable and Cloneable are
 * radically different. Two distinct classes are said to be unrelated if neither
 * class is a descendant of the other [JLS, 5.5]. For example, String and
 * Throwable are unrelated. It is impossible for any object to be an instance of
 * two unrelated classes, so unrelated classes are radically different, too.
 * 
 * There may be times when you feel the need to violate the guidelines in this
 * item, especially when evolving existing classes. For example, consider
 * String, which has had a contentEquals(StringBuffer) method since Java 4. In
 * Java 5, CharSequence was added to provide a common interface for
 * StringBuffer, StringBuilder, String, CharBuffer, and other similar types. At
 * the same time that CharSequence was added, String was outfitted with an
 * overloading of the contentEquals method that takes a CharSequence.
 * 
 * While the resulting overloading clearly violates the guidelines in this item,
 * it causes no harm because both overloaded methods do exactly the same thing
 * when they are invoked on the same object reference. The programmer may not
 * know which overloading will be invoked, but it is of no consequence so long
 * as they behave identically. The standard way to ensure this behavior is to
 * have the more specific overloading forward to the more general:
 * 
 * While the Java libraries largely adhere to the spirit of the advice in this
 * item, there are a number of classes that violate it. For example, String
 * exports two overloaded static factory methods, valueOf(char[]) and
 * valueOf(Object), that do completely different things when passed the same
 * object reference. There is no real justification for this, and it should be
 * regarded as an anomaly with the potential for real confusion.
 *
 */
public class Overloading {

	// Ensuring that 2 methods have identical behavior by forwarding
	public boolean contentEquals(StringBuffer sb) {
		return contentEquals((CharSequence) sb);
	}

	public boolean contentEquals(CharSequence cs) {
		return true;
	}

	public static void main(String[] args) {
		new Thread(System.out::println).start();

		ExecutorService exec = Executors.newCachedThreadPool();
		// exec.submit(System.out::println); //won't compile
	}
}
