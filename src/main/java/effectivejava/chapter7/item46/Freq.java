package effectivejava.chapter7.item46;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * 
 * Item 46: Prefer side-effect-free functions in streams.
 * 
 * If you’re new to streams, it can be difficult to get the hang of them. Merely
 * expressing your computation as a stream pipeline can be hard. When you
 * succeed, your program will run, but you may realize little if any benefit.
 * Streams isn’t just an API, it’s a paradigm based on functional programming.
 * In order to obtain the expressiveness, speed, and in some cases
 * parallelizability that streams have to offer, you have to adopt the paradigm
 * as well as the API.
 * 
 * The most important part of the streams paradigm is to structure your
 * computation as a sequence of transformations where the result of each stage
 * is as close as possible to a pure function of the result of the previous
 * stage. A pure function is one whose result depends only on its input: it does
 * not depend on any mutable state, nor does it update any state. In order to
 * achieve this, any function objects that you pass into stream operations, both
 * intermediate and terminal, should be free of side-effects
 * 
 * The forEach operation should be used only to report the result of a stream
 * computation, not to perform the computation. Occasionally, it makes sense to
 * use forEach for some other purpose, such as adding the results of a stream
 * computation to a pre-existing collection.
 * 
 * The Collectors API is intimidating: it has thirty nine methods, some of which
 * have as many as five type parameters. For starters, you can ignore the
 * Collector interface and think of a collector as an opaque object that
 * encapsulates a reduction strategy.The collectors for gathering the elements
 * of a stream into a true Collection are straightforward. There are three such
 * collectors: toList(), toSet(), and toCollection(collectionFactory). They
 * return, respectively, a set, a list, and a programmer-specified collection
 * type. It is customary and wise to statically import all members of Collectors
 * because it makes stream pipelines more readable.So what about the other
 * thirty-six methods in Collectors? Most of them exist to let you collect
 * streams into maps, which is far more complicated than collecting them into
 * true collections. The simplest map collector is toMap(keyMapper,
 * valueMapper),This simple form of toMap is perfect if each element in the
 * stream maps to a unique key. If multiple stream elements map to the same key,
 * the pipeline will terminate with an IllegalStateException.
 * 
 * The more complicated forms of toMap, as well as the groupingBy method, give
 * you various ways to provide strategies for dealing with such collisions.The
 * three-argument form of toMap is also useful to make a map from a key to a
 * chosen element associated with that key.Another use of the three-argument
 * form of toMap is to produce a collector that imposes a last-write-wins policy
 * when there are collisions.
 * 
 * Collector to impose last-write-wins policy -------------------
 * toMap(keyMapper, valueMapper, (v1, v2) -> v2)
 * 
 * The third and final version of toMap takes a fourth argument, which is a map
 * factory, for use when you want to specify a particular map implementation
 * such as an EnumMap or a TreeMap.
 * 
 * In addition to the toMap method, the Collectors API provides the groupingBy
 * method, which returns collectors to produce maps that group elements into
 * categories based on a classifier function.If you want groupingBy to return a
 * collector that produces a map with values other than lists, you can specify a
 * downstream collector in addition to a classifier.
 * 
 * In summary, the essence of programming stream pipelines is side-effect-free
 * function objects. This applies to all of the many function objects passed to
 * streams and related objects. The terminal operation forEach should only be
 * used to report the result of a computation performed by a stream, not to
 * perform the computation. In order to use streams properly, you have to know
 * about collectors. The most important collector factories are toList, toSet,
 * toMap, groupingBy, and joining.
 *
 */
// Frequency table examples showing improper and proper use of stream (Page 210-11)
public class Freq {

	public enum Suit {
		SPADE, HEART, DIAMOND, CLUB
	}

	public static void main(String[] args) throws FileNotFoundException {
		// File file = new File(args[0]);

		String str = "eat tea tan ate eat bat tan tea eat mat bike pin road car race can ";

		// Uses the streams API but not the paradigm--Don't do this!
		Map<String, Long> freqstream = new HashMap<>();
		try (Stream<String> words = new Scanner(str).tokens()) {
			words.forEach(word -> {
				freqstream.merge(word.toLowerCase(), 1L, Long::sum);
			});
		}
		System.out.println(freqstream);

		// Proper use of streams to initialize a frequency table (
		Map<String, Long> freq;
		try (Stream<String> words = new Scanner(str).tokens()) {
			freq = words.collect(groupingBy(String::toLowerCase, counting()));
		}
		System.out.println(freq);

		// Pipeline to get a top-ten list of words from a frequency table
		// freq::get looks up the word in the frequency table and returns the
		// number of times the word appears in the file.
		List<String> topTen = freq.keySet().stream().sorted(comparing(freq::get).reversed()).limit(10)
				.collect(toList());
		System.out.println(topTen);

		// Using a toMap collector to make a map from string to enum
		Map<String, Suit> stringToEnum = Stream.of(Suit.values()).collect(toMap(Object::toString, e -> e));
		System.out.println(stringToEnum);

		// Suppose we have a stream of record albums by various artists, and we want a
		// map from recording artist to best-selling album.
		// Collector to generate a map from key to chosen element for key
		// Map<Artist, Album> topHits = albums.collect(toMap(Album::artist, a -> a,
		// maxBy(comparing(Album::sales))));
	}
}
