package effectivejava.chapters11.item79;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * Item 79: Avoid excessive synchronization Item 78 warns of the dangers of
 * insufficient synchronization. This item concerns the opposite problem.
 * Depending on the situation, excessive synchronization can cause reduced
 * performance, deadlock, or even nondeterministic behavior.
 * 
 * To avoid liveness and safety failures, never cede control to the client
 * within a synchronized method or block.
 * 
 * To make this concrete, consider the following Version 1, which implements an
 * observable set wrapper. It allows clients to subscribe to notifications when
 * elements are added to the set. This is the Observer pattern [Gamma95]. For
 * brevity’s sake, the class does not provide notifications when elements are
 * removed from the set, but it would be a simple matter to provide them. This
 * class is implemented atop the reusable ForwardingSet from Item 18 (page 90):
 * 
 * Luckily, it is usually not too hard to fix this sort of problems with Version
 * 1 by moving alien method invocations out of synchronized blocks. For the
 * notifyElementAdded method, this involves taking a “snapshot” of the observers
 * list that can then be safely traversed without a lock. With this change, both
 * of the previous examples run without exception or deadlock:
 * 
 * In fact, there’s a better way to move the alien method invocations out of the
 * synchronized block. The libraries provide a concurrent collection (Item 81)
 * known as CopyOnWriteArrayList that is tailor-made for this purpose. This List
 * implementation is a variant of ArrayList in which all modification operations
 * are implemented by making a fresh copy of the entire underlying array.
 * Because the internal array is never modified, iteration requires no locking
 * and is very fast. For most uses, the performance of CopyOnWriteArrayList
 * would be atrocious, but it’s perfect for observer lists, which are rarely
 * modified and often traversed.
 * 
 * As a rule, you should do as little work as possible inside synchronized
 * regions. Obtain the lock, examine the shared data, transform it as necessary,
 * and drop the lock. If you must perform some time-consuming activity, find a
 * way to move it out of the synchronized region without violating the
 * guidelines in Item 78.
 * 
 * While the cost of synchronization has plummeted since the early days of Java,
 * it is more important than ever not to oversynchronize. In a multicore world,
 * the real cost of excessive synchronization is not the CPU time spent getting
 * locks; it is contention: the lost opportunities for parallelism and the
 * delays imposed by the need to ensure that every core has a consistent view of
 * memory. Another hidden cost of oversynchronization is that it can limit the
 * VM’s ability to optimize code execution.
 * 
 * If you are writing a mutable class, you have two options: you can omit all
 * synchronization and allow the client to synchronize externally if concurrent
 * use is desired, or you can synchronize internally, making the class
 * thread-safe (Item 82).The collections in java.util (with the exception of the
 * obsolete Vector and Hashtable) take the former approach, while those in
 * java.util.concurrent take the latter (Item 81).
 * 
 * In the early days of Java, many classes violated these guidelines. For
 * example, StringBuffer instances are almost always used by a single thread,
 * yet they perform internal synchronization. It is for this reason that
 * StringBuffer was supplanted by StringBuilder, which is just an unsynchronized
 * StringBuffer. Similarly, it’s a large part of the reason that the thread-safe
 * pseudorandom number generator in java.util.Random was supplanted by the
 * unsynchronized implementation in java.util.concurrent.ThreadLocalRandom. When
 * in doubt, do not synchronize your class, but document that it is not
 * thread-safe.
 * 
 * In summary, to avoid deadlock and data corruption, never call an alien method
 * from within a synchronized region. More generally, keep the amount of work
 * that you do from within synchronized regions to a minimum. When you are
 * designing a mutable class, think about whether it should do its own
 * synchronization. In the multicore era, it is more important than ever not to
 * oversynchronize. Synchronize your class internally only if there is a good
 * reason to do so, and document your decision clearly (Item 82).
 *
 */
// Broken - invokes alien method from synchronized block!
public class ObservableSet<E> extends ForwardingSet<E> {
	public ObservableSet(Set<E> set) {
		super(set);
	}

	// ----------------------------------------------------------------
	// Version 1
	private final List<SetObserver<E>> observers = new ArrayList<>();

	public void addObserver1(SetObserver<E> observer) {
		synchronized (observers) {
			observers.add(observer);
		}
	}

	public boolean removeObserver1(SetObserver<E> observer) {
		synchronized (observers) {
			return observers.remove(observer);
		}
	}

	private void notifyElementAdded1(E element) {
		synchronized (observers) {
			for (SetObserver<E> observer : observers)
				observer.added(this, element);
		}
	}

	// Alien method moved outside of synchronized block - open calls
	private void notifyElementAddedFixed1(E element) {
		List<SetObserver<E>> snapshot = null;
		synchronized (observers) {
			snapshot = new ArrayList<>(observers);
		}
		for (SetObserver<E> observer : snapshot)
			observer.added(this, element);
	}
	// ----------------------------------------------------------------

	// Version 2 : Thread-safe observable set with CopyOnWriteArrayList
	private final List<SetObserver<E>> observersWithCopyOnWrite = new CopyOnWriteArrayList<>();

	public void addObserver2(SetObserver<E> observer) {
		observersWithCopyOnWrite.add(observer);
	}

	public boolean removeObserver2(SetObserver<E> observer) {
		return observersWithCopyOnWrite.remove(observer);
	}

	private void notifyElementAdded2(E element) {
		for (SetObserver<E> observer : observersWithCopyOnWrite)
			observer.added(this, element);
	}

	// ---------------------------------------------------------------

	@Override
	public boolean add(E element) {
		boolean added = super.add(element);
		if (added)
			notifyElementAdded2(element);
		return added;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean result = false;
		for (E element : c)
			result |= add(element); // Calls notifyElementAdded
		return result;
	}
}
