/*
 * Author: Spencer Buchanan
 */

package molecules;

public class MoleculeView
{
	public static void printMolecule(Molecule molecule)
	{
		System.out.println("Atoms in the molecule " + molecule.moleculeName);

		molecule.atoms.forEach((atom) -> printAtom(atom));
	}

	private static void printAtom(Atom atom)
	{
		System.out.printf("Atom ID:\t%-4s\n", atom.getAtomID());
		System.out.printf("Element:\t%-2s\n", atom.getAtomElement());
		System.out.printf("X Coordinate: \t%10f\n", atom.getAtomX());
		System.out.printf("Y Coordinate: \t%10f\n", atom.getAtomY());
		System.out.printf("Z Coordinate: \t%10f\n", atom.getAtomZ());
		System.out.print("\n");

	}
}
