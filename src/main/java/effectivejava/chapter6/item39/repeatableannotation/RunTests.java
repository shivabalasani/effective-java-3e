package effectivejava.chapter6.item39.repeatableannotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import effectivejava.chapter6.item39.annotationmarker.Test;

/**
 * 
 * Processing repeatable annotations requires care. A repeated annotation
 * generates a synthetic annotation of the containing annotation type. The
 * getAnnotationsByType method glosses over this fact, and can be used to access
 * both repeated and non-repeated annotations of a repeatable annotation type.
 * But isAnnotationPresent makes it explicit that repeated annotations are not
 * of the annotation type, but of the containing annotation type. If an element
 * has a repeated annotation of some type and you use the isAnnotationPresent
 * method to check if the element has an annotation of that type, you’ll find
 * that it does not. Using this method to check for the presence of an
 * annotation type will therefore cause your program to silently ignore repeated
 * annotations. Similarly, using this method to check for the containing
 * annotation type will cause the program to silently ignore non-repeated
 * annotations. To detect repeated and non-repeated annotations with
 * isAnnotationPresent, you much check for both the annotation type and its
 * containing annotation type. Here’s how the relevant part of our RunTests
 * program looks when modified to use the repeatable version of the
 * ExceptionTest annotation:
 *
 * Repeatable annotations were added to improve the readability of source code
 * that logically applies multiple instances of the same annotation type to a
 * given program element. If you feel they enhance the readability of your
 * source code, use them, but remember that there is more boilerplate in
 * declaring and processing repeatable annotations, and that processing
 * repeatable annotations is error-prone.
 */
// Program to process marker annotations and repeatable annotations (Page 187)
public class RunTests {
    public static void main(String[] args) throws Exception {
	int tests = 0;
	int passed = 0;
	Class testClass = Class.forName("effectivejava.chapter6.item39.repeatableannotation.Sample4");
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
		    System.out.println("INVALID @Test: " + m);
		}
	    }

	    // Processing repeatable annotations (Page 187)
	    if (m.isAnnotationPresent(ExceptionTest.class) || m.isAnnotationPresent(ExceptionTestContainer.class)) {
		tests++;
		try {
		    m.invoke(null);
		    System.out.printf("Test %s failed: no exception%n", m);
		} catch (Throwable wrappedExc) {
		    Throwable exc = wrappedExc.getCause();
		    int oldPassed = passed;
		    ExceptionTest[] excTests = m.getAnnotationsByType(ExceptionTest.class);
		    for (ExceptionTest excTest : excTests) {
			if (excTest.value().isInstance(exc)) {
			    passed++;
			    break;
			}
		    }
		    if (passed == oldPassed)
			System.out.printf("Test %s failed: %s %n", m, exc);
		}
	    }
	}
	System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
    }
}
