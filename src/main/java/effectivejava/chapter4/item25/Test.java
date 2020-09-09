package effectivejava.chapter4.item25;

/**
 * 
 * If you are tempted to put multiple top-level classes into a single source
 * file, consider using static member classes (Item 24) as an alternative to
 * splitting the classes into separate source files. If the classes are
 * subservient to another class, making them into static member classes is
 * generally the better alternative because it enhances readability and makes it
 * possible to reduce the accessibility of the classes by declaring them private
 * (Item 15).
 *
 */
// Static member classes instead of multiple top-level classes (Page 116)
public class Test {
	public static void main(String[] args) {
		System.out.println(Utensil.NAME + Dessert.NAME);
	}

	private static class Utensil {
		static final String NAME = "pan";
	}

	private static class Dessert {
		static final String NAME = "cake";
	}
}
