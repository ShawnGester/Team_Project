package application;

import java.util.ArrayList;
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

/**
 * PopUpMeal class manages the pop up window used to add a meal to
 * the meal list.
 *
 * @author Shawn Ge
 * @author Danica Fliss
 * @author Alex Fusco
 * @author Cole Thompson
 * @author Leah Witt
 */
public class PopUpMeal{
	//declare fields
	Stage mealWindow;
	boolean errorThere = true;
	
	public PopUpMeal(List<FoodItem> food, MealData mealData){
		//set up base of pop up window		
		mealWindow = new Stage();
		mealWindow.setTitle("Create A New Meal");
		
		//different parts of pop up window
		BorderPane mealBorder = new BorderPane();
		HBox title = new HBox(10);
		VBox left = new VBox(10);
		VBox right = new VBox(10);
		HBox buttonBox = new HBox(10);
		
		//top of page
		Label header = new Label("Create Your Meal!");
		header.setFont(new Font(25));
		
		title.getChildren().add(header);
		title.setAlignment(Pos.CENTER);
		title.setPadding(new Insets(10,0,0,0));
		
		//left side of window, prompts for meal name
		Label namePrompt = new Label("Enter Name of Meal");
		TextField nameInput = new TextField();
		nameInput.setPromptText("e.g. \"Breakfast\"");
		nameInput.setFocusTraversable(false);
		
		left.getChildren().addAll(namePrompt, nameInput);
		left.setAlignment(Pos.CENTER_LEFT);
		left.setPadding(new Insets(0,0,0,30));
		
		//right side of window, scrollable checklist of foods
		Label foodLabel = new Label("Choose the food to include in you meal");
		ScrollPane foodList = new ScrollPane();
		foodList.setMinHeight(200);
		foodList.setMaxHeight(300);
		foodList.setMaxWidth(300);
		
		//box to sit inside scroll
		VBox allCheckFood = new VBox(5);
		//list to be able to access checkboxes to handle events
		List<CheckBox> boxes = new ArrayList<CheckBox>();
		
		//add checkboxes wilth names of all food items to scroll
		try{
			for(int j=0; j<food.size(); j++){
				String tempName = food.get(j).getName();
				boxes.add(new CheckBox(tempName));
				allCheckFood.getChildren().add(boxes.get(j));
			}
			foodList.setContent(allCheckFood);
		}catch(Exception e){
			//ignore
		}

		//add pieces to right side
		right.getChildren().addAll(foodLabel, foodList);
		right.setAlignment(Pos.CENTER_LEFT);
		right.setPadding(new Insets(0,30,0,0));
		
		//bottom of the window
		Button addMeal = new Button("Add Meal");
		
		buttonBox.getChildren().add(addMeal);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setPadding(new Insets(10,0,30,0));
		
		//add meal when button is pressed
		addMeal.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//list of foods to be added in meal
				List<FoodItem> selectFoodList = new ArrayList<FoodItem>();
				for(int j=0; j<boxes.size(); j++){
					//check if food is selected
					if(boxes.get(j).isSelected()){
						//get name of food
						String selectFoodName = boxes.get(j).getText();
						//find FoodItem that matches string
						for(int i=0; i<food.size(); i++){
							if(food.get(i).getName().equals(selectFoodName)){
								selectFoodList.add(food.get(i));
								break;
							}
						}
					}
				}
				
				//get name of meal
				String mealName = nameInput.getText();
				//check if valid name
				if(mealName != null && !mealName.trim().equals("")){
					Meal newMeal = new Meal(mealName, selectFoodList);
					mealData.addMeal(newMeal);
					mealWindow.hide();
				}else{
					//put up an error message if invalid name
					Label errorMeal = new Label("*Error: invalid name");
					errorMeal.setFont(new Font(10));
					errorMeal.setTextFill(Color.RED);
					if(errorThere){
						left.getChildren().add(1, errorMeal);
						errorThere = false;
					}
				}
				
			}
		});
		
		//put all boxes in appropriate border
		mealBorder.setTop(title);
		mealBorder.setLeft(left);
		mealBorder.setRight(right);
		mealBorder.setBottom(buttonBox);
		
		//add borders to display
		Scene mealScene = new Scene(mealBorder, 600, 400);
		
		mealWindow.setScene(mealScene);
		mealWindow.showAndWait();
	}

}
