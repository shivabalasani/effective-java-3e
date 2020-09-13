package effectivejava.chapter6.item34;

/**
 * 
 * Note that Planet, like all enums, has a static values method that returns an
 * array of its values in the order they were declared. Note also that the
 * toString method returns the declared name of each enum value, enabling easy
 * printing by println and printf. If you’re dissatisfied with this string
 * representation, you can change it by overriding the toString method.
 *
 */
// Takes earth-weight and prints table of weights on all planets (Page 160)
public class WeightTable {
    public static void main(String[] args) {

	double earthWeight = Double.parseDouble("170.00");
	double mass = earthWeight / Planet.EARTH.surfaceGravity();

	for (Planet p : Planet.values())
	    System.out.printf("Weight on %s is %f%n", p, p.surfaceWeight(mass));
    }
}
