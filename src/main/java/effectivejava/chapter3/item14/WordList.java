package effectivejava.chapter3.item14;

import java.util.*;

// The benefits of implementing Comparable (Page 66)
public class WordList {
	public static void main(String[] args) {

		// The following program, which relies on the fact that String implements
		// Comparable, prints an alphabetized list of its command-line arguments with
		// duplicates eliminated:

		Set<String> s = new TreeSet<>();
		Collections.addAll(s, "z", "s", "ac", "ab", "ab");
		System.out.println(s);
	}
}
