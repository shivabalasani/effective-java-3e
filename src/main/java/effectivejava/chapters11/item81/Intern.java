package effectivejava.chapters11.item81;

import java.util.concurrent.*;

/**
 * 
 * Item 81: Prefer concurrency utilities to wait and notify.
 * 
 * The first edition of this book devoted an item to the correct use of wait and
 * notify [Bloch01, Item 50]. Its advice is still valid and is summarized at end
 * of this item, but this advice is far less important than it once was. This is
 * because there is far less reason to use wait and notify. Since Java 5, the
 * platform has provided higher-level concurrency utilities that do the sorts of
 * things you formerly had to hand-code atop wait and notify. Given the
 * difficulty of using wait and notify correctly, you should use the
 * higher-level concurrency utilities instead.
 * 
 * The higher-level utilities in java.util.concurrent fall into three
 * categories: the Executor Framework, which was covered briefly in Item 80;
 * concurrent collections; and synchronizers. Concurrent collections and
 * synchronizers are covered briefly in this item.
 * 
 * The concurrent collections are high-performance concurrent implementations of
 * standard collection interfaces such as List, Queue, and Map. To provide high
 * concurrency, these implementations manage their own synchronization
 * internally (Item 79). Therefore, it is impossible to exclude concurrent
 * activity from a concurrent collection; locking it will only slow the program.
 * 
 * Map’s putIfAbsent(key, value) method inserts a mapping for a key if none was
 * present and returns the previous value associated with the key, or null if
 * there was none. This makes it easy to implement thread-safe canonicalizing
 * maps. Version 1 method simulates the behavior of String.intern:
 * 
 * In fact, you can do even better. ConcurrentHashMap is optimized for retrieval
 * operations, such as get. Therefore, it is worth invoking get initially and
 * calling putIfAbsent only if get indicates that it is necessary: Version 2
 * 
 * Besides offering excellent concurrency, ConcurrentHashMap is very fast. On my
 * machine, the intern method in Version 2 is over six times faster than
 * String.intern (but keep in mind that String.intern must employ some strategy
 * to keep from leaking memory in a long-lived application). Concurrent
 * collections make synchronized collections largely obsolete. For example, use
 * ConcurrentHashMap in preference to Collections.synchronizedMap. Simply
 * replacing synchronized maps with concurrent maps can dramatically increase
 * the performance of concurrent applications.
 * 
 * Some of the collection interfaces were extended with blocking operations,
 * which wait (or block) until they can be successfully performed.For example,
 * BlockingQueue extends Queue and adds several methods, including take, which
 * removes and returns the head element from the queue, waiting if the queue is
 * empty.This allows blocking queues to be used for work queues (also known as
 * producer-consumer queues), to which one or more producer threads enqueue work
 * items and from which one or more consumer threads dequeue and process items
 * as they become available. As you’d expect, most ExecutorService
 * implementations, including ThreadPoolExecutor, use a BlockingQueue (Item 80).
 * 
 * Synchronizers are objects that enable threads to wait for one another,
 * allowing them to coordinate their activities. The most commonly used
 * synchronizers are CountDownLatch and Semaphore. Less commonly used are
 * CyclicBarrier and Exchanger. The most powerful synchronizer is Phaser.
 * 
 * In summary, using wait and notify directly is like programming in
 * “concurrency assembly language,” as compared to the higher-level language
 * provided by java.util.concurrent. There is seldom, if ever, a reason to use
 * wait and notify in new code. If you maintain code that uses wait and notify,
 * make sure that it always invokes wait from within a while loop using the
 * standard idiom. The notifyAll method should generally be used in preference
 * to notify. If notify is used, great care must be taken to ensure liveness.
 *
 */
// Concurrent canonicalizing map atop ConcurrentMap - Pages 273-274
public class Intern {

	private static final ConcurrentMap<String, String> map = new ConcurrentHashMap<>();

	// Version 1 : Concurrent canonicalizing map atop ConcurrentMap - not optimal
	public static String internSlow(String s) {
		String previousValue = map.putIfAbsent(s, s);
		return previousValue == null ? s : previousValue;
	}

	// Version 2 : Concurrent canonicalizing map atop ConcurrentMap - faster!
	public static String intern(String s) {
		String result = map.get(s);
		if (result == null) {
			result = map.putIfAbsent(s, s);
			if (result == null)
				result = s;
		}
		return result;
	}
}