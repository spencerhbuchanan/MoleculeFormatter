package application;

import molecules.Molecules;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		Molecules molecules = new Molecules();
		molecules.createMolecule("Susan");
		molecules.createMolecule("Karen");
		molecules.createMolecule("Janice");

		
		
		BorderPane externalBorderPane = new BorderPane();

		WindowControls.setStage(primaryStage);
		WindowControls.setMolecules(molecules);
		
		externalBorderPane.setTop(WindowControls.createToolbar());
		externalBorderPane.setCenter(WindowControls.getMoleculesTabPane());
		
		primaryStage.setTitle("Molecule Formatter");
		
		Scene scene = new Scene(externalBorderPane, 450, 300);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args)
	{		
		launch(args); //IGNORE BUG: Its standard
	}
}
