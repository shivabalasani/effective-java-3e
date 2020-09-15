package effectivejava.chapter7.item45;

import java.math.BigInteger;
import java.util.stream.Stream;

import static java.math.BigInteger.*;

/**
 * 
 * A Mersenne number is a number of the form 2^p - 1. If p is prime, the
 * corresponding Mersenne number may be prime; if so, it’s a Mersenne prime.
 * 
 * If we want to precede each Mersenne prime with its exponent (p). This value
 * is present only in the initial stream, so it is inaccessible in the terminal
 * operation, which prints the results. Luckily, it’s easy to compute the
 * exponent of a Mersenne number by inverting the mapping that took place in the
 * first intermediate operation. The exponent is simply the number of bits in
 * the binary representation, so this terminal operation generates the desired
 * result: 
 *
 */
// Generating the first twenty Mersenne primes using streams (Page 208)
public class MersennePrimes {
	static Stream<BigInteger> primes() {
		return Stream.iterate(TWO, BigInteger::nextProbablePrime);
	}

	public static void main(String[] args) {
		primes().map(p -> TWO.pow(p.intValueExact()).subtract(ONE)).filter(mersenne -> mersenne.isProbablePrime(50))
				.limit(20).forEach(mp -> System.out.println(mp.bitLength() + ": " + mp));
	}
}
