package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;


public class Main extends Application {
	//FIXME: Find why you need the below line for the below bits of code (annotated with a warnings comment)
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) {
		
		TableView<AtomEntry> table = new TableView<AtomEntry>();
		
		
		//Creates a new column, with type (object, valuetype), and gives the column a title
		TableColumn<AtomEntry, String> atomIDCol
			= new TableColumn<AtomEntry, String>("Atom ID");
		TableColumn<AtomEntry, String> atomElementCol
			= new TableColumn<AtomEntry, String>("Element");
		
		TableColumn<AtomEntry, Double> xyzCol					//Parent column for XYZ
			= new TableColumn<AtomEntry, Double>("Coordinates");
		
		TableColumn<AtomEntry, Double> atomXCol
			= new TableColumn<AtomEntry, Double>("X");
		TableColumn<AtomEntry, Double> atomYCol
			= new TableColumn<AtomEntry, Double>("Y");
		TableColumn<AtomEntry, Double> atomZCol
			= new TableColumn<AtomEntry, Double>("Z");
		
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
		
		ObservableList<AtomEntry> list = getAtomList();
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
	
	private ObservableList<AtomEntry> getAtomList()
	{
		AtomEntry atomOne = new AtomEntry("a1", "H", 1.1, 2.2, 3.3);
		AtomEntry atomTwo = new AtomEntry("a2", "C", 1.2, 2.3, 3.4);
		AtomEntry atomThree = new AtomEntry("a3", "O", 1.3, 2.4, 3.5);
		
		ObservableList<AtomEntry> list = FXCollections.observableArrayList(atomOne, atomTwo, atomThree);
		
		return list;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
