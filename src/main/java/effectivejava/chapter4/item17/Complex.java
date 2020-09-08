package effectivejava.chapter4.item17;

/**
 * 
 * Item 17: Minimize mutability
 * 
 * An immutable class is simply a class whose instances cannot be modified. All
 * of the information contained in each instance is fixed for the lifetime of
 * the object, so no changes can ever be observed. The Java platform libraries
 * contain many immutable classes, including String, the boxed primitive
 * classes, and BigInteger and BigDecimal. There are many good reasons for this:
 * Immutable classes are easier to design, implement, and use than mutable
 * classes. They are less prone to error and are more secure. To make a class
 * immutable, follow these five rules:
 * 
 * 1. Don’t provide methods that modify the object’s state (known as mutators).
 * 
 * 2. Ensure that the class can’t be extended. Preventing subclassing is
 * generally accomplished by making the class final, but there is an alternative
 * 
 * 3. Make all fields final.
 * 
 * 4. Make all fields private.
 * 
 * 5. Ensure exclusive access to any mutable components.
 * 
 * Immutable objects are simple. An immutable object can be in exactly one
 * state, the state in which it was created.Immutable objects are inherently
 * thread-safe; they require no synchronization. Immutable objects can be shared
 * freely.
 * 
 * Not only can you share immutable objects, but they can share their
 * internals.Immutable objects make great building blocks for other objects,
 * whether mutable or immutable.Immutable objects provide failure atomicity for
 * free (Item 76).The major disadvantage of immutable classes is that they
 * require a separate object for each distinct value.
 *
 * String class, whose mutable companion is StringBuilder (and its obsolete
 * predecessor, StringBuffer).
 * 
 * To guarantee immutability, a class must not permit itself to be subclassed.
 * This can be done by making the class final, but there is another, more
 * flexible alternative. Instead of making an immutable class final, you can
 * make all of its constructors private or package-private and add public static
 * factories in place of the public constructors (Item 1).
 * 
 * To summarize, resist the urge to write a setter for every getter. Classes
 * should be immutable unless there’s a very good reason to make them
 * mutable.There are some classes for which immutability is impractical. If a
 * class cannot be made immutable, limit its mutability as much as possible.
 * Declare every field private final unless there’s a good reason to do
 * otherwise. Constructors should create fully initialized objects with all of
 * their invariants established.
 */
// Immutable complex number class (Pages 81-82)
public final class Complex {
    // public class Complex { //flexible alternative
    private final double re;
    private final double im;

    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);
    public static final Complex I = new Complex(0, 1);

    // private Complex(double re, double im) { //flexible alternative
    private Complex(double re, double im) {
	this.re = re;
	this.im = im;
    }

    public double realPart() {
	return re;
    }

    public double imaginaryPart() {
	return im;
    }

    public Complex plus(Complex c) {
	return new Complex(re + c.re, im + c.im);
    }

    // Static factory, used in conjunction with private constructor (Page 85)
    public static Complex valueOf(double re, double im) {
	return new Complex(re, im);
    }

    public Complex minus(Complex c) {
	return new Complex(re - c.re, im - c.im);
    }

    public Complex times(Complex c) {
	return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
    }

    public Complex dividedBy(Complex c) {
	double tmp = c.re * c.re + c.im * c.im;
	return new Complex((re * c.re + im * c.im) / tmp, (im * c.re - re * c.im) / tmp);
    }

    @Override
    public boolean equals(Object o) {
	if (o == this)
	    return true;
	if (!(o instanceof Complex))
	    return false;
	Complex c = (Complex) o;

	// See page 47 to find out why we use compare instead of ==
	return Double.compare(c.re, re) == 0 && Double.compare(c.im, im) == 0;
    }

    @Override
    public int hashCode() {
	return 31 * Double.hashCode(re) + Double.hashCode(im);
    }

    @Override
    public String toString() {
	return "(" + re + " + " + im + "i)";
    }
}
