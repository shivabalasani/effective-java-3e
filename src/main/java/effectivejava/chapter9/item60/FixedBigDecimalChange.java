package effectivejava.chapter9.item60;

import java.math.BigDecimal;

/**
 * 
 * There are, however, two disadvantages to using BigDecimal: it’s a lot less
 * convenient than using a primitive arithmetic type, and it’s a lot slower. The
 * latter disadvantage is irrelevant if you’re solving a single short problem,
 * but the former may annoy you.
 *
 */
public class FixedBigDecimalChange {
	public static void main(String[] args) {
		final BigDecimal TEN_CENTS = new BigDecimal(".10");

		int itemsBought = 0;
		BigDecimal funds = new BigDecimal("1.00");
		for (BigDecimal price = TEN_CENTS; funds.compareTo(price) >= 0; price = price.add(TEN_CENTS)) {
			funds = funds.subtract(price);
			itemsBought++;
		}
		System.out.println(itemsBought + " items bought.");
		System.out.println("Money left over: $" + funds);
	}
}
