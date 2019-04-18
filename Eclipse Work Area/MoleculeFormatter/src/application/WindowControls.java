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
			if(currentlySelectedMolecule.get() == null)
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("No Table For Import");
				alert.setHeaderText("No currently open Molecule available for import");
				alert.setContentText("Try creating a Molecule table first");
				
				alert.showAndWait();
			} else {
				final FileChooser fileChooser = new FileChooser();
				molecules.importMolecule(currentlySelectedMolecule.get(), fileChooser.showOpenDialog(stage).getAbsolutePath());
			}
		});

		fileMenu.getItems().addAll(newItem, saveItem, importItem, exportItem);
		menuBar.getMenus().add(fileMenu);
		
		return menuBar;
	}
	
	public static TabPane getMoleculesTabPane()
	{
		if(moleculesTabPane == null) createMoleculesTabPane();
		currentlySelectedMolecule.set(moleculesTabPane.getSelectionModel().getSelectedItem().getText());
		
		return moleculesTabPane;
	}
	
	public static void createMoleculesTabPane()
	{
		moleculesTabPane = new TabPane();
		
		molecules.forEachMoleculeTable((moleculeName, moleculeTable) -> {
			createMoleculeTab(moleculeName, moleculeTable);
		});
		
		moleculesTabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			if(newTab != null)
			{
				currentlySelectedMolecule.set(newTab.getText());
				System.out.println(moleculesTabPane.getSelectionModel().getSelectedItem().getGraphic());
			} else {
				currentlySelectedMolecule.set(null);
			}
		});
	}
	
	private static void createMoleculeTab(String moleculeName, Node moleculeTable)
	{
		Tab tab = new Tab();
		Label label = new Label(moleculeName);
		tab.setGraphic(label);
		
		TextField textField = new TextField();
		label.setOnMouseClicked((event) -> {
			if(event.getClickCount() == 2)
			{
				textField.setText(label.getText());
				tab.setGraphic(textField);
				textField.selectAll();
				textField.requestFocus();
				
				//TODO: CHECK IF THIS CHANGES THE MOLECULE NAME
			}
		});
		
		textField.setOnAction((event) -> {
			molecules.renameMolecule(label.getText(), textField.getText());
			
			label.setText(textField.getText());
			tab.setGraphic(label);
			
		});
		
		textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
			if(newVal == false) 						//If the tab is being unselected
			{
				tab.setGraphic(label);
			}
		});
		
		moleculesTabPane.getTabs().add(tab);
	}
}
