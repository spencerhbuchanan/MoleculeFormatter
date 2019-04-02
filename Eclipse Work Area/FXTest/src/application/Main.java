package application;
	
import molecules.Molecule;

import javafx.application.Application;
//import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.util.Callback;
//import javafx.util.StringConverter;
//import javafx.util.converter.DoubleStringConverter;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextField;
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
		
		
		//FIX THIS
		Callback<TableColumn, TableCell> doubleValueCellFactory =
				new Callback<TableColumn, TableCell>(){
					public TableCell call(TableColumn p){
						return new EditingDoubleCell();
					}
		};
		
	//Creates a new column, with type (object, valuetype), and gives the column a title
		TableColumn<molecules.Atom, String> atomIDCol
			= new TableColumn<molecules.Atom, String>("Atom ID");
		TableColumn<molecules.Atom, String> atomElementCol
			= new TableColumn<molecules.Atom, String>("Element");
		
		TableColumn<molecules.Atom, Double> xyzCol					//Parent column for XYZ
			= new TableColumn<molecules.Atom, Double>("Coordinates");
		
		TableColumn<molecules.Atom, Number> atomXCol
			= new TableColumn<molecules.Atom, Number>("X");
		TableColumn<molecules.Atom, Number> atomYCol
			= new TableColumn<molecules.Atom, Number>("Y");
		TableColumn<molecules.Atom, Number> atomZCol
			= new TableColumn<molecules.Atom, Number>("Z");
		
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
		
		//Coordinates Display
		atomXCol.setCellValueFactory(cellData -> cellData.getValue().atomXProperty());
		atomYCol.setCellValueFactory(cellData -> cellData.getValue().atomYProperty());
		atomZCol.setCellValueFactory(cellData -> cellData.getValue().atomZProperty());
		
		atomXCol.setCellFactory(doubleCellFactory);
		
		//On Edit Commits
		
		
	//Groups children atomX,Y,ZCol under parent xyzCol
	//warnings, find why suppression needed
		xyzCol.getColumns().addAll(atomXCol, atomYCol, atomZCol);
		
	//Filling the list
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

	
	//WHAT
	class EditingDoubleCell extends TableCell<molecules.Atom, Number>
	{
		private TextField textField;
		
		public EditingDoubleCell() {}
		
		@Override
		public void startEdit()
		{
			super.startEdit();
			
			if(textField == null)
			{
				createTextField();
			}
			
			setGraphic(textField);
			setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			textField.selectAll();
		}
		
		@Override
		public void cancelEdit()
		{
			super.cancelEdit();
			
			setText(String.valueOf(getItem()));
			setContentDisplay(ContentDisplay.TEXT_ONLY);
		}

		 @Override
	      public void updateItem(Number number, boolean empty) {
	          super.updateItem(number, empty);
	         
	          if (empty) {
	              setText(null);
	              setGraphic(null);
	          } else {
	              if (isEditing()) {
	                  if (textField != null) {
	                      textField.setText(getString());
	                  }
	                  setGraphic(textField);
	                  setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	              } else {
	                  setText(getString());
	                  setContentDisplay(ContentDisplay.TEXT_ONLY);
	              }
	          }
	      }
		
		private void createTextField()
		{
			textField = new TextField(getString());
			textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

			textField.setOnKeyPressed(new EventHandler<KeyEvent>()
			{

				@Override
				public void handle(KeyEvent t)
				{
					if(t.getCode() == KeyCode.ENTER)
					{
						commitEdit(Double.parseDouble(textField.getText()));
					} else if(t.getCode() == KeyCode.ESCAPE)
					{
						cancelEdit();
					}
				}
			});
		}
		
		private String getString()
		{
			return getItem() == null ? "" : getItem().toString();
		}
	}
	
	public static void main(String[] args)
	{	
		launch(args);
	}
}
