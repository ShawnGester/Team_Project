package application;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
//		mealWindow = new Stage();
//		VBox left = new VBox(30);
//		VBox right = new VBox(10);
//		VBox mealInfoBox = new VBox(10);
//		HBox buttonBox = new HBox(10);
//		HBox window = new HBox(10);
//		
//		Label mealEntry = new Label("Enter Name of Meal");
//		TextField mealNameBox = new TextField();
//		mealNameBox.setPromptText("e.g. \"Breakfast\"");
//		mealNameBox.setFocusTraversable(false);
//		Button addMealButton = new Button("Add Meal");
//		
//		mealInfoBox.getChildren().addAll(mealEntry, mealNameBox);
//		buttonBox.getChildren().add(addMealButton);
//		buttonBox.setAlignment(Pos.CENTER);
//		
//		left.getChildren().addAll(mealInfoBox, buttonBox);
//		
//		window.getChildren().addAll(left, right);
//		window.setAlignment(Pos.BOTTOM_CENTER);
//		window.setPrefWidth(600);
//		window.setPrefHeight(400);
//		
//		window.setStyle("-fx-background-color: aquamarine");
//		
//		Scene mealScene = new Scene(window, 600, 400);
//		
//		
//		mealWindow.setScene(mealScene);
//		mealWindow.show();
		
		
		mealWindow = new Stage();
		
		BorderPane mealBorder = new BorderPane();
		HBox title = new HBox(10);
		VBox left = new VBox(10);
		VBox right = new VBox(10);
		HBox buttonBox = new HBox(10);
		
		Label header = new Label("Create Your Meal!");
		header.setFont(new Font(25));
		
		title.getChildren().add(header);
		title.setAlignment(Pos.CENTER);
		
		Label namePrompt = new Label("Enter Name of Meal");
		TextField nameInput = new TextField();
		nameInput.setPromptText("e.g. \"Breakfast\"");
		nameInput.setFocusTraversable(false);
		
		left.getChildren().addAll(namePrompt, nameInput);
		left.setAlignment(Pos.CENTER);
		
		Label foodLabel = new Label("Choose the food to include in you meal");
		ScrollPane foodList = new ScrollPane();
		foodList.setMinHeight(200);
		
		right.getChildren().addAll(foodLabel, foodList);
		right.setAlignment(Pos.CENTER);
		
		Button addMeal = new Button("Add Meal");
		
		buttonBox.getChildren().add(addMeal);
		buttonBox.setAlignment(Pos.CENTER);
		
		mealBorder.setTop(title);
		mealBorder.setLeft(left);
		mealBorder.setRight(right);
		mealBorder.setBottom(buttonBox);
		
		Scene mealScene = new Scene(mealBorder, 600, 400);
		
		mealWindow.setScene(mealScene);
		mealWindow.show();
	}

}
