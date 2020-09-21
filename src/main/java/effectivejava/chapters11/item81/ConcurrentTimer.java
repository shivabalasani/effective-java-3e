package effectivejava.chapters11.item81;

import java.util.concurrent.*;

/**
 * 
 * Synchronizers are objects that enable threads to wait for one another,
 * allowing them to coordinate their activities. The most commonly used
 * synchronizers are CountDownLatch and Semaphore. Less commonly used are
 * CyclicBarrier and Exchanger. The most powerful synchronizer is Phaser.
 * 
 * Countdown latches are single-use barriers that allow one or more threads to
 * wait for one or more other threads to do something. The sole constructor for
 * CountDownLatch takes an int that is the number of times the countDown method
 * must be invoked on the latch before all waiting threads are allowed to
 * proceed.
 * 
 * It is surprisingly easy to build useful things atop this simple primitive.
 * For example, suppose you want to build a simple framework for timing the
 * concurrent execution of an action. This framework consists of a single method
 * that takes an executor to execute the action, a concurrency level
 * representing the number of actions to be executed concurrently, and a
 * runnable representing the action. All of the worker threads ready themselves
 * to run the action before the timer thread starts the clock. When the last
 * worker thread is ready to run the action, the timer thread “fires the
 * starting gun,” allowing the worker threads to perform the action. As soon as
 * the last worker thread finishes performing the action, the timer thread stops
 * the clock. Implementing this logic directly on top of wait and notify would
 * be messy to say the least, but it is surprisingly straightforward on top of
 * CountDownLatch:
 * 
 * Note that the method uses three countdown latches. The first, ready, is used
 * by worker threads to tell the timer thread when they’re ready. The worker
 * threads then wait on the second latch, which is start. When the last worker
 * thread invokes ready.countDown, the timer thread records the start time and
 * invokes start.countDown, allowing all of the worker threads to proceed. Then
 * the timer thread waits on the third latch, done, until the last of the worker
 * threads finishes running the action and calls done.countDown. As soon as this
 * happens, the timer thread awakens and records the end time.
 * 
 * A few more details bear noting. The executor passed to the time method must
 * allow for the creation of at least as many threads as the given concurrency
 * level, or the test will never complete. This is known as a thread starvation
 * deadlock [Goetz06, 8.1.1]. If a worker thread catches an
 * InterruptedException, it reasserts the interrupt using the idiom
 * Thread.currentThread().interrupt() and returns from its run method. This
 * allows the executor to deal with the interrupt as it sees fit. Note that
 * System.nanoTime is used to time the activity. For interval timing, always use
 * System.nanoTime rather than System.currentTimeMillis. System.nanoTime is both
 * more accurate and more precise and is unaffected by adjustments to the
 * system’s real-time clock. Finally, note that the code in this example won’t
 * yield accurate timings unless action does a fair amount of work, say a second
 * or more. Accurate microbenchmarking is notoriously hard and is best done with
 * the aid of a specialized framework such as jmh [JMH].
 * 
 * While you should always use the concurrency utilities in preference to wait
 * and notify, you might have to maintain legacy code that uses wait and notify.
 * The wait method is used to make a thread wait for some condition. It must be
 * invoked inside a synchronized region that locks the object on which it is
 * invoked. Here is the standard idiom for using the wait method:
 * 
 * // The standard idiom for using the wait method
 * 
 * synchronized (obj) { while (<condition does not hold>) obj.wait(); //
 * (Releases lock, and reacquires on wakeup) ... // Perform action appropriate
 * to condition }
 * 
 * Always use the wait loop idiom to invoke the wait method; never invoke it
 * outside of a loop. The loop serves to test the condition before and after
 * waiting.
 * 
 * Just as placing the wait invocation in a loop protects against accidental or
 * malicious notifications on a publicly accessible object, using notifyAll in
 * place of notify protects against accidental or malicious waits by an
 * unrelated thread. Such waits could otherwise “swallow” a critical
 * notification, leaving its intended recipient waiting indefinitely.
 *
 */

// Simple framework for timing concurrent execution 327
public class ConcurrentTimer {
	private ConcurrentTimer() {
	} // Noninstantiable

	public static long time(Executor executor, int concurrency, Runnable action) throws InterruptedException {
		CountDownLatch ready = new CountDownLatch(concurrency);
		CountDownLatch start = new CountDownLatch(1);
		CountDownLatch done = new CountDownLatch(concurrency);

		for (int i = 0; i < concurrency; i++) {
			executor.execute(() -> {
				ready.countDown(); // Tell timer we're ready
				try {
					start.await(); // Wait till peers are ready
					action.run();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} finally {
					done.countDown(); // Tell timer we're done
				}
			});
		}

		ready.await(); // Wait for all workers to be ready
		long startNanos = System.nanoTime();
		start.countDown(); // And they're off!
		done.await(); // Wait for all workers to finish
		return System.nanoTime() - startNanos;
	}
}
