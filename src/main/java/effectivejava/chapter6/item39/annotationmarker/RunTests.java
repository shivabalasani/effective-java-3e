package effectivejava.chapter6.item39.annotationmarker;

// Program to process marker annotations (Page 182)

import java.lang.reflect.*;

/**
 * 
 * Item 39: Prefer annotations to naming patterns.
 * 
 * Historically, it was common to use naming patterns to indicate that some
 * program elements demanded special treatment by a tool or framework. For
 * example, prior to release 4, the JUnit testing framework required its users
 * to designate test methods by beginning their names with the characters test
 * [Beck04]. This technique works, but it has several big disadvantages. First,
 * typographical errors result in silent failures. For example, suppose you
 * accidentally named a test method tsetSafetyOverride instead of
 * testSafetyOverride. JUnit 3 wouldn’t complain, but it wouldn’t execute the
 * test either, leading to a false sense of security.A second disadvantage of
 * naming patterns is that there is no way to ensure that they are used only on
 * appropriate program elements.A third disadvantage of naming patterns is that
 * they provide no good way to associate parameter values with program elements.
 * 
 * Annotations [JLS, 9.7] solve all naming patterns problems nicely, and JUnit
 * adopted them starting with release 4.
 * 
 * There is simply no reason to use naming patterns when you can use annotations
 * instead.That said, with the exception of toolsmiths, most programmers will
 * have no need to define annotation types. But all programmers should use the
 * predefined annotation types that Java provides (Items 40, 27). Also, consider
 * using the annotations provided by your IDE or static analysis tools. Such
 * annotations can improve the quality of the diagnostic information provided by
 * these tools. Note, however, that these annotations have yet to be
 * standardized, so you may have some work to do if you switch tools or if a
 * standard emerges.
 *
 */

public class RunTests {
	public static void main(String[] args) throws Exception {
		int tests = 0;
		int passed = 0;
		Class<?> testClass = Class.forName("effectivejava.chapter6.item39.annotationmarker.Sample");
		for (Method m : testClass.getDeclaredMethods()) {
			if (m.isAnnotationPresent(Test.class)) {
				tests++;
				try {
					m.invoke(null);
					passed++;
				} catch (InvocationTargetException wrappedExc) {
					Throwable exc = wrappedExc.getCause();
					System.out.println(m + " failed: " + exc);
				} catch (Exception exc) {
					System.out.println("Invalid @Test: " + m);
				}
			}
		}
		System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
	}
}
