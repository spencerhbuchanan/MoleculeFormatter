package application;

import molecules.Molecules;
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
		Molecules molecules = new Molecules();
		
		molecules.createMolecule("Susan");

		BorderPane borderPane = new BorderPane();

		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
		MenuItem importItem = new MenuItem("Import");

		importItem.setOnAction((event) -> {
			final FileChooser fileChooser = new FileChooser();

			molecules.importMolecule("Susan", fileChooser.showOpenDialog(primaryStage).getAbsolutePath());

			// TODO: Find out if the old atomTable is retained in memory (this is intended
			// to refresh the table)
			borderPane.setCenter(molecules.getMoleculeTable("Susan"));
		});

		fileMenu.getItems().add(importItem);
		menuBar.getMenus().add(fileMenu);

		borderPane.setTop(menuBar);
		borderPane.setCenter(molecules.getMoleculeTable("Susan"));

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
