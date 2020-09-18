package effectivejava.chapter9.item59;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 
 * Given all the advantages of libraries, it seems only logical to use library
 * facilities in preference to ad hoc implementations, yet many programmers
 * don’t. Why not? Perhaps they don’t know the library facilities exist.
 * Numerous features are added to the libraries in every major release, and it
 * pays to keep abreast of these additions. Each time there is a major release
 * of the Java platform, a web page is published describing its new features.
 * These pages are well worth reading [Java8-feat, Java9-feat]. To reinforce
 * this point, suppose you wanted to write a program to print the contents of a
 * URL specified on the command line (which is roughly what the Linux curl
 * command does). Prior to Java 9, this code was a bit tedious, but in Java 9
 * the transferTo method was added to InputStream. Here is a complete program to
 * perform this task using this new method:
 * 
 * The libraries are too big to study all the documentation [Java9-api], but
 * every programmer should be familiar with the basics of java.lang, java.util,
 * and java.io, and their subpackages. Knowledge of other libraries can be
 * acquired on an as-needed basis.
 * 
 * Several libraries bear special mention. The collections framework and the
 * streams library (Items 45–48) should be part of every programmer’s basic
 * toolkit, as should parts of the concurrency utilities in
 * java.util.concurrent.
 * 
 * If you can’t find what you need in Java platform libraries, your next choice
 * should be to look in high-quality third-party libraries, such as Google’s
 * excellent, open source Guava library [Guava]. If you can’t find the
 * functionality that you need in any appropriate library, you may have no
 * choice but to implement it yourself.
 *
 */

// Printing the contents of a URL with transferTo, added in Java 9 (Page 269)
public class Curl {
	public static void main(String[] args) throws IOException {
		try (InputStream in = new URL("http://www.google.com").openStream()) {
			in.transferTo(System.out);
		}
	}
}
