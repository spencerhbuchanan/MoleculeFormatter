/*
 * Author: Spencer Buchanan
 * 
 * Desc: Contains atoms that make up a molecule
 * 
 * Stores: atoms boundAtoms bondOrders
 * 
 */

package molecules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Molecule
{
	protected String				moleculeName;

	protected ArrayList<Atom>		atoms		= new ArrayList<Atom>();

	protected Map<String, Integer>	atomMap		= new HashMap<String, Integer>();

	protected ArrayList<String>		boundAtoms	= new ArrayList<String>();
	protected ArrayList<Short>		bondOrder		= new ArrayList<Short>();

	public Molecule(String moleculeName)
	{
		this.moleculeName = moleculeName;
	}

	public Molecule()
	{
		this.moleculeName = "unnamedMolecule";
	}

	/*
	 * Basic Constructor
	 * 
	 * Used when: Only atomID is given
	 * 
	 * First a new atom object is created, then the ID passed to the method
	 * is put into the newest atom object (at the end of the atoms ArrayList)
	 */
	public void addAtom(String atomID)
	{
		this.atoms.add(new Atom());
		this.atoms.get(atoms.size()).setAtomID(atomID);

		this.refreshAtomicMap();
	}

	
	/*
	 * Extended Constructor
	 * 
	 * Used when: Atom ID, element, and coordinates are given
	 * 
	 * First a new atom object is created, and the index of the newest element
	 * is stored (at the end, so index = sizeOf(atoms)). Then, the information
	 * passed to the constructor is transfered to the Atom object.
	 */
	public void addAtom(String atomID, String atomElement, double atomXCoordinate, double atomYCoordinate, double atomZCoordinate)
	{
		this.atoms.add(new Atom());

		int newestItemIndex = this.atoms.size() - 1;

		/*
		 * Fills the newly created atom object with the data passed to this method
		 */
		this.atoms.get(newestItemIndex).setAtomID(atomID);
		this.atoms.get(newestItemIndex).setAtomElement(atomElement);
		this.atoms.get(newestItemIndex).setAtomX(atomXCoordinate);
		this.atoms.get(newestItemIndex).setAtomY(atomYCoordinate);
		this.atoms.get(newestItemIndex).setAtomZ(atomZCoordinate);

		this.refreshAtomicMap();
	}

	
	/*
	 * TODO: Add bond storage into the Atom class.
	 */
	public void addBond(String boundAtoms, short bondOrder)
	{
		this.boundAtoms.add(boundAtoms);
		this.bondOrder.add(bondOrder);
	}

	/*
	 * Refreshes a map which is used to access atoms via their ID's
	 */
	private void refreshAtomicMap()
	{
		for(int atomArrayIndex = 0; atomArrayIndex < atoms.size(); atomArrayIndex++)
		{
			String atomID = atoms.get(atomArrayIndex).getAtomID();

			if(atomMap.containsKey(atomID)) // If mapping is present
			{
				if(atomMap.get(atomID) == atomArrayIndex) // If correctly mapped
				{
					System.out.println("atomID mapped and correct index" + "\tatomID:" + atomID + "\tindex:" + atomArrayIndex);
					continue;
				} else
				{ // If mapped but incorrectly, re-map
					atomMap.remove(atomID);
					atomMap.put(atomID, atomArrayIndex);
					System.out.println("atomID remapped" + "\tatomID:" + atomID + "\tindex:" + atomArrayIndex);
					continue;
				}

			} else
			{ // If mapping not present, map
				System.out.println("atomID not mapped. Adding." + "\tatomID:" + atomID + "\tindex:" + atomArrayIndex);
				atomMap.putIfAbsent(atomID, atomArrayIndex); // Avoid overwriting JIC

				continue;
			}
		}
	}

	/*
	 * FIXME Temporary function, only for testing
	 */
	public void printMolecule()
	{
		MoleculeView.printMolecule(this);
	}

	public void importFile(String filePath)
	{
		MoleculeImporter.importCmlFile(this, filePath);
	}
}
