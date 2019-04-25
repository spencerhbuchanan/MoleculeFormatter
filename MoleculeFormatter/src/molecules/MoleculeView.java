package molecules;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

/**
 * <h1>Molecule View</h1>
 * Generally used to generate views of the molecule in multiple formats
 * 
 * @author Spencer Buchanan
 * @version 0.1
 * @since 2019-21-04
 */
public class MoleculeView {

  /**
   * Prints the passed molecule's ID, element, and coordinates to the console
   * 
   * @param molecule Molecule to print
   */
  public static void printMolecule(MoleculeModel molecule) {
    System.out.println("Atoms in the molecule " + molecule.moleculeName);

    molecule.atoms.forEach((atom) -> printAtom(atom));
  }

  private static void printAtom(Atom atom) {
    System.out.printf("Atom ID:\t%-4s%n", atom.getAtomID());
    System.out.printf("Element:\t%-2s%n", atom.getAtomElement());
    System.out.printf("X Coordinate: \t%10f%n", atom.getAtomX());
    System.out.printf("Y Coordinate: \t%10f%n", atom.getAtomY());
    System.out.printf("Z Coordinate: \t%10f%n", atom.getAtomZ());
    System.out.print("\n");
  }

  /**
   * Creates a table view for the passed molecule
   * 
   * @param molecule    The molecule to generate a table view for
   * @return TableView  The generated TableView
   */
  @SuppressWarnings("unchecked")
  public static TableView<Atom> addAtomTable(MoleculeModel molecule) {
    TableView<Atom> moleculeTable = new TableView<Atom>();

    moleculeTable.setEditable(true);

    // Creates a new column, with type (object, value type), and gives the column a title
    TableColumn<Atom, String> atomIDCol = new TableColumn<Atom, String>("Atom ID");
    TableColumn<Atom, String> atomElementCol = new TableColumn<Atom, String>("Element");

    // Parent column for XYZ coordinates
    TableColumn<Atom, Double> xyzCol = new TableColumn<Atom, Double>("Coordinates");

    TableColumn<Atom, Number> atomXCol = new TableColumn<Atom, Number>("X");
    TableColumn<Atom, Number> atomYCol = new TableColumn<Atom, Number>("Y");
    TableColumn<Atom, Number> atomZCol = new TableColumn<Atom, Number>("Z");

    atomIDCol.setMinWidth(40);
    atomElementCol.setMinWidth(40);

    /*
     * Text Field section, for AtomID and Element cells
     */
    // Atom ID
    atomIDCol.setCellValueFactory(cellData -> cellData.getValue().atomIDProperty());
    atomIDCol.setCellFactory(TextFieldTableCell.<Atom>forTableColumn());

    // On Edit Commit (for Atom ID Column)
    atomIDCol.setOnEditCommit((CellEditEvent<Atom, String> event) -> {
      TablePosition<Atom, String> pos = event.getTablePosition();

      String newAtomID = event.getNewValue();

      int row = pos.getRow();
      Atom atom = event.getTableView().getItems().get(row);

      atom.setAtomID(newAtomID);
    });

    // Atom Element
    atomElementCol.setCellValueFactory(cellData -> cellData.getValue().atomElementProperty());
    atomElementCol.setCellFactory(TextFieldTableCell.<Atom>forTableColumn());

    // On Edit Commit (for Element Column)
    atomElementCol.setOnEditCommit((CellEditEvent<Atom, String> event) -> {
      TablePosition<Atom, String> pos = event.getTablePosition();

      String newAtomElement = event.getNewValue();

      int row = pos.getRow();
      Atom atom = event.getTableView().getItems().get(row);

      atom.setAtomElement(newAtomElement);
    });

    /*
     * Number Field Section, for the XYZ Coordinates
     */
    
    /*
     * This creates a callback for the table item from the class CoordinateCellFactory, which
     * decides how the cell is rendered.
     * 
     * Allows the table cell to be editable with a spinner.
     * 
     * Yes, the line-format is bad. But its a long parameterized callback with a lambda so it needs it space
     */
    Callback<TableColumn<Atom, Number>,
             TableCell<Atom, Number>>
    coordinateColumnFactory = (TableColumn<Atom, Number> egg) -> {
                                                                   return new CoordinateCellFactory();
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
    atomXCol.setOnEditCommit((CellEditEvent<Atom, Number> event) -> {
      TablePosition<Atom, Number> pos = event.getTablePosition();

      double newAtomX = event.getNewValue().doubleValue();

      int row = pos.getRow();
      Atom atom = event.getTableView().getItems().get(row);

      atom.setAtomX(newAtomX);
    });
    atomYCol.setOnEditCommit((CellEditEvent<Atom, Number> event) -> {
      TablePosition<Atom, Number> pos = event.getTablePosition();

      double newAtomY = event.getNewValue().doubleValue();

      int row = pos.getRow();
      Atom atom = event.getTableView().getItems().get(row);

      atom.setAtomY(newAtomY);
    });
    atomZCol.setOnEditCommit((CellEditEvent<Atom, Number> event) -> {
      TablePosition<Atom, Number> pos = event.getTablePosition();

      double newAtomZ = event.getNewValue().doubleValue();

      int row = pos.getRow();
      Atom atom = event.getTableView().getItems().get(row);

      atom.setAtomZ(newAtomZ);
    });

    // Groups children atomX,Y, Z Columns under parent XYZ Column
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
