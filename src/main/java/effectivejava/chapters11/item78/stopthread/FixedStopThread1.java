package effectivejava.chapters11.item78.stopthread;

import java.util.concurrent.*;

/**
 * 
 * Note that both the write method (requestStop) and the read method (stop-
 * Requested) are synchronized. It is not sufficient to synchronize only the
 * write method! Synchronization is not guaranteed to work unless both read and
 * write operations are synchronized. Occasionally a program that synchronizes
 * only writes (or reads) may appear to work on some machines, but in this case,
 * appearances are deceiving.
 * 
 * The actions of the synchronized methods in StopThread would be atomic even
 * without synchronization. In other words, the synchronization on these methods
 * is used solely for its communication effects, not for mutual exclusion. While
 * the cost of synchronizing on each iteration of the loop is small, there is a
 * correct alternative that is less verbose and whose performance is likely to
 * be better. The locking in the second version of StopThread can be omitted if
 * stopRequested is declared volatile. While the volatile modifier performs no
 * mutual exclusion, it guarantees that any thread that reads the field will see
 * the most recently written value:
 *
 */
// Properly synchronized cooperative thread termination
public class FixedStopThread1 {
	private static boolean stopRequested;

	private static synchronized void requestStop() {
		stopRequested = true;
	}

	private static synchronized boolean stopRequested() {
		return stopRequested;
	}

	public static void main(String[] args) throws InterruptedException {
		Thread backgroundThread = new Thread(() -> {
			int i = 0;
			while (!stopRequested())
				i++;
		});
		backgroundThread.start();

		TimeUnit.SECONDS.sleep(1);
		requestStop();
	}
}