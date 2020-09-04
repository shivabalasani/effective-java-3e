package effectivejava.chapter2.item8;

/**
 * - Item 8: Avoid finalizers and cleaners
 * 
 * - Finalizers are unpredictable, often dangerous, and generally unnecessary. The Java 9 replacement for finalizers is
 *	 cleaners. Cleaners are less dangerous than finalizers, but still unpredictable, slow, and generally unnecessary.
 *   You should never do anything time-critical in a finalizer or cleaner.
 *   You should never depend on a finalizer or cleaner to update persistent state.
 *   
 * - There is a severe performance penalty for using finalizers and cleaners.The time to create a simple 
 *   AutoCloseable object, to close it using try-with-resources, and to have the garbage collector reclaim it is about 12 ns.
 *	 Using a finalizer instead increases the time to 550 ns.
 * 
 * - Finalizers have a serious security problem: they open your class up to finalizer attacks.
 * - Throwing an exception from a constructor should be sufficient to prevent an object from coming into existence; in the
 *	 presence of finalizers, it is not.
 * - To protect nonfinal classes from finalizer attacks, write a final finalize method that does nothing.
 *
 */
// Well-behaved client of resource with cleaner safety-net (Page 33)
public class Adult {
    public static void main(String[] args) {
    	// Room’s cleaner is used only as a safety net. If clients surround all Room instantiations 
    	// in try-with-resource blocks, automatic cleaning will never be required.
        try (Room myRoom = new Room(7)) {
            System.out.println("Goodbye");
        }
    }
}
