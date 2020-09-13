package effectivejava.chapter6.item35;

/**
 * 
 * While this enum works, it is a maintenance nightmare. If the constants are
 * reordered, the numberOfMusicians method will break. If you want to add a
 * second enum constant associated with an int value that you’ve already used,
 * you’re out of luck. For example, it might be nice to add a constant for
 * double quartet, which, like an octet, consists of eight musicians, but there
 * is no way to do it.
 * 
 * Also, you can’t add a constant for an int value without adding constants for
 * all intervening int values. For example, suppose you want to add a constant
 * representing a triple quartet, which consists of twelve musicians. There is
 * no standard term for an ensemble consisting of eleven musicians, so you are
 * forced to add a dummy constant for the unused int value (11). At best, this
 * is ugly. If many int values are unused, it’s impractical.
 *
 */
// Abuse of ordinal to derive an associated value - DON'T DO THIS
public enum EnsembleDeficient {

    SOLO, DUET, TRIO, QUARTET, QUINTET, SEXTET, SEPTET, OCTET, NONET, DECTET;
    public int numberOfMusicians() {
	return ordinal() + 1;
    }

    public static void main(String[] args) {
	for (EnsembleDeficient ensemble : values()) {
	    System.out.println(ensemble.ordinal());
	    // System.out.println(ensemble.numberOfMusicians());
	}
    }

}
