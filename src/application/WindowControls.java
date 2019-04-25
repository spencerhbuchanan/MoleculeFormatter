package application;

import java.io.File;
import java.io.IOException;
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
import molecules.MoleculeExporter;
import molecules.Molecules;

/**
 * <h1>Window Controls</h1> 
 * Builds up the controls for the formatters window.
 * Also builds the Molecules Tab Pane
 * 
 * @author Spencer Buchanan
 * @version 0.2
 * @since 2019-22-04
 */
public class WindowControls {
  static Stage stage;
  static Molecules molecules;

  static TabPane moleculesTabPane;

  /**
   * Sets the primary stage for the class, really only used for the file chooser
   * 
   * @param stage
   */
  public static void setStage(Stage stage) {
    WindowControls.stage = stage;
  }

  /**
   * Sets the Molecules object for the table to be based off of
   * 
   * @param molecules
   */
  public static void setMolecules(Molecules molecules) {
    WindowControls.molecules = molecules;
  }

  /**
   * Creates and returns the MenuBar to be displayed at the top of the window (file, edit, etc.)
   * 
   * @return The MenuBar to be displayed at the top of the window
   */
  public static MenuBar createToolbar() {
    MenuBar menuBar = new MenuBar();
    Menu fileMenu = new Menu("File");

    MenuItem newItem = new MenuItem("New Molecule");
    //MenuItem saveItem = new MenuItem("Save");       // UNIMPLEMENTED
    MenuItem importItem = new MenuItem("Import CML"); // TODO: Make import window w/options for import
    MenuItem exportItem = new MenuItem("Export XYZ"); // TODO: Make export window w/options for export

    newItem.setOnAction((event) -> {
      String newMoleculeID = molecules.createMolecule("New Molecule");

      createMoleculeTab(newMoleculeID, 
                        molecules.getMolecule(newMoleculeID).getMoleculeName(),
                        molecules.getMoleculeTable(newMoleculeID));

      moleculesTabPane.getSelectionModel().selectLast();
    });

    importItem.setOnAction((event) -> {
      if (moleculesTabPane.getSelectionModel().getSelectedItem() == null) {
        // TODO: Decide if this should just make a new table instead
        Alert alert = new Alert(AlertType.ERROR);

        alert.setTitle("No Table For Import");
        alert.setHeaderText("No currently open Molecule available for import");
        alert.setContentText("Try creating a Molecule table first");

        alert.showAndWait();
      } else {
        // TODO: Add checks to be sure the file is an acceptable format
        // TODO: Ask user if they want to overwrite the current table
        final FileChooser fileChooser = new FileChooser();
        final FileChooser.ExtensionFilter extensionFilter =
            new FileChooser.ExtensionFilter("Chemical Markup Language files (*.cml)", "*.cml");

        fileChooser.getExtensionFilters().add(extensionFilter);

        String moleculeToImportToID =
            moleculesTabPane.getSelectionModel().getSelectedItem().getId();
        File fileToImport = fileChooser.showOpenDialog(stage);

        if (fileToImport == null) {
          Alert alert = new Alert(AlertType.ERROR);

          alert.setTitle("No File Selected");
          alert.setHeaderText("No file selected to import");

          alert.showAndWait();
        } else {
          molecules.importMolecule(moleculeToImportToID, fileToImport);
        }
      }
    });

    exportItem.setOnAction((event) -> {
      if (moleculesTabPane.getSelectionModel().getSelectedItem() == null) {
        Alert alert = new Alert(AlertType.ERROR);

        alert.setTitle("No Table For Export");
        alert.setHeaderText("No currently open or selected molecule for export");
        alert.setContentText("Try creating a Molecule table first");

        alert.showAndWait();
      } else {
        final FileChooser fileChooser = new FileChooser();
        final FileChooser.ExtensionFilter extensionFilter =
            new FileChooser.ExtensionFilter("XYZ File (*.xyz), ", "*.xyz");

        fileChooser.getExtensionFilters().add(extensionFilter);

        String moleculeToExportID = moleculesTabPane.getSelectionModel().getSelectedItem().getId();
        File fileExportDestination = fileChooser.showSaveDialog(stage);

        if (fileExportDestination == null) {
          Alert alert = new Alert(AlertType.ERROR);

          alert.setTitle("No File Selected");
          alert.setHeaderText("No File Destination selected");

          alert.showAndWait();
        } else {
          try {
            MoleculeExporter.exportMoleculeAsXYZ(molecules.getMolecule(moleculeToExportID),
                                                 fileExportDestination);
          } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);

            alert.setTitle("Error on File Write");
            alert.setHeaderText("There was an error when trying to export the file");

            alert.showAndWait();
          }
        }
      }
    });

    fileMenu.getItems().addAll(newItem, importItem, exportItem);
    menuBar.getMenus().add(fileMenu);

    return menuBar;
  }

  /**
   * Returns the TabPane containing tables for the Molecules
   * 
   * @return The TabPane containing the Tables for the Molecules
   */
  public static TabPane getMoleculesTabPane() {
    if (moleculesTabPane == null) {
      createMoleculesTabPane();
    }

    return moleculesTabPane;
  }

  /**
   * Creates the TabPane for this instance of WindowControls. Usually only runs once at startup.
   */
  private static void createMoleculesTabPane() {
    moleculesTabPane = new TabPane();

    //Creates a Tab for each molecule currently in Molecules
    molecules.forEachMoleculeID((moleculeID) -> {
      String moleculeName = molecules.getMolecule(moleculeID).getMoleculeName();
      Node moleculeTable = molecules.getMoleculeTable(moleculeID);

      createMoleculeTab(moleculeID, moleculeName, moleculeTable);
    });
  }

  /**
   * Creates a singular tab containing a Molecules table and adds it to the tab pane
   * 
   * @param moleculeID      Which molecule to make the tab for
   * @param moleculeName    Name of the molecule (essentially the tab)
   * @param moleculeTable   The moleculeTable to display
   */
  private static void createMoleculeTab(String moleculeID, String moleculeName,
                                        Node moleculeTable) {
    Tab moleculeTab = new Tab();
    Label moleculeTabLabel = new Label(moleculeName);

    // Used as a reference to the tab/molecule, to retrieve the molecule object when needed
    moleculeTab.setId(moleculeID);

    moleculeTab.setGraphic(moleculeTabLabel);
    moleculeTab.setContent(moleculeTable);

    TextField editingTextBox = new TextField();

    //When the Tab name is double clicked, it goes into an editing state
    moleculeTabLabel.setOnMouseClicked((event) -> {
      if (event.getClickCount() == 2) {
        editingTextBox.setText(moleculeTabLabel.getText());
        moleculeTab.setGraphic(editingTextBox);
        editingTextBox.selectAll();
        editingTextBox.requestFocus();
      }
    });

    // If Enter key is hit, the name in the edit box is saved, and molecules is updated acordingly
    editingTextBox.setOnAction((event) -> {
      molecules.renameMolecule(moleculeID, editingTextBox.getText());

      moleculeTabLabel.setText(editingTextBox.getText());
      moleculeTab.setGraphic(moleculeTabLabel);
    });

    // If clicked out of (canceled), cancels the edit and returns the tab name to the original/
    editingTextBox.focusedProperty().addListener((obs, oldVal, newVal) -> {
      // If the tab is, in fact, being unselected
      if (newVal == false) {
        moleculeTab.setGraphic(moleculeTabLabel);
      }
    });

    //Adds the tab created here to the tab pane
    moleculesTabPane.getTabs().add(moleculeTab);
  }
}
