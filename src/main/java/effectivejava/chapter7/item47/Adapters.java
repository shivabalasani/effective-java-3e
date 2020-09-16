package effectivejava.chapter7.item47;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 
 * Item 47: Prefer Collection to Stream as a return type.
 * 
 * Many methods return sequences of elements. Prior to Java 8, the obvious
 * return types for such methods were the collection interfaces Collection, Set,
 * and List; Iterable; and the array types. Usually, it was easy to decide which
 * of these types to return. The norm was a collection interface. If the method
 * existed solely to enable for-each loops or the returned sequence couldn’t be
 * made to implement some Collection method (typically, contains(Object)), the
 * Iterable interface was used. If the returned elements were primitive values
 * or there were stringent performance requirements, arrays were used. In Java
 * 8, streams were added to the platform, substantially complicating the task of
 * choosing the appropriate return type for a sequence-returning method.
 * 
 * You may hear it said that streams are now the obvious choice to return a
 * sequence of elements, but as discussed in Item 45, streams do not make
 * iteration obsolete: writing good code requires combining streams and
 * iteration judiciously.Stream interface contains the sole abstract method in
 * the Iterable interface, and Stream’s specification for this method is
 * compatible with Iterable’s. The only thing preventing programmers from using
 * a for-each loop to iterate over a stream is Stream’s failure to extend
 * Iterable.
 * 
 * If you’re writing a method that returns a sequence of objects and you know
 * that it will only be used in a stream pipeline, then of course you should
 * feel free to return a stream. Similarly, a method returning a sequence that
 * will only be used for iteration should return an Iterable. But if you’re
 * writing a public API that returns a sequence, you should provide for users
 * who want to write stream pipelines as well as those who want to write
 * for-each statements, unless you have a good reason to believe that most of
 * your users will want to use the same mechanism.
 * 
 * In summary, when writing a method that returns a sequence of elements,
 * remember that some of your users may want to process them as a stream while
 * others may want to iterate over them. Try to accommodate both groups. If it’s
 * feasible to return a collection, do so. If you already have the elements in a
 * collection or the number of elements in the sequence is small enough to
 * justify creating a new one, return a standard collection such as ArrayList.
 * Otherwise, consider implementing a custom collection as we did for the power
 * set. If it isn’t feasible to return a collection, return a stream or
 * iterable, whichever seems more natural. If, in a future Java release, the
 * Stream interface declaration is modified to extend Iterable, then you should
 * feel free to return streams because they will allow for both stream
 * processing and iteration.
 *
 */

// Adapters from stream to iterable and vice-versa (Page 216)
public class Adapters {

	// Won't compile, due to limitations on Java's type inference
	// for (ProcessHandle ph : ProcessHandle.allProcesses()::iterator) {
	// Process the process }

	// Hideous workaround to iterate over a stream
	// In order to make the code compile, you have to cast the method reference to
	// an appropriately parameterized Iterable:
	// for (ProcessHandle ph : (Iterable<ProcessHandle>)
	// ProcessHandle.allProcesses()::iterator)

	// Above client code works, but it is too noisy and opaque to use in practice. A
	// better workaround is to use an adapter method.
	// Adapter from Stream<E> to Iterable<E> (
	// Note that no cast is necessary in the adapter method because
	// Java’s type inference works properly in this context:
	public static <E> Iterable<E> iterableOf(Stream<E> stream) {
		return stream::iterator;
	}

	// With this adapter, you can iterate over any stream with a for-each statement:
	// for (ProcessHandle p : iterableOf(ProcessHandle.allProcesses())) {
	// Process the process
	// }

	// Adapter from Iterable<E> to Stream<E>
	public static <E> Stream<E> streamOf(Iterable<E> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false);
	}
}
