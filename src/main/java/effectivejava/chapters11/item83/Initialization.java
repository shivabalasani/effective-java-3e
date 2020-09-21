package effectivejava.chapters11.item83;

/**
 * 
 * Item 83: Use lazy initialization judiciously.
 * 
 * Lazy initialization is the act of delaying the initialization of a field
 * until its value is needed. If the value is never needed, the field is never
 * initialized. This technique is applicable to both static and instance fields.
 * While lazy initialization is primarily an optimization, it can also be used
 * to break harmful circularities in class and instance initialization.
 * 
 * As is the case for most optimizations, the best advice for lazy
 * initialization is “don’t do it unless you need to” (Item 67).That said, lazy
 * initialization has its uses. If a field is accessed only on a fraction of the
 * instances of a class and it is costly to initialize the field, then lazy
 * initialization may be worthwhile. The only way to know for sure is to measure
 * the performance of the class with and without lazy initialization.
 * 
 * In the presence of multiple threads, lazy initialization is tricky. If two or
 * more threads share a lazily initialized field, it is critical that some form
 * of synchronization be employed, or severe bugs can result (Item 78). All of
 * the initialization techniques discussed in this item are thread-safe.
 * 
 * Under most circumstances, normal initialization is preferable to lazy
 * initialization. Here is a typical declaration for a normally initialized
 * instance field. Note the use of the final modifier (Item 17): Version 1
 * 
 * If you use lazy initialization to break an initialization circularity, use a
 * synchronized accessor because it is the simplest, clearest alternative:
 * Version 2
 * 
 * Both of these idioms (normal initialization and lazy initialization with a
 * synchronized accessor) are unchanged when applied to static fields, except
 * that you add the static modifier to the field and accessor declarations.
 * 
 * If you need to use lazy initialization for performance on a static field, use
 * the lazy initialization holder class idiom. This idiom exploits the guarantee
 * that a class will not be initialized until it is used [JLS, 12.4.1]. Here’s
 * how it looks: Version 3. When getField is invoked for the first time, it
 * reads FieldHolder.field for the first time, causing the initialization of the
 * FieldHolder class. The beauty of this idiom is that the getField method is
 * not synchronized and performs only a field access, so lazy initialization
 * adds practically nothing to the cost of access. A typical VM will synchronize
 * field access only to initialize the class. Once the class is initialized, the
 * VM patches the code so that subsequent access to the field does not involve
 * any testing or synchronization.
 * 
 * If you need to use lazy initialization for performance on an instance field,
 * use the double-check idiom. This idiom avoids the cost of locking when
 * accessing the field after initialization (Item 79). The idea behind the idiom
 * is to check the value of the field twice (hence the name double-check): once
 * without locking and then, if the field appears to be uninitialized, a second
 * time with locking. Only if the second check indicates that the field is
 * uninitialized does the call initialize the field. Because there is no locking
 * once the field is initialized, it is critical that the field be declared
 * volatile (Item 78). Here is the idiom: Version 4
 * 
 * This code may appear a bit convoluted. In particular, the need for the local
 * variable (result) may be unclear. What this variable does is to ensure that
 * field is read only once in the common case where it’s already initialized.
 * While not strictly necessary, this may improve performance and is more
 * elegant by the standards applied to low-level concurrent programming. On my
 * machine, the method above is about 1.4 times as fast as the obvious version
 * without a local variable.
 * 
 * While you can apply the double-check idiom to static fields as well, there is
 * no reason to do so: the lazy initialization holder class idiom is a better
 * choice.
 * 
 * Two variants of the double-check idiom bear noting. Occasionally, you may
 * need to lazily initialize an instance field that can tolerate repeated
 * initialization. If you find yourself in this situation, you can use a variant
 * of the double-check idiom that dispenses with the second check. It is, not
 * surprisingly, known as the singlecheck idiom. Here is how it looks. Note that
 * field is still declared volatile: Version 5.
 * 
 * All of the initialization techniques discussed in this item apply to
 * primitive fields as well as object reference fields. When the double-check or
 * single-check idiom is applied to a numerical primitive field, the field’s
 * value is checked against 0 (the default value for numerical primitive
 * variables) rather than null.
 * 
 * In summary, you should initialize most fields normally, not lazily. If you
 * must initialize a field lazily in order to achieve your performance goals or
 * to break a harmful initialization circularity, then use the appropriate lazy
 * initialization technique. For instance fields, it is the double-check idiom;
 * for static fields, the lazy initialization holder class idiom. For instance
 * fields that can tolerate repeated initialization, you may also consider the
 * single-check idiom.
 *
 */
// Initialization styles - Pages 333-
public class Initialization {

    // Version 1 : Normal initialization of an instance field
    private final FieldType field1 = computeFieldValue();

    // ----------------------------------------------------------------------------------
    // Version 2 : Lazy initialization of instance field - synchronized accessor
    private FieldType field2;

    private synchronized FieldType getField2() {
	if (field2 == null)
	    field2 = computeFieldValue();
	return field2;
    }

    // ----------------------------------------------------------------------------------
    // Version 3 : Lazy initialization holder class idiom for static fields
    private static class FieldHolder {
	static final FieldType field = computeFieldValue();
    }

    private static FieldType getField() {
	return FieldHolder.field;
    }

    // ----------------------------------------------------------------------------------
    // Version 4 : Double-check idiom for lazy initialization of instance fields
    private volatile FieldType field4;

    // NOTE: The code for this method in the first printing had a serious error (see
    // errata for details)!
    private FieldType getField4() {
	FieldType result = field4;
	if (result != null) // First check (no locking)
	    return result;

	synchronized (this) {
	    if (field4 == null) // Second check (with locking)
		field4 = computeFieldValue();
	    return field4;
	}
    }

    // ----------------------------------------------------------------------------------
    // Version 5 : Single-check idiom - can cause repeated initialization!
    private volatile FieldType field5;

    private FieldType getField5() {
	FieldType result = field5;
	if (result == null)
	    field5 = result = computeFieldValue();
	return result;
    }
    // ----------------------------------------------------------------------------------

    private static FieldType computeFieldValue() {
	return new FieldType();
    }
}
