package molecules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * <h1>Molecule Class</h1> Acts as a container for multiple Atoms
 * 
 * @author Spencer Buchanan
 * @version 0.1
 * @since 2019-21-04
 */
public class Molecule
{
	protected StringProperty			moleculeName	= new SimpleStringProperty();

	protected ArrayList<Atom>		atoms		= new ArrayList<Atom>();

	protected Map<String, Integer>	atomMap		= new HashMap<String, Integer>();

	protected ArrayList<String>		boundAtoms	= new ArrayList<String>();
	protected ArrayList<Short>		bondOrder		= new ArrayList<Short>();

	/**
	 * Molecule Constructor with name provided
	 * 
	 * @param moleculeName
	 */
	public Molecule(String moleculeName)
	{
		this.moleculeName.set(moleculeName);
	}

	/**
	 * Molecule Constructor with no name provided
	 */
	public Molecule()
	{
		this.moleculeName.set("unnamedMolecule");
	}

	/**
	 * Gets the molecule's name
	 * 
	 * @return String Name of the molecule
	 */
	public String getMoleculeName()
	{
		return moleculeName.get();
	}

	/**
	 * Sets the molecule's name
	 * 
	 * @param newMoleculeName
	 */
	public void setMoleculeName(String newMoleculeName)
	{
		moleculeName.set(newMoleculeName);
	}

	/**
	 * Adds an atom to the molecule, only specifying its ID
	 * 
	 * @param atomID
	 */
	public void addAtom(String atomID)
	{
		this.atoms.add(new Atom());
		this.atoms.get(atoms.size()).setAtomID(atomID);

		this.refreshAtomicMap();
	}

	/**
	 * Adds an atom to the molecule, specifying its ID, Element, and coordinates
	 * 
	 * @param atomID
	 * @param atomElement
	 * @param atomXCoordinate
	 * @param atomYCoordinate
	 * @param atomZCoordinate
	 */
	public void addAtom(String atomID,
					String atomElement,
					double atomXCoordinate,
					double atomYCoordinate,
					double atomZCoordinate)
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

	/**
	 * Adds a bond to the molecule, specifying the bound atoms and the bond order
	 * <p>
	 * <b>FUNCTIONALITY UNIMPLEMENTED. ONLY STORES THE BONDS.</b>
	 * 
	 * @param boundAtoms The bonded atoms, specified by their ID's
	 * @param bondOrder  The order of the bond (Often from 1 to 3)
	 */
	public void addBond(String boundAtoms, short bondOrder)
	{
		this.boundAtoms.add(boundAtoms);
		this.bondOrder.add(bondOrder);
	}

	/**
	 * Refreshes a map which can be used to access atoms by their ID
	 * <p>
	 * <b>MAY BE REMOVED IN FUTURE</b>
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
					System.out.println("atomID mapped and correct index" + "\tatomID:"
									+ atomID
									+ "\tindex:"
									+ atomArrayIndex);
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

	/**
	 * Returns an ObservableList of the atoms array list
	 * 
	 * @return ObservableList Observable list of the atoms in the molecule
	 */
	public ObservableList<Atom> getAtomList()
	{
		ObservableList<Atom> observableAtoms = FXCollections.observableArrayList(this.atoms);

		return observableAtoms;
	}

}
