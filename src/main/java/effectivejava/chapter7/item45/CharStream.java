package effectivejava.chapter7.item45;

// Refrain from using streams to process char values (Page 206)
public class CharStream {
	public static void main(String[] args) {
		// Does not produce the expected result
		// The elements of the stream returned by "Hello world!".chars() are not char
		// values but int values
		"Hello world!".chars().forEach(System.out::print); // output : 721011081081113211911111410810033
		System.out.println();

		// Fixes the problem
		"Hello world!".chars().forEach(x -> System.out.print((char) x));
		System.out.println();
	}
}
