package effectivejava.chapter5.item33;

import java.util.*;

/**
 * 
 * Item 33: Consider typesafe heterogeneous containers
 * 
 * Common uses of generics include collections, such as Set<E> and Map<K,V>, and
 * single-element containers, such as ThreadLocal<T> and AtomicReference<T>. In
 * all of these uses, it is the container that is parameterized. This limits you
 * to a fixed number of type parameters per container. Normally that is exactly
 * what you want. A Set has a single type parameter, representing its element
 * type; a Map has two, representing its key and value types; and so forth.
 * 
 * Sometimes, however, you need more flexibility. For example, a database row
 * can have arbitrarily many columns,and it would be nice to be able to access
 * all of them in a typesafe manner.Luckily, there is an easy way to achieve
 * this effect. The idea is to parameterize the key instead of the container.
 * Then present the parameterized key to the container to insert or retrieve a
 * value.
 * 
 * A Favorites instance is typesafe: it will never return an Integer when you
 * ask it for a String. It is also heterogeneous: unlike an ordinary map, all
 * the keys are of different types. Therefore, we call Favorites a typesafe
 * heterogeneous container.
 * 
 * In summary, the normal use of generics, exemplified by the collections APIs,
 * restricts you to a fixed number of type parameters per container. You can get
 * around this restriction by placing the type parameter on the key rather than
 * the container. You can use Class objects as keys for such typesafe
 * heterogeneous containers. A Class object used in this fashion is called a
 * type token. You can also use a custom key type. For example, you could have a
 * DatabaseRow type representing a database row (the container), and a generic
 * type Column<T> as its key.
 *
 */
// Typesafe heterogeneous container pattern (Pages 151-4)
public class Favorites {
	private Map<Class<?>, Object> favorites = new HashMap<>();

	public <T> void putFavoriteWithoutCast(Class<T> type, T instance) {
		favorites.put(Objects.requireNonNull(type), instance);
	}

	// getFavorite implementation dynamically casts the object reference
	// to the type represented by the Class object, using Class’s cast method.
	public <T> T getFavorite(Class<T> type) {
		return type.cast(favorites.get(type));
	}

//    // Achieving runtime type safety with a dynamic cast
	public <T> void putFavorite(Class<T> type, T instance) {
		favorites.put(Objects.requireNonNull(type), type.cast(instance));
	}

	public static void main(String[] args) {
		Favorites f = new Favorites();

		f.putFavorite(String.class, "Java");
		f.putFavorite(Integer.class, 0xcafebabe);
		f.putFavorite(Class.class, Favorites.class);

		String favoriteString = f.getFavorite(String.class);
		int favoriteInteger = f.getFavorite(Integer.class);
		Class<?> favoriteClass = f.getFavorite(Class.class);

		System.out.printf("%s %x %s%n", favoriteString, favoriteInteger, favoriteClass.getName());
	}
}