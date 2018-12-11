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

public class PopUpFood {
	Stage foodWindow;
	
	public PopUpFood(){
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
		Label fileLabel = new Label("Input Filename");
		
		Button uploadButton = new Button("Upload");
		
		BorderPane left = new BorderPane();
		VBox text = new VBox(10);
		text.getChildren().addAll(fileLabel, filePath, uploadButton);
		left.setTop(upload);
		left.setCenter(text);
		
		food.setLeft(left);
		food.setAlignment(left, Pos.CENTER_RIGHT);
		
		Label newFood = new Label("Add New Food to List");
		newFood.setFont(new Font(20));
		
		Label foodName = new Label("Name of Food");
		TextField foodInput = new TextField("e.g. \"Apple\"");
		foodInput.setFocusTraversable(false);
		
		VBox nameInfo = new VBox(10);
		nameInfo.getChildren().addAll(foodName, foodInput);
		
		Label calories = new Label("calories");
		TextField calorieInput = new TextField();
		
//		HBox calInfo = new HBox(10);
//		calInfo.getChildren().addAll(calories, calorieInput);
		
		Label protein = new Label("protein in grams");
		TextField proteinInput = new TextField();
		
//		HBox proteinInfo = new HBox(10);
//		proteinInfo.getChildren().addAll(protein, proteinInput);
		
		Label carb = new Label("carbohydrates in grams");
		TextField carbInput = new TextField();
		
//		HBox carbInfo = new HBox(10);
//		carbInfo.getChildren().addAll(carb, carbInput);
		
		Label fat = new Label("fat in grams");
		TextField fatInput = new TextField();
		
//		HBox fatInfo = new HBox(10);
//		fatInfo.getChildren().addAll(fat, fatInput);
		
		Label fiber = new Label("fiber in grams");
		TextField fiberInput = new TextField();
		
//		HBox fiberInfo = new HBox(10);
//		fiberInfo.getChildren().addAll(fiber, fiberInput);
		
		Button addFood = new Button("Add Food");
		
		VBox right = new VBox(10);		
		VBox labels = new VBox(20);
		labels.getChildren().addAll(calories, protein, carb, fat, fiber);
		
		VBox fields = new VBox(10);
		fields.getChildren().addAll(calorieInput, proteinInput, carbInput, fatInput, fiberInput);
		
		HBox nutrientBox = new HBox(10);
		nutrientBox.getChildren().addAll(labels, fields);
		
		right.getChildren().addAll(newFood, nameInfo, nutrientBox, addFood);
		food.setRight(right);
		
		Scene foodScene = new Scene(food, 600, 400);
		foodWindow.setScene(foodScene);
		foodWindow.show();
		
	}

}
