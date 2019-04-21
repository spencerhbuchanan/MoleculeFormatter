package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import molecules.Molecules;

public class WindowControls
{
	static Stage stage;
	static Molecules molecules;
	
	static StringProperty currentlySelectedMolecule = new SimpleStringProperty();
	static TabPane moleculesTabPane;
	
	public static void setStage(Stage stage)
	{
		WindowControls.stage = stage;
	}
	
	public static void setMolecules(Molecules molecules)
	{
		WindowControls.molecules = molecules;
	}
	
	public static MenuBar createToolbar()
	{
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
		
		MenuItem newItem = new MenuItem("New Molecule");	//UNIMPLEMENTED
		MenuItem saveItem = new MenuItem("Save");		//UNIMPLEMENTED
		MenuItem importItem = new MenuItem("Import");
		MenuItem exportItem = new MenuItem("Export");		//UNIMPLEMENTED

		newItem.setOnAction((event) -> {
			molecules.createMolecule("New Molecule");
			
			createMoleculeTab("New Molecule", molecules.getMoleculeTable("New Molecule"));
			
			moleculesTabPane.getSelectionModel().selectLast();
		});
		
		importItem.setOnAction((event) -> {
			if(moleculesTabPane.getSelectionModel().getSelectedItem().getId() == null)
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("No Table For Import");
				alert.setHeaderText("No currently open Molecule available for import");
				alert.setContentText("Try creating a Molecule table first");
				
				alert.showAndWait();
			} else {				
				final FileChooser fileChooser = new FileChooser();
				molecules.importMolecule(moleculesTabPane.getSelectionModel().getSelectedItem().getId(), fileChooser.showOpenDialog(stage).getAbsolutePath());
			}
		});

		fileMenu.getItems().addAll(newItem, saveItem, importItem, exportItem);
		menuBar.getMenus().add(fileMenu);
		
		return menuBar;
	}
	
	public static TabPane getMoleculesTabPane()
	{
		if(moleculesTabPane == null)
		{
			createMoleculesTabPane();
		}
		
		return moleculesTabPane;
	}
	
	public static void createMoleculesTabPane()
	{
		moleculesTabPane = new TabPane();
		
		molecules.forEachMoleculeTable((moleculeName, moleculeTable) -> {
			createMoleculeTab(moleculeName, moleculeTable);
		});
		
	}
	
	private static void createMoleculeTab(String moleculeName, Node moleculeTable)
	{
		Tab moleculeTab = new Tab();
		Label moleculeTabLabel = new Label(moleculeName);
		
		moleculeTab.setId(moleculeName);
		
		moleculeTab.setGraphic(moleculeTabLabel);
		moleculeTab.setContent(moleculeTable);
		
		TextField editingTextBox = new TextField();
		
		moleculeTabLabel.setOnMouseClicked((event) -> {
			if(event.getClickCount() == 2)
			{
				editingTextBox.setText(moleculeTabLabel.getText());
				moleculeTab.setGraphic(editingTextBox);
				editingTextBox.selectAll();
				editingTextBox.requestFocus();
			}
		});
		
		//If Enter key is hit
		editingTextBox.setOnAction((event) -> {
			molecules.renameMolecule(moleculeTabLabel.getText(), editingTextBox.getText());
			//TODO: CHECK IF THIS CHANGES THE MOLECULE NAME
			moleculeTab.setId(editingTextBox.getText());
			
			moleculeTabLabel.setText(editingTextBox.getText());
			moleculeTab.setGraphic(moleculeTabLabel);
			
		});
		
		//If clicked out of
		editingTextBox.focusedProperty().addListener((obs, oldVal, newVal) -> {
			//If the tab is being unselected
			if(newVal == false)
			{
				moleculeTab.setGraphic(moleculeTabLabel);
			}
		});
		
		
		moleculesTabPane.getTabs().add(moleculeTab);
	}
}
