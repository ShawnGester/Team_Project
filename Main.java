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


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,1400,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Label shawn = new Label("Shawn Ge");
			Label cole = new Label("Cole Thomson");
			Label dani = new Label("Danica Fliss");
			Label leah = new Label("Leah Witt");
			Label alex = new Label("Alexander Fusco");
			HBox names = new HBox(200.0, shawn, cole, dani, leah, alex);
			
			Button newFood = new Button("+New Food");
			Button newMeal = new Button("+New Meal");
			Label topLabel = new Label("FoodGUI");
			
			Label centerLabel = new Label("Details");
			centerLabel.setFont(new Font("Arial", 30));
			
			
//			BorderPane left = new BorderPane(newFood);
			BorderPane right = new BorderPane(newMeal);
			BorderPane bottom = new BorderPane(names);
			BorderPane top = new BorderPane(topLabel);
			BorderPane center = new BorderPane(centerLabel);
//			BorderPane test = new BorderPane(newMeal);
			
//			root.setLeft(test);
			root.setRight(right);
			root.setTop(top);
			root.setBottom(bottom);
			root.setCenter(center);
			BorderPane.setAlignment(centerLabel, Pos.TOP_LEFT); //FIXME: fix left and right borderpane boundaries
			
			FlowPane flow = new FlowPane(Orientation.VERTICAL);
			root.setLeft(flow);
			flow.setColumnHalignment(HPos.LEFT); // align labels on left
			flow.getChildren().add(newFood);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
