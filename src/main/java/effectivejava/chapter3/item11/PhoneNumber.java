package effectivejava.chapter3.item11;
import java.util.*;

/**
 * 
 * Item 11: Always override hashCode when you override equals
 *   You must override hashCode in every class that overrides equals.
 * 
 * -  When the hashCode method is invoked on an object repeatedly during an
 *    execution of an application, it must consistently return the same value,
 *    provided no information used in equals comparisons is modified.
 *   
 *  - If two objects are equal according to the equals(Object) method, then calling
 *    hashCode on the two objects must produce the same integer result.
 *    
 *  - If two objects are unequal according to the equals(Object) method, it is not
 *    required that calling hashCode on each of the objects must produce distinct results.
 *    
 *    The key provision that is violated when you fail to override hashCode is the second one: equal objects must have equal hash codes.
 *    Do not be tempted to exclude significant fields from the hash code computation to improve performance.
 *    Don’t provide a detailed specification for the value returned by hashCode, so clients can’t reasonably depend on it; this gives 
 *    you the flexibility to change it.
 *
 */
// Shows the need for overriding hashcode when you override equals (Pages 50-53 )
public final class PhoneNumber {
    private final short areaCode, prefix, lineNum;

    public PhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode = rangeCheck(areaCode, 999, "area code");
        this.prefix   = rangeCheck(prefix,   999, "prefix");
        this.lineNum  = rangeCheck(lineNum, 9999, "line num");
    }

    private static short rangeCheck(int val, int max, String arg) {
        if (val < 0 || val > max)
            throw new IllegalArgumentException(arg + ": " + val);
        return (short) val;
    }

    @Override public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PhoneNumber))
            return false;
        PhoneNumber pn = (PhoneNumber)o;
        return pn.lineNum == lineNum && pn.prefix == prefix
                && pn.areaCode == areaCode;
    }


    // Broken with no hashCode; works with any of the three below

//    // Typical hashCode method (Page 52)
//    @Override public int hashCode() {
//        int result = Short.hashCode(areaCode);
//        result = 31 * result + Short.hashCode(prefix);
//        result = 31 * result + Short.hashCode(lineNum);
//        return result;
//    }
    
//    //The worst possible legal hashCode implementation - never use!
//    @Override public int hashCode() { return 42; }

//    // One-line hashCode method - mediocre performance  (page 53)
//    @Override public int hashCode() {
//        return Objects.hash(lineNum, prefix, areaCode);
//    }

//    // hashCode method with lazily initialized cached hash code  (page 53)
//    private int hashCode; // Automatically initialized to 0
//
//    @Override public int hashCode() {
//        int result = hashCode;
//        if (result == 0) {
//            result = Short.hashCode(areaCode);
//            result = 31 * result + Short.hashCode(prefix);
//            result = 31 * result + Short.hashCode(lineNum);
//            hashCode = result;
//        }
//        return result;
//    }

    public static void main(String[] args) {
        Map<PhoneNumber, String> m = new HashMap<>();
        m.put(new PhoneNumber(707, 867, 5309), "Jenny");
        System.out.println(m.get(new PhoneNumber(707, 867, 5309)));
        
        int value = 1343;
        System.out.println((int)(value ^ (value >>> 32)));
    }
}
