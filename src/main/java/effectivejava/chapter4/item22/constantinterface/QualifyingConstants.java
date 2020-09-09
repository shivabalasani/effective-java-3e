package effectivejava.chapter4.item22.constantinterface;

import static effectivejava.chapter4.item22.constantutilityclass.PhysicalConstants.*;

//Use of static import to avoid qualifying constants
public class QualifyingConstants {
	double atoms(double mols) {
		return AVOGADROS_NUMBER * mols;
	}
//Many more uses of PhysicalConstants justify static import
}
