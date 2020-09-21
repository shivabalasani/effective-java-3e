package effectivejava.chapters12.item88;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;

import effectivejava.chapters12.Util;

/**
 * Suppose you decide that you want this class to be serializable. Because the
 * physical representation of a Period object exactly mirrors its logical data
 * content, it is not unreasonable to use the default serialized form (Item 87).
 * Therefore, it might seem that all you have to do to make the class
 * serializable is to add the words implements Serializable to the class
 * declaration. If you did so, however, the class would no longer guarantee its
 * critical invariants.
 * 
 * The problem is that the readObject method is effectively another public
 * constructor, and it demands all of the same care as any other constructor.
 * Just as a constructor must check its arguments for validity (Item 49) and
 * make defensive copies of parameters where appropriate (Item 50), so must a
 * readObject method. If a readObject method fails to do either of these things,
 * it is a relatively simple matter for an attacker to violate the class�s
 * invariants. Loosely speaking, readObject is a constructor that takes a byte
 * stream as its sole parameter. In normal use, the byte stream is generated by
 * serializing a normally constructed instance. The problem arises when
 * readObject is presented with a byte stream that is artificially constructed
 * to generate an object that violates the invariants of its class. Such a byte
 * stream can be used to create an impossible object, which could not have been
 * created using a normal constructor.
 * 
 * Assume that we simply added implements Serializable to the class declaration
 * for Period. This ugly program would then generate a Period instance whose end
 * precedes its start. The casts on byte values whose high-order bit is set is a
 * consequence of Java�s lack of byte literals combined with the unfortunate
 * decision to make the byte type signed:
 * 
 * The byte array literal used to initialize serializedForm was generated by
 * serializing a normal Period instance and hand-editing the resulting byte
 * stream. The details of the stream are unimportant to the example, but if
 * you�re curious, the serialization byte-stream format is described in the Java
 * Object Serialization Specification [Serialization, 6]. If you run this
 * program, it prints Fri Jan 01 12:00:00 PST 1999 - Sun Jan 01 12:00:00 PST
 * 1984. Simply declaring Period serializable enabled us to create an object
 * that violates its class invariants.
 * 
 * To fix this problem, provide a readObject method for Period that calls
 * defaultReadObject and then checks the validity of the deserialized object. If
 * the validity check fails, the readObject method throws
 * InvalidObjectException, preventing the deserialization from completing:
 *
 */
public class BogusPeriod {
    // Byte stream couldn't have come from a real Period instance!
    private static final byte[] serializedForm = { (byte) 0xac, (byte) 0xed, 0x00, 0x05, 0x73, 0x72, 0x00, 0x06, 0x50,
	    0x65, 0x72, 0x69, 0x6f, 0x64, 0x40, 0x7e, (byte) 0xf8, 0x2b, 0x4f, 0x46, (byte) 0xc0, (byte) 0xf4, 0x02,
	    0x00, 0x02, 0x4c, 0x00, 0x03, 0x65, 0x6e, 0x64, 0x74, 0x00, 0x10, 0x4c, 0x6a, 0x61, 0x76, 0x61, 0x2f, 0x75,
	    0x74, 0x69, 0x6c, 0x2f, 0x44, 0x61, 0x74, 0x65, 0x3b, 0x4c, 0x00, 0x05, 0x73, 0x74, 0x61, 0x72, 0x74, 0x71,
	    0x00, 0x7e, 0x00, 0x01, 0x78, 0x70, 0x73, 0x72, 0x00, 0x0e, 0x6a, 0x61, 0x76, 0x61, 0x2e, 0x75, 0x74, 0x69,
	    0x6c, 0x2e, 0x44, 0x61, 0x74, 0x65, 0x68, 0x6a, (byte) 0x81, 0x01, 0x4b, 0x59, 0x74, 0x19, 0x03, 0x00, 0x00,
	    0x78, 0x70, 0x77, 0x08, 0x00, 0x00, 0x00, 0x66, (byte) 0xdf, 0x6e, 0x1e, 0x00, 0x78, 0x73, 0x71, 0x00, 0x7e,
	    0x00, 0x03, 0x77, 0x08, 0x00, 0x00, 0x00, (byte) 0xd5, 0x17, 0x69, 0x22, 0x00, 0x78 };

    public static void main(String[] args) {
	Period p = (Period) Util.deserialize(serializedForm);
	// Period p = (Period) deserialize(Util.serialize(new Period(new Date(), new
	// Date())));
	System.out.println(p);
    }

}