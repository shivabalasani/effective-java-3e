package effectivejava.chapter7.item47;

import java.util.*;

/**
 * 
 * The Collection interface is a subtype of Iterable and has a stream method, so
 * it provides for both iteration and stream access. Therefore, Collection or an
 * appropriate subtype is generally the best return type for a public,
 * sequencereturning method. Arrays also provide for easy iteration and stream
 * access with the Arrays.asList and Stream.of methods. If the sequence you’re
 * returning is small enough to fit easily in memory, you’re probably best off
 * returning one of the standard collection implementations, such as ArrayList
 * or HashSet. But do not store a large sequence in memory just to return it as
 * a collection.
 * 
 * Note that PowerSet.of throws an exception if the input set has more than 30
 * elements. This highlights a disadvantage of using Collection as a return type
 * rather than Stream or Iterable: Collection has an int-returning size method,
 * which limits the length of the returned sequence to Integer.MAX_VALUE, or
 * 2^31 - 1. The Collection specification does allow the size method to return
 * 2^31 - 1 if the collection is larger, even infinite, but this is not a wholly
 * satisfying solution.
 *
 */

public class PowerSet {
	// Returns the power set of an input set as custom collection (Page 218)
	public static final <E> Collection<Set<E>> of(Set<E> s) {
		List<E> src = new ArrayList<>(s);
		if (src.size() > 30)
			throw new IllegalArgumentException("Set too big " + s);
		return new AbstractList<Set<E>>() {
			@Override
			public int size() {
				return 1 << src.size(); // 2 to the power srcSize
			}

			@Override
			public boolean contains(Object o) {
				return o instanceof Set && src.containsAll((Set) o);
			}

			@Override
			public Set<E> get(int index) {
				Set<E> result = new HashSet<>();
				for (int i = 0; index != 0; i++, index >>= 1)
					if ((index & 1) == 1)
						result.add(src.get(i));
				return result;
			}
		};
	}

	public static void main(String[] args) {
		String[] strings = { "a", "b", "c" };
		Set s = new HashSet(Arrays.asList(strings));
		System.out.println(PowerSet.of(s));
	}
}