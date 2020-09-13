package effectivejava.chapter6.item40;

import java.util.*;

/**
 * 
 * Item 40: Consistently use the Override annotation.
 * 
 * The Java libraries contain several annotation types. For the typical
 * programmer, the most important of these is @Override. This annotation can be
 * used only on method declarations, and it indicates that the annotated method
 * declaration overrides a declaration in a supertype. If you consistently use
 * this annotation, it will protect you from a large class of nefarious bugs.
 * 
 * The main program repeatedly adds twenty-six bigrams, each consisting of two
 * identical lowercase letters, to a set. Then it prints the size of the set.
 * You might expect the program to print 26, as sets cannot contain duplicates.
 * If you try running the program, you’ll find that it prints not 26 but 260.
 * What is wrong with it?
 * 
 * Clearly, the author of the Bigram class intended to override the equals
 * method (Item 10) and even remembered to override hashCode in tandem (Item
 * 11). Unfortunately, our hapless programmer failed to override equals,
 * overloading it instead (Item 52). To override Object.equals, you must define
 * an equals method whose parameter is of type Object, but the parameter of
 * Bigram’s equals method is not of type Object, so Bigram inherits the equals
 * method from Object. This equals method tests for object identity, just like
 * the == operator. Each of the ten copies of each bigram is distinct from the
 * other nine, so they are deemed unequal by Object.equals, which explains why
 * the program prints 260.
 * 
 * In summary, the compiler can protect you from a great many errors if you use
 * the Override annotation on every method declaration that you believe to
 * override a supertype declaration, with one exception. In concrete classes,
 * you need not annotate methods that you believe to override abstract method
 * declarations (though it is not harmful to do so).
 *
 */
// Can you spot the bug? (Page 188)
public class Bigram {
    private final char first;
    private final char second;

    public Bigram(char first, char second) {
	this.first = first;
	this.second = second;
    }

    public boolean equals(Bigram b) {
	return b.first == first && b.second == second;
    }

    public int hashCode() {
	return 31 * first + second;
    }

    public static void main(String[] args) {
	Set<Bigram> s = new HashSet<>();
	for (int i = 0; i < 10; i++)
	    for (char ch = 'a'; ch <= 'z'; ch++)
		s.add(new Bigram(ch, ch));
	System.out.println(s.size());
    }
}
