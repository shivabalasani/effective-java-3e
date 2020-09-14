package effectivejava.chapter2.item3.field;

/**
 * 
 * The main advantage of the public field approach is that the API makes it
 * clear that the class is a singleton: the public static field is final, so it
 * will always contain the same object reference.
 * 
 * The second advantage is that it’s simpler.
 *
 */
// Singleton with public final field  (Page 17)
public class Elvis {
	public static final Elvis INSTANCE = new Elvis();

	// A privileged client can invoke the private constructor reflectively
	// (Item 65) with the aid of the AccessibleObject.setAccessible method. If you
	// need to defend against this attack, modify the constructor to make it throw
	// an
	// exception if it’s asked to create a second instance.
	private Elvis() {
	}

	public void leaveTheBuilding() {
		System.out.println("Whoa baby, I'm outta here!");
	}

	// This code would normally appear outside the class!
	public static void main(String[] args) {
		Elvis elvis = Elvis.INSTANCE;
		elvis.leaveTheBuilding();
	}
}