package effectivejava.chapter6.item34;

/**
 * 
 * In addition to rectifying the deficiencies of int enums, enum types let you
 * add arbitrary methods and fields and implement arbitrary interfaces. They
 * provide high-quality implementations of all the Object methods (Chapter 3),
 * they implement Comparable (Item 14) and Serializable (Chapter 12), and their
 * serialized form is designed to withstand most changes to the enum type.
 * 
 * So why would you want to add methods or fields to an enum type? For starters,
 * you might want to associate data with its constants. Our Apple and Orange
 * types, for example, might benefit from a method that returns the color of the
 * fruit, or one that returns an image of it. You can augment an enum type with
 * any method that seems appropriate. An enum type can start life as a simple
 * collection of enum constants and evolve over time into a full-featured
 * abstraction.
 * 
 * It is easy to write a rich enum type such as Planet. To associate data with
 * enum constants, declare instance fields and write a constructor that takes
 * the data and stores it in the fields. Enums are by their nature immutable, so
 * all fields should be final (Item 17). Fields can be public, but it is better
 * to make them private and provide public accessors (Item 16). In the case of
 * Planet, the constructor also computes and stores the surface gravity, but
 * this is just an optimization. The gravity could be recomputed from the mass
 * and radius each time it was used by the surfaceWeight method, which takes an
 * object’s mass and returns its weight on the planet represented by the
 * constant
 *
 */
// Enum type with data and behavior (159-160)
public enum Planet {
    MERCURY(3.302e+23, 2.439e6), VENUS(4.869e+24, 6.052e6), EARTH(5.975e+24, 6.378e6), MARS(6.419e+23,
	    3.393e6), JUPITER(1.899e+27,
		    7.149e7), SATURN(5.685e+26, 6.027e7), URANUS(8.683e+25, 2.556e7), NEPTUNE(1.024e+26, 2.477e7);

    private final double mass; // In kilograms
    private final double radius; // In meters
    private final double surfaceGravity; // In m / s^2

    // Universal gravitational constant in m^3 / kg s^2
    private static final double G = 6.67300E-11;

    // Constructor
    Planet(double mass, double radius) {
	this.mass = mass;
	this.radius = radius;
	surfaceGravity = G * mass / (radius * radius);
    }

    public double mass() {
	return mass;
    }

    public double radius() {
	return radius;
    }

    public double surfaceGravity() {
	return surfaceGravity;
    }

    public double surfaceWeight(double mass) {
	return mass * surfaceGravity; // F = ma
    }
}
