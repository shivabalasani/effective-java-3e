package effectivejava.chapters10.item71;

/**
 * 
 * Item 71: Avoid unnecessary use of checked exceptions.
 * 
 * Many Java programmers dislike checked exceptions, but used properly, they can
 * improve APIs and programs. Unlike return codes and unchecked exceptions, they
 * force programmers to deal with problems, enhancing reliability. That said,
 * overuse of checked exceptions in APIs can make them far less pleasant to use.
 * If a method throws checked exceptions, the code that invokes it must handle
 * them in one or more catch blocks, or declare that it throws them and let them
 * propagate outward. Either way, it places a burden on the user of the API. The
 * burden increased in Java 8, as methods throwing checked exceptions can�t be
 * used directly in streams (Items 45�48).
 * 
 * This burden may be justified if the exceptional condition cannot be prevented
 * by proper use of the API and the programmer using the API can take some
 * useful action once confronted with the exception. Unless both of these
 * conditions are met, an unchecked exception is appropriate. As a litmus test,
 * ask yourself how the programmer will handle the exception. Is this the best
 * that can be done?
 * 
 * } catch (TheCheckedException e) { throw new AssertionError(); // Can't
 * happen! } Or this?
 * 
 * } catch (TheCheckedException e) { e.printStackTrace(); // Oh well, we lose.
 * System.exit(1); }
 * 
 * If the programmer can do no better, an unchecked exception is called for.
 * 
 * You can also turn a checked exception into an unchecked exception by breaking
 * the method that throws the exception into two methods, the first of which
 * returns a boolean indicating whether the exception would be thrown. This API
 * refactoring transforms the calling sequence from this:
 * 
 * // Invocation with checked exception
 * 
 * try { obj.action(args); } catch (TheCheckedException e) { ... // Handle
 * exceptional condition } into this:
 * 
 * // Invocation with state-testing method and unchecked exception
 * 
 * if (obj.actionPermitted(args)) { obj.action(args); } else { ... // Handle
 * exceptional condition }
 *
 * This refactoring is not always appropriate, but where it is, it can make an
 * API more pleasant to use. While the latter calling sequence is no prettier
 * than the former, the refactored API is more flexible. If the programmer knows
 * the call will succeed, or is content to let the thread terminate if it fails,
 * the refactoring also allows this trivial calling sequence: obj.action(args);
 * 
 * If you suspect that the trivial calling sequence will be the norm, then the
 * API refactoring may be appropriate. The resulting API is essentially the
 * state-testing method API in Item 69 and the same caveats apply: if an object
 * is to be accessed concurrently without external synchronization or it is
 * subject to externally induced state transitions, this refactoring is
 * inappropriate because the object�s state may change between the calls to
 * actionPermitted and action. If a separate actionPermitted method would
 * duplicate the work of the action method, the refactoring may be ruled out on
 * performance grounds.
 * 
 * In summary, when used sparingly, checked exceptions can increase the
 * reliability of programs; when overused, they make APIs painful to use. If
 * callers won�t be able to recover from failures, throw unchecked exceptions.
 * If recovery may be possible and you want to force callers to handle
 * exceptional conditions, first consider returning an optional. Only if this
 * would provide insufficient information in the case of failure should you
 * throw a checked exception.
 */

public class CheckedExceptions {

}
