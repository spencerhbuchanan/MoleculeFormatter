package application;

import molecules.Molecule;
import molecules.MoleculeView;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class Main extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		Molecule molecule = new Molecule("Janet");
		molecule.importFile("C:\\Users\\Spencer Buchanan\\OneDrive - Florida Gulf Coast University\\CompChem\\Anion.cml");

		BorderPane borderPane = new BorderPane();

		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
		MenuItem importItem = new MenuItem("Import");

		importItem.setOnAction((event) -> {
			final FileChooser fileChooser = new FileChooser();

			molecule.removeAllAtoms();
			molecule.removeAllBonds();

			molecule.importFile(fileChooser.showOpenDialog(primaryStage).getAbsolutePath());

			// TODO: Find out if the old atomTable is retained in memory (this is intended
			// to refresh the table)
			borderPane.setCenter(MoleculeView.addAtomTable(molecule));
		});

		fileMenu.getItems().add(importItem);
		menuBar.getMenus().add(fileMenu);

		borderPane.setTop(menuBar);
		borderPane.setCenter(MoleculeView.addAtomTable(molecule));

		primaryStage.setTitle("Atom Table");

		Scene scene = new Scene(borderPane, 450, 300);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
