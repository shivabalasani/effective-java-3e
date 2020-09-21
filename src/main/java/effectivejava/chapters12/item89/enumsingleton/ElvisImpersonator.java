package effectivejava.chapters12.item89.enumsingleton;

import effectivejava.chapters12.Util;

/**
 * 
 * Finally, here is an ugly program that deserializes a handcrafted stream to
 * produce two distinct instances of the flawed singleton. The deserialize
 * method is omitted from this program because it’s identical to the one on page
 * 354:
 * 
 * Running this program produces the following output, conclusively proving that
 * it’s possible to create two distinct Elvis instances (with different tastes
 * in music):
 * 
 * [Hound Dog, Heartbreak Hotel] [A Fool Such as I]
 * 
 * You could fix the problem by declaring the favoriteSongs field transient, but
 * you’re better off fixing it by making Elvis a single-element enum type (Item
 * 3). As demonstrated by the ElvisStealer attack, using a readResolve method to
 * prevent a “temporary” deserialized instance from being accessed by an
 * attacker is fragile and demands great care.
 * 
 * If you write your serializable instance-controlled class as an enum, Java
 * guarantees you that there can be no instances besides the declared constants,
 * unless an attacker abuses a privileged method such as
 * AccessibleObject.setAccessible. Any attacker who can do that already has
 * sufficient privileges to execute arbitrary native code, and all bets are off.
 * Here’s how our Elvis example looks as an enum: see ElvisPreferred
 *
 */
public class ElvisImpersonator {
	// Byte stream couldn't have come from a real Elvis instance!
	private static final byte[] serializedForm = { (byte) 0xac, (byte) 0xed, 0x00, 0x05, 0x73, 0x72, 0x00, 0x05, 0x45,
			0x6c, 0x76, 0x69, 0x73, (byte) 0x84, (byte) 0xe6, (byte) 0x93, 0x33, (byte) 0xc3, (byte) 0xf4, (byte) 0x8b,
			0x32, 0x02, 0x00, 0x01, 0x4c, 0x00, 0x0d, 0x66, 0x61, 0x76, 0x6f, 0x72, 0x69, 0x74, 0x65, 0x53, 0x6f, 0x6e,
			0x67, 0x73, 0x74, 0x00, 0x12, 0x4c, 0x6a, 0x61, 0x76, 0x61, 0x2f, 0x6c, 0x61, 0x6e, 0x67, 0x2f, 0x4f, 0x62,
			0x6a, 0x65, 0x63, 0x74, 0x3b, 0x78, 0x70, 0x73, 0x72, 0x00, 0x0c, 0x45, 0x6c, 0x76, 0x69, 0x73, 0x53, 0x74,
			0x65, 0x61, 0x6c, 0x65, 0x72, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0x00, 0x01, 0x4c, 0x00,
			0x07, 0x70, 0x61, 0x79, 0x6c, 0x6f, 0x61, 0x64, 0x74, 0x00, 0x07, 0x4c, 0x45, 0x6c, 0x76, 0x69, 0x73, 0x3b,
			0x78, 0x70, 0x71, 0x00, 0x7e, 0x00, 0x02 };

	public static void main(String[] args) {
		// Initializes ElvisStealer.impersonator and returns
		// the real Elvis (which is Elvis.INSTANCE)

		// Elvis elvis = (Elvis) Util.deserialize(Util.serialize(Elvis.INSTANCE));
		Elvis elvis = (Elvis) Util.deserialize(serializedForm);
		Elvis impersonator = ElvisStealer.impersonator;
		elvis.printFavorites();
		impersonator.printFavorites();
	}
}
