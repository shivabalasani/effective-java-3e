package effectivejava.chapter6.item41;

/**
 * 
 * Item 41: Use marker interfaces to define types.
 * 
 * A marker interface is an interface that contains no method declarations but
 * merely designates (or “marks”) a class that implements the interface as
 * having some property. For example, consider the Serializable interface
 * (Chapter 12). By implementing this interface, a class indicates that its
 * instances can be written to an ObjectOutputStream (or “serialized”).
 *
 * You may hear it said that marker annotations (Item 39) make marker interfaces
 * obsolete. This assertion is incorrect. Marker interfaces have two advantages
 * over marker annotations. First and foremost, marker interfaces define a type
 * that is implemented by instances of the marked class; marker annotations do
 * not. The existence of a marker interface type allows you to catch errors at
 * compile time that you couldn’t catch until runtime if you used a marker
 * annotation.
 * 
 * Java’s serialization facility (Chapter 6) uses the Serializable marker
 * interface to indicate that a type is serializable. The
 * ObjectOutputStream.writeObject method, which serializes the object that is
 * passed to it, requires that its argument be serializable. Had the argument of
 * this method been of type Serializable, an attempt to serialize an
 * inappropriate object would have been detected at compile time (by type
 * checking). Compile-time error detection is the intent of marker interfaces,
 * but unfortunately, the ObjectOutputStream.write API does not take advantage
 * of the Serializable interface: its argument is declared to be of type Object,
 * so attempts to serialize an unserializable object won’t fail until runtime.
 * 
 * Another advantage of marker interfaces over marker annotations is that they
 * can be targeted more precisely.If an annotation type is declared with target
 * ElementType.TYPE, it can be applied to any class or interface. Suppose you
 * have a marker that is applicable only to implementations of a particular
 * interface. If you define it as a marker interface, you can have it extend the
 * sole interface to which it is applicable, guaranteeing that all marked types
 * are also subtypes of the sole interface to which it is applicable.
 * 
 * The chief advantage of marker annotations over marker interfaces is that they
 * are part of the larger annotation facility. Therefore, marker annotations
 * allow for consistency in annotation-based frameworks.
 * 
 * In summary, marker interfaces and marker annotations both have their uses. If
 * you want to define a type that does not have any new methods associated with
 * it, a marker interface is the way to go. If you want to mark program elements
 * other than classes and interfaces or to fit the marker into a framework that
 * already makes heavy use of annotation types, then a marker annotation is the
 * correct choice. If you find yourself writing a marker annotation type whose
 * target is ElementType.TYPE, take the time to figure out whether it really
 * should be an annotation type or whether a marker interface would be more
 * appropriate.
 */
public interface MarkerInterface {

}
