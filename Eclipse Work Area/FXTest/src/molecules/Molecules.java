package molecules;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.TableView;

public class Molecules
{
	protected Map<String, Molecule>		molecules			= new HashMap<String, Molecule>();
	protected Map<String, TableView<Atom>>	moleculeTables		= new HashMap<String, TableView<Atom>>();
	
	public void createMolecule(String moleculeName)
	{
		Molecule molecule = new Molecule(moleculeName);
		molecules.put(moleculeName, molecule);
	}
	
	public Molecule getMolecule(String moleculeName)
	{
		return molecules.get(moleculeName);
	}
	
	public TableView<Atom> getMoleculeTable(String moleculeName)
	{
		if(!moleculeTables.containsKey(moleculeName))
		{
			TableView<Atom> tableView = MoleculeView.addAtomTable(molecules.get(moleculeName));
			moleculeTables.put(moleculeName, tableView);
		}
		
		return moleculeTables.get(moleculeName);
	}
	
	public void refreshMoleculeTable(String moleculeName)
	{
		moleculeTables.get(moleculeName).setItems(molecules.get(moleculeName).getAtomList());
	}
	
	public void importMolecule(String toMolecule, String filePath)
	{
		//TODO: ALWAYS OVERRITES MOLECULE WITH A NEW MOLECULE OBJECT OF THE SAME NAME
		molecules.put(toMolecule, new Molecule(toMolecule));	//Overwrites previous Molecule reference with a new Molecule reference
		
		//By this point, the Molecule references should be clean. AKA: Fresh Molecule
		//Additionally, the old Molecule should be picked up by Garbage Collection
		
		MoleculeImporter.importCmlFile(molecules.get(toMolecule), filePath);
		
		refreshMoleculeTable(toMolecule);					//Refreshed the table with the NEWLY IMPORTED molecule
	}
}
