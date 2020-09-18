package effectivejava.chapter9.item58;

import java.util.*;

/**
 * 
 * If you’re really unlucky and the size of the outer collection is a multiple
 * of the size of the inner collection—perhaps because they’re the same
 * collection—the loop will terminate normally, but it won’t do what you want.
 * 
 * Version 1 doesn’t throw an exception, but it prints only the six “doubles”
 * (from “ONE ONE” to “SIX SIX”), instead of the expected thirty-six
 * combinations.
 *
 */

// Same bug as NestIteration.java (but different symptom)!! - Page 213
public class DiceRolls {
	enum Face {
		ONE, TWO, THREE, FOUR, FIVE, SIX
	}

	public static void main(String[] args) {
		// Same bug, different symptom!
		Collection<Face> faces = EnumSet.allOf(Face.class);

		// Version 1
		for (Iterator<Face> i = faces.iterator(); i.hasNext();)
			for (Iterator<Face> j = faces.iterator(); j.hasNext();)
				System.out.println(i.next() + " " + j.next());

		System.out.println("***************************");

		// Version 2
		for (Face f1 : faces)
			for (Face f2 : faces)
				System.out.println(f1 + " " + f2);
	}
}
