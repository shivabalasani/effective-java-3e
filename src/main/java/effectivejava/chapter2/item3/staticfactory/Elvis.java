package effectivejava.chapter2.item3.staticfactory;

/**
 * 
 * 	One advantage of the static factory approach is that it gives you the flexibility
 *	to change your mind about whether the class is a singleton without changing its API.
 * 
 *  A second advantage is that you can write a generic singleton factory if your application requires it (Item 30).
 *
 *	A final advantage of using a static factory is that a method reference can
 *  be used as a supplier, for example Elvis::instance is a Supplier<Elvis>.
 *  
 *	Unless one of these advantages is relevant, the public field approach is preferable.
 *
 */
// Singleton with static factory (Page 17)
public class Elvis {
	private static final Elvis INSTANCE = new Elvis();

	private Elvis() {
	}

	public static Elvis getInstance() {
		return INSTANCE;
	}

	public void leaveTheBuilding() {
		System.out.println("Whoa baby, I'm outta here!");
	}

	// This code would normally appear outside the class!
	public static void main(String[] args) {
		Elvis elvis = Elvis.getInstance();
		elvis.leaveTheBuilding();
	}
}
