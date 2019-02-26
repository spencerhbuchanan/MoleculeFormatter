package molecules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Molecule
{
	private String				moleculeName;
	
	private ArrayList<Atom> atoms = new ArrayList<Atom>();
	
	Map<String, Integer> atomMap = new HashMap<String, Integer>();
	
	private ArrayList<String> boundAtoms = new ArrayList<String>();
	private ArrayList<Short> bondOrder = new ArrayList<Short>();

	public Molecule(String moleculeName)
	{
		this.moleculeName = moleculeName;
	}

	public Molecule()
	{
		this.moleculeName = "unnamedMolecule";
	}

	public void addAtom(String atomID)
	{
		this.atoms.add(new Atom());
		this.atoms.get(atoms.size()).setAtomID(atomID);
	}

	public void addAtom(String atomID, String atomElement, double atomXCoordinate, double atomYCoordinate, double atomZCoordinate)
	{
		this.atoms.add(new Atom());
		
		int newestItemIndex = this.atoms.size() - 1;
		
		this.atoms.get(newestItemIndex).setAtomID(atomID);
		this.atoms.get(newestItemIndex).setAtomElement(atomElement);
		this.atoms.get(newestItemIndex).setAtomX(atomXCoordinate);
		this.atoms.get(newestItemIndex).setAtomY(atomYCoordinate);
		this.atoms.get(newestItemIndex).setAtomZ(atomZCoordinate);
	}
	
	public void refreshAtomicMap()
	{
		for(int i = 0; i < atoms.size(); i++)
		{
			String atomID = atoms.get(i).getAtomID();
		}
	}
	
	

}
