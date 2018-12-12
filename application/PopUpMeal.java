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

public class PopUpMeal{
	//Popup meal = new Popup();
	Stage mealWindow;
	boolean errorThere = true;
	
	public PopUpMeal(List<FoodItem> food, MealData mealData){
				
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
		foodList.setMaxHeight(300);
		foodList.setMaxWidth(300);
		
		VBox allCheckFood = new VBox(5);
		List<CheckBox> boxes = new ArrayList<CheckBox>();
		try{
			for(int j=0; j<food.size(); j++){
				String tempName = food.get(j).getName();
				boxes.add(new CheckBox(tempName));
				allCheckFood.getChildren().add(boxes.get(j));
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
		buttonBox.setPadding(new Insets(10,0,30,0));
		
		addMeal.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				List<FoodItem> selectFoodList = new ArrayList<FoodItem>();
				for(int j=0; j<boxes.size(); j++){
					if(boxes.get(j).isSelected()){
						String selectFoodName = boxes.get(j).getText();
						for(int i=0; i<food.size(); i++){
							if(food.get(i).getName().equals(selectFoodName)){
								selectFoodList.add(food.get(i));
								break;
							}
						}
					}
				}
				String mealName = nameInput.getText();
				if(mealName != null && !mealName.trim().equals("")){
					Meal newMeal = new Meal(mealName, selectFoodList);
					mealData.addMeal(newMeal);
					mealWindow.hide();
				}else{
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
		
		mealBorder.setTop(title);
		mealBorder.setLeft(left);
		mealBorder.setRight(right);
		mealBorder.setBottom(buttonBox);
		
		Scene mealScene = new Scene(mealBorder, 600, 400);
		
		mealWindow.setScene(mealScene);
		mealWindow.showAndWait();
	}

}
