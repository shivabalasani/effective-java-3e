package effectivejava.chapter6.item39.repeatableannotation;

import java.lang.annotation.*;

/**
 * 
 * As of Java 8, there is another way to do multivalued annotations. Instead of
 * declaring an annotation type with an array parameter, you can annotate the
 * declaration of an annotation with the @Repeatable meta-annotation, to
 * indicate that the annotation may be applied repeatedly to a single element.
 * This meta-annotation takes a single parameter, which is the class object of a
 * containing annotation type, whose sole parameter is an array of the
 * annotation type [JLS, 9.6.3]. Here’s how the annotation declarations look if
 * we take this approach with our ExceptionTest annotation. Note that the
 * containing annotation type must be annotated with an appropriate retention
 * policy and target, or the declarations won’t compile:
 *
 */

// Repeatable annotation type (Page 186)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(ExceptionTestContainer.class)
public @interface ExceptionTest {
    Class<? extends Throwable> value();
}