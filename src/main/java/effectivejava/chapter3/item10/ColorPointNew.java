package effectivejava.chapter3.item10;

public class ColorPointNew extends Point {
    private final Color color;

    public ColorPointNew(int x, int y, Color color) {
	super(x, y);
	this.color = color;
    }

    // Broken - violates symmetry!
    // @Override
    // public boolean equals(Object o) {
    // if (!(o instanceof ColorPoint))
    // return false;
    // return super.equals(o) && ((ColorPoint) o).color == color;
    // }

    // Broken - violates transitivity!
    @Override
    public boolean equals(Object o) {
	if (!(o instanceof Point))
	    return false;
	// If o is a normal Point, do a color-blind comparison
	if (!(o instanceof ColorPointNew))
	    return o.equals(this);
	// o is a ColorPoint; do a full comparison
	return super.equals(o) && ((ColorPointNew) o).color == color;
    }

    public static void main(String[] args) {
	ColorPointNew p1 = new ColorPointNew(1, 2, Color.RED);
	Point p2 = new Point(1, 2);
	ColorPointNew p3 = new ColorPointNew(1, 2, Color.BLUE);

	System.out.println(p1.equals(p2)); // returns true
	System.out.println(p2.equals(p3)); // returns true
	System.out.println(p1.equals(p3)); // returns false
    }
}
