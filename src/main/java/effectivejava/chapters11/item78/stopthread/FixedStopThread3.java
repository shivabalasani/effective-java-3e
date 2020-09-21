package effectivejava.chapters11.item78.stopthread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * The intent of the generateSerialNumber method is to guarantee that every
 * invocation returns a unique value (so long as there are no more than 2^32 =
 * 4294967296 invocations). The method’s state consists of a single atomically
 * accessible field, nextSerialNumber, and all possible values of this field are
 * legal. Therefore, no synchronization is necessary to protect its invariants.
 * Still, the method won’t work properly without synchronization.
 * 
 * The problem is that the increment operator (++) is not atomic. It performs
 * two operations on the nextSerialNumber field: first it reads the value, and
 * then it writes back a new value, equal to the old value plus one. If a second
 * thread reads the field between the time a thread reads the old value and
 * writes back a new one, the second thread will see the same value as the first
 * and return the same serial number. This is a safety failure: the program
 * computes the wrong results.
 * 
 * One way to fix generateSerialNumber is to add the synchronized modifier to
 * its declaration. This ensures that multiple invocations won’t be interleaved
 * and that each invocation of the method will see the effects of all previous
 * invocations. Once you’ve done that, you can and should remove the volatile
 * modifier from nextSerialNumber. To bulletproof the method, use long instead
 * of int, or throw an exception if nextSerialNumber is about to wrap.
 * 
 * Better still, follow the advice in Item 59 and use the class AtomicLong,
 * which is part of java.util.concurrent.atomic. This package provides
 * primitives for lock-free, thread-safe programming on single variables. While
 * volatile provides only the communication effects of synchronization, this
 * package also provides atomicity. This is exactly what we want for
 * generateSerialNumber, and it is likely to outperform the synchronized
 * version:
 *
 */
public class FixedStopThread3 {

    // Broken - requires synchronization!
    private static volatile int nextSerialNumber = 0;

    // Lock-free synchronization with java.util.concurrent.atomic
    private static final AtomicLong nextSerialNumBetter = new AtomicLong(0);

    public static int generateSerialNumber() {
	return nextSerialNumber++;
    }

    public static long generateSerialNumberBetter() {
	return nextSerialNumBetter.getAndIncrement();
    }

    public static void main(String[] args) throws InterruptedException {
	Thread backgroundThread = new Thread(() -> {
	    long i = 0;
	    while (i < 10000000) { // Adding one more zero will break which is more than 2^32
		generateSerialNumber();
		generateSerialNumberBetter();
		i++;
	    }
	});
	backgroundThread.start();

	TimeUnit.SECONDS.sleep(1);

	System.out.println(nextSerialNumber);
	System.out.println(nextSerialNumBetter);
    }

}
