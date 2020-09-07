package effectivejava.chapter2.item2.builder;

// Builder Pattern  (Page 13)
/**
 * 
 * Item 2: Consider a builder when faced with many constructor parameters
 * 
 * - The telescoping constructor pattern works, but it is hard to write client
 * code when there are many parameters, and harder still to read it. - A second
 * alternative when you’re faced with many optional parameters in a constructor
 * is the JavaBeans pattern,
 *
 * - The Builder pattern simulates named optional parameters as found in Python
 * and Scala. - The Builder pattern is well suited to class hierarchies.
 * 
 * - The Builder pattern is a good choice when designing classes whose
 * constructors or static factories would have more than a handful of
 * parameters.
 *
 */
public class NutritionFacts {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    public static class Builder {
	// Required parameters
	private final int servingSize;
	private final int servings;

	// Optional parameters - initialized to default values
	private int calories = 0;
	private int fat = 0;
	private int sodium = 0;
	private int carbohydrate = 0;

	public Builder(int servingSize, int servings) {
	    this.servingSize = servingSize;
	    this.servings = servings;
	}

	public Builder calories(int val) {
	    calories = val;
	    return this;
	}

	public Builder fat(int val) {
	    fat = val;
	    return this;
	}

	public Builder sodium(int val) {
	    sodium = val;
	    return this;
	}

	public Builder carbohydrate(int val) {
	    carbohydrate = val;
	    return this;
	}

	public NutritionFacts build() {
	    return new NutritionFacts(this);
	}
    }

    private NutritionFacts(Builder builder) {
	servingSize = builder.servingSize;
	servings = builder.servings;
	calories = builder.calories;
	fat = builder.fat;
	sodium = builder.sodium;
	carbohydrate = builder.carbohydrate;
    }

    public static void main(String[] args) {
	NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8).calories(100).sodium(35).carbohydrate(27).build();
    }
}