package effectivejava.chapter3.item13;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Item 13: Override clone judiciously In practice, a class implementing
 * Cloneable is expected to provide a properly functioning public clone method.
 * Immutable classes should never provide a clone method
 * 
 * The general contract for the clone method is weak. Here it is, copied from
 * the Object specification : Creates and returns a copy of this object. The
 * precise meaning of “copy” may depend on the class of the object. The general
 * intent is that, for any object x, the expression
 * 
 * x.clone() != x will be true, and the expression
 * 
 * x.clone().getClass() == x.getClass() will be true, but these are not absolute
 * requirements. While it is typically the case that
 * 
 * x.clone().equals(x) will be true, this is not an absolute requirement.
 */
// Adding a clone method to PhoneNumber (page 59)
public final class PhoneNumber implements Cloneable {
	private final short areaCode, prefix, lineNum;

	public PhoneNumber(int areaCode, int prefix, int lineNum) {
		this.areaCode = rangeCheck(areaCode, 999, "area code");
		this.prefix = rangeCheck(prefix, 999, "prefix");
		this.lineNum = rangeCheck(lineNum, 9999, "line num");
	}

	private static short rangeCheck(int val, int max, String arg) {
		if (val < 0 || val > max)
			throw new IllegalArgumentException(arg + ": " + val);
		return (short) val;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof PhoneNumber))
			return false;
		PhoneNumber pn = (PhoneNumber) o;
		return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
	}

	@Override
	public int hashCode() {
		int result = Short.hashCode(areaCode);
		result = 31 * result + Short.hashCode(prefix);
		result = 31 * result + Short.hashCode(lineNum);
		return result;
	}

	/**
	 * Returns the string representation of this phone number. The string consists
	 * of twelve characters whose format is "XXX-YYY-ZZZZ", where XXX is the area
	 * code, YYY is the prefix, and ZZZZ is the line number. Each of the capital
	 * letters represents a single decimal digit.
	 *
	 * If any of the three parts of this phone number is too small to fill up its
	 * field, the field is padded with leading zeros. For example, if the value of
	 * the line number is 123, the last four characters of the string representation
	 * will be "0123".
	 */
	@Override
	public String toString() {
		return String.format("%03d-%03d-%04d", areaCode, prefix, lineNum);
	}

	// Clone method for class with no references to mutable state (Page 59)
	@Override
	public PhoneNumber clone() {
		try {
			return (PhoneNumber) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(); // Can't happen
		}
	}

	public static void main(String[] args) {
		PhoneNumber pn = new PhoneNumber(707, 867, 5309);
		PhoneNumber pnClone = pn.clone();
		Map<PhoneNumber, String> m = new HashMap<>();
		m.put(pn, "Jenny");
		System.out.println(m.get(pnClone));

		System.out.println(pnClone != pn); // true
		System.out.println(pnClone.getClass() == pn.getClass()); // true
		System.out.println(pnClone.equals(pn)); // true

	}
}
