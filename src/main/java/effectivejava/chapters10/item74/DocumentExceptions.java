package effectivejava.chapters10.item74;

/**
 * 
 * Item 74: Document all exceptions thrown by each method.
 * 
 * A description of the exceptions thrown by a method is an important part of
 * the documentation required to use the method properly. Therefore, it is
 * critically important that you take the time to carefully document all of the
 * exceptions thrown by each method (Item 56).
 * 
 * Always declare checked exceptions individually, and document precisely the
 * conditions under which each one is thrown using the Javadoc @throws tag.
 * Don�t take the shortcut of declaring that a method throws some superclass of
 * multiple exception classes that it can throw. As an extreme example, don�t
 * declare that a public method throws Exception or, worse, throws Throwable.
 * One exception to this advice is the main method, which can safely be declared
 * to throw Exception because it is called only by VM.
 * 
 * While the language does not require programmers to declare the unchecked
 * exceptions that a method is capable of throwing, it is wise to document them
 * as carefully as the checked exceptions. Unchecked exceptions generally
 * represent programming errors (Item 70), and familiarizing programmers with
 * all of the errors they can make helps them avoid making these errors. A
 * well-documented list of the unchecked exceptions that a method can throw
 * effectively describes the preconditions for its successful execution. It is
 * essential that every public method�s documentation describe its preconditions
 * (Item 56), and documenting its unchecked exceptions is the best way to
 * satisfy this requirement.
 * 
 * It is particularly important that methods in interfaces document the
 * unchecked exceptions they may throw. This documentation forms a part of the
 * interface�s general contract and enables common behavior among multiple
 * implementations of the interface.
 * 
 * Use the Javadoc @throws tag to document each exception that a method can
 * throw, but do not use the throws keyword on unchecked exceptions. It is
 * important that programmers using your API are aware of which exceptions are
 * checked and which are unchecked because the programmers� responsibilities
 * differ in these two cases. The documentation generated by the Javadoc @throws
 * tag without a corresponding throws clause in the method declaration provides
 * a strong visual cue to the programmer that an exception is unchecked.
 * 
 * If an exception is thrown by many methods in a class for the same reason, you
 * can document the exception in the class�s documentation comment rather than
 * documenting it individually for each method. A common example is
 * NullPointerException. It is fine for a class�s documentation comment to say,
 * �All methods in this class throw a NullPointerException if a null object
 * reference is passed in any parameter,� or words to that effect.
 * 
 * In summary, document every exception that can be thrown by each method that
 * you write. This is true for unchecked as well as checked exceptions, and for
 * abstract as well as concrete methods. This documentation should take the form
 * of throws tags in doc comments. Declare each checked exception individually
 * in a method�s throws clause, but do not declare unchecked exceptions. If you
 * fail to document the exceptions that your methods can throw, it will be
 * difficult or impossible for others to make effective use of your classes and
 * interfaces.
 *
 */

public class DocumentExceptions {

}
