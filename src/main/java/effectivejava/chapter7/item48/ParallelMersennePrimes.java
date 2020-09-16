package effectivejava.chapter7.item48;

import java.math.BigInteger;
import java.util.stream.Stream;

import static java.math.BigInteger.*;

/**
 * 
 * Item 48: Use caution when making streams parallel
 * 
 * Among mainstream languages, Java has always been at the forefront of
 * providing facilities to ease the task of concurrent programming. When Java
 * was released in 1996, it had built-in support for threads, with
 * synchronization and wait/notify. Java 5 introduced the java.util.concurrent
 * library, with concurrent collections and the executor framework. Java 7
 * introduced the fork-join package, a highperformance framework for parallel
 * decomposition. Java 8 introduced streams, which can be parallelized with a
 * single call to the parallel method. Writing concurrent programs in Java keeps
 * getting easier, but writing concurrent programs that are correct and fast is
 * as difficult as it ever was. Safety and liveness violations are a fact of
 * life in concurrent programming, and parallel stream pipelines are no
 * exception.
 * 
 * What’s going on here? Simply put, the streams library has no idea how to
 * parallelize this pipeline and the heuristics fail. Even under the best of
 * circumstances, parallelizing a pipeline is unlikely to increase its
 * performance if the source is from Stream.iterate, or the intermediate
 * operation limit is used. This pipeline has to contend with both of these
 * issues. Worse, the default parallelization strategy deals with the
 * unpredictability of limit by assuming there’s no harm in processing a few
 * extra elements and discarding any unneeded results.Do not parallelize stream
 * pipelines indiscriminately. The performance consequences may be disastrous.
 * 
 * As a rule, performance gains from parallelism are best on streams over
 * ArrayList, HashMap, HashSet, and ConcurrentHashMap instances; arrays; int
 * ranges; and long ranges. What these data structures have in common is that
 * they can all be accurately and cheaply split into subranges of any desired
 * sizes, which makes it easy to divide work among parallel threads.
 * 
 * Another important factor that all of these data structures have in common is
 * that they provide good-to-excellent locality of reference when processed
 * sequentially: sequential element references are stored together in memory.The
 * data structures with the best locality of reference are primitive arrays
 * because the data itself is stored contiguously in memory.
 * 
 * The operations performed by Stream’s collect method, which are known as
 * mutable reductions, are not good candidates for parallelism because the
 * overhead of combining collections is costly.
 * 
 * In summary, do not even attempt to parallelize a stream pipeline unless you
 * have good reason to believe that it will preserve the correctness of the
 * computation and increase its speed. The cost of inappropriately parallelizing
 * a stream can be a program failure or performance disaster. If you believe
 * that parallelism may be justified, ensure that your code remains correct when
 * run in parallel, and do careful performance measurements under realistic
 * conditions. If your code remains correct and these experiments bear out your
 * suspicion of increased performance, then and only then parallelize the stream
 * in production code.
 *
 */

// Parallel stream-based program to generate the first 20 Mersenne primes - HANGS!!! (Page 222)
public class ParallelMersennePrimes {
	public static void main(String[] args) {
		primes().map(p -> TWO.pow(p.intValueExact()).subtract(ONE)).parallel()
				.filter(mersenne -> mersenne.isProbablePrime(50)).limit(20).forEach(System.out::println);
	}

	static Stream<BigInteger> primes() {
		return Stream.iterate(TWO, BigInteger::nextProbablePrime);
	}
}
