package effectivejava.chapter4.item24;

/**
 * 
 * Item 24: Favor static member classes over nonstatic
 * 
 * A nested class is a class defined within another class. A nested class should
 * exist only to serve its enclosing class. If a nested class would be useful in
 * some other context, then it should be a top-level class. There are four kinds
 * of nested classes: static member classes, nonstatic member classes, anonymous
 * classes, and local classes. All but the first kind are known as inner
 * classes.
 *
 * One common use of a static member class is as a public helper class, useful
 * only in conjunction with its outer class.
 * 
 * If you declare a member class that does not require access to an enclosing
 * instance, always put the static modifier in its declaration, making it a
 * static rather than a nonstatic member class. If you omit this modifier, each
 * instance will have a hidden extraneous reference to its enclosing instance.
 * As previously mentioned, storing this reference takes time and space. More
 * seriously, it can result in the enclosing instance being retained when it
 * would otherwise be eligible for garbage collection (Item 7). The resulting
 * memory leak can be catastrophic. It is often difficult to detect because the
 * reference is invisible.
 * 
 * Local classes are the least frequently used of the four kinds of nested
 * classes. A local class can be declared practically anywhere a local variable
 * can be declared and obeys the same scoping rules.
 * 
 * To recap, there are four different kinds of nested classes, and each has its
 * place. If a nested class needs to be visible outside of a single method or is
 * too long to fit comfortably inside a method, use a member class. If each
 * instance of a member class needs a reference to its enclosing instance, make
 * it nonstatic; otherwise, make it static. Assuming the class belongs inside a
 * method, if you need to create instances from only one location and there is a
 * preexisting type that characterizes the class, make it an anonymous class;
 * otherwise, make it a local class.
 */
public class StaticMemberClass {

	public static void main(String[] args) {
		Main.EnclosedClass.accessEnclosingClass();
		Main.EnclosedClass ec = new Main.EnclosedClass();
		ec.accessEnclosingClass2();
	}
}

class Main {
	private static int outerVariable;

	private static void privateStaticOuterMethod() {
		System.out.println(outerVariable);
	}

	static void staticOuterMethod() {
		EnclosedClass.accessEnclosingClass();
	}

	static class EnclosedClass {
		static void accessEnclosingClass() {
			outerVariable = 1;
			privateStaticOuterMethod();
		}

		void accessEnclosingClass2() {
			staticOuterMethod();
		}
	}
}