import molecules.Molecule;

public class Main
{

	public static void main(String[] args) {
		Molecule molecule = new Molecule("MoleculeTest");
		
		molecule.importFile("Included");
		molecule.printAtoms();
	}
}
