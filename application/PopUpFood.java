package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * This class manages the pop up window that adds food or uploads
 * a file with food items.
 * 
 * @author Shawn Ge
 * @author Danica Fliss
 * @author Alex Fusco
 * @author Cole Thompson
 * @author Leah Witt
 *
 */
public class PopUpFood {
	//declare fields
	Stage foodWindow;
	private FoodData foodData;
	boolean error1 = true;//error involved in adding food
	boolean error2 = true;//error involved in uploading file
	
	/**
	 * Public constructor - contains all the code to manage
	 * the window
	 * @param FoodData
	 */
	public PopUpFood(FoodData foodData){
		//initialize field
		this.foodData = foodData;
		foodWindow = new Stage();
		//base of window
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
		
		//button to upload file of foods
		Button uploadButton = new Button("Upload");
		
		//side the prompts new file to be uploaded
		BorderPane left = new BorderPane();
		VBox text = new VBox(10);
		text.getChildren().addAll(uploadButton);
		text.setPadding(new Insets(30,0,0,0));
		left.setTop(upload);
		left.setCenter(uploadButton);
		left.setPadding(new Insets(10,20,20,20));
		BorderPane.setMargin(uploadButton, new Insets(35,0,0,0));
		BorderPane.setAlignment(uploadButton, Pos.TOP_CENTER);
		food.setLeft(left);
		
		
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

				FileChooser fc = new FileChooser();
				File selectedFile = null;
				
				fc.setTitle("Open Food List");
				fc.getExtensionFilters().addAll(
						new ExtensionFilter("Text Files", "*.txt"),
						new ExtensionFilter("Comma Seperated Values", "*.csv"),
						new ExtensionFilter("All Files", "*.*"));
				
					fc.setInitialDirectory(new File("application\\"));
					
				try {
					selectedFile = fc.showOpenDialog(foodWindow);
				} catch (IllegalArgumentException e) {
					// Directory does not exist on users cpu, just open up a directory
					fc.setInitialDirectory(null);
					selectedFile = fc.showOpenDialog(foodWindow);
				}

				if (selectedFile != null) {
					try{
						foodData.loadFoodItems(selectedFile.getPath());
						foodWindow.hide();
					}catch(NullPointerException e){
						//inform user that
						Label fileErrorMessage = new Label("*Error: unable to upload file");
						fileErrorMessage.setFont(new Font(10));
						fileErrorMessage.setTextFill(Color.RED);
						
						//only display error message if not already there
						if(error2){
							text.getChildren().add(2, fileErrorMessage);
							error2 = false;
						}
					}catch(NumberFormatException e){
						Label formatError = new Label("WARNING - FORMAT ERROR: may not have uploaded all food items");
						formatError.setFont(new Font(25));
						formatError.setTextFill(Color.RED);
						formatError.setWrapText(true);
						formatError.setPadding(new Insets(10,10,10,10));
						Scene problem = new Scene(formatError, 300, 200);
						foodWindow.setScene(problem);
					}catch(Exception e){
						foodWindow.hide();
					}
				}
			}
		});

		//create a food when addFood button is pressed
		addFood.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//Create arbitrary 24 character long id
				String id;
				boolean validID = false;
				FoodItem newFood;
				
				//get food name
				String name = foodInput.getText();
				
				//create an id until it is unique
				while(!validID){
					try{
						id = UUID.randomUUID().toString();
						id = id.replace("-", "");
						id = id.substring(0,24);
						validID = true;
						
						//create new food
						if(!name.trim().equals(""))
							newFood = new FoodItem(id, name);
						
					}catch(Exception e){
						//ignore
					}
				}

				

				try{
					if(!name.trim().equals("")){
						
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
	 * @param String
	 * @return
	 */
	private Double getValue(String value){
		if(value == null || value.equals("")) 
			//if no value specified, automatically set value
			return 0.0;
		else
			return Double.parseDouble(value);
	}

}
