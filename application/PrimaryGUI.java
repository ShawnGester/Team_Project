package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Represents the Primary GUI that the user will interact with. Initializes all GUI objects in 
 * constructor. Handles all action events as well as the addition of stages via pop-up windows.
 * @author Cole Thomson
 * @author Shawn Ge
 * @author Alex Fusco
 * @author Danica Fliss
 * @author Leah Witt
 *
 */
public class PrimaryGUI {
	
	// Food Related Fields
	private FoodData foodData; 							// Food Data used in Meal Planner Program
	private HashMap<String, FoodItem> foodItemsHMap;	// Food items
	private List<String> displayedFoodNamesList; 		// Food names
	private List<String> foodFilterRules; 				// Filter rules for food items
	private boolean foodNotFoundFlag; 					// Flag for showing food not found message
	private int numFoodItems; 							// Number of food items in the program
	private int numFoodsDisplayed; 						// Number of food items displayed
	
	// Meal Related Fields
	private MealData mealData; 							// Meal Data used in Meal Planner Program
	private HashMap<String, Meal> mealItemsHMap; 		// Meals
	private List<String> displayedMealNamesList; 		// Meal names
	private List<String> mealFilterRules; 				// Filter rules for meal items
	private boolean mealNotFoundFlag; 					// Flag for showing meal not found message
	private int numMeals; 								// Number of meals in the program
	private int numMealsDisplayed; 						// Number of meal items displayed
	private PieChart chart;								// Pie chart for displaying meal analysis
	
	// Width and height (respectively) of screen that is displaying GUI
	private static final double SCREEN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth(); 
	private static final double SCREEN_HEIGHT = Screen.getPrimary().getVisualBounds().getWidth();
	
