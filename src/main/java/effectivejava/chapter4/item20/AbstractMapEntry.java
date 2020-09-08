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
 * Interfaces are ideal for defining mixins.
 * 
 * Interfaces allow for the construction of nonhierarchical type frameworks.
 * 
 * Interfaces enable safe, powerful functionality enhancements
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
