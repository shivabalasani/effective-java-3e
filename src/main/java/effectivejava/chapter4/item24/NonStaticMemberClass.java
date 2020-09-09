package effectivejava.chapter4.item24;

import java.util.AbstractSet;
import java.util.Iterator;

/**
 * 
 * One common use of a nonstatic member class is to define an Adapter [Gamma95]
 * that allows an instance of the outer class to be viewed as an instance of
 * some unrelated class. For example, implementations of the Map interface
 * typically use nonstatic member classes to implement their collection views,
 * which are returned by Map’s keySet, entrySet, and values methods. Similarly,
 * implementations of the collection interfaces, such as Set and List, typically
 * use nonstatic member classes to implement their iterators:
 *
 */
//Typical use of a nonstatic member class
public class NonStaticMemberClass<E> extends AbstractSet<E> {
	// Bulk of the class omitted
	@Override
	public Iterator<E> iterator() {
		return new MyIterator();
	}

	private class MyIterator implements Iterator<E> {

		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public E next() {
			return null;
		}

	}

	@Override
	public int size() {
		return 0;
	}
}