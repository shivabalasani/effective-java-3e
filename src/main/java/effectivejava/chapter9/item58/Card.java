package effectivejava.chapter9.item58;

import java.util.*;

/**
 * 
 * The problem in Version 1 is that the next method is called too many times on
 * the iterator for the outer collection (suits). It should be called from the
 * outer loop so that it is called once per suit, but instead it is called from
 * the inner loop, so it is called once per card. After you run out of suits,
 * the loop throws a NoSuchElementException.
 * 
 * If instead you use a nested for-each loop like in Version 3, the problem
 * simply disappears. The resulting code is as succinct as you could wish for:
 * 
 * Unfortunately, there are three common situations where you can’t use
 * for-each:
 * 
 * • Destructive filtering — If you need to traverse a collection removing
 * selected elements, then you need to use an explicit iterator so that you can
 * call its remove method. You can often avoid explicit traversal by using
 * Collection’s removeIf method, added in Java 8.
 * 
 * • Transforming — If you need to traverse a list or array and replace some or
 * all of the values of its elements, then you need the list iterator or array
 * index in order to replace the value of an element.
 * 
 * • Parallel iteration — If you need to traverse multiple collections in
 * parallel, then you need explicit control over the iterator or index variable
 * so that all iterators or index variables can be advanced in lockstep (as
 * demonstrated unintentionally in the buggy card and dice examples).
 * 
 * If you find yourself in any of these situations, use an ordinary for loop and
 * be wary of the traps mentioned in this item.
 *
 */
public class Card {
	private final Suit suit;
	private final Rank rank;

	// Can you spot the bug?
	enum Suit {
		CLUB, DIAMOND, HEART, SPADE
	}

	enum Rank {
		ACE, DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
	}

	static Collection<Suit> suits = Arrays.asList(Suit.values());
	static Collection<Rank> ranks = Arrays.asList(Rank.values());

	Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public static void main(String[] args) {
		List<Card> deck = new ArrayList<>();

		// Version 1
		for (Iterator<Suit> i = suits.iterator(); i.hasNext();)
			for (Iterator<Rank> j = ranks.iterator(); j.hasNext();)
				deck.add(new Card(i.next(), j.next()));

		// Version 2 : Fixed, but ugly - you can do better!
		for (Iterator<Suit> i = suits.iterator(); i.hasNext();) {
			Suit suit = i.next();
			for (Iterator<Rank> j = ranks.iterator(); j.hasNext();)
				deck.add(new Card(suit, j.next()));
		}

		// Version 3 : Preferred idiom for nested iteration on collections and arrays
		for (Suit suit : suits)
			for (Rank rank : ranks)
				deck.add(new Card(suit, rank));
	}
}
