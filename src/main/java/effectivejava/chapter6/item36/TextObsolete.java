package effectivejava.chapter6.item36;

/**
 * 
 * If the elements of an enumerated type are used primarily in sets, it is
 * traditional to use the int enum pattern (Item 34), assigning a different
 * power of 2 to each constant:
 * 
 * The bit field representation also lets you perform set operations such as
 * union and intersection efficiently using bitwise arithmetic. But bit fields
 * have all the disadvantages of int enum constants and more. It is even harder
 * to interpret a bit field than a simple int enum constant when it is printed
 * as a number. There is no easy way to iterate over all of the elements
 * represented by a bit field. Finally, you have to predict the maximum number
 * of bits you’ll ever need at the time you’re writing the API and choose a type
 * for the bit field (typically int or long) accordingly. Once you’ve picked a
 * type, you can’t exceed its width (32 or 64 bits) without changing the API.
 *
 */
// Bit field enumeration constants - OBSOLETE!
public class TextObsolete {
    public static final int STYLE_BOLD = 1 << 0; // 1
    public static final int STYLE_ITALIC = 1 << 1; // 2
    public static final int STYLE_UNDERLINE = 1 << 2; // 4
    public static final int STYLE_STRIKETHROUGH = 1 << 3; // 8
    // Parameter is bitwise OR of zero or more STYLE_ constants

    public void applyStyles(int styles) {
	System.out.println(styles);
    }

    // Sample use
    public static void main(String[] args) {
	TextObsolete text = new TextObsolete();
	// This representation lets you use the bitwise OR operation to combine several
	// constants into a set, known as a bit field:
	text.applyStyles(STYLE_BOLD | STYLE_ITALIC);
    }
}