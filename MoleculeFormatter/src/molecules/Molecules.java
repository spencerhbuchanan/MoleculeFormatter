package molecules;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javafx.scene.control.TableView;

/**
 * <h1>Molecules Class</h1> 
 * This class acts as a facade/controller between the program and the storage of molecules.
 * 
 * TODO: See if ImportMolecule needs to be in here, or if it can be static like the exporter.
 * @author Spencer Buchanan
 * @version 0.1
 * @since 2019-21-04
 */
public class Molecules {
  protected Map<String, MoleculeModel> molecules = new HashMap<String, MoleculeModel>();
  protected Map<String, TableView<Atom>> moleculeTables = new HashMap<String, TableView<Atom>>();

  // Tracking/incrementing variable for fresh moleculeID's
  protected int nextMoleculeID = 1;

  /**
   * Creates a new Molecule with the specified name.
   * 
   * @param moleculeName
   * @return String The ID of the newly created Molecule
   */
  public String createMolecule(String moleculeName) {
    MoleculeModel molecule = new MoleculeModel(moleculeName);

    String newMoleculesID = Integer.toHexString(nextMoleculeID); // Made hex because it neat and I can.
    molecules.put(newMoleculesID, molecule);

    // Increments nextMoleculeID so that its value is new for the next molecule
    nextMoleculeID++;

    return newMoleculesID;
  }

  /**
   * Gets a Molecule with the corresponding ID
   * 
   * @param moleculeID
   * @return Molecule The Molecule with the specified ID
   */
  public MoleculeModel getMolecule(String moleculeID) {
    return molecules.get(moleculeID);
  }

  /**
   * Renames the Molecule with the corresponding ID
   * 
   * @param moleculeID
   * @param newMoleculeName
   */
  public void renameMolecule(String moleculeID, String newMoleculeName) {
    molecules.get(moleculeID).setMoleculeName(newMoleculeName);
  }

  /**
   * Gets the TableView of the Molecule with the corresponding ID
   * 
   * @param moleculeID
   * @return TableView The TableView of the Molecule
   */
  public TableView<Atom> getMoleculeTable(String moleculeID) {
    // Creates a new TableView for the specified molecule if it does not exist yet
    if (moleculeTables.get(moleculeID) == null) {
      moleculeTables.put(moleculeID, MoleculeView.addAtomTable(molecules.get(moleculeID)));
    }

    return moleculeTables.get(moleculeID);
  }

  /**
   * Performs the given action for each moleculeID stored
   * 
   * @param action A lambda which uses each molecule's ID
   */
  public void forEachMoleculeID(Consumer<? super String> action) {
    molecules.forEach((moleculeID, molecule) -> {
      action.accept(moleculeID);
    });
  }

  /**
   * Refreshes the Molecule with the corresponding ID's table
   * 
   * @param moleculeID
   */
  private void refreshMoleculeTable(String moleculeID) {
    // Creates a new TableView for the specified molecule if it does not exist yet
    if (moleculeTables.get(moleculeID) == null) {
      moleculeTables.put(moleculeID, MoleculeView.addAtomTable(molecules.get(moleculeID)));
    }

    moleculeTables.get(moleculeID).setItems(molecules.get(moleculeID).getAtomList());
  }

  /**
   * Imports a molecule file at the specified location into the Molecule with the corresponding ID
   * 
   * CURRENTLY ONLY SUPPORTS .CML
   * 
   * @param moleculeID
   * @param fileToImport
   */
  public void importMolecule(String moleculeID, File fileToImport) {
    //Overwrites the currently stored molecule with a fresh (empty) molecule object.
    molecules.put(moleculeID, new MoleculeModel(molecules.get(moleculeID).getMoleculeName()));

    MoleculeImporter.importCmlFile(molecules.get(moleculeID), fileToImport);

    refreshMoleculeTable(moleculeID);
  }
}
