package application;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PopUpMeal{
	//Popup meal = new Popup();
	Stage mealWindow;
	
	public PopUpMeal(List<FoodItem> food){
				
		mealWindow = new Stage();
		mealWindow.setTitle("Create A New Meal");
		
		BorderPane mealBorder = new BorderPane();
		HBox title = new HBox(10);
		VBox left = new VBox(10);
		VBox right = new VBox(10);
		HBox buttonBox = new HBox(10);
		
		Label header = new Label("Create Your Meal!");
		header.setFont(new Font(25));
		
		title.getChildren().add(header);
		title.setAlignment(Pos.CENTER);
		title.setPadding(new Insets(10,0,0,0));
		
		Label namePrompt = new Label("Enter Name of Meal");
		TextField nameInput = new TextField();
		nameInput.setPromptText("e.g. \"Breakfast\"");
		nameInput.setFocusTraversable(false);
		
		left.getChildren().addAll(namePrompt, nameInput);
		left.setAlignment(Pos.CENTER_LEFT);
		left.setPadding(new Insets(0,0,0,30));
		
		Label foodLabel = new Label("Choose the food to include in you meal");
		ScrollPane foodList = new ScrollPane();
		foodList.setMinHeight(200);
		
		VBox allCheckFood = new VBox(5);
		try{
			for(int j=0; j<food.size(); j++){
				String tempName = food.get(j).getName();
				allCheckFood.getChildren().add(new CheckBox(tempName));
			}
			foodList.setContent(allCheckFood);
		}catch(Exception e){
			//should I include an error message?
		}


		right.getChildren().addAll(foodLabel, foodList);
		right.setAlignment(Pos.CENTER_LEFT);
		right.setPadding(new Insets(0,30,0,0));
		
		Button addMeal = new Button("Add Meal");
		
		buttonBox.getChildren().add(addMeal);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setPadding(new Insets(0,0,30,0));
		
		mealBorder.setTop(title);
		mealBorder.setLeft(left);
		mealBorder.setRight(right);
		mealBorder.setBottom(buttonBox);
		
		Scene mealScene = new Scene(mealBorder, 600, 400);
		
		mealWindow.setScene(mealScene);
		mealWindow.show();
	}

}
