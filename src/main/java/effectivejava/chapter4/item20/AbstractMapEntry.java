package effectivejava.chapter4.item20;

import java.util.*;

/**
 * 
 * Item 20: Prefer interfaces to abstract classes
 * 
 * Existing classes can easily be retrofitted to implement a new interface.For
 * example, many existing classes were retrofitted to implement the Comparable,
 * Iterable, and Autocloseable interfaces when they were added to the platform.
 * 
 * Interfaces are ideal for defining mixins.For example, Comparable is a mixin
 * interface that allows a class to declare that its instances are ordered with
 * respect to other mutually comparable objects.
 * 
 * Interfaces allow for the construction of nonhierarchical type frameworks.
 * 
 * Interfaces enable safe, powerful functionality enhancements via the wrapper
 * class idiom (Item 18).
 * 
 * To summarize, an interface is generally the best way to define a type that
 * permits multiple implementations. If you export a nontrivial interface, you
 * should strongly consider providing a skeletal implementation to go with it.
 * To the extent possible, you should provide the skeletal implementation via
 * default methods on the interface so that all implementors of the interface
 * can make use of it. That said, restrictions on interfaces typically mandate
 * that a skeletal implementation take the form of an abstract class.
 *
 */
// Skeletal implementation class (Pages 102-3)
public abstract class AbstractMapEntry<K, V> implements Map.Entry<K, V> {
	// Entries in a modifiable map must override this method
	@Override
	public V setValue(V value) {
		throw new UnsupportedOperationException();
	}

	// Implements the general contract of Map.Entry.equals
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Map.Entry))
			return false;
		Map.Entry<?, ?> e = (Map.Entry) o;
		return Objects.equals(e.getKey(), getKey()) && Objects.equals(e.getValue(), getValue());
	}

	// Implements the general contract of Map.Entry.hashCode
	@Override
	public int hashCode() {
		return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
	}

	@Override
	public String toString() {
		return getKey() + "=" + getValue();
	}
}