	/**
	 * Handles Primary GUI for Meal Planner program. Single stage with single scene (excluding 
	 * generated pop-ups) that has three main panes. First pane on left-hand side of screen is 
	 * "Food Pane". Food Pane allows for user to load, add, remove, filter and save food lists. 
	 * The second pane in the middle of the screen is "Details Pane". Details Pane displays 
	 * output information to user. Output information is considered food or meal information that
	 * user can view but can not interact with. The third pane on the right-hand side of the screen
	 * is the "Meal Pane". Meal pane allows users to create, remove, and view meals that are 
	 * comprised of foods available in the food list.
	 * 
	 * @param foodData - all food items
	 * @param primaryStage - primaryStage that contains primary scene
	 */
	public PrimaryGUI(FoodData foodData, MealData mealData, Stage primaryStage) {
		try {
			// Instantiate food related fields
			this.foodData = foodData; 
			this.foodItemsHMap = new HashMap<String, FoodItem>();
			this.displayedFoodNamesList = new ArrayList<String>();
			this.foodFilterRules = new ArrayList<String>();
			this.foodNotFoundFlag = false;
			this.numFoodItems = 0;
			
			// Instantiate meal related fields
			this.mealData = mealData;
			this.mealItemsHMap = new HashMap<String, Meal>();
			this.displayedMealNamesList = new ArrayList<String>();
			this.mealFilterRules = new ArrayList<String>();
			this.mealNotFoundFlag = false;
			this.numMeals = 0;
			
			ScrollPane root = new ScrollPane(); 		// Primary Pane for GUI, allows scrolling
			BorderPane mainBPane = new BorderPane();	// Structure for visual display	
			Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT); // Create Scene
			
			// Set main BorderPane on ScrollPane
			root.setContent(mainBPane);	
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
                        
			Label progNameLabel = new Label("Meal Planner"); 	// Name of program (top pane)
			progNameLabel.setId("title"); 						// Set CSS Id
			Label displayPaneLabel = new Label("Details"); 		// Header for display (center pane)
			
			BorderPane topBPane = new BorderPane(progNameLabel); 	// BPane for top of GUI
			BorderPane centerBPane = new BorderPane(); 				// BPane for center of GUI
			BorderPane displayBPane = new BorderPane(); 			// BPane nested in centerBPane 
			
			// Set nested panes in main BPane
			mainBPane.setTop(topBPane); 
			mainBPane.setCenter(centerBPane);
			
			// Set "Details" Label and nested BPane in center BPane 
			centerBPane.setTop(displayPaneLabel);
			centerBPane.setCenter(displayBPane);
			
			// Format BPane objects
			BorderPane.setMargin(displayBPane, new Insets(30,10,10,10)); 
			BorderPane.setAlignment(displayPaneLabel, Pos.TOP_LEFT); 
		
			/*
			 * Food Pane (left section of main BoarderPane)
			 */
			
			VBox foodPaneVBox = new VBox(10.0); // Holds food pane GUI objects
			
			// Set Width of foodPaneVBox to 25% of screen width and format position
			foodPaneVBox.setPrefWidth(SCREEN_WIDTH / 4);
			foodPaneVBox.setAlignment(Pos.TOP_LEFT);
			foodPaneVBox.setPadding(new Insets(0,0,0,10));
			 
			Button newFoodButton = new Button("+ New Food"); 		// Upload food list or add food 
			TextField queryFoodField = new TextField(); 			// Food query field
			ListView<String> foodLView = new ListView<String>(); 	// Filtered list of cur foods
			
			// Set prompting text for user to search for a food
			queryFoodField.setPromptText("Search for a food...");
			
				
			// Set Width of food ListView to ~24% of the screen width
			foodLView.prefWidthProperty().set(SCREEN_WIDTH/4.2);
			// Set Height of food ListView to ~14% of screen height
			foodLView.setPrefHeight(SCREEN_HEIGHT/7);
			
			Label foodPaneLabel = new Label("Foods"); // Heading for food pane (left pane)
			Label dispFoodsLabel = new Label("Displaying 0 of 0 foods"); // # of total foods disp.
			Label foodNameDetailsPane = new Label(); // Food Label used in details pane
			Button displayFoodButton = new Button("Display Food"); 	// Button to display food details
			Button downloadFoodButton = new Button("Download Food List"); // Download list of foods
			VBox foodFilterVBox = new VBox(10.0); // Holds food filtering Nodes
			
			ObservableList<String> filterOptions = FXCollections.observableArrayList(
					"Calories",
					"Fat",
					"Carbohydrates",
					"Fiber",
					"Protein"
				); // Options to filter food list by
			
			ComboBox<String> filtersCBox = new ComboBox<String>(filterOptions); // Filter CBox
			
			// Set prompting text for user to select a filter
			filtersCBox.setPromptText("Select a filter");
			
			ObservableList<String> foodCompOptions = FXCollections.observableArrayList(
					"==",
					">=",
					"<="
				); // Types of comparisons available for filters
			
			ComboBox<String> compFiltersCBox = new ComboBox<String>(foodCompOptions); // (==,<=,>=) 
			
			// Set default value of foodCompFiltersCBox to "=="
			compFiltersCBox.setValue("==");
			
			TextField filterValue = new TextField(""); // value for filter to use for comparisons
			
			// Set Width of foodFilterValue 
			filterValue.setPrefWidth(100);
			
			Button foodAddFilterButton = new Button("Add"); // Applies filter and adds to list
			HBox filterHBox = new HBox(10); // Holds group of food filter GUI objects
			ListView<String> filterLView = new ListView<String>(); // List of applied filters
			
			// Set Height of foodFilterLView to ~9% of screen height
			filterLView.setPrefHeight(SCREEN_HEIGHT/11);
			
			HBox buttonsHBox = new HBox(10); // Holds "Edit" and "Delete" buttons
			Button foodDeleteFilterButton = new Button("Delete"); // Button to delete a filter
			
			/**
			 * EVENT HANDLERS FOOD PANE 
			 */
			
			// "New Food" Button clicked
			newFoodButton.setOnAction((ae) -> {

				new PopUpFood(foodData); // Pop-up for user to handle food addition
				
				// Update list of food items [after user inputs food item(s)]
				updateFoodNamesList(foodData.getAllFoodItems());
				foodLView.setItems(FXCollections.observableList(displayedFoodNamesList));
				
				// Update list by applying current filters to food list
				updateFoodNamesList(foodData.filterByNutrients(foodFilterRules));
				foodLView.setItems(FXCollections.observableList(displayedFoodNamesList));
				
				// Update size of food list and number of foods displayed
				updateFoodListSize(dispFoodsLabel);
			});
			
			// Query food in list by text 
			queryFoodField.setOnAction((ae) -> {
				
				String searchedFood = queryFoodField.getText(); // User's text to use as query
				
				// Update list by applying user's text as a filter
				updateFoodNamesList(foodData.filterByName(searchedFood));
				foodLView.setItems(FXCollections.observableList(displayedFoodNamesList));
				
				// Update size of food list and number of foods displayed
	            updateFoodListSize(dispFoodsLabel);
	            
				// If no foods match the search
				if(foodData.filterByName(searchedFood).size()==0) {
					if (!this.foodNotFoundFlag) {
						// Inform user that no foods exist
						Label foodDNEErrorMessage = new Label("*Error: no foods exist.");
						foodDNEErrorMessage.setFont(new Font(10));
						foodDNEErrorMessage.setTextFill(Color.RED);
						foodPaneVBox.getChildren().add(2, foodDNEErrorMessage);
						this.foodNotFoundFlag = true;
					}
				}
				// Else clear error message if up
				else {
					// If error message is up, delete it and reset flag
					if(this.foodNotFoundFlag) {
						foodPaneVBox.getChildren().remove(2);
						this.foodNotFoundFlag = false;
					}
				}	
			}); 
			
			// Add comparator-style filter to list of rules
			foodAddFilterButton.setOnAction((ae) -> {
				
				try {
					Double comparableValue = Double.parseDouble(filterValue.getText()); // comp val
					String comparator = compFiltersCBox.getValue(); // (==,<=,>=) selected
					String nutrientFilter = filtersCBox.getValue(); // nutrient to filter by

					// If nutrient filter is selected and value to compare to is not negative
					if (nutrientFilter != null && comparableValue >= 0) {
						String rule = nutrientFilter + " " + comparator + " " + comparableValue;
						// If the rule already exists, don't add it
						if (!this.foodFilterRules.contains(rule)) {
							this.foodFilterRules.add(rule);
							
							// Update list by applying food query rule
							updateFoodNamesList(this.foodData.filterByNutrients(this.foodFilterRules));
							foodLView.setItems(FXCollections.observableList(displayedFoodNamesList));
							filterLView.setItems(FXCollections.observableList(foodFilterRules));
							filterValue.setText("");
							
							// update size of food list and number of foods displayed
							updateFoodListSize(dispFoodsLabel);
						}
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			});
			
			// Delete Filter from list of rules (one at a time)
			foodDeleteFilterButton.setOnAction((ae) -> {
				
				String ruleToDelete = filterLView.getSelectionModel().getSelectedItem();
				// Find and delete rule from rule list
				for(int i = 0; i < foodFilterRules.size(); i++) {
					if(ruleToDelete.equals(foodFilterRules.get(i))) {
						foodFilterRules.remove(i);
						break;
					}
				}
				// Update list after removing food query rule
				updateFoodNamesList(this.foodData.filterByNutrients(this.foodFilterRules));
				foodLView.setItems(FXCollections.observableList(displayedFoodNamesList));
				filterLView.setItems(FXCollections.observableList(foodFilterRules));
				filterValue.setText("");
				
				//update size of food list and number of foods displayed
	            updateFoodListSize(dispFoodsLabel);
			});
			
			// Display food data to the detail pane
			displayFoodButton.setOnAction((ae) -> {
				
				String selectedFoodName = foodLView.getSelectionModel().getSelectedItem(); // Food
				foodNameDetailsPane.setText("Food Name: " + selectedFoodName);
				foodNameDetailsPane.setId("foodNameLabel"); // Set CSS Id
				
				VBox nutrientVBox = new VBox();			// Holds nutrient labels
				VBox nutrientValueVBox = new VBox(); 	// Holds nutrient values
				
				// If the chart for meal analysis is showing, remove it
				if(chart != null) {
					displayBPane.setBottom(null);
				}
				
				// Nutrient labels for VBox on left side of BPane in Details Pane
				Label foodIDLabel = new Label();
				Label caloriesLabel = new Label();
				Label fatLabel = new Label();
				Label carbLabel = new Label();
				Label fiberLabel = new Label();
				Label proteinLabel = new Label();
				
				// Nutrient value labels for VBox on right side of BPane in Details Pane 
				Label caloriesVal= new Label();
				Label fatVal = new Label();
				Label carbVal = new Label();
				Label fiberVal = new Label();
				Label proteinVal = new Label();
				
				FoodItem selectedFood = this.foodItemsHMap.get(selectedFoodName); // selected food
				
				// Get attributes about the food
				String foodID = selectedFood.getID();
				double calories = selectedFood.getNutrientValue("calories");
				double fat = selectedFood.getNutrientValue("fat");
				double carbs = selectedFood.getNutrientValue("carbohydrate");
				double fiber = selectedFood.getNutrientValue("fiber");
				double protein = selectedFood.getNutrientValue("protein");
				
				// Get attribute labels padded with periods on right side
				String calLabelText = padStrWithPer("Calories (kcal):",200);
				String fatLabelText = padStrWithPer("Fat (g):",200);
				String carbLabelText = padStrWithPer("Carbs (g):",200);
				String fiberLabelText = padStrWithPer("Fiber (g):",200);
				String proteinLabelText = padStrWithPer("Protein (g):",200);
				
				// Set text of Food attribute labels
				foodIDLabel.setText("Food ID: " + foodID);
				caloriesLabel.setText(calLabelText);
				fatLabel.setText(fatLabelText);
				carbLabel.setText(carbLabelText);
				fiberLabel.setText(fiberLabelText);
				proteinLabel.setText(proteinLabelText);
				
				// Set text of Food value labels
				caloriesVal.setText("" + calories);
				fatVal.setText("" + fat);
				carbVal.setText("" + carbs);
				fiberVal.setText("" + fiber);
				proteinVal.setText("" + protein);
				
				// Add attribute labels to vbox
				nutrientVBox.getChildren().addAll(foodIDLabel, caloriesLabel, fatLabel
						, carbLabel, fiberLabel, proteinLabel);
				// Add Nutrient Values to VBox
				nutrientValueVBox.getChildren().addAll(new Label(), caloriesVal, fatVal, carbVal
						, fiberVal, proteinVal);
				
				// Set nodes in display pane
				displayBPane.setTop(foodNameDetailsPane);
				displayBPane.setLeft(nutrientVBox); 
				displayBPane.setRight(nutrientValueVBox);
				
				nutrientVBox.setSpacing(15);		// Spacing between labels
				nutrientValueVBox.setSpacing(15);	// Spacing between values
				
				// Set width of Panes in Display Pane
				nutrientVBox.prefWidthProperty().set(centerBPane.getWidth()*.65);
				nutrientValueVBox.prefWidthProperty().set(centerBPane.getWidth()*.2);
				
				// Set alignments in panes and margins for VBoxes 
				BorderPane.setAlignment(foodNameDetailsPane, Pos.TOP_CENTER);
				BorderPane.setAlignment(nutrientValueVBox, Pos.TOP_LEFT);
				BorderPane.setMargin(nutrientVBox, new Insets(35,0,0,10));
				BorderPane.setMargin(nutrientValueVBox, new Insets(35,0,0,0));
			});
			
			// Download list of foods to file
			downloadFoodButton.setOnAction((ae) -> {
				FileChooser fc = new FileChooser(); // Allow use of file explorer
				File selectedFile = null;			// File to save to
				List<FoodItem> foodList = foodData.getAllFoodItems();
				
				fc.setTitle("Download Food List");
				fc.getExtensionFilters().addAll(
						new ExtensionFilter("Text Files", "*.txt"),
						new ExtensionFilter("Comma Seperated Values", "*.csv"),
						new ExtensionFilter("All Files", "*.*"));
				
				// Set initial directory shown as application (if exists)
				fc.setInitialDirectory(new File("application\\"));
					
				try {
					selectedFile = fc.showSaveDialog(primaryStage);
				} catch (IllegalArgumentException e) {
					// Directory does not exist on users cpu, just open standard directory
					fc.setInitialDirectory(null);
					selectedFile = fc.showSaveDialog(primaryStage);
				}
				
				// If a file was selected, write food list data to that file
				if (selectedFile != null) {
					FileWriter fileWriter = null;
					PrintWriter printWriter = null; 
					try {
						 	fileWriter = new FileWriter(selectedFile);
						    printWriter = new PrintWriter(fileWriter);
						    
						    for (int i = 0; i < foodList.size(); i++) {
						    	printWriter.print(foodList.get(i).getID() + ",");
						    	printWriter.print(foodList.get(i).getName() + ",");
						    	printWriter.print("calories," + foodList.get(i).getNutrientValue("calories") + ",");
						    	printWriter.print("fat," + foodList.get(i).getNutrientValue("fat") + ",");
						    	printWriter.print("carbohydrate," + foodList.get(i).getNutrientValue("carbohydrate") + ",");
						    	printWriter.print("fiber," + foodList.get(i).getNutrientValue("fiber") + ",");
						    	printWriter.println("protein," + foodList.get(i).getNutrientValue("protein"));
						    }
						    
		                } catch (IOException e) {
		                    System.out.println(e.getMessage());
		                } finally {
		                	printWriter.close();
		                }
				} 
			});
			
			// Add food filter GUI objects to HBox
			filterHBox.getChildren().addAll(compFiltersCBox, filterValue, foodAddFilterButton); 
			
			// Add "Edit" and "Delete" buttons to HBox
			buttonsHBox.getChildren().addAll(foodDeleteFilterButton);
			
			// Set all elements onto filter GUI to return
			foodFilterVBox.getChildren().addAll(filtersCBox, filterHBox, filterLView, buttonsHBox);

			// Set GUI Objects on Food Pane
			foodPaneVBox.getChildren().addAll(newFoodButton, queryFoodField, foodLView, dispFoodsLabel,
					foodFilterVBox, displayFoodButton, downloadFoodButton, foodPaneLabel);
			
			/**
			 * Meal Pane (right section of main BoarderPane)
			 */
			
			VBox mealPaneVBox = new VBox(10.0); // Holds meal pane of GUI objects
			
			// Set Width of mealPaneVBox to 25% of screen width and format position
			mealPaneVBox.setPrefWidth(SCREEN_WIDTH / 4);
			mealPaneVBox.setAlignment(Pos.TOP_LEFT);
			mealPaneVBox.setPadding(new Insets(0,0,0,10));
			 
			Button newMealButton = new Button("+ New Meal"); 		// Create meal
			TextField queryMealField = new TextField(); 			// Meal query field
			ListView<String> mealListView = new ListView<String>(); // Filtered list of cur meals

			// Set prompting text for user to search for a meal
			queryMealField.setPromptText("Search for a meal...");
		
			// Set Width of mealListView to ~24% of the screen width
			mealListView.prefWidthProperty().set(SCREEN_WIDTH/4.2);
			// Set Height of mealListView to ~14% of screen height
			mealListView.setPrefHeight(SCREEN_HEIGHT/7);
			
			Label mealPaneLabel = new Label("Meals"); // Heading for meal pane (right pane)
			Label dispMealsLabel = new Label("Displaying 0 of 0 meals"); // # of total meals disp.
			Label mealNameDetailsPane = new Label(); // Meal Label used in details pane
			Label blankLabel = new Label(""); // Blank label for formatting
			Button displayMealButton = new Button("Analyze Meal"); 	// Button to analyze/disp. meal			
			VBox mealFilterVBox = new VBox(10.0); // Holds meal filtering Nodes
			
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
			Button mealDeleteFilterButton = new Button("Delete"); 	// Button to delete a filter
			
			/*
			 * EVENT HANDLERS FOR MEAL PANE 
			 */

			// "New Meal" Button clicked
			newMealButton.setOnAction((ae) -> {

				new PopUpMeal(foodData.getAllFoodItems(), mealData); // Pop-up for meal addition
				
				// Update list od meal items (after addition of a meal)
				updateMealNamesList(mealData.getMealList());
				mealListView.setItems(FXCollections.observableList(displayedMealNamesList));
				
				// Update list by applying current filters to food list
				updateMealNamesList(mealData.filterByNutrient(mealFilterRules));
				mealListView.setItems(FXCollections.observableList(displayedMealNamesList));
				
				//update size of meal list and number of meals displayed
				updateMealListSize(dispMealsLabel);
			});
			
			// Query meal is list by text
			queryMealField.setOnAction((ae) -> {
				
				String searchedMeal = queryMealField.getText(); //User's text to use a query
				
				// Update list by applying user's text as a filter
				updateMealNamesList(mealData.filterByName(searchedMeal));
				mealListView.setItems(FXCollections.observableList(displayedMealNamesList));
				
				// Update size of meal list and number of meals displayed
				updateMealListSize(dispMealsLabel);
				
				// If no meals match the search
				if (mealData.filterByName(searchedMeal).size() == 0) {
					if(!this.mealNotFoundFlag) {
						// inform user that no meals exist 
						Label mealDNEErrorMessage = new Label("*Error: no meals exist.");
						mealDNEErrorMessage.setFont(new Font(10));
						mealDNEErrorMessage.setTextFill(Color.RED);
						mealPaneVBox.getChildren().add(2, mealDNEErrorMessage);
						this.mealNotFoundFlag = true;
					}
				}
				// Else meal doesn't exist, display message
				else {
					// If error message is up, delete it and reset flag
					if (this.mealNotFoundFlag) {
						mealPaneVBox.getChildren().remove(2);
						this.mealNotFoundFlag = false;
					}
				}
			});
			
			
			// Add comparator-style filter to list of rules
			mealFilterAddButton.setOnAction((ae) -> {

				try {
					Double comparableValue = Double.parseDouble(mealFilterValue.getText()); 
					String comparator = mealCompFiltersCBox.getValue(); // (==,<=,>=) selected
					String nutrientFilter = mealFiltersCBox.getValue(); // nutrient to filter by

					// If nutrient filter is selected and value to compare is not negative
					if (nutrientFilter != null && comparableValue >= 0) {
						String rule = nutrientFilter + " " + comparator + " " + comparableValue;
						// If the rule already exists, don't add it
						if (!this.mealFilterRules.contains(rule)) {
							this.mealFilterRules.add(rule);
							updateMealNamesList(this.mealData.filterByNutrient(this.mealFilterRules));
							mealListView.setItems(FXCollections.observableList(displayedMealNamesList));
							mealFilterLView.setItems(FXCollections.observableList(mealFilterRules));
							mealFilterValue.setText("");
							
							// update size of meal list and number of meal displayed
							updateMealListSize(dispMealsLabel);
						}
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			});

			// Delete Filter from list of meal rules (one at a time)
			mealDeleteFilterButton.setOnAction((ae) -> {
				
				String ruleToDelete = mealFilterLView.getSelectionModel().getSelectedItem();
				// Find and delete rule from rule list
				for (int i = 0; i < mealFilterRules.size(); i++) {
					if (ruleToDelete.equals(mealFilterRules.get(i))) {
						mealFilterRules.remove(i);
						break;
					}
				}
				// Update list after removing meal query rule
				updateMealNamesList(this.mealData.filterByNutrient(this.mealFilterRules));
				mealListView.setItems(FXCollections.observableList(displayedMealNamesList));
				mealFilterLView.setItems(FXCollections.observableList(mealFilterRules));
				mealFilterValue.setText("");
				// update size of meal list and number of meals displayed
				updateMealListSize(dispMealsLabel);
				
			});
			
			// Display Meal Button to the detail pane
			displayMealButton.setOnAction((ae) -> {

				String selectedMealName = mealListView.getSelectionModel().getSelectedItem();
				mealNameDetailsPane.setText("Meal Name: " + selectedMealName);
				mealNameDetailsPane.setId("mealNameLabel");
				
				BorderPane nutrientBPane = new BorderPane(); 	// BPane in left of display pane
				BorderPane mealFoodsBPane = new BorderPane(); 	// BPane in right of display pane
				Label nutrientLabel = new Label("Nutrients");	// Nutrient data displayed below
				Label foodListLabel = new Label("Foods");		// List of foods displayed below
				nutrientLabel.setId("nutrientLabel");
				foodListLabel.setId("foodLabel");
				
				ListView<String> mealFoodsListView = new ListView<String>(); // List of foods in meal
				
				// Set Width of mealFoodsListView to ~20% of the screen width
				mealFoodsListView.prefWidthProperty().set(SCREEN_WIDTH/5.5);
				// Set Height of mealFoodsListView to ~14% of screen height
				mealFoodsListView.prefHeightProperty().set(SCREEN_HEIGHT/7);
				
				VBox nutrientVBox = new VBox(); // VBox to hold nutrient labels
				VBox nutrientValueVBox = new VBox(); // VBox to hold nutrient values
				
				// Labels for VBox on left side of BPane in Details Pane
				Label caloriesLabel = new Label();
				Label fatLabel = new Label();
				Label carbLabel = new Label();
				Label fiberLabel = new Label();
				Label proteinLabel = new Label();
				
				// Labels for VBox on right side of BPane in nutrients BPane (nutrient Vals)
				Label caloriesVal = new Label();
				Label fatVal = new Label();
				Label carbVal = new Label();
				Label fiberVal = new Label();
				Label proteinVal = new Label();

				Meal selectedMeal = this.mealItemsHMap.get(selectedMealName); // selected meal
				List<FoodItem> mealFoods = selectedMeal.getFoodList(); //Foods in meal
				List<String> mealFoodsNames = getFoodNamesFromMeal(mealFoods);
				
				// Get attributes about the meal
				double calories = selectedMeal.getNutrientValue("calories");
				double fat = selectedMeal.getNutrientValue("fat");
				double carbs = selectedMeal.getNutrientValue("carbohydrate");
				double fiber = selectedMeal.getNutrientValue("fiber");
				double protein = selectedMeal.getNutrientValue("protein");
				
				// Get attribute labels padded with periods on right side
				String calLabelText = padStrWithPer("Calories (kcal):", 200);
				String fatLabelText = padStrWithPer("Fat (g):", 200);
				String carbLabelText = padStrWithPer("Carbs (g):", 200);
				String fiberLabelText = padStrWithPer("Fiber (g):", 200);
				String proteinLabelText = padStrWithPer("Protein (g):", 200);
				
				// Set text of Food attribute labels
				caloriesLabel.setText(calLabelText);
				fatLabel.setText(fatLabelText);
				carbLabel.setText(carbLabelText);
				fiberLabel.setText(fiberLabelText);
				proteinLabel.setText(proteinLabelText);
				
				// Set text of Meal value labels
				caloriesVal.setText("" + calories);
				fatVal.setText("" + fat);
				carbVal.setText("" + carbs);
				fiberVal.setText("" + fiber);
				proteinVal.setText("" + protein);
				
				// Make pie chart to display nutrient data
				ObservableList<PieChart.Data> pieChartData =
		                FXCollections.observableArrayList(
		                new PieChart.Data("Fat", fat),
		                new PieChart.Data("Carbs", carbs),
		                new PieChart.Data("Fiber", fiber),
		                new PieChart.Data("Protein", protein));
		        this.chart = new PieChart(pieChartData);
		        
		        // Set pie chart properties
		        chart.setTitle("Meal Analysis");
		        chart.setLabelLineLength(10);
		        chart.setLegendSide(Side.LEFT);
		        chart.prefHeightProperty().set(centerBPane.getHeight()*0.45);
		        chart.prefWidthProperty().set(centerBPane.getWidth()*0.45);
		        
				mealFoodsListView.setItems(FXCollections.observableList(mealFoodsNames));
				
				// Add attribute labels to vbox
				nutrientVBox.getChildren().addAll(caloriesLabel, fatLabel, carbLabel, fiberLabel,
						proteinLabel);
				// Add Nutrient Values to VBox
				nutrientValueVBox.getChildren().addAll(caloriesVal, fatVal, carbVal, fiberVal, proteinVal);
				// Add "Nutrients" Label to nutrientsBPane
				nutrientBPane.setTop(nutrientLabel);
				// Add "Foods" Label to mealFoodsBPane
				mealFoodsBPane.setTop(foodListLabel);
				
				// Add ListView of Foods to center of mealFoodsBPane
				mealFoodsBPane.setCenter(mealFoodsListView);
				
				// Set left and right nodes in nutrientsBPane
				nutrientBPane.setLeft(nutrientVBox);
				nutrientBPane.setRight(nutrientValueVBox);
				
				// Set nodes in display pane
				displayBPane.setTop(mealNameDetailsPane);
				displayBPane.setLeft(nutrientBPane);
				displayBPane.setRight(mealFoodsBPane);
				displayBPane.setBottom(chart);
				nutrientVBox.setSpacing(15); // Spacing between labels
				nutrientValueVBox.setSpacing(15); // Spacing between values
				
				// Set width of Panes in Display Pane
				nutrientVBox.prefWidthProperty().set(centerBPane.getWidth() * 0.32);
				nutrientValueVBox.prefWidthProperty().set(centerBPane.getWidth() * 0.13);
				
				// Set alignments in panes and margins for Nodes
				BorderPane.setAlignment(mealNameDetailsPane, Pos.TOP_CENTER);
				BorderPane.setAlignment(nutrientValueVBox, Pos.TOP_LEFT);
				BorderPane.setAlignment(nutrientLabel, Pos.TOP_CENTER);
				BorderPane.setAlignment(foodListLabel, Pos.TOP_CENTER);
				BorderPane.setMargin(nutrientLabel, new Insets(15,0,0,0));
				BorderPane.setMargin(foodListLabel, new Insets(15,0,0,0));
				BorderPane.setMargin(nutrientVBox, new Insets(20, 0, 0, 7));
				BorderPane.setMargin(nutrientValueVBox, new Insets(20, 0, 0, 0));
				BorderPane.setMargin(mealFoodsListView, new Insets(20, 7, 0, 0));
				BorderPane.setMargin(chart, new Insets(10,0,0,0));

			});


			// Add food filter GUI objects to HBox
			mealFilterHBox.getChildren().addAll(mealCompFiltersCBox, mealFilterValue, mealFilterAddButton); 
			
			// Add "Edit" and "Delete" buttons to HBox
			mealButtonsHBox.getChildren().addAll(mealDeleteFilterButton);
			
			// Set all elements onto filter GUI to return
			mealFilterVBox.getChildren().addAll(mealFiltersCBox, mealFilterHBox, mealFilterLView, mealButtonsHBox);
			
		
			// Set GUI Objects on Food Pane
			mealPaneVBox.getChildren().addAll(newMealButton, queryMealField, mealListView, dispMealsLabel, 
					mealFilterVBox, displayMealButton, blankLabel, mealPaneLabel);
			
			// Set Food (left) and Meal (right) pane in main BoarderPane
			mainBPane.setLeft(foodPaneVBox);
			mainBPane.setRight(mealPaneVBox);
			
			// Set IDs for application.css to use for formatting
			mainBPane.setId("root");
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
	 * Helper method that updates list of names of all foods. Also updates HashMap of FoodItems
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
	/**
	 * Helper method for updating the label that displays number of foods being viewed
	 * @param displayLabel updated label of number of foods that are displayed out of total
	 */
	private void updateFoodListSize(Label displayLabel) {
		
		this.numFoodItems = foodData.getAllFoodItems().size();
		this.numFoodsDisplayed = this.displayedFoodNamesList.size();
		displayLabel.setText("Displaying " + this.numFoodsDisplayed 
				+ " of " + this.numFoodItems + " foods"); // # of total foods disp.	
	}
	
	/**
	 * Helper method that updates list of names of all foods. Also updates HashMap of FoodItems
	 * @return foodNames a list of type string that holds all names of foods
	 */
	private void updateMealNamesList(List<Meal> mealItems) {
		List<String> mealNames = new ArrayList<String>();
		
		// Get all food names and add to return list
		for(int i = 0; i < mealItems.size(); i++) {
			mealNames.add(mealItems.get(i).getName());
			//put items in Hash Map
			this.mealItemsHMap.put(mealItems.get(i).getName(), mealItems.get(i));
		}
		this.displayedMealNamesList = mealNames;
	}
	/**
	 * Helper method for updating the label that displays number of meals being viewed
	 * @param displayLabel updated label of number of meals that are displayed out of total
	 */
	private void updateMealListSize(Label displayLabel) {
		this.numMeals = mealData.getMealList().size();
		this.numMealsDisplayed = this.displayedMealNamesList.size();
		displayLabel.setText("Displaying " + this.numMealsDisplayed 
				+ " of " + this.numMeals + " meals"); // # of total foods disp.	
	}
	/**
	 * Helper method to get list of food names from list of foods for use in diplaying the
	 * foods in a meal
	 * @param foods List of foods
	 * @return foodNames list of food names
	 */
	private List<String> getFoodNamesFromMeal(List<FoodItem> foods) {
		List<String> foodNames = new ArrayList<String>();
		for(int i = 0; i < foods.size(); i++) {
			foodNames.add(foods.get(i).getName());
		}
		return foodNames;
	}
	
	/**
	 * Helper method that pads string with periods
	 * @param str string to pad with periods
	 * @param totalLength total length of string to be after padding
	 * @return str updated string with padding
	 */
	private String padStrWithPer(String str, int totalLength) {

		StringBuilder sb = new StringBuilder();
		char[] padding = new char[totalLength - str.length()];
		Arrays.fill(padding, '.');
		str += sb.append(padding).toString();

		return str;
	}
	
}
