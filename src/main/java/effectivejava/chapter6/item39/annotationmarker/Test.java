package effectivejava.chapter6.item39.annotationmarker;

// Marker annotation type declaration - Page 180
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Marker annotation type declaration (Page 180)

/**
 * The declaration for the Test annotation type is itself annotated with
 * Retention and Target annotations. Such annotations on annotation type
 * declarations are known as meta-annotations.
 * The @Retention(RetentionPolicy.RUNTIME) meta-annotation indicates that Test
 * annotations should be retained at runtime. Without it, Test annotations would
 * be invisible to the test tool. The Target.get(ElementType.METHOD)
 * meta-annotation indicates that the Test annotation is legal only on method
 * declarations: it cannot be applied to class declarations, field declarations,
 * or other program elements.
 * 
 * Indicates that the annotated method is a test method. Use only on
 * parameterless static methods.
 * 
 * The comment before the Test annotation declaration says, “Use only on
 * parameterless static methods.” It would be nice if the compiler could enforce
 * this, but it can’t, unless you write an annotation processor to do so. For
 * more on this topic, see the documentation for javax.annotation.processing. In
 * the absence of such an annotation processor, if you put a Test annotation on
 * the declaration of an instance method or on a method with one or more
 * parameters, the test program will still compile, leaving it to the testing
 * tool to deal with the problem at runtime.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
}