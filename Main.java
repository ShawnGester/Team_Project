package application;
	
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * Main class that runs JavaFX program.
 * @author Cole Thomson
 *
 */
public class Main extends Application {
	public FoodData foodData;
	public PrimaryGUI primaryGUI;
	
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryGUI = new PrimaryGUI(foodData, primaryStage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
