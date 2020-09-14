package effectivejava.chapter2.item8;

import java.lang.ref.Cleaner;

/**
 * 
 * An autocloseable class using a cleaner as a safety net (Page 32) So what
 * should you do instead of writing a finalizer or cleaner for a class whose
 * objects encapsulate resources that require termination, such as files or
 * threads? Just have your class implement AutoCloseable, and require its
 * clients to invoke the close method on each instance when it is no longer
 * needed.
 *
 */

public class Room implements AutoCloseable {
	private static final Cleaner cleaner = Cleaner.create();

	// Resource that requires cleaning. Must not refer to Room!
	private static class State implements Runnable {
		int numJunkPiles; // Number of junk piles in this room

		State(int numJunkPiles) {
			this.numJunkPiles = numJunkPiles;
		}

		// Invoked by close method or cleaner
		@Override
		public void run() {
			System.out.println("Cleaning room");
			numJunkPiles = 0;
		}
	}

	// The state of this room, shared with our cleanable
	private final State state;

	// Our cleanable. Cleans the room when it is eligible for gc
	private final Cleaner.Cleanable cleanable;

	public Room(int numJunkPiles) {
		state = new State(numJunkPiles);
		cleanable = cleaner.register(this, state);
	}

	@Override
	public void close() {
		cleanable.clean();
	}
}
