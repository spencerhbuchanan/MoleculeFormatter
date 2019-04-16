package molecules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.TableView;

public class Molecules
{
	//TODO: Decide necessity of the array lists (since the maps refer to the objects as well)
	private ArrayList<Molecule> 			molecules				= new ArrayList<Molecule>();
	private ArrayList<TableView<Atom>>		moleculeTable 			= new ArrayList<TableView<Atom>>();
	
	protected Map<String, Molecule>		moleculesMap			= new HashMap<String, Molecule>();
	protected Map<String, TableView<Atom>>	moleculeTableMap		= new HashMap<String, TableView<Atom>>();
	
	public void createMolecule(String moleculeName)
	{
		Molecule molecule = new Molecule(moleculeName);
		molecules.add(molecule);
		moleculesMap.put(moleculeName, molecule);
	}
	
	public Molecule getMolecule(String moleculeName)
	{
		return moleculesMap.get(moleculeName);
	}
	
	public TableView<Atom> getMoleculeTable(String moleculeName)
	{
		if(!moleculeTableMap.containsKey(moleculeName))
		{
			TableView<Atom> tableView = MoleculeView.addAtomTable(moleculesMap.get(moleculeName));
			moleculeTable.add(tableView);
			moleculeTableMap.put(moleculeName, tableView);
		}
		
		return moleculeTableMap.get(moleculeName);
	}
	
	public void refreshMoleculeTable(String moleculeName)
	{
		moleculeTableMap.get(moleculeName).setItems(moleculesMap.get(moleculeName).getAtomList());
	}
	
	public void importMolecule(String toMolecule, String filePath)
	{
		//TODO: ALWAYS OVERRITES MOLECULE WITH A NEW MOLECULE OBJECT OF THE SAME NAME
		Molecule molecule = moleculesMap.get(toMolecule);		//Transfers reference to variable
		molecules.remove(molecule);						//Removes reference in ArrayList
		molecule = new Molecule(toMolecule);				//Puts a new Molecule reference into molecule of the same name
		molecules.add(molecule);							//Copies new Molecule reference into molecules ArrayList
		moleculesMap.put(toMolecule, molecule);				//Overwrites previous Molecule reference with the new Molecule reference
		
		//By this point, the Molecule references should be clean. AKA: Fresh Molecule
		
		MoleculeImporter.importCmlFile(moleculesMap.get(toMolecule), filePath);
		
		refreshMoleculeTable(toMolecule);					//Refreshed the table with the NEWLY IMPORTED molecule
	}
}
