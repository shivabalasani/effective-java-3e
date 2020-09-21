package effectivejava.chapters12.item87;

import java.io.*;

/**
 * 
 * Item 87: Consider using a custom serialized form.
 * 
 * When you are writing a class under time pressure, it is generally appropriate
 * to concentrate your efforts on designing the best API. Sometimes this means
 * releasing a “throwaway” implementation that you know you’ll replace in a
 * future release. Normally this is not a problem, but if the class implements
 * Serializable and uses the default serialized form, you’ll never be able to
 * escape completely from the throwaway implementation. It will dictate the
 * serialized form forever. This is not just a theoretical problem. It happened
 * to several classes in the Java libraries, including BigInteger.
 * 
 * Do not accept the default serialized form without first considering whether
 * it is appropriate.
 * 
 * The default serialized form is likely to be appropriate if an object’s
 * physical representation is identical to its logical content. For example, the
 * default serialized form would be reasonable for the following class, which
 * simplistically represents a person’s name: see Name
 * 
 * Even if you decide that the default serialized form is appropriate, you often
 * must provide a readObject method to ensure invariants and security.In the
 * case of Name, the readObject method must ensure that the fields lastName and
 * firstName are non-null. This issue is discussed at length in Items 88 and 90.
 * 
 * Note that there are documentation comments on the lastName, firstName, and
 * middleName fields, even though they are private. That is because these
 * private fields define a public API, which is the serialized form of the
 * class, and this public API must be documented. The presence of the @serial
 * tag tells Javadoc to place this documentation on a special page that
 * documents serialized forms.
 * 
 * As in StringListAwful Using the default serialized form when an object’s
 * physical representation differs substantially from its logical data content
 * has four disadvantages:
 * 
 * • It permanently ties the exported API to the current internal
 * representation.
 * 
 * • It can consume excessive space.
 * 
 * • It can consume excessive time.
 * 
 * • It can cause stack overflows.
 * 
 * Before deciding to make a field nontransient, convince yourself that its
 * value is part of the logical state of the object. If you use a custom
 * serialized form, most or all of the instance fields should be labeled
 * transient, as in the StringList example.
 * 
 * Whether or not you use the default serialized form, you must impose any
 * synchronization on object serialization that you would impose on any other
 * method that reads the entire state of the object.
 * 
 * Regardless of what serialized form you choose, declare an explicit serial
 * version UID in every serializable class you write.
 * 
 * Do not change the serial version UID unless you want to break compatibility
 * with all existing serialized instances of a class.
 * 
 * To summarize, if you have decided that a class should be serializable (Item
 * 86), think hard about what the serialized form should be. Use the default
 * serialized form only if it is a reasonable description of the logical state
 * of the object; otherwise design a custom serialized form that aptly describes
 * the object. You should allocate as much time to designing the serialized form
 * of a class as you allocate to designing an exported method (Item 51). Just as
 * you can’t eliminate exported methods from future versions, you can’t
 * eliminate fields from the serialized form; they must be preserved forever to
 * ensure serialization compatibility. Choosing the wrong serialized form can
 * have a permanent, negative impact on the complexity and performance of a
 * class.
 * 
 */

// StringList with a reasonable custom serialized form - Page 349
public final class StringList implements Serializable {
    private transient int size = 0;
    private transient Entry head = null;

    // No longer Serializable!
    private static class Entry {
	String data;
	Entry next;
	Entry previous;
    }

    // Appends the specified string to the list
    public final void add(String s) {
    }

    /**
     * Serialize this {@code StringList} instance.
     *
     * @serialData The size of the list (the number of strings it contains) is
     *             emitted ({@code int}), followed by all of its elements (each a
     *             {@code String}), in the proper sequence.
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
	s.defaultWriteObject();
	s.writeInt(size);

	// Write out all elements in the proper order.
	for (Entry e = head; e != null; e = e.next)
	    s.writeObject(e.data);
    }

    // writeObject for synchronized class with default serialized form
    private synchronized void writeObjectSynchronized(ObjectOutputStream s) throws IOException {
	s.defaultWriteObject();
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
	s.defaultReadObject();
	int numElements = s.readInt();

	// Read in all elements and insert them in list
	for (int i = 0; i < numElements; i++)
	    add((String) s.readObject());
    }

    // Remainder omitted
}

// Awful candidate for default serialized form
final class StringListAwful implements Serializable {
    private int size = 0;
    private Entry head = null;

    private static class Entry implements Serializable {
	String data;
	Entry next;
	Entry previous;
    }
    // Remainder omitted
}
