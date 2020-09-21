package effectivejava.chapters11.item78.stopthread;

import java.util.concurrent.*;

/**
 * 
 * Item 78: Synchronize access to shared mutable data.
 * 
 * The synchronized keyword ensures that only a single thread can execute a
 * method or block at one time. Many programmers think of synchronization solely
 * as a means of mutual exclusion, to prevent an object from being seen in an
 * inconsistent state by one thread while it’s being modified by another.
 * 
 * This view is correct, but it’s only half the story. Without synchronization,
 * one thread’s changes might not be visible to other threads. Not only does
 * synchronization prevent threads from observing an object in an inconsistent
 * state, but it ensures that each thread entering a synchronized method or
 * block sees the effects of all previous modifications that were guarded by the
 * same lock.
 * 
 * The language specification guarantees that reading or writing a variable is
 * atomic unless the variable is of type long or double [JLS, 17.4, 17.7]. In
 * other words, reading a variable other than a long or double is guaranteed to
 * return a value that was stored into that variable by some thread, even if
 * multiple threads modify the variable concurrently and without
 * synchronization.
 * 
 * Synchronization is required for reliable communication between threads as
 * well as for mutual exclusion. This is due to a part of the language
 * specification known as the memory model, which specifies when and how changes
 * made by one thread become visible to others [JLS, 17.4; Goetz06, 16].
 * 
 * The consequences of failing to synchronize access to shared mutable data can
 * be dire even if the data is atomically readable and writable. Consider the
 * task of stopping one thread from another. The libraries provide the
 * Thread.stop method, but this method was deprecated long ago because it is
 * inherently unsafe—its use can result in data corruption. Do not use
 * Thread.stop. A recommended way to stop one thread from another is to have the
 * first thread poll a boolean field that is initially false but can be set to
 * true by the second thread to indicate that the first thread is to stop
 * itself. Because reading and writing a boolean field is atomic, some
 * programmers dispense with synchronization when accessing the field:
 * 
 * You might expect this program to run for about a second, after which the main
 * thread sets stopRequested to true, causing the background thread’s loop to
 * terminate. On my machine, however, the program never terminates: the
 * background thread loops forever!
 * 
 * The problem is that in the absence of synchronization, there is no guarantee
 * as to when, if ever, the background thread will see the change in the value
 * of stopRequested made by the main thread. In the absence of synchronization,
 * it’s quite acceptable for the virtual machine to transform this code:
 * 
 * while (!stopRequested) i++;
 * 
 * into this code:
 * 
 * if (!stopRequested) while (true) i++;
 * 
 * This optimization is known as hoisting, and it is precisely what the OpenJDK
 * Server VM does. The result is a liveness failure: the program fails to make
 * progress. One way to fix the problem is to synchronize access to the
 * stopRequested field. This program terminates in about one second, as
 * expected:
 *
 * The best way to avoid the problems discussed in this item is not to share
 * mutable data. Either share immutable data (Item 17) or don’t share at all. In
 * other words, confine mutable data to a single thread.
 * 
 * It is acceptable for one thread to modify a data object for a while and then
 * to share it with other threads, synchronizing only the act of sharing the
 * object reference. Other threads can then read the object without further
 * synchronization so long as it isn’t modified again. Such objects are said to
 * be effectively immutable [Goetz06, 3.5.4]. Transferring such an object
 * reference from one thread to others is called safe publication [Goetz06,
 * 3.5.3]. There are many ways to safely publish an object reference: you can
 * store it in a static field as part of class initialization; you can store it
 * in a volatile field, a final field, or a field that is accessed with normal
 * locking; or you can put it into a concurrent collection (Item 81).
 * 
 * In summary, when multiple threads share mutable data, each thread that reads
 * or writes the data must perform synchronization. In the absence of
 * synchronization, there is no guarantee that one thread’s changes will be
 * visible to another thread. The penalties for failing to synchronize shared
 * mutable data are liveness and safety failures. These failures are among the
 * most difficult to debug. They can be intermittent and timing-dependent, and
 * program behavior can vary radically from one VM to another. If you need only
 * inter-thread communication, and not mutual exclusion, the volatile modifier
 * is an acceptable form of synchronization, but it can be tricky to use
 * correctly.
 */

// Broken! - How long would you expect this program to run? (Page 312)
public class BrokenStopThread {
    private static boolean stopRequested;

    public static void main(String[] args) throws InterruptedException {
	Thread backgroundThread = new Thread(() -> {
	    int i = 0;
	    while (!stopRequested)
		i++;
	});
	backgroundThread.start();

	TimeUnit.SECONDS.sleep(1);
	stopRequested = true;
    }
}