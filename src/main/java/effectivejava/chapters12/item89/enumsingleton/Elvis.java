package effectivejava.chapters12.item89.enumsingleton;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * Item 89: For instance control, prefer enum types to readResolve.
 * 
 * Item 3 describes the Singleton pattern and gives the following example of a
 * singleton class. This class restricts access to its constructor to ensure
 * that only a single instance is ever created.
 * 
 * As noted in Item 3, this class would no longer be a singleton if the words
 * implements Serializable were added to its declaration. It doesn’t matter
 * whether the class uses the default serialized form or a custom serialized
 * form (Item 87), nor does it matter whether the class provides an explicit
 * readObject method (Item 88). Any readObject method, whether explicit or
 * default, returns a newly created instance, which will not be the same
 * instance that was created at class initialization time.
 * 
 * As noted in Item 3, this class would no longer be a singleton if the words
 * implements Serializable were added to its declaration. It doesn’t matter
 * whether the class uses the default serialized form or a custom serialized
 * form (Item 87), nor does it matter whether the class provides an explicit
 * readObject method (Item 88). Any readObject method, whether explicit or
 * default, returns a newly created instance, which will not be the same
 * instance that was created at class initialization time.
 * 
 * The readResolve feature allows you to substitute another instance for the one
 * created by readObject [Serialization, 3.7]. If the class of an object being
 * deserialized defines a readResolve method with the proper declaration, this
 * method is invoked on the newly created object after it is deserialized. The
 * object reference returned by this method is then returned in place of the
 * newly created object. In most uses of this feature, no reference to the newly
 * created object is retained, so it immediately becomes eligible for garbage
 * collection.
 * 
 * If the Elvis class is made to implement Serializable, the following read-
 * Resolve method suffices to guarantee the singleton property:
 * 
 * This method ignores the deserialized object, returning the distinguished
 * Elvis instance that was created when the class was initialized. Therefore,
 * the serialized form of an Elvis instance need not contain any real data; all
 * instance fields should be declared transient. In fact, if you depend on
 * readResolve for instance control, all instance fields with object reference
 * types must be declared transient. Otherwise, it is possible for a determined
 * attacker to secure a reference to the deserialized object before its
 * readResolve method is run, using a technique that is somewhat similar to the
 * MutablePeriod attack in Item 88.
 * 
 * The attack is a bit complicated, but the underlying idea is simple. If a
 * singleton contains a nontransient object reference field, the contents of
 * this field will be deserialized before the singleton’s readResolve method is
 * run. This allows a carefully crafted stream to “steal” a reference to the
 * originally deserialized singleton at the time the contents of the object
 * reference field are deserialized.
 * 
 * Here’s how it works in more detail. First, write a “stealer” class that has
 * both a readResolve method and an instance field that refers to the serialized
 * singleton in which the stealer “hides.” In the serialization stream, replace
 * the singleton’s nontransient field with an instance of the stealer. You now
 * have a circularity: the singleton contains the stealer, and the stealer
 * refers to the singleton.
 * 
 * Because the singleton contains the stealer, the stealer’s readResolve method
 * runs first when the singleton is deserialized. As a result, when the
 * stealer’s readResolve method runs, its instance field still refers to the
 * partially deserialized (and as yet unresolved) singleton.
 * 
 * The stealer’s readResolve method copies the reference from its instance field
 * into a static field so that the reference can be accessed after the
 * readResolve method runs. The method then returns a value of the correct type
 * for the field in which it’s hiding. If it didn’t do this, the VM would throw
 * a ClassCastException when the serialization system tried to store the stealer
 * reference into this field.
 * 
 * To make this concrete, consider the following broken singleton:
 */
// Broken singleton - has nontransient object reference field!
public class Elvis implements Serializable {

    public static final Elvis INSTANCE = new Elvis();

    private Elvis() {
    }

    private String[] favoriteSongs = { "Hound Dog", "Heartbreak Hotel" };

    public void printFavorites() {
	System.out.println(Arrays.toString(favoriteSongs));
    }

    // readResolve for instance control - you can do better!
    private Object readResolve() {
	// Return the one true Elvis and let the garbage collector
	// take care of the Elvis impersonator.
	return INSTANCE;
    }
}