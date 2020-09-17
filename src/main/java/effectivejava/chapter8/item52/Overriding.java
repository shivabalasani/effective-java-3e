package effectivejava.chapter8.item52;

import java.util.List;

/**
 * 
 * As a reminder, a method is overridden when a subclass contains a method
 * declaration with the same signature as a method declaration in an ancestor.
 * If an instance method is overridden in a subclass and this method is invoked
 * on an instance of the subclass, the subclass’s overriding method executes,
 * regardless of the compile time type of the subclass instance.
 * 
 * Because overriding is the norm and overloading is the exception, overriding
 * sets people’s expectations for the behavior of method invocation.Therefore
 * you should avoid confusing uses of overloading.
 * 
 * A safe, conservative policy is never to export two overloadings with the same
 * number of parameters. If a method uses varargs, a conservative policy is not
 * to overload it at all, except as described in Item 53. If you adhere to these
 * restrictions, programmers will never be in doubt as to which overloading
 * applies to any set of actual parameters. These restrictions are not terribly
 * onerous because you can always give methods different names instead of
 * overloading them.
 * 
 * For constructors, you don’t have the option of using different names:
 * multiple constructors for a class are always overloaded. You do, in many
 * cases, have the option of exporting static factories instead of constructors
 * (Item 1). Also, with constructors you don’t have to worry about interactions
 * between overloading and overriding, because constructors can’t be overridden.
 * You will probably have occasion to export multiple constructors with the same
 * number of parameters, so it pays to know how to do it safely.
 *
 */

//Classification using method overrriding (Page 239)
class Champagne extends SparklingWine {
	@Override
	String name() {
		return "champagne";
	}
}

//Classification using method overrriding (Page 239)
class SparklingWine extends Wine {
	@Override
	String name() {
		return "sparkling wine";
	}
}

//Classification using method overrriding (Page 239)
class Wine {
	String name() {
		return "wine";
	}
}

// Classification using method overrriding (Page 239)
public class Overriding {
	public static void main(String[] args) {
		List<Wine> wineList = List.of(new Wine(), new SparklingWine(), new Champagne());

		for (Wine wine : wineList)
			System.out.println(wine.name());
	}
}
