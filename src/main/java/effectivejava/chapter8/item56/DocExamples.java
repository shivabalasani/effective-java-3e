package effectivejava.chapter8.item56;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * 
 * Item 56: Write doc comments for all exposed API elements.
 * 
 * If an API is to be usable, it must be documented. Traditionally, API
 * documentation was generated manually, and keeping it in sync with code was a
 * chore. The Java programming environment eases this task with the Javadoc
 * utility. Javadoc generates API documentation automatically from source code
 * with specially formatted documentation comments, more commonly known as doc
 * comments.
 * 
 * While the doc comment conventions are not officially part of the language,
 * they constitute a de facto API that every Java programmer should know. These
 * conventions are described in the How to Write Doc Comments web page
 * [Javadocguide]. While this page has not been updated since Java 4 was
 * released, it is still an invaluable resource. One important doc tag was added
 * in Java 9, {@index}; one in Java 8, {@implSpec}; and two in Java 5,
 * {@literal} and {@code}. These tags are missing from the aforementioned web
 * page, but are discussed in this item.
 * 
 * To document your API properly, you must precede every exported class,
 * interface, constructor, method, and field declaration with a doc comment.
 * 
 * The doc comment for a method should describe succinctly the contract between
 * the method and its client.
 * 
 * Doc comments should be readable both in the source code and in the generated
 * documentation. To avoid confusion, no two members or constructors in a class
 * or interface should have the same summary description.
 * 
 * When documenting a generic type or method, be sure to document all type
 * parameters:
 * 
 * When documenting an enum type, be sure to document the constants as well as
 * the type and any public methods. When documenting an annotation type, be sure
 * to document any members as well as the type itself.
 * 
 * Whether or not a class or static method is threadsafe, you should document
 * its thread-safety level, as described in Item 82. If a class is serializable,
 * you should document its serialized form, as described in Item 87.
 * 
 * To summarize, documentation comments are the best, most effective way to
 * document your API. Their use should be considered mandatory for all exported
 * API elements. Adopt a consistent style that adheres to standard conventions.
 * Remember that arbitrary HTML is permissible in documentation comments and
 * that HTML metacharacters must be escaped.
 *
 */
// Documentation comment examples (Pages 255-9)
public class DocExamples<E> {
	// Method comment (Page 255)
	/**
	 * Returns the element at the specified position in this list.
	 *
	 * <p>
	 * This method is <i>not</i> guaranteed to run in constant time. In some
	 * implementations it may run in time proportional to the element position.
	 *
	 * @param index index of element to return; must be non-negative and less than
	 *              the size of this list
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException if the index is out of range
	 *                                   ({@code index < 0 || index >= this.size()})
	 */
	E get(int index) {
		return null;
	}

	// Use of @implSpec to describe self-use patterns & other visible implementation
	// details. (Page 256)
	/**
	 * Returns true if this collection is empty.
	 *
	 * @implSpec This implementation returns {@code this.size() == 0}.
	 *
	 * @return true if this collection is empty
	 */
	public boolean isEmpty() {
		return false;
	}

	// Use of the @literal tag to include HTML and javadoc metacharacters in javadoc
	// comments. (Page 256)
	/**
	 * A geometric series converges if {@literal |r| < 1}.
	 */
	public void fragment() {
	}

	// Controlling summary description when there is a period in the first
	// "sentence" of doc comment. (Page 257)
	/**
	 * A suspect, such as Colonel Mustard or {@literal Mrs. Peacock}.
	 */
	public enum FixedSuspect {
		MISS_SCARLETT, PROFESSOR_PLUM, MRS_PEACOCK, MR_GREEN, COLONEL_MUSTARD, MRS_WHITE
	}

	// Generating a javadoc index entry in Java 9 and later releases. (Page 258)
	/**
	 * This method complies with the {@index IEEE 754} standard.
	 */
	public void fragment2() {
	}

	// Documenting enum constants (Page 258)
	/**
	 * An instrument section of a symphony orchestra.
	 */
	public enum OrchestraSection {
		/** Woodwinds, such as flute, clarinet, and oboe. */
		WOODWIND,

		/** Brass instruments, such as french horn and trumpet. */
		BRASS,

		/** Percussion instruments, such as timpani and cymbals. */
		PERCUSSION,

		/** Stringed instruments, such as violin and cello. */
		STRING;
	}

	// Documenting an annotation type (Page 259)
	/**
	 * Indicates that the annotated method is a test method that must throw the
	 * designated exception to pass.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface ExceptionTest {
		/**
		 * The exception that the annotated test method must throw in order to pass.
		 * (The test is permitted to throw any subtype of the type described by this
		 * class object.)
		 */
		Class<? extends Throwable> value();
	}
}
