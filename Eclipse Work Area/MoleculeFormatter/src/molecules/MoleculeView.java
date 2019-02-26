package molecules;

import java.util.ArrayList;

public class MoleculeView
{
	public void printAtoms(ArrayList<Atom> atoms)
	{
		System.out.println("Printing atoms...");
		
		
		atoms.forEach((atom) -> printAtom(atom));
	}
	
	private void printAtom(Atom atom)
	{
		System.out.printf("Atom ID:\t%-4s\n", atom.getAtomID());
		System.out.printf("Element:\t%-2s\n", atom.getAtomElement());
		System.out.printf("X Coordinate: \t%10f\n", atom.getAtomX());
		System.out.printf("Y Coordinate: \t%10f\n", atom.getAtomY());
		System.out.printf("Z Coordinate: \t%10f\n", atom.getAtomZ());
		System.out.print("\n");
		
	}
}
