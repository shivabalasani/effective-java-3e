package effectivejava.chapter6.item35;

/**
 * 
 * Item 35: Use instance fields instead of ordinals
 * 
 * Many enums are naturally associated with a single int value. All enums have
 * an ordinal method, which returns the numerical position of each enum constant
 * in its type. You may be tempted to derive an associated int value from the
 * ordinal:
 * 
 * Luckily, there is a simple solution to the problems in EnsembleDeficient.
 * Never derive a value associated with an enum from its ordinal; store it in an
 * instance field instead:
 * 
 * The Enum specification has this to say about ordinal: �Most programmers will
 * have no use for this method. It is designed for use by general-purpose
 * enumbased data structures such as EnumSet and EnumMap.� Unless you are
 * writing code with this character, you are best off avoiding the ordinal
 * method entirely.
 *
 */

// Enum with integer data stored in an instance field (Page 168)
public enum Ensemble {
    SOLO(1), DUET(2), TRIO(3), QUARTET(4), QUINTET(5), SEXTET(6), SEPTET(7), OCTET(8), DOUBLE_QUARTET(8), NONET(
	    9), DECTET(10), TRIPLE_QUARTET(12);

    private final int numberOfMusicians;

    Ensemble(int size) {
	this.numberOfMusicians = size;
    }

    public int numberOfMusicians() {
	return numberOfMusicians;
    }
}
