package effectivejava.chapters12.item90;

import java.io.Serializable;
import java.util.EnumSet;

/**
 * 
 * 
 * Consider the case of EnumSet (Item 36). This class has no public
 * constructors, only static factories. From the client’s perspective, they
 * return EnumSet instances, but in the current OpenJDK implementation, they
 * return one of two subclasses, depending on the size of the underlying enum
 * type. If the underlying enum type has sixty-four or fewer elements, the
 * static factories return a RegularEnumSet; otherwise, they return a
 * JumboEnumSet.
 * 
 * Now consider what happens if you serialize an enum set whose enum type has
 * sixty elements, then add five more elements to the enum type, and then
 * deserialize the enum set. It was a RegularEnumSet instance when it was
 * serialized, but it had better be a JumboEnumSet instance once it is
 * deserialized. In fact that’s exactly what happens, because EnumSet uses the
 * serialization proxy pattern. In case you’re curious, here is EnumSet’s
 * serialization proxy. It really is this simple:
 * 
 * The serialization proxy pattern has two limitations. It is not compatible
 * with classes that are extendable by their users (Item 19). Also, it is not
 * compatible with some classes whose object graphs contain circularities: if
 * you attempt to invoke a method on such an object from within its
 * serialization proxy’s readResolve method, you’ll get a ClassCastException
 * because you don’t have the object yet, only its serialization proxy.
 * 
 * Finally, the added power and safety of the serialization proxy pattern are
 * not free. On my machine, it is 14 percent more expensive to serialize and
 * deserialize Period instances with serialization proxies than it is with
 * defensive copying.
 *
 */
// EnumSet's serialization proxy
class SerializationProxy<E extends Enum<E>> implements Serializable {
    // The element type of this enum set.
    private final Class<E> elementType;
    // The elements contained in this enum set.
    private final Enum<?>[] elements;

    SerializationProxy(EnumSet<E> set) {
	elementType = null;
	// elementType = set.elementType;
	elements = set.toArray(new Enum<?>[0]);
    }

    private Object readResolve() {
	EnumSet<E> result = EnumSet.noneOf(elementType);
	for (Enum<?> e : elements)
	    result.add((E) e);
	return result;
    }

    private static final long serialVersionUID = 362491234563181265L;
}