package effectivejava.chapter4.item16;

/**
 * 
 * Item 16: In public classes, use accessor methods, not public fields
 * 
 * If a class is accessible outside its package, provide accessor methods to
 * preserve the flexibility to change the class’s internal representation.
 * However, if a class is package-private or is a private nested class, there is
 * nothing inherently wrong with exposing its data fields—
 * 
 * Several classes in the Java platform libraries violate the advice that public
 * classes should not expose fields directly. Prominent examples include the
 * Point and Dimension classes in the java.awt package.
 * 
 * In summary, public classes should never expose mutable fields. It is less
 * harmful, though still questionable, for public classes to expose immutable
 * fields. It is, however, sometimes desirable for package-private or private
 * nested classes to expose fields, whether mutable or immutable.
 *
 */
// Encapsulation of data by accessor methods and mutators (Page 78)
class Point {
	private double x;
	private double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
}
