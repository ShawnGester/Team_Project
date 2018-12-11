package application;

import java.util.List;
import java.util.UUID;

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
	boolean error1 = true;//error involved in adding food
	boolean error2 = true;//error involved in uploading file
	
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
		BorderPane.setAlignment(top, Pos.CENTER);
		
		//Left side of pop up window
		Label upload = new Label("Upload New Food File");
		upload.setFont(new Font(20));
		
		TextField filePath = new TextField();
		filePath.setFocusTraversable(false);
		Label fileLabel = new Label("Input Filename");
		
		Button uploadButton = new Button("Upload");
		
		//side the prompts new file to be uploaded
		BorderPane left = new BorderPane();
		VBox text = new VBox(10);
		text.getChildren().addAll(fileLabel, filePath, uploadButton);
		text.setPadding(new Insets(30,0,0,0));
		left.setTop(upload);
		left.setCenter(text);
		left.setPadding(new Insets(10,20,20,20));
		
		food.setLeft(left);
		BorderPane.setAlignment(left, Pos.CENTER_RIGHT);//not sure if this is working
		
		//right side of pop up window
		Label newFood = new Label("Add New Food to List");
		newFood.setFont(new Font(20));
		
		Label foodName = new Label("Name of Food");
		TextField foodInput = new TextField();
		foodInput.setPromptText("e.g. \"Apple\"");
		foodInput.setFocusTraversable(false);
		
		VBox nameInfo = new VBox(10);
		nameInfo.getChildren().addAll(foodName, foodInput);
		
		//calories
		Label calories = new Label("calories");
		TextField calorieInput = new TextField();
		calorieInput.setPromptText("unit kcal");
		calorieInput.setFocusTraversable(false);
		
		//protein
		Label protein = new Label("protein");
		TextField proteinInput = new TextField();
		proteinInput.setPromptText("unit grams");
		proteinInput.setFocusTraversable(false);
		
		//carbohydrates
		Label carb = new Label("carbohydrates");
		TextField carbInput = new TextField();
		carbInput.setPromptText("unit grams");
		carbInput.setFocusTraversable(false);
		
		//fat
		Label fat = new Label("fat");
		TextField fatInput = new TextField();
		fatInput.setPromptText("unit grams");
		fatInput.setFocusTraversable(false);
		
		//fiber
		Label fiber = new Label("fiber");
		TextField fiberInput = new TextField();
		fiberInput.setPromptText("unit grams");
		fiberInput.setFocusTraversable(false);
		
		Button addFood = new Button("Add Food");
		
		VBox right = new VBox(10);		
		VBox labels = new VBox(20);
		labels.getChildren().addAll(calories, protein, carb, fat, fiber);
		
		VBox fields = new VBox(10);
		fields.getChildren().addAll(calorieInput, proteinInput, carbInput, fatInput, fiberInput);
		
		HBox nutrientBox = new HBox(10);
		nutrientBox.getChildren().addAll(labels, fields);
		
		//side that prompts addition of food to existing list
		right.getChildren().addAll(newFood, nameInfo, nutrientBox, addFood);
		right.setPadding(new Insets(10,20,20,20));
		food.setRight(right);
		
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
						//inform user that
						Label fileErrorMessage = new Label("*Error: unable to upload file");
						fileErrorMessage.setFont(new Font(10));
						fileErrorMessage.setTextFill(Color.RED);
						
						//only display error message if not already there
						if(error2){
							text.getChildren().add(2, fileErrorMessage);
							error2 = false;
						}
					}
				}
			}
		});

		//create a food when addFood button is pressed
		addFood.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//Create arbitrary 24 character long id
				String id = UUID.randomUUID().toString();
				id = id.replace("-", "");
				id = id.substring(0,24); 

				//get food name
				String name = foodInput.getText();

				try{
					if(!name.trim().equals("")){
						//create new food
						FoodItem newFood = new FoodItem(id, name);
						
						//input nutrient values
						newFood.addNutrient("calories", getValue(calorieInput.getText()));
						newFood.addNutrient("protein", getValue(proteinInput.getText()));
						newFood.addNutrient("carbohydrates", getValue(carbInput.getText()));
						newFood.addNutrient("fat", getValue(fatInput.getText()));
						newFood.addNutrient("fiber", getValue(fiberInput.getText()));
						
						//add food to foodData
						foodData.addFoodItem(newFood);
						
						//close window
            			foodWindow.hide();
            		}else{ 
            			//do not except a name if it has only whitespace characters
            			throw new Exception();
            		}
            	}catch(Exception e){
            		//prompt user to fix their mistake
            		Label errorMessage = new Label("*Error: improper input fields");
            		errorMessage.setFont(new Font(10));
            		errorMessage.setTextFill(Color.RED);
            		
            		//only add an instance of the error message if not currently there
            		if(error1){
            			right.getChildren().add(3, errorMessage);
            			error1 = false;
            		}
            	}
            }
	      });
		
		Scene foodScene = new Scene(food, 600, 400);
		foodWindow.setScene(foodScene);
		foodWindow.showAndWait();
		
	}
	
	/**
	 * checks to see if input value is a valid input. If there is no
	 * input, the value is automatically set to 0.0. Throws exception
	 * if parameter value cannot be parsed into a Double.
	 * @param value
	 * @return
	 */
	private Double getValue(String value){
		if(value == null || value.equals("")) 
			return 0.0;
		else
			return Double.parseDouble(value);
	}

}
