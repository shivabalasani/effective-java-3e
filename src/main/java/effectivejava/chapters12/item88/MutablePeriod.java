package effectivejava.chapters12.item88;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * 
 * While the Period instance is created with its invariants intact, it is
 * possible to modify its internal components at will. Once in possession of a
 * mutable Period instance, an attacker might cause great harm by passing the
 * instance to a class that depends on Period’s immutability for its security.
 * This is not so far-fetched: there are classes that depend on String’s
 * immutability for their security.
 * 
 * The source of the problem is that Period’s readObject method is not doing
 * enough defensive copying. When an object is deserialized, it is critical to
 * defensively copy any field containing an object reference that a client must
 * not possess. Therefore, every serializable immutable class containing private
 * mutable components must defensively copy these components in its readObject
 * method. The following readObject method suffices to ensure Period’s
 * invariants and to maintain its immutability:
 *
 */
public class MutablePeriod {
	// A period instance
	public final Period period;
	// period's start field, to which we shouldn't have access
	public final Date start;
	// period's end field, to which we shouldn't have access
	public final Date end;

	public MutablePeriod() {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			// Serialize a valid Period instance
			out.writeObject(new Period(new Date(), new Date()));
			/*
			 * Append rogue "previous object refs" for internal Date fields in Period. For
			 * details, see "Java Object Serialization Specification," Section 6.4.
			 */
			byte[] ref = { 0x71, 0, 0x7e, 0, 5 }; // Ref #5
			bos.write(ref); // The start field
			ref[4] = 4; // Ref # 4
			bos.write(ref); // The end field
			// Deserialize Period and "stolen" Date references
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
			period = (Period) in.readObject();
			start = (Date) in.readObject();
			end = (Date) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new AssertionError(e);
		}
	}

}
