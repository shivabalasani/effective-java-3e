package effectivejava.chapter2.item9.tryfinally;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TopLine {
	// try-finally - No longer the best way to close resources! (page 34)
	static String firstLineOfFile(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		try {
			return br.readLine();
		} finally {
			br.close();
		}
	}

	// try-with-resources - the the best way to close resources!

	// If exceptions are thrown by both the readLine call and the (invisible) close,
	// the latter exception is suppressed in favor of the former. In fact, multiple
	// exceptions may be suppressed in order to preserve the exception that you
	// actually want to see. These suppressed exceptions are not merely discarded; they are
	// printed in the stack trace with a notation saying that they were suppressed.
	// You can also access them programmatically with the getSuppressed method, which
	// was added to Throwable in Java 7.
	static String firstLineOfFileBestWay(String path) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			return br.readLine();
		}
	}
	
	// try-with-resources with a catch clause
	static String firstLineOfFileWithCatch(String path, String defaultVal) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			return br.readLine();
		} catch (IOException e) {
			return defaultVal;
		}
	}

	public static void main(String[] args) throws IOException {
		String path = args[0];
		System.out.println(firstLineOfFile(path));
	}
}
