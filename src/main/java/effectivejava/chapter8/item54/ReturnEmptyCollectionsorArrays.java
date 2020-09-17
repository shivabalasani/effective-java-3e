package effectivejava.chapter8.item54;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * Item 54: Return empty collections or arrays, not nulls.
 * 
 * This sort of circumlocution in Version 1 is required in nearly every use of a
 * method that returns null in place of an empty collection or array. It is
 * error-prone, because the programmer writing the client might forget to write
 * the special-case code to handle a null return. Such an error may go unnoticed
 * for years because such methods usually return one or more objects. Also,
 * returning null in place of an empty container complicates the implementation
 * of the method returning the container.
 * 
 * It is sometimes argued that a null return value is preferable to an empty
 * collection or array because it avoids the expense of allocating the empty
 * container. This argument fails on two counts. First, it is inadvisable to
 * worry about performance at this level unless measurements have shown that the
 * allocation in question is a real contributor to performance problems (Item
 * 67). Second, it is possible to return empty collections and arrays without
 * allocating them. Here Version 2 is the typical code to return a possibly
 * empty collection.
 * 
 * In the unlikely event that you have evidence suggesting that allocating empty
 * collections is harming performance, you can avoid the allocations by
 * returning the same immutable empty collection repeatedly, as immutable
 * objects may be shared freely (Item 17). Version 3 is the code to do it, using
 * the Collections.emptyList method. If you were returning a set, you�d use
 * Collections.emptySet; if you were returning a map, you�d use
 * Collections.emptyMap. But remember, this is an optimization, and it�s seldom
 * called for. If you think you need it, measure performance before and after,
 * to ensure that it�s actually helping:
 * 
 * The situation for arrays is identical to that for collections. Never return
 * null instead of a zero-length array. Normally, you should simply return an
 * array of the correct length, which may be zero. Note that we�re passing a
 * zero-length array into the toArray method to indicate the desired return
 * type, which is Cheese[]: see Version 4
 * 
 * If you believe that allocating zero-length arrays is harming performance, you
 * can return the same zero-length array repeatedly because all zero-length
 * arrays are immutable: see Version 5
 * 
 * In the optimized version, we pass the same empty array into every toArray
 * call, and this array will be returned from getCheeses whenever cheesesInStock
 * is empty. Do not preallocate the array passed to toArray in hopes of
 * improving performance. Studies have shown that it is counterproductive
 * [Shipil�v16]: see Version 6
 *
 * In summary, never return null in place of an empty array or collection. It
 * makes your API more difficult to use and more prone to error, and it has no
 * performance advantages.
 */
public class ReturnEmptyCollectionsorArrays {

	// Returns null to indicate an empty collection. Don�t do this!
	private static final List<Cheese> cheesesInStock = new ArrayList<Cheese>();

	private static final Cheese[] EMPTY_CHEESE_ARRAY = new Cheese[0];

	/**
	 * Version 1
	 * 
	 * @return a list containing all of the cheeses in the shop, or null if no
	 *         cheeses are available for purchase.
	 */
	public static List<Cheese> getCheeses() {
		return cheesesInStock.isEmpty() ? null : new ArrayList<>(cheesesInStock);
	}

	// Version 2 : The right way to return a possibly empty collection
	public static List<Cheese> getCheesesRight() {
		return new ArrayList<>(cheesesInStock);
	}

	// Version 3 : Optimization - avoids allocating empty collections
	public List<Cheese> getCheesesOptimized() {
		return cheesesInStock.isEmpty() ? Collections.emptyList() : new ArrayList<>(cheesesInStock);
	}

	// Verison 4: The right way to return a possibly empty array
	public Cheese[] getCheesesEmptyArray() {
		return cheesesInStock.toArray(new Cheese[0]);
	}

	// Version 5 : Optimization - avoids allocating empty arrays
	public Cheese[] getCheesesOptimizedArray() {
		return cheesesInStock.toArray(EMPTY_CHEESE_ARRAY);
	}

	// Version 6 : // Don�t do this - preallocating the array harms performance!
	public Cheese[] getCheesesPreallocate() {
		return cheesesInStock.toArray(new Cheese[cheesesInStock.size()]);
	}

	public static void main(String[] args) {
		List<Cheese> cheeses = getCheeses();
		List<Cheese> cheesesRight = getCheesesRight();

		// Doing so requires extra code in the client to handle the possibly
		// null return value
		if (cheeses != null && cheeses.contains(Cheese.STILTON))
			System.out.println("Jolly good, just the thing.");

		if (cheesesRight.contains(Cheese.STILTON))
			System.out.println("Jolly good, just the thing.");
	}
}

enum Cheese {
	STILTON, SWISS, PROVOLONE
}
