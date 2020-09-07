package effectivejava.chapter3.item10;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 
 * - Item 10: Obey the general contract when overriding equals
 * 
 *  When you override the equals method, you must adhere to its general contract. Here is the contract, from the specification for Object :
 *  The equals method implements an equivalence relation. It has these properties:
 *  
 * •  Reflexive: For any non-null reference value x, x.equals(x) must return true.
 * •  Symmetric: For any non-null reference values x and y, x.equals(y) must return true if and only if y.equals(x) returns true.
 * •  Transitive: For any non-null reference values x, y, z, if x.equals(y) returns true and y.equals(z) returns true, then x.equals(z) must return true.
 * •  Consistent: For any non-null reference values x and y, multiple invocations of x.equals(y) must consistently return true or consistently return false,
 *	provided no information used in equals comparisons is modified.
 * •  For any non-null reference value x, x.equals(null) must return false.
 * 
 * Putting it all together, here’s a recipe for a high-quality equals method:
 *  1. Use the == operator to check if the argument is a reference to this object.
 *  2. Use the instanceof operator to check if the argument has the correct type.
 *  3. Cast the argument to the correct type.
 *  4. For each “significant” field in the class, check if that field of the argument matches the corresponding field of this object.
 *  
 *  When you are finished writing your equals method, ask yourself three questions: Is it symmetric? Is it transitive? Is it consistent?
 *  • Always override hashCode when you override equals (Item 11).
 *  • Don’t try to be too clever.
 *  • Don’t substitute another type for Object in the equals declaration.
 *
 */
// Broken - violates symmetry! (Page 39)
public final class CaseInsensitiveString {
    private final String s;

    public CaseInsensitiveString(String s) {
	this.s = Objects.requireNonNull(s);
    }

    // Broken - violates symmetry!
    @Override
    public boolean equals(Object o) {
	if (o instanceof CaseInsensitiveString)
	    return s.equalsIgnoreCase(((CaseInsensitiveString) o).s);
	if (o instanceof String) // One-way interoperability!
	    return s.equalsIgnoreCase((String) o);
	return false;
    }

    // Demonstration of the problem (Page 40)
    public static void main(String[] args) {
	CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
	String s = "polish";

	// cis.equals(s) returns true. s.equals(cis) returns false, a clear violation of symmetry.
	System.out.println(cis.equals(s)); // true
	System.out.println(s.equals(cis)); // false

	List<CaseInsensitiveString> list = new ArrayList<>();
	list.add(cis);

	System.out.println(list.contains(s));
    }

    // // Fixed equals method (Page 40)
    // @Override public boolean equals(Object o) {
    // return o instanceof CaseInsensitiveString &&
    // ((CaseInsensitiveString) o).s.equalsIgnoreCase(s);
    // }
}
