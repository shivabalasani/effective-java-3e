package effectivejava.chapters12.item85;

import static effectivejava.chapters12.Util.*;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Item 85: Prefer alternatives to Java serialization.
 * 
 * Without using any gadgets, you can easily mount a denial-of-service attack by
 * causing the deserialization of a short stream that requires a long time to
 * deserialize. Such streams are known as deserialization bombs [Svoboda16].
 * Here’s an example by Wouter Coekaerts that uses only hash sets and a string
 * [Coekaerts15]:
 * 
 * The object graph consists of 201 HashSet instances, each of which contains 3
 * or fewer object references. The entire stream is 5,744 bytes long, yet the
 * sun would burn out long before you could deserialize it. The problem is that
 * deserializing a HashSet instance requires computing the hash codes of its
 * elements. The 2 elements of the root hash set are themselves hash sets
 * containing 2 hash-set elements, each of which contains 2 hash-set elements,
 * and so on, 100 levels deep. Therefore, deserializing the set causes the
 * hashCode method to be invoked over 2100 times. Other than the fact that the
 * deserialization is taking forever, the deserializer has no indication that
 * anything is amiss. Few objects are produced, and the stack depth is bounded.
 * 
 * So what can you do defend against these problems? You open yourself up to
 * attack whenever you deserialize a byte stream that you don’t trust. The best
 * way to avoid serialization exploits is never to deserialize anything. In the
 * words of the computer named Joshua in the 1983 movie WarGames, “the only
 * winning move is not to play.” There is no reason to use Java serialization in
 * any new system you write. There are other mechanisms for translating between
 * objects and byte sequences that avoid many of the dangers of Java
 * serialization, while offering numerous advantages, such as cross-platform
 * support, high performance, a large ecosystem of tools, and a broad community
 * of expertise.In this book, we refer to these mechanisms as cross-platform
 * structured-data representations. While others sometimes refer to them as
 * serialization systems, this book avoids that usage to prevent confusion with
 * Java serialization.
 * 
 * The leading cross-platform structured data representations are JSON [JSON]
 * and Protocol Buffers, also known as protobuf [Protobuf]. JSON was designed by
 * Douglas Crockford for browser-server communication, and protocol buffers were
 * designed by Google for storing and interchanging structured data among its
 * servers. Even though these representations are sometimes called
 * language-neutral, JSON was originally developed for JavaScript and protobuf
 * for C++; both representations retain vestiges of their origins.
 * 
 * If you can’t avoid Java serialization entirely, perhaps because you’re
 * working in the context of a legacy system that requires it, your next best
 * alternative is to never deserialize untrusted data.
 * 
 * If you can’t avoid serialization and you aren’t absolutely certain of the
 * safety of the data you’re deserializing, use the object deserialization
 * filtering added in Java 9 and backported to earlier releases
 * (java.io.ObjectInputFilter). This facility lets you specify a filter that is
 * applied to data streams before they’re deserialized. It operates at the class
 * granularity, letting you accept or reject certain classes. Accepting classes
 * by default and rejecting a list of potentially dangerous ones is known as
 * blacklisting; rejecting classes by default and accepting a list of those that
 * are presumed safe is known as whitelisting. Prefer whitelisting to
 * blacklisting, as blacklisting only protects you against known threats. A tool
 * called Serial Whitelist Application Trainer (SWAT) can be used to
 * automatically prepare a whitelist for your application [Schneider16]. The
 * filtering facility will also protect you against excessive memory usage, and
 * excessively deep object graphs, but it will not protect you against
 * serialization bombs like the one shown above.
 * 
 * Unfortunately, serialization is still pervasive in the Java ecosystem. If you
 * are maintaining a system that is based on Java serialization, seriously
 * consider migrating to a cross-platform structured-data representation, even
 * though this may be a time-consuming endeavor. Realistically, you may still
 * find yourself having to write or maintain a serializable class. It requires
 * great care to write a serializable class that is correct, safe, and
 * efficient. The remainder of this chapter provides advice on when and how to
 * do this.
 * 
 * In summary, serialization is dangerous and should be avoided. If you are
 * designing a system from scratch, use a cross-platform structured-data
 * representation such as JSON or protobuf instead. Do not deserialize untrusted
 * data. If you must do so, use object deserialization filtering, but be aware
 * that it is not guaranteed to thwart all attacks. Avoid writing serializable
 * classes. If you must do so, exercise great caution.
 * 
 *
 */
// Deserialization bomb - deserializing this stream takes forever - Page 340
public class DeserializationBomb {
    public static void main(String[] args) throws Exception {
	System.out.println(bomb().length);
	deserialize(bomb());
    }

    static byte[] bomb() {
	Set<Object> root = new HashSet<>();
	Set<Object> s1 = root;
	Set<Object> s2 = new HashSet<>();
	for (int i = 0; i < 100; i++) {
	    Set<Object> t1 = new HashSet<>();
	    Set<Object> t2 = new HashSet<>();
	    t1.add("foo"); // make it not equal to t2
	    s1.add(t1);
	    s1.add(t2);
	    s2.add(t1);
	    s2.add(t2);
	    s1 = t1;
	    s2 = t2;
	}
	return serialize(root);
    }
}
