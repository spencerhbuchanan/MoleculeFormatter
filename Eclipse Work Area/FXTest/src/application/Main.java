package application;
	
import molecules.Molecule;

import javafx.application.Application;
//import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.Stage;
//import javafx.util.StringConverter;
//import javafx.util.converter.DoubleStringConverter;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;


public class Main extends Application
{
	
	//FIXME: Find why you need the below line for the below bits of code (annotated with a warnings comment)
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage)
	{
		Molecule molecule = new Molecule("Janet");
		molecule.importFile("C:\\Users\\shbuchanan3669\\Documents\\GitHub\\MoleculeFormatter\\Cation.cml");
		
		TableView<molecules.Atom> table = new TableView<molecules.Atom>();
		
		table.setEditable(true);
		
	//Creates a new column, with type (object, valuetype), and gives the column a title
		TableColumn<molecules.Atom, String> atomIDCol
			= new TableColumn<molecules.Atom, String>("Atom ID");
		TableColumn<molecules.Atom, String> atomElementCol
			= new TableColumn<molecules.Atom, String>("Element");
		
		TableColumn<molecules.Atom, Double> xyzCol					//Parent column for XYZ
			= new TableColumn<molecules.Atom, Double>("Coordinates");
		
		TableColumn<molecules.Atom, Double> atomXCol
			= new TableColumn<molecules.Atom, Double>("X");
		TableColumn<molecules.Atom, Double> atomYCol
			= new TableColumn<molecules.Atom, Double>("Y");
		TableColumn<molecules.Atom, Double> atomZCol
			= new TableColumn<molecules.Atom, Double>("Z");
		
		atomIDCol.setMinWidth(40);
		atomElementCol.setMinWidth(40);
		
	//Text Fields (AtomID and Element)
		
		//Atom ID
		atomIDCol.setCellValueFactory(cellData -> cellData.getValue().atomIDProperty());
		atomIDCol.setCellFactory(TextFieldTableCell.<molecules.Atom> forTableColumn());
		
		//On Edit Commit (for Atom ID Column)
		atomIDCol.setOnEditCommit((CellEditEvent<molecules.Atom, String> event) -> {
			TablePosition<molecules.Atom, String> pos = event.getTablePosition();
			
			String newAtomID = event.getNewValue();
			
			int row = pos.getRow();
			molecules.Atom atom = event.getTableView().getItems().get(row);
			
			atom.setAtomID(newAtomID);
		});
		
		//Atom Element
		atomElementCol.setCellValueFactory(cellData -> cellData.getValue().atomElementProperty());
		atomElementCol.setCellFactory(TextFieldTableCell.<molecules.Atom> forTableColumn());
		
		//On Edit Commit (for Element Column)
		atomElementCol.setOnEditCommit((CellEditEvent<molecules.Atom, String> event) -> {
			TablePosition<molecules.Atom, String> pos = event.getTablePosition();
			
			String newAtomElement = event.getNewValue();
			
			int row = pos.getRow();
			molecules.Atom atom = event.getTableView().getItems().get(row);
			
			atom.setAtomElement(newAtomElement);
			
			//molecule.printMolecule(); LOOK AT ME!
		});
		
	//Number Fields (atom x y z)
		
		//Atom X
		//atomXCol.setCellValueFactory(new PropertyValueFactory<>("atomX"));
		//atomXCol.setCellFactory(TextFieldTableCell.<Atom, Double>forTableColumn(new DoubleStringConverter()));
		
		
	//Groups children atomX,Y,ZCol under parent xyzCol
	//warnings, find why suppression needed
		xyzCol.getColumns().addAll(atomXCol, atomYCol, atomZCol);
		
	//How to fill cells (which variables to use)
		
		//REMOVING THESE
		atomXCol.setCellValueFactory(new PropertyValueFactory<>("atomX"));
		atomYCol.setCellValueFactory(new PropertyValueFactory<>("atomY"));
		atomZCol.setCellValueFactory(new PropertyValueFactory<>("atomZ"));
		
		atomIDCol.setSortType(TableColumn.SortType.DESCENDING);
		
		ObservableList<molecules.Atom> list = molecule.getAtomList();
		table.setItems(list);
		
	//Adds columns to table
	//warnings, find why suppression needed
		table.getColumns().addAll(atomIDCol, atomElementCol, xyzCol);
		
		StackPane root = new StackPane();
		root.setPadding(new Insets(5));
		root.getChildren().add(table);
		
		primaryStage.setTitle("Atom Table");
		
		Scene scene = new Scene(root, 450, 300);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void main(String[] args)
	{	
		launch(args);
	}
}
