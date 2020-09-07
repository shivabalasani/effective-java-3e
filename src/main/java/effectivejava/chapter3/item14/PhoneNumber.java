package effectivejava.chapter3.item14;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import static java.util.Comparator.*;

/**
 * 
 * The general contract of the compareTo method is similar to that of equals:
 * Comparator.compare - int compare(T o1, T o2);
 * 
 * Compares its two arguments for order. Returns a negative integer, zero, or a
 * positive integer as the first argument is less than, equal to, or greater
 * than the second.
 * 
 * The implementor must ensure that {@code sgn(compare(x, y)) ==
 * -sgn(compare(y, x))} for all {@code x} and {@code y}. (This implies that
 * {@code compare(x, y)} must throw an exception if and only if
 * {@code compare(y, x)} throws an exception.)
 *
 * The implementor must also ensure that the relation is transitive:
 * {@code ((compare(x, y)>0) && (compare(y, z)>0))} implies
 * {@code compare(x, z)>0}.
 *
 * Finally, the implementor must ensure that {@code compare(x, y)==0} implies
 * that {@code sgn(compare(x, z))==sgn(compare(y, z))} for all {@code z}.
 *
 * It is generally the case, but <i>not</i> strictly required that
 * {@code (compare(x, y)==0) == (x.equals(y))}. Generally speaking, any
 * comparator that violates this condition should clearly indicate this fact.
 * The recommended language is "Note: this comparator imposes orderings that are
 * inconsistent with equals."
 * <p>
 * 
 *
 */
// Making PhoneNumber comparable (Pages 69-70)
public final class PhoneNumber implements Cloneable, Comparable<PhoneNumber> {
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
	if (!(o instanceof effectivejava.chapter3.item11.PhoneNumber))
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

    // // Multiple-field Comparable with primitive fields (page 69)
    // public int compareTo(PhoneNumber pn) {
    // int result = Short.compare(areaCode, pn.areaCode);
    // if (result == 0) {
    // result = Short.compare(prefix, pn.prefix);
    // if (result == 0)
    // result = Short.compare(lineNum, pn.lineNum);
    // }
    // return result;
    // }

    // ------------------------------------------------------------------------------------------------------
    // BROKEN difference-based comparator - violates transitivity!
    // Do not use this technique. It is fraught with danger from integer overflow
    // and IEEE 754 floating point arithmetic artifacts
    // Use either a static compare method or a comparator construction method:
    static Comparator<Object> hashCodeOrder = new Comparator<>() {
	public int compare(Object o1, Object o2) {
	    return o1.hashCode() - o2.hashCode();
	}
    };

    // Comparator based on static compare method
    static Comparator<Object> hashCodeOrderUsingCompare = new Comparator<>() {
	public int compare(Object o1, Object o2) {
	    return Integer.compare(o1.hashCode(), o2.hashCode());
	}
    };

    // Comparator based on Comparator construction method
    static Comparator<Object> hashCodeOrderUsingComapringInt = Comparator.comparingInt(o -> o.hashCode());
    // ----------------------------------------------------------------------------------------------------

    // Comparable with comparator construction methods (page 70)
    private static final Comparator<PhoneNumber> COMPARATOR = comparingInt((PhoneNumber pn) -> pn.areaCode)
	    .thenComparingInt(pn -> pn.prefix).thenComparingInt(pn -> pn.lineNum);

    public int compareTo(PhoneNumber pn) {
	return COMPARATOR.compare(this, pn);
    }

    private static PhoneNumber randomPhoneNumber() {
	Random rnd = ThreadLocalRandom.current();
	return new PhoneNumber((short) rnd.nextInt(1000), (short) rnd.nextInt(1000), (short) rnd.nextInt(10000));
    }

    public static void main(String[] args) {
	NavigableSet<PhoneNumber> s = new TreeSet<PhoneNumber>();
	for (int i = 0; i < 100; i++)
	    s.add(randomPhoneNumber());
	System.out.println(s);
    }
}
