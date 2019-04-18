package molecules;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

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
	
	public void renameMolecule(String oldMoleculeName, String newMoleculeName)
	{
		molecules.get(oldMoleculeName).moleculeName.set(newMoleculeName);
		
		/* Re-keys the molecule maps
		 * The second parameter of .put can be .remove since .remove returns the old
		 * mapped value(object)
		 */
		
		molecules.put(newMoleculeName, molecules.remove(oldMoleculeName));
		moleculeTables.put(newMoleculeName, moleculeTables.remove(oldMoleculeName));
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
	
	//Lambda adaptor, passes in a BiConsumer and makes the BiConsumer accept the molecules name and table
	public void forEachMoleculeTable(BiConsumer<? super String, ? super TableView<Atom>> consumer)
	{
		molecules.forEach((moleculeName, molecule) -> {
			consumer.accept(moleculeName, getMoleculeTable(moleculeName));
		});
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
