package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Represents the Primary GUI that the user will interact with. Initializes all GUI objects in 
 * constructor.
 * @author Cole Thomosn
 * @author Shawn Ge
 * @author Alex Fusco
 * @author Danica Fliss
 * @author Leah Witt
 *
 */
public class PrimaryGUI {
	
	private FoodData foodData; // Food Data used in Meal Planner Program
	private HashMap<String, FoodItem> foodItemsHMap;  // Hash Map of Food Items
	private List<String> displayedFoodNamesList; //Food Names List
	private List<String> foodFilterRules; //filter rules for food items
	private boolean foodNotFoundFlag; //flag for showing food not found message
	private int numFoodItems; // Number of food items in the program
	private int numFoodsDisplayed; // Number of food items displayed
	private static final double SCREEN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth(); 
	private static final double SCREEN_HEIGHT = Screen.getPrimary().getVisualBounds().getWidth();
	
	/**
	 * Handles Primary GUI for Meal Planner program. Single stage with single scene that has
	 * three main panes. First pane on left-hand side of screen is "Food Pane". Food Pane allows
	 * for user to load, add, remove, filter and save food lists. The second pane in the middle
	 * of the screen is "Details Pane". Details Pane displays output information to user. 
	 * The third pane on the right-hand side of the screen is the "Meal Pane". Meal pane allows
	 * users to create, remove, and view meals that are comprised of foods available in the
	 * food list.
	 * @param foodData - all of food items
	 * @param primaryStage - primaryStage that contains primary scene
	 */
	public PrimaryGUI(FoodData foodData, Stage primaryStage) {
		try {
			this.foodData = foodData; // FoodData instance
			this.foodItemsHMap = new HashMap<String, FoodItem>();
			this.displayedFoodNamesList = new ArrayList<String>();
			this.foodFilterRules = new ArrayList<String>();
			this.foodNotFoundFlag = false;
			this.numFoodItems = 0;
			
			ScrollPane root = new ScrollPane(); 		// Primary Pane for GUI, allows scrolling
			BorderPane boarderPane = new BorderPane();	// Structure for visual display	
			Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT); // Create Scene
			
			// Set BoarderPane on ScrollPane
			root.setContent(boarderPane);	
			// Fit contents of ScrollPane to width of pane
			root.setFitToWidth(true);		
			// Allow ScrollPane to be pannable
			root.setPannable(true);			
			// get CSS for styling information
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			// Set the Scene on the Stage
			primaryStage.setScene(scene);	
			
			/*
			 * Create GUI Objects
			 */
            
            List<String> foodNamesList = displayedFoodNamesList;
            ObservableList<String> foodNamesOList = FXCollections.observableList(foodNamesList);
            
			Label progNameLabel = new Label("Meal Planner"); // Name of program (top pane)
			progNameLabel.setId("title");
			Label displayPaneLabel = new Label("Details");	 // Heading for display (center pane)
			BorderPane topBPane = new BorderPane(progNameLabel); // BPane for top of GUI
			BorderPane centerBPane = new BorderPane(); // BPane for center of GUI
			
			// Set nested panes in main BoarderPane
			boarderPane.setTop(topBPane);
			boarderPane.setCenter(centerBPane);
			
			centerBPane.setTop(displayPaneLabel);
			// Place "Details" Heading at Top-Left of center section of main BPane
			BorderPane.setAlignment(displayPaneLabel, Pos.TOP_LEFT); 
		
			/*
			 * Food Pane (left section of main BoarderPane)
			 */
			
			VBox foodPaneVBox = new VBox(10.0); // VBox to hold food pane GUI objects
			
			// Set Width of foodPaneVBox to 25% of screen width
			foodPaneVBox.setPrefWidth(SCREEN_WIDTH / 4);
			// Place foodPaneVBox at Top-Left of left section of main BPane
			foodPaneVBox.setAlignment(Pos.TOP_LEFT);
			// Set padding of foodPaneVBox along left side
			foodPaneVBox.setPadding(new Insets(0,0,0,10));
			 
			Button newFoodButton = new Button("+ New Food"); // Button for adding food to list
			TextField queryFoodField = new TextField(); // Food query field
			queryFoodField.setPromptText("Search for a food...");
			ListView<String> foodListView = new ListView<String>(); // Filtered list of cur foods
				
			// Set Width of foodListView to ~24% of the screen width
			foodListView.prefWidthProperty().set(SCREEN_WIDTH/4.2);
			// Set Height of foodListView to ~14% of screen height
			foodListView.setPrefHeight(SCREEN_HEIGHT/7);
			
			Label dispFoodsLabel = new Label("Displaying 0 of 0 foods"); // # of total foods disp.
			Label foodNameDetailsPane = new Label(); // Food Label used in details pane
			Button displayFoodButton = new Button("Display Food"); 	// Button to display food details
			Button downloadFoodButton = new Button("Download Food List"); // download list of foods
			Label foodPaneLabel = new Label("Foods"); // Heading for food pane (left pane)
			
			// Creates filter section of food VBox
			VBox filterVBox = new VBox(10.0);
			
			ObservableList<String> filterOptions = FXCollections.observableArrayList(
					"Calories",
					"Fat",
					"Carbohydrates",
					"Fiber",
					"Protein"
				); // Options to filter food list by
			
			ComboBox<String> filtersCBox = new ComboBox<String>(filterOptions); // Filter CBox
			
			// Set default value of foodFilterCBox to prompt user to select a filter
			filtersCBox.setPromptText("Select a filter");
			
			ObservableList<String> foodCompOptions = FXCollections.observableArrayList(
					"==",
					">=",
					"<="
				); // Types of comparisons available for filters
			
			// ComboBox of comparison operators to determine food filter criteria 
			ComboBox<String> compFiltersCBox = new ComboBox<String>(foodCompOptions);  
			
			// Set default value of foodCompFiltersCBox to "="
			compFiltersCBox.setValue("==");
			
			TextField filterValue = new TextField(""); // user inputs filter value
			
			// Set Width of foodFilterValue 
			filterValue.setPrefWidth(100);
			
			Button foodAddFilterButton = new Button("Add"); // Applies filter and adds to list
			HBox filterHBox = new HBox(10); // HBox to hold group of food filter GUI objects
			ListView<String> filterLView = new ListView<String>(); // list of applied filters
			
			// Set Height of foodFilterLView to ~9% of screen height
			filterLView.setPrefHeight(SCREEN_HEIGHT/11);
			
			HBox buttonsHBox = new HBox(10); // HBox to hold "Edit" and "Delete" buttons
			Button foodDeleteFilterButton = new Button("Delete"); 	// Button to delete a filter
			
			/**
			 * EVENT HANDLERS FOOD PANE 
			 */
			
			// New Food Button event handler
			newFoodButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                new PopUpFood(foodData);
	                
	                updateFoodNamesList(foodData.getAllFoodItems());
	                foodListView.setItems(FXCollections.observableList(displayedFoodNamesList));
	                
	                
	                updateFoodNamesList(foodData.filterByNutrients(foodFilterRules));
		            foodListView.setItems(FXCollections.observableList(displayedFoodNamesList));
		            //update size of food list and number of foods displayed
		            updateFoodListSize(dispFoodsLabel);
	            }
		      });
			
			// Search for a food Field
			queryFoodField.setOnAction((ae) -> {
				String searchedFood = queryFoodField.getText();
				updateFoodNamesList(foodData.filterByName(searchedFood));
				foodListView.setItems(FXCollections.observableList(displayedFoodNamesList));
				//update size of food list and number of foods displayed
	            updateFoodListSize(dispFoodsLabel);
	            
				// if no foods match the search
				if(foodData.filterByName(searchedFood).size()==0) {
					if(!this.foodNotFoundFlag) {
						// inform user that
						Label foodDNEErrorMessage = new Label("*Error: food does not exist.");
						foodDNEErrorMessage.setFont(new Font(10));
						foodDNEErrorMessage.setTextFill(Color.RED);
						foodPaneVBox.getChildren().add(2, foodDNEErrorMessage);
						this.foodNotFoundFlag = true;
						}
				}
				// else food doesn't exist, display message
				else {
					//if error message is up, delete it and reset flag
					if(this.foodNotFoundFlag) {
						foodPaneVBox.getChildren().remove(2);
						this.foodNotFoundFlag = false;
					}
				}	
			}); 
			
			// Add Filter to list of rules
			foodAddFilterButton.setOnAction((ae) -> {
				Double comparableValue = Double.parseDouble(filterValue.getText()); //FIXME NumberFormatException
				String comparator = compFiltersCBox.getValue();
				String nutrientFilter = filtersCBox.getValue();

				if (nutrientFilter != null && comparableValue >= 0) {
					String rule = nutrientFilter + " " + comparator + " " + comparableValue;
					// If the rule already exists, don't add it
					if (!this.foodFilterRules.contains(rule)) {
						this.foodFilterRules.add(rule);
						updateFoodNamesList(this.foodData.filterByNutrients(this.foodFilterRules));
						foodListView.setItems(FXCollections.observableList(displayedFoodNamesList));
						filterLView.setItems(FXCollections.observableList(foodFilterRules));
						filterValue.setText("");
						//update size of food list and number of foods displayed
			            updateFoodListSize(dispFoodsLabel);
					}
				}
			});
			
			// Delete Filter from list of rules (one at a time)
			foodDeleteFilterButton.setOnAction((ae) -> {
				String ruleToDelete = filterLView.getSelectionModel().getSelectedItem();
				//find and delete rule from rule list
				for(int i = 0; i < foodFilterRules.size(); i++) {
					if(ruleToDelete.equals(foodFilterRules.get(i))) {
						foodFilterRules.remove(i);
						break;
					}
				}
				updateFoodNamesList(this.foodData.filterByNutrients(this.foodFilterRules));
				foodListView.setItems(FXCollections.observableList(displayedFoodNamesList));
				filterLView.setItems(FXCollections.observableList(foodFilterRules));
				filterValue.setText("");
				//update size of food list and number of foods displayed
	            updateFoodListSize(dispFoodsLabel);
			});
			
			// Display Food Button to the detail pane
			displayFoodButton.setOnAction((ae) -> {
				String selectedFood = foodListView.getSelectionModel().getSelectedItem();
				foodNameDetailsPane.setText("Food Name: " + selectedFood);
				centerBPane.setCenter(foodNameDetailsPane);
				BorderPane.setAlignment(foodNameDetailsPane, Pos.TOP_CENTER);
				BorderPane.setMargin(foodNameDetailsPane, new Insets(50,10,10,10));
			});
			
			// Add food filter GUI objects to HBox
			filterHBox.getChildren().addAll(compFiltersCBox, filterValue, foodAddFilterButton); 
			
			// Add "Edit" and "Delete" buttons to HBox
			buttonsHBox.getChildren().addAll(foodDeleteFilterButton);
			
			// Set all elements onto filter GUI to return
			filterVBox.getChildren().addAll(filtersCBox, filterHBox, filterLView, buttonsHBox);
			
			

			
			
			// Set GUI Objects on Food Pane
			foodPaneVBox.getChildren().addAll(newFoodButton, queryFoodField, foodListView, dispFoodsLabel,
					filterVBox, displayFoodButton, downloadFoodButton, foodPaneLabel);
			
			/**
			 * Meal Pane (right section of main BoarderPane)
			 */
			VBox mealPaneVBox = new VBox(10.0); // VBox to hold meal pane GUI objects
			
			// Set Width of mealPaneVBox to 25% of screen width
			mealPaneVBox.setPrefWidth(SCREEN_WIDTH / 4);
			// Place mealPaneVBox at Top-Left of left section of main BPane
			mealPaneVBox.setAlignment(Pos.TOP_LEFT);
			// Set padding of mealPaneVBox along left side
			mealPaneVBox.setPadding(new Insets(0,0,0,10));
			 
			Button newMealButton = new Button("+ New Meal"); // Button for creating new meal
			TextField queryMealField = new TextField(); // Meal query field
			queryMealField.setPromptText("Search for a meal...");
			ListView<String> mealListView = new ListView<String>(); // Filtered list of cur meals
			
			newMealButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                new PopUpMeal(null);
	            }
		      });
			
			// Set Width of mealListView to ~24% of the screen width
			mealListView.prefWidthProperty().set(SCREEN_WIDTH/4.2);
			// Set Height of mealListView to ~14% of screen height
			mealListView.setPrefHeight(SCREEN_HEIGHT/7);
			
			Label dispMealsLabel = new Label("Displaying 0 of 0 meals"); // # of total meals disp.

			Button displayMealButton = new Button("Analyze Meal"); 	// Button to analyze/disp. meal
			Label blankLabel = new Label(""); // Blank label for formatting
			Label mealPaneLabel = new Label("Meals"); // Heading for meal pane (right pane)
			
			// Creates filter section of meal VBox
			FilterGUI mealFilter = new FilterGUI("Meal", SCREEN_HEIGHT, SCREEN_WIDTH);
			VBox mealFilterVBox = new VBox(10.0);
			
			ObservableList<String> mealFilterOptions = FXCollections.observableArrayList(
					"Calories",
					"Fat",
					"Carbohydrates",
					"Fiber",
					"Protein"
				); // Options to filter food list by
			
			ComboBox<String> mealFiltersCBox = new ComboBox<String>(mealFilterOptions); // Filter CBox
			
			// Set default value of foodFilterCBox to prompt user to select a filter
			mealFiltersCBox.setPromptText("Select a filter");
			
			ObservableList<String> mealFoodCompOptions = FXCollections.observableArrayList(
					"==",
					">=",
					"<="
				); // Types of comparisons available for filters
			
			// ComboBox of comparison operators to determine food filter criteria 
			ComboBox<String> mealCompFiltersCBox = new ComboBox<String>(mealFoodCompOptions);  
			
			// Set default value of foodCompFiltersCBox to "="
			mealCompFiltersCBox.setValue("==");
			
			TextField mealFilterValue = new TextField(""); // user inputs filter value
			
			// Set Width of foodFilterValue 
			mealFilterValue.setPrefWidth(100);
			
			Button mealFilterAddButton = new Button("Add"); // Applies filter and adds to list
			HBox mealFilterHBox = new HBox(10); // HBox to hold group of food filter GUI objects
			ListView<String> mealFilterLView = new ListView<String>(); // list of applied filters
			
			// Set Height of foodFilterLView to ~9% of screen height
			mealFilterLView.setPrefHeight(SCREEN_HEIGHT/11);
			
			HBox mealButtonsHBox = new HBox(10); // HBox to hold "Edit" and "Delete" buttons
			Button mealFoodDeleteFilterButton = new Button("Delete"); 	// Button to delete a filter
			
			/*
			 * EVENT HANDLERS FOR MEAL PANE 
			 */
			
			
			// Add food filter GUI objects to HBox
			mealFilterHBox.getChildren().addAll(mealCompFiltersCBox, mealFilterValue, mealFilterAddButton); 
			
			// Add "Edit" and "Delete" buttons to HBox
			mealButtonsHBox.getChildren().addAll(mealFoodDeleteFilterButton);
			
			// Set all elements onto filter GUI to return
			mealFilterVBox.getChildren().addAll(mealFiltersCBox, mealFilterHBox, mealFilterLView, mealButtonsHBox);
			
		
			// Set GUI Objects on Food Pane
			mealPaneVBox.getChildren().addAll(newMealButton, queryMealField, mealListView, dispMealsLabel, 
					mealFilterVBox, displayMealButton, blankLabel, mealPaneLabel);
			
			// Set Food (left) and Meal (right) pane in main BoarderPane
			boarderPane.setLeft(foodPaneVBox);
			boarderPane.setRight(mealPaneVBox);
			
			// Set IDs for application.css to use for formatting
			boarderPane.setId("root");
			root.setId("scrollPane");
			mealPaneLabel.setId("meals");
			mealPaneVBox.setId("right");
			foodPaneLabel.setId("foods");
			foodPaneVBox.setId("left");
			displayPaneLabel.setId("centerLabel");
			topBPane.setId("top");
			centerBPane.setId("center");
			
			// Show Stage
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Helper method that returns list of names of all foods. Also updates HashMap of FoodItems
	 * @return foodNames a list of type string that holds all names of foods
	 */
	private void updateFoodNamesList(List<FoodItem> foodItems) {
		List<String> foodNames = new ArrayList<String>();
		
		// Get all food names and add to return list
		for(int i = 0; i < foodItems.size(); i++) {
			foodNames.add(foodItems.get(i).getName());
			//put items in Hash Map
			this.foodItemsHMap.put(foodItems.get(i).getName(), foodItems.get(i));
		}
		this.displayedFoodNamesList = foodNames;
	}
	private void updateFoodListSize(Label displayLabel) {
		this.numFoodItems = foodData.getAllFoodItems().size();
		this.numFoodsDisplayed = this.displayedFoodNamesList.size();
		displayLabel.setText("Displaying " + this.numFoodsDisplayed 
				+ " of " + this.numFoodItems + " foods"); // # of total foods disp.
		
	}
}
