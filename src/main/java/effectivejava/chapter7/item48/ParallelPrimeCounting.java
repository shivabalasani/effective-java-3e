package effectivejava.chapter7.item48;

import java.math.BigInteger;
import java.util.stream.LongStream;

/**
 * 
 * Not only can parallelizing a stream lead to poor performance, including
 * liveness failures; it can lead to incorrect results and unpredictable
 * behavior (safety failures). Safety failures may result from parallelizing a
 * pipeline that uses mappers, filters, and other programmer-supplied function
 * objects that fail to adhere to their specifications. The Stream specification
 * places stringent requirements on these function objects. For example, the
 * accumulator and combiner functions passed to Stream’s reduce operation must
 * be associative, non-interfering, and stateless. If you violate these
 * requirements (some of which are discussed in Item 46) but run your pipeline
 * sequentially, it will likely yield correct results; if you parallelize it, it
 * will likely fail, perhaps catastrophically.
 * 
 * Under the right circumstances, it is possible to achieve near-linear speedup
 * in the number of processor cores simply by adding a parallel call to a stream
 * pipeline.
 * 
 * On my machine, it takes 31 seconds to compute pi(10^8) using this function.
 * Simply adding a parallel() call reduces the time to 9.2 seconds:
 * 
 * If you are going to parallelize a stream of random numbers, start with a
 * SplittableRandom instance rather than a ThreadLocalRandom (or the essentially
 * obsolete Random). SplittableRandom is designed for precisely this use, and
 * has the potential for linear speedup. ThreadLocalRandom is designed for use
 * by a single thread, and will adapt itself to function as a parallel stream
 * source, but won’t be as fast as SplittableRandom. Random synchronizes on
 * every operation, so it will result in excessive, parallelism-killing
 * contention.
 *
 */
public class ParallelPrimeCounting {
	// Prime-counting stream pipeline - parallel version (Page 225)
	static long pi(long n) {
		return LongStream.rangeClosed(2, n).parallel().mapToObj(BigInteger::valueOf).filter(i -> i.isProbablePrime(50))
				.count();
	}

	static long piNormal(long n) {
		return LongStream.rangeClosed(2, n).mapToObj(BigInteger::valueOf).filter(i -> i.isProbablePrime(50)).count();
	}

	public static void main(String[] args) {
		System.out.println(pi(10_000_000));
	}
}
