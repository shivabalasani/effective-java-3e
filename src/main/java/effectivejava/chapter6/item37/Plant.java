package effectivejava.chapter6.item37;

import java.util.*;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

/**
 * 
 * Item 37: Use EnumMap instead of ordinal indexing.
 * 
 * Occasionally you may see code that uses the ordinal method (Item 35) to index
 * into an array or list.This technique works, but it is fraught with problems.
 * Because arrays are not compatible with generics (Item 28), the program
 * requires an unchecked cast and will not compile cleanly. Because the array
 * does not know what its index represents, you have to label the output
 * manually. But the most serious problem with this technique is that when you
 * access an array that is indexed by an enum’s ordinal, it is your
 * responsibility to use the correct int value; ints do not provide the type
 * safety of enums. If you use the wrong value, the program will silently do the
 * wrong thing or—if you’re lucky—throw an ArrayIndexOutOfBoundsException.
 * 
 * There is a much better way to achieve the same effect. The array is
 * effectively serving as a map from the enum to a value, so you might as well
 * use a Map. More specifically, there is a very fast Map implementation
 * designed for use with enum keys, known as java.util.EnumMap.
 * 
 * The reason that EnumMap is comparable in speed to an ordinal-indexed array is
 * that EnumMap uses such an array internally, but it hides this implementation
 * detail from the programmer, combining the richness and type safety of a Map
 * with the speed of an array. Note that the EnumMap constructor takes the Class
 * object of the key type: this is a bounded type token, which provides runtime
 * generic type information (Item 33).
 * 
 * In summary, it is rarely appropriate to use ordinals to index into arrays:
 * use EnumMap instead. If the relationship you are representing is
 * multidimensional, use EnumMap<..., EnumMap<...>>. This is a special case of
 * the general principle that application programmers should rarely, if ever,
 * use Enum.ordinal (Item 35
 *
 */
// Using an EnumMap to associate data with an enum (Pages 171-3)

// Simplistic class representing a plant (Page 171)
class Plant {
    enum LifeCycle {
	ANNUAL, PERENNIAL, BIENNIAL
    }

    final String name;
    final LifeCycle lifeCycle;

    Plant(String name, LifeCycle lifeCycle) {
	this.name = name;
	this.lifeCycle = lifeCycle;
    }

    @Override
    public String toString() {
	return name;
    }

    public static void main(String[] args) {
	Plant[] garden = { new Plant("Basil", LifeCycle.ANNUAL), new Plant("Carroway", LifeCycle.BIENNIAL),
		new Plant("Dill", LifeCycle.ANNUAL), new Plant("Lavendar", LifeCycle.PERENNIAL),
		new Plant("Parsley", LifeCycle.BIENNIAL), new Plant("Rosemary", LifeCycle.PERENNIAL) };

	// Using ordinal() to index into an array - DON'T DO THIS! (Page 171)
	Set<Plant>[] plantsByLifeCycleArr = (Set<Plant>[]) new Set[Plant.LifeCycle.values().length];
	for (int i = 0; i < plantsByLifeCycleArr.length; i++)
	    plantsByLifeCycleArr[i] = new HashSet<>();
	for (Plant p : garden)
	    plantsByLifeCycleArr[p.lifeCycle.ordinal()].add(p);
	// Print the results
	for (int i = 0; i < plantsByLifeCycleArr.length; i++) {
	    System.out.printf("%s: %s%n", Plant.LifeCycle.values()[i], plantsByLifeCycleArr[i]);
	}

	// ----------------------------------------------------------------------------------------
	// Solution 1 : Using an EnumMap to associate data with an enum (Page 172)
	Map<Plant.LifeCycle, Set<Plant>> plantsByLifeCycle = new EnumMap<>(Plant.LifeCycle.class);
	for (Plant.LifeCycle lc : Plant.LifeCycle.values())
	    plantsByLifeCycle.put(lc, new HashSet<>());
	for (Plant p : garden)
	    plantsByLifeCycle.get(p.lifeCycle).add(p);
	System.out.println(plantsByLifeCycle);

	// ----------------------------------------------------------------------------------------
	// Solution 2 : Naive stream-based approach - unlikely to produce an EnumMap!
	// (Page 172)

	// The problem with below code is that it chooses its own map implementation,
	// and in practice it won’t be an EnumMap, so it won’t match the space and time
	// performance of the version with the explicit EnumMap.
	System.out.println(Arrays.stream(garden).collect(groupingBy(p -> p.lifeCycle)));

	// ----------------------------------------------------------------------------------------
	// Solution 3: Using a stream and an EnumMap to associate data with an enum
	// (Page 173)

	// To rectify above problem, use the three parameter form of
	// Collectors.groupingBy, which allows the caller to specify
	// the map implementation using the mapFactory parameter
	System.out.println(Arrays.stream(garden)
		.collect(groupingBy(p -> p.lifeCycle, () -> new EnumMap<>(LifeCycle.class), toSet())));
    }
}
