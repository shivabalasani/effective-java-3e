package effectivejava.chapter4.item23.taggedclass;

/**
 * 
 * Item 23: Prefer class hierarchies to tagged classes.
 * 
 * Occasionally you may run across a class whose instances come in two or more
 * flavors and contain a tag field indicating the flavor of the instance.In
 * short, tagged classes are verbose, error-prone, and inefficient.
 * 
 * Luckily, object-oriented languages such as Java offer a far better
 * alternative for defining a single data type capable of representing objects
 * of multiple flavors: subtyping. A tagged class is just a pallid imitation of
 * a class hierarchy.
 * 
 * In summary, tagged classes are seldom appropriate. If you’re tempted to write
 * a class with an explicit tag field, think about whether the tag could be
 * eliminated and the class replaced by a hierarchy. When you encounter an
 * existing class with a tag field, consider refactoring it into a hierarchy.
 *
 */

// Tagged class - vastly inferior to a class hierarchy! (Page 109)
class Figure {
	enum Shape {
		RECTANGLE, CIRCLE
	};

	// Tag field - the shape of this figure
	final Shape shape;

	// These fields are used only if shape is RECTANGLE
	double length;
	double width;

	// This field is used only if shape is CIRCLE
	double radius;

	// Constructor for circle
	Figure(double radius) {
		shape = Shape.CIRCLE;
		this.radius = radius;
	}

	// Constructor for rectangle
	Figure(double length, double width) {
		shape = Shape.RECTANGLE;
		this.length = length;
		this.width = width;
	}

	double area() {
		switch (shape) {
		case RECTANGLE:
			return length * width;
		case CIRCLE:
			return Math.PI * (radius * radius);
		default:
			throw new AssertionError(shape);
		}
	}
}
