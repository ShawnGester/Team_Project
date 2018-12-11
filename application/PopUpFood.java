package application;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class PopUpFood {
	Stage foodWindow;
	private FoodData foodData;
	public PopUpFood(FoodData foodData){
		this.foodData = foodData;
		foodWindow = new Stage();
		foodWindow.setTitle("Create A Food Item");
		BorderPane food = new BorderPane();
		
		//top of the pop up window
		Label foodTitle = new Label("Create Food!");
		foodTitle.setFont(new Font(24));
		HBox top = new HBox(10);
		top.getChildren().add(foodTitle);
//		food.setTop(top);
		BorderPane.setAlignment(top, Pos.CENTER);
		
		//Left side of pop up window
		Label upload = new Label("Upload New Food File");
		upload.setFont(new Font(20));
		
		TextField filePath = new TextField();
		filePath.setFocusTraversable(false);
		Label fileLabel = new Label("Input Filename");
		
		Button uploadButton = new Button("Upload");
		
		//load the file when upload button is pressed
		uploadButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                String filename = filePath.getText();
	                if(filename != null){
	                	try{
	                		foodData.loadFoodItems(filename);
	                		foodWindow.hide();
	                	}catch(Exception e){
	                		//maybe have words show up with error message?
	                	}
	                }
	            }
		      });
		
		BorderPane left = new BorderPane();
		VBox text = new VBox(10);
		text.getChildren().addAll(fileLabel, filePath, uploadButton);
		text.setPadding(new Insets(30,0,0,0));
		left.setTop(upload);
		left.setCenter(text);
		left.setPadding(new Insets(10,20,20,20));
		
		food.setLeft(left);
		BorderPane.setAlignment(left, Pos.CENTER_RIGHT);//not sure if this is working
		
		Label newFood = new Label("Add New Food to List");
		newFood.setFont(new Font(20));
		
		Label foodName = new Label("Name of Food");
		TextField foodInput = new TextField();
		foodInput.setPromptText("e.g. \"Apple\"");
		foodInput.setFocusTraversable(false);
		
		VBox nameInfo = new VBox(10);
		nameInfo.getChildren().addAll(foodName, foodInput);
		
		Label calories = new Label("calories");
		TextField calorieInput = new TextField();
		calorieInput.setPromptText("unit kcal");
		calorieInput.setFocusTraversable(false);
		
		Label protein = new Label("protein");
		TextField proteinInput = new TextField();
		proteinInput.setPromptText("unit grams");
		proteinInput.setFocusTraversable(false);
		
		Label carb = new Label("carbohydrates");
		TextField carbInput = new TextField();
		carbInput.setPromptText("unit grams");
		carbInput.setFocusTraversable(false);
		
		Label fat = new Label("fat");
		TextField fatInput = new TextField();
		fatInput.setPromptText("unit grams");
		fatInput.setFocusTraversable(false);
		
		Label fiber = new Label("fiber");
		TextField fiberInput = new TextField();
		fiberInput.setPromptText("unit grams");
		fiberInput.setFocusTraversable(false);
		
		Button addFood = new Button("Add Food");
		
		addFood.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //How to make a food item without an id?
            	foodWindow.hide();
            }
	      });
		
		VBox right = new VBox(10);		
		VBox labels = new VBox(20);
		labels.getChildren().addAll(calories, protein, carb, fat, fiber);
		
		VBox fields = new VBox(10);
		fields.getChildren().addAll(calorieInput, proteinInput, carbInput, fatInput, fiberInput);
		
		HBox nutrientBox = new HBox(10);
		nutrientBox.getChildren().addAll(labels, fields);
		
		right.getChildren().addAll(newFood, nameInfo, nutrientBox, addFood);
		right.setPadding(new Insets(10,20,20,20));
		food.setRight(right);
		
		Scene foodScene = new Scene(food, 600, 400);
		foodWindow.setScene(foodScene);
		foodWindow.show();
		
	}

}
