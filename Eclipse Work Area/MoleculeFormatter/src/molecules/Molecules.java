package molecules;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javafx.scene.control.TableView;

public class Molecules
{
	protected Map<String, Molecule>		molecules			= new HashMap<String, Molecule>();
	protected Map<String, TableView<Atom>>	moleculeTables		= new HashMap<String, TableView<Atom>>();
	
	protected int nextMoleculeID = 1;
	
	public String createMolecule(String moleculeName)
	{
		Molecule molecule = new Molecule(moleculeName);
		
		String newMoleculesID = Integer.toHexString(nextMoleculeID); 
		molecules.put(newMoleculesID, molecule);
		
		nextMoleculeID++;
		
		return newMoleculesID;
	}
	
	public Molecule getMolecule(String moleculeID)
	{
		return molecules.get(moleculeID);
	}
	
	public void renameMolecule(String moleculeID, String newMoleculeName)
	{
		molecules.get(moleculeID).setMoleculeName(newMoleculeName);
	}
	
	public TableView<Atom> getMoleculeTable(String moleculeID)
	{
		if(moleculeTables.get(moleculeID) == null)
		{
			moleculeTables.put(moleculeID, MoleculeView.addAtomTable(molecules.get(moleculeID)));
		}
		
		return moleculeTables.get(moleculeID);
	}
	
	//Lambda adaptor, passes in a BiConsumer and makes the BiConsumer accept the molecules name and table
	public void forEachMoleculeID(Consumer<? super String> consumer)
	{
		molecules.forEach((moleculeID, molecule) -> {
			consumer.accept(moleculeID);
		});
	}
	
	public void refreshMoleculeTable(String moleculeName)
	{
		moleculeTables.get(moleculeName).setItems(molecules.get(moleculeName).getAtomList());
	}
	
	public void importMolecule(String moleculeID, String filePath)
	{
		molecules.put(moleculeID, new Molecule(molecules.get(moleculeID).getMoleculeName()));
		
		MoleculeImporter.importCmlFile(molecules.get(moleculeID), filePath);
		
		refreshMoleculeTable(moleculeID);
	}
}
