package effectivejava.chapter4.item22.constantinterface;

/**
 * 
 * Item 22: Use interfaces only to define types
 * 
 * When a class implements an interface, the interface serves as a type that can
 * be used to refer to instances of the class. That a class implements an
 * interface should therefore say something about what a client can do with
 * instances of the class. It is inappropriate to define an interface for any
 * other purpose.
 * 
 * One kind of interface that fails this test is the so-called constant
 * interface. Such an interface contains no methods; it consists solely of
 * static final fields, each exporting a constant.The constant interface pattern
 * is a poor use of interfaces.Implementing a constant interface causes this
 * implementation detail to leak into the class�s exported API.
 * 
 * There are several constant interfaces in the Java platform libraries, such as
 * java.io.ObjectStreamConstants. These interfaces should be regarded as
 * anomalies and should not be emulated.
 * 
 * If you want to export constants, there are several reasonable choices. If the
 * constants are strongly tied to an existing class or interface, you should add
 * them to the class or interface. For example, all of the boxed numerical
 * primitive classes, such as Integer and Double, export MIN_VALUE and MAX_VALUE
 * constants. If the constants are best viewed as members of an enumerated type,
 * you should export them with an enum type (Item 34). Otherwise, you should
 * export the constants with a noninstantiable utility class (Item 4).
 * 
 * In summary, interfaces should be used only to define types. They should not
 * be used merely to export constants.
 *
 */
// Constant interface antipattern - do not use!
public interface PhysicalConstants {
	// Avogadro's number (1/mol)
	static final double AVOGADROS_NUMBER = 6.022_140_857e23;

	// Boltzmann constant (J/K)
	static final double BOLTZMANN_CONSTANT = 1.380_648_52e-23;

	// Mass of the electron (kg)
	static final double ELECTRON_MASS = 9.109_383_56e-31;
}
