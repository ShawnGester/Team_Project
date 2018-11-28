package application;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PrimaryGUI {
	public FoodData foodData;
//	public FoodItemAddForm foodAddForm;
	
	
	public PrimaryGUI(FoodData foodData, Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,1400,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); //FIXME: Alex's
			primaryStage.setScene(scene);
			primaryStage.show();
			
//			Label shawn = new Label("Shawn Ge");
//			Label cole = new Label("Cole Thomson");
//			Label dani = new Label("Danica Fliss");
//			Label leah = new Label("Leah Witt");
//			Label alex = new Label("Alexander Fusco");
//			HBox names = new HBox(200.0, shawn, cole, dani, leah, alex); //FIXME: ?
			
			Button newFood = new Button("+New Food");
			Button newMeal = new Button("+New Meal");
			Label topLabel = new Label("Meal Planner");
			
			Label centerLabel = new Label("Details");
//			centerLabel.setFont(new Font("Arial", 30));
			
			
//			BorderPane left = new BorderPane(newFood);
			BorderPane right = new BorderPane(newMeal);
//			BorderPane bottom = new BorderPane(names);
			BorderPane top = new BorderPane(topLabel);
			BorderPane center = new BorderPane(centerLabel);
			
//			root.setLeft(test);
			root.setRight(right);
			root.setTop(top);
//			root.setBottom(bottom);
			root.setCenter(center);
			BorderPane.setAlignment(centerLabel, Pos.TOP_LEFT); //FIXME: fix left and right borderpane boundaries
			
			FlowPane flow = new FlowPane(Orientation.VERTICAL, 0, 10); //gives vertical spacing between 
			flow.setAlignment(Pos.TOP_LEFT); //left aligns all members of flow pane
			flow.setPadding(new Insets(0,0,0,10));
			root.setLeft(flow);
			flow.setColumnHalignment(HPos.LEFT); // align labels on left
			flow.getChildren().add(newFood);
			flow.getChildren().add(newMeal);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
