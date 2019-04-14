/*
 * Author: Spencer Buchanan
 */

package molecules;

import application.CoordinateCellFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

public class MoleculeView
{
	public static void printMolecule(Molecule molecule)
	{
		System.out.println("Atoms in the molecule " + molecule.moleculeName);

		molecule.atoms.forEach((atom) -> printAtom(atom));
	}

	private static void printAtom(Atom atom)
	{
		System.out.printf("Atom ID:\t%-4s\n", atom.getAtomID());
		System.out.printf("Element:\t%-2s\n", atom.getAtomElement());
		System.out.printf("X Coordinate: \t%10f\n", atom.getAtomX());
		System.out.printf("Y Coordinate: \t%10f\n", atom.getAtomY());
		System.out.printf("Z Coordinate: \t%10f\n", atom.getAtomZ());
		System.out.print("\n");

	}

	@SuppressWarnings("unchecked")
	public static TableView<molecules.Atom> addAtomTable(Molecule molecule)
	{
		TableView<molecules.Atom> moleculeTable = new TableView<molecules.Atom>();

		moleculeTable.setEditable(true);

		// Creates a new column, with type (object, valuetype), and gives the column a
		// title
		TableColumn<molecules.Atom, String> atomIDCol = new TableColumn<molecules.Atom, String>("Atom ID");
		TableColumn<molecules.Atom, String> atomElementCol = new TableColumn<molecules.Atom, String>("Element");

		TableColumn<molecules.Atom, Double> xyzCol // Parent column for XYZ
				= new TableColumn<molecules.Atom, Double>("Coordinates");

		TableColumn<molecules.Atom, Number> atomXCol = new TableColumn<molecules.Atom, Number>("X");
		TableColumn<molecules.Atom, Number> atomYCol = new TableColumn<molecules.Atom, Number>("Y");
		TableColumn<molecules.Atom, Number> atomZCol = new TableColumn<molecules.Atom, Number>("Z");

		atomIDCol.setMinWidth(40);
		atomElementCol.setMinWidth(40);

		// Text Fields (AtomID and Element)

		// Atom ID
		atomIDCol.setCellValueFactory(cellData -> cellData.getValue().atomIDProperty());
		atomIDCol.setCellFactory(TextFieldTableCell.<molecules.Atom>forTableColumn());

		// On Edit Commit (for Atom ID Column)
		atomIDCol.setOnEditCommit((CellEditEvent<molecules.Atom, String> event) -> {
			TablePosition<molecules.Atom, String> pos = event.getTablePosition();

			String newAtomID = event.getNewValue();

			int row = pos.getRow();
			molecules.Atom atom = event.getTableView().getItems().get(row);

			atom.setAtomID(newAtomID);

			molecule.printMolecule();
		});

		// Atom Element
		atomElementCol.setCellValueFactory(cellData -> cellData.getValue().atomElementProperty());
		atomElementCol.setCellFactory(TextFieldTableCell.<molecules.Atom>forTableColumn());

		// On Edit Commit (for Element Column)
		atomElementCol.setOnEditCommit((CellEditEvent<molecules.Atom, String> event) -> {
			TablePosition<molecules.Atom, String> pos = event.getTablePosition();

			String newAtomElement = event.getNewValue();

			int row = pos.getRow();
			molecules.Atom atom = event.getTableView().getItems().get(row);

			atom.setAtomElement(newAtomElement);

			// molecule.printMolecule(); LOOK AT ME!
		});

		// Number Fields (atom x y z)

		/*
		 * This creates a callback for the table item from the class
		 * CoordinateCellFactory, which decides how the cell is rendered. The callback
		 * is typed to the TableColumn and TableCell, and those are typed to
		 * molecules.Atom and Number
		 * 
		 * TODO: Maybe find a better way to format these lines, they ugly and long
		 */
		Callback<TableColumn<molecules.Atom, Number>, TableCell<molecules.Atom, Number>> coordinateColumnFactory = new Callback<TableColumn<molecules.Atom, Number>, TableCell<molecules.Atom, Number>>(){
			@Override
			public TableCell<molecules.Atom, Number> call(TableColumn<molecules.Atom, Number> eggplant)
			{
				return new CoordinateCellFactory();
			}
		};

		// TODO: Find good width for XYZ cells

		// Coordinates Display
		atomXCol.setCellValueFactory(cellData -> cellData.getValue().atomXProperty());
		atomYCol.setCellValueFactory(cellData -> cellData.getValue().atomYProperty());
		atomZCol.setCellValueFactory(cellData -> cellData.getValue().atomZProperty());

		atomXCol.setCellFactory(coordinateColumnFactory);
		atomYCol.setCellFactory(coordinateColumnFactory);
		atomZCol.setCellFactory(coordinateColumnFactory);

		// On Edit Commits
		atomXCol.setOnEditCommit((CellEditEvent<molecules.Atom, Number> event) -> {
			TablePosition<molecules.Atom, Number> pos = event.getTablePosition();

			double newAtomX = event.getNewValue().doubleValue();

			int row = pos.getRow();
			molecules.Atom atom = event.getTableView().getItems().get(row);

			atom.setAtomX(newAtomX);
		});
		atomXCol.setOnEditCommit((CellEditEvent<molecules.Atom, Number> event) -> {
			TablePosition<molecules.Atom, Number> pos = event.getTablePosition();

			double newAtomX = event.getNewValue().doubleValue();

			int row = pos.getRow();
			molecules.Atom atom = event.getTableView().getItems().get(row);

			atom.setAtomX(newAtomX);
		});
		atomYCol.setOnEditCommit((CellEditEvent<molecules.Atom, Number> event) -> {
			TablePosition<molecules.Atom, Number> pos = event.getTablePosition();

			double newAtomY = event.getNewValue().doubleValue();

			int row = pos.getRow();
			molecules.Atom atom = event.getTableView().getItems().get(row);

			atom.setAtomY(newAtomY);
		});
		atomZCol.setOnEditCommit((CellEditEvent<molecules.Atom, Number> event) -> {
			TablePosition<molecules.Atom, Number> pos = event.getTablePosition();

			double newAtomZ = event.getNewValue().doubleValue();

			int row = pos.getRow();
			molecules.Atom atom = event.getTableView().getItems().get(row);

			atom.setAtomZ(newAtomZ);
		});

		// Groups children atomX,Y,ZCol under parent xyzCol
		// warnings, find why suppression needed
		xyzCol.getColumns().addAll(atomXCol, atomYCol, atomZCol);

		// Filling the list
		atomIDCol.setSortType(TableColumn.SortType.DESCENDING);
		moleculeTable.setItems(molecule.getAtomList());

		// Adds columns to table
		// warnings, find why suppression needed
		moleculeTable.getColumns().addAll(atomIDCol, atomElementCol, xyzCol);

		return moleculeTable;
	}
}
