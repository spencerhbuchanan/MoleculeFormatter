/*
 * Author: Spencer Buchanan
 * 
 * Desc: Contains atoms that make up a molecule
 * 
 * Stores: atoms boundAtoms bondOrders
 * 
 * Methods: addAtom addBond importFile printMolecule
 * 
 */

package molecules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Molecule {
  protected String moleculeName;

  protected ArrayList<Atom> atoms = new ArrayList<Atom>();

  protected Map<String, Integer> atomMap = new HashMap<String, Integer>();

  protected ArrayList<String> boundAtoms = new ArrayList<String>();
  protected ArrayList<Short> bondOrder = new ArrayList<Short>();

  public Molecule(String moleculeName) {
    this.moleculeName = moleculeName;
  }

  public Molecule() {
    this.moleculeName = "unnamedMolecule";
  }

  public void addAtom(String atomID) {
    this.atoms.add(new Atom());
    this.atoms.get(atoms.size()).setAtomID(atomID);

    this.refreshAtomicMap();
  }

  public void addAtom(String atomID, String atomElement, double atomXCoordinate,
      double atomYCoordinate, double atomZCoordinate) {
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

  public void addBond(String boundAtoms, short bondOrder) {
    this.boundAtoms.add(boundAtoms);
    this.bondOrder.add(bondOrder);
  }

  /*
   * Refreshes a map which is used to access atoms via their ID's
   */
  private void refreshAtomicMap() {
    for (int atomArrayIndex = 0; atomArrayIndex < atoms.size(); atomArrayIndex++) {
      String atomID = atoms.get(atomArrayIndex).getAtomID();

      if (atomMap.containsKey(atomID)) // If mapping is present
      {
        if (atomMap.get(atomID) == atomArrayIndex) // If correctly mapped
        {
          System.out.println("atomID mapped and correct index" + "\tatomID:" + atomID + "\tindex:"
              + atomArrayIndex);
          continue;
        } else { // If mapped but incorrectly, re-map
          atomMap.remove(atomID);
          atomMap.put(atomID, atomArrayIndex);
          System.out
              .println("atomID remapped" + "\tatomID:" + atomID + "\tindex:" + atomArrayIndex);
          continue;
        }

      } else { // If mapping not present, map
        System.out.println(
            "atomID not mapped. Adding." + "\tatomID:" + atomID + "\tindex:" + atomArrayIndex);
        atomMap.putIfAbsent(atomID, atomArrayIndex); // Avoid overwriting JIC

        continue;
      }
    }
  }

  public void printMolecule() {
    MoleculeView.printMolecule(this);
  }

  public void importFile(String filePath) {
    MoleculeImporter.importCmlFile(this, filePath);
  }
}
