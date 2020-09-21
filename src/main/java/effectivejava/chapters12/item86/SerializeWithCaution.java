package effectivejava.chapters12.item86;

import java.io.InvalidObjectException;

/**
 * 
 * Item 86: Implement Serializable with great caution.
 * 
 * Allowing a class’s instances to be serialized can be as simple as adding the
 * words implements Serializable to its declaration. Because this is so easy to
 * do, there was a common misconception that serialization requires little
 * effort on the part of the programmer. The truth is far more complex. While
 * the immediate cost to make a class serializable can be negligible, the
 * long-term costs are often substantial.
 * 
 * A major cost of implementing Serializable is that it decreases the
 * flexibility to change a class’s implementation once it has been released.
 * 
 * A second cost of implementing Serializable is that it increases the
 * likelihood of bugs and security holes (Item 85).
 * 
 * A third cost of implementing Serializable is that it increases the testing
 * burden associated with releasing a new version of a class.
 * 
 * Implementing Serializable is not a decision to be undertaken lightly.
 * 
 * Classes designed for inheritance (Item 19) should rarely implement
 * Serializable, and interfaces should rarely extend it.
 * 
 * Classes designed for inheritance that do implement Serializable include
 * Throwable and Component. Throwable implements Serializable so RMI can send
 * exceptions from server to client. Component implements Serializable so GUIs
 * can be sent, saved, and restored, but even in the heyday of Swing and AWT,
 * this facility was little-used in practice.
 * 
 * Inner classes (Item 24) should not implement Serializable.
 * 
 * To summarize, the ease of implementing Serializable is specious. Unless a
 * class is to be used only in a protected environment where versions will never
 * have to interoperate and servers will never be exposed to untrusted data,
 * implementing Serializable is a serious commitment that should be made with
 * great care. Extra caution is warranted if a class permits inheritance.
 *
 */
public class SerializeWithCaution {

	// readObjectNoData for stateful extendable serializable classes
	private void readObjectNoData() throws InvalidObjectException {
		throw new InvalidObjectException("Stream data required");
	}

}
