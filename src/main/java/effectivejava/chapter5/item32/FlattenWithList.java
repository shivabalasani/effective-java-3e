package effectivejava.chapter5.item32;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * An alternative to using the SafeVarargs annotation is to take the advice of
 * Item 28 and replace the varargs parameter (which is an array in disguise)
 * with a List parameter.
 * 
 * The advantage of this approach is that the compiler can prove that the method
 * is typesafe. You don’t have to vouch for its safety with a SafeVarargs
 * annotation, and you don’t have worry that you might have erred in determining
 * that it was safe. The main disadvantage is that the client code is a bit more
 * verbose and may be a bit slower.
 *
 */
// List as a typesafe alternative to a generic varargs parameter (page 149)
public class FlattenWithList {

	static <T> List<T> flatten(List<List<? extends T>> lists) {
		List<T> result = new ArrayList<>();
		for (List<? extends T> list : lists)
			result.addAll(list);
		return result;
	}

	public static void main(String[] args) {
		List<Integer> flatList = flatten(List.of(List.of(1, 2), List.of(3, 4, 5), List.of(6, 7)));
		System.out.println(flatList);
	}
}
