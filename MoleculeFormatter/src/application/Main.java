package application;

import molecules.Molecules;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) {
    
    //Adds some molecules to start
    Molecules molecules = new Molecules();
    molecules.createMolecule("Susan");
    molecules.createMolecule("Karen");
    molecules.createMolecule("Janice");

    //Creates the Border Pane, in which everything else is placed in
    BorderPane externalBorderPane = new BorderPane();

    //Gives WindowControls the primary stage, and sets which molecules to display
    WindowControls.setStage(primaryStage);
    WindowControls.setMolecules(molecules);

    //Sets the toolbar to display in the top of the border pane, and the tables to display in the center
    externalBorderPane.setTop(WindowControls.createToolbar());
    externalBorderPane.setCenter(WindowControls.getMoleculesTabPane());
    
    primaryStage.setTitle("Molecule Formatter");

    Scene scene = new Scene(externalBorderPane, 450, 300);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args); // IGNORE BUG: Its standard format
  }
}
