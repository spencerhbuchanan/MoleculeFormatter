package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;


public class Main extends Application {
	//FIXME: Find why you need the below line for the below bits of code (annotated with a warnings comment)
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) {
		
		TableView<Atom> table = new TableView<Atom>();
		
		table.setEditable(true);
		
	//Creates a new column, with type (object, valuetype), and gives the column a title
		TableColumn<Atom, String> atomIDCol
			= new TableColumn<Atom, String>("Atom ID");
		TableColumn<Atom, String> atomElementCol
			= new TableColumn<Atom, String>("Element");
		
		TableColumn<Atom, Double> xyzCol					//Parent column for XYZ
			= new TableColumn<Atom, Double>("Coordinates");
		
		TableColumn<Atom, Double> atomXCol
			= new TableColumn<Atom, Double>("X");
		TableColumn<Atom, Double> atomYCol
			= new TableColumn<Atom, Double>("Y");
		TableColumn<Atom, Double> atomZCol
			= new TableColumn<Atom, Double>("Z");
		
		atomIDCol.setMinWidth(40);
		atomElementCol.setMinWidth(40);
		
	//Text Fields (AtomID and Element)
		
		//Atom ID
		atomIDCol.setCellValueFactory(new PropertyValueFactory<>("atomID"));
		atomIDCol.setCellFactory(TextFieldTableCell.<Atom> forTableColumn());
		
		//On Edit Commit (for Atom ID Column)
		atomIDCol.setOnEditCommit((CellEditEvent<Atom, String> event) -> {
			TablePosition<Atom, String> pos = event.getTablePosition();
			
			String newAtomID = event.getNewValue();
			
			int row = pos.getRow();
			Atom atom = event.getTableView().getItems().get(row);
			
			atom.setAtomID(newAtomID);
		});
		
		//Atom Element
		atomElementCol.setCellValueFactory(new PropertyValueFactory<>("atomElement"));
		atomElementCol.setCellFactory(TextFieldTableCell.<Atom> forTableColumn());
		
		//On Edit Commit (for Element Column)
		atomElementCol.setOnEditCommit((CellEditEvent<Atom, String> event) -> {
			TablePosition<Atom, String> pos = event.getTablePosition();
			
			String newAtomElement = event.getNewValue();
			
			int row = pos.getRow();
			Atom atom = event.getTableView().getItems().get(row);
			
			atom.setAtomElement(newAtomElement);
			
		});
		
	//Number Fields (atom x y z)
		
		//Atom X
		//////////WORKING HERE/////////////
		
		
		
	//Groups children atomX,Y,ZCol under parent xyzCol
	//warnings, find why suppression needed
		xyzCol.getColumns().addAll(atomXCol, atomYCol, atomZCol);
		
	//How to fill cells (which variables to use)
		atomIDCol.setCellValueFactory(new PropertyValueFactory<>("atomID"));
		atomElementCol.setCellValueFactory(new PropertyValueFactory<>("atomElement"));
		
		atomXCol.setCellValueFactory(new PropertyValueFactory<>("atomX"));
		atomYCol.setCellValueFactory(new PropertyValueFactory<>("atomY"));
		atomZCol.setCellValueFactory(new PropertyValueFactory<>("atomZ"));
		
		atomIDCol.setSortType(TableColumn.SortType.DESCENDING);
		
		ObservableList<Atom> list = getAtomList();
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
	
	private ObservableList<Atom> getAtomList()
	{
		Atom atomOne = new Atom("a1", "H", 1.1, 2.2, 3.3);
		Atom atomTwo = new Atom("a2", "C", 1.2, 2.3, 3.4);
		Atom atomThree = new Atom("a3", "O", 1.3, 2.4, 3.5);
		
		ObservableList<Atom> list = FXCollections.observableArrayList(atomOne, atomTwo, atomThree);
		
		return list;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
