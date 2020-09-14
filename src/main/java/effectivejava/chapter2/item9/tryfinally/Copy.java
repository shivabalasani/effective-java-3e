package effectivejava.chapter2.item9.tryfinally;

import java.io.*;

/**
 * 
 * - Item 9: Prefer try-with-resources to try-finally.
 * 
 * The Java libraries include many resources that must be closed manually by
 * invoking a close method. Examples include InputStream, OutputStream, and
 * java.sql.Connection. Closing resources is often overlooked by clients, with
 * predictably dire performance consequences. While many of these resources use
 * finalizers as a safety net, finalizers don’t work very well (Item 8).
 * 
 * All of these problems were solved in one fell swoop when Java 7 introduced
 * the try-with-resources statement [JLS, 14.20.3]. To be usable with this
 * construct, a resource must implement the AutoCloseable interface, which
 * consists of a single void-returning close method.
 *
 */
public class Copy {
	private static final int BUFFER_SIZE = 8 * 1024;

	// try-finally is ugly when used with more than one resource! (Page 34)
	static void copy(String src, String dst) throws IOException {
		InputStream in = new FileInputStream(src);
		try {
			OutputStream out = new FileOutputStream(dst);
			try {
				byte[] buf = new byte[BUFFER_SIZE];
				int n;
				while ((n = in.read(buf)) >= 0)
					out.write(buf, 0, n);
			} finally {
				out.close();
			}
		} finally {
			in.close();
		}
	}

	// try-with-resources on multiple resources - short and sweet
	static void copyBestWay(String src, String dst) throws IOException {
		try (InputStream in = new FileInputStream(src); OutputStream out = new FileOutputStream(dst)) {
			byte[] buf = new byte[BUFFER_SIZE];
			int n;
			while ((n = in.read(buf)) >= 0)
				out.write(buf, 0, n);
		}
	}

	public static void main(String[] args) throws IOException {
		String src = args[0];
		String dst = args[1];
		copy(src, dst);
	}
}
