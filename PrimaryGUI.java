package application;

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
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Represents the Primary GUI that the user will interact with. Initializes all GUI objects in 
 * constructor.
 * @author Cole Thomosn
 *
 */
public class PrimaryGUI {
	
	public FoodData foodData; // Food Data used in Meal Planner Program
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
			
			Label progNameLabel = new Label("Meal Planner"); // Name of program (top pane)
			progNameLabel.setId("title");
			Label displayPaneLabel = new Label("Details");	 // Heading for display (center pane)
			BorderPane topBPane = new BorderPane(progNameLabel); // BPane for top of GUI
			BorderPane centerBPane = new BorderPane(displayPaneLabel); // BPane for center of GUI
			
			// Set nested panes in main BoarderPane
			boarderPane.setTop(topBPane);
			boarderPane.setCenter(centerBPane);
			
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
			TextField queryFoodField = new TextField("Search for a food..."); // Food query field
			ListView<String> foodListView = new ListView<String>(); // Filtered list of cur foods
			
			// Set Width of foodListView to ~24% of the screen width
			foodListView.prefWidthProperty().set(SCREEN_WIDTH/4.2);
			// Set Height of foodListView to ~14% of screen height
			foodListView.setPrefHeight(SCREEN_HEIGHT/7);
			
			Label dispFoodsLabel = new Label("Displaying 0 of 0 foods"); // # of total foods disp.
			ObservableList<String> filterOptions = FXCollections.observableArrayList(
					"Calories",
					"Fat",
					"Carbohydrates",
					"Fiber",
					"Protein"
				); // Options to filter food list by
			
			ComboBox<String> foodFiltersCBox = new ComboBox<String>(filterOptions); // Filter CBox
			
			// Set default value of foodFilterCBox to prompt user to select a filter
			foodFiltersCBox.setValue("Select a filter");
			
			ObservableList<String> foodCompOptions = FXCollections.observableArrayList(
					"=",
					">=",
					"<="
				); // Types of comparisons available for filters
			
			// ComboBox of comparison operators to determine food filter criteria 
			ComboBox<String> foodCompFiltersCBox = new ComboBox<String>(foodCompOptions);  
			
			// Set default value of foodCompFiltersCBox to "="
			foodCompFiltersCBox.setValue("=");
			
			TextField foodFilterValue = new TextField(""); // user inputs filter value
			
			// Set Width of foodFilterValue 
			foodFilterValue.setPrefWidth(100);
			
			Button foodFilterAddButton = new Button("Add"); // Applies filter and adds to list
			HBox foodFilterHBox = new HBox(10); // HBox to hold group of food filter GUI objects
			ListView<String> foodFilterLView = new ListView<String>(); // list of applied filters
			
			// Set Height of foodFilterLView to ~9% of screen height
			foodFilterLView.setPrefHeight(SCREEN_HEIGHT/11);
			
			HBox buttonsHBox = new HBox(10); // HBox to hold "Edit" and "Delete" buttons
			Button foodEditFilterButton = new Button("Edit"); 		// Button to edit a filter
			Button foodDeleteFilterButton = new Button("Delete"); 	// Button to delete a filter
			Button displayFoodButton = new Button("Display Food"); 	// Button to display food details
			Button downloadFoodButton = new Button("Download Food List"); // download list of foods
			Label foodPaneLabel = new Label("Foods"); // Heading for food pane (left pane)
			
			// Add food filter GUI objects to HBox
			foodFilterHBox.getChildren().addAll(foodCompFiltersCBox, foodFilterValue, foodFilterAddButton); 
			
			// Add "Edit" and "Delete" buttons to HBox
						buttonsHBox.getChildren().addAll(foodEditFilterButton, foodDeleteFilterButton);
			
			// Set GUI Objects on Food Pane
			foodPaneVBox.getChildren().addAll(newFoodButton, queryFoodField, foodListView, dispFoodsLabel, foodFiltersCBox, 
					foodFilterHBox, foodFilterLView, buttonsHBox, displayFoodButton, downloadFoodButton, foodPaneLabel);
			
			
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
			TextField queryMealField = new TextField("Search for a meal..."); // Meal query field
			ListView<String> mealListView = new ListView<String>(); // Filtered list of cur meals
			
			// Set Width of mealListView to ~24% of the screen width
			mealListView.prefWidthProperty().set(SCREEN_WIDTH/4.2);
			// Set Height of mealListView to ~14% of screen height
			mealListView.setPrefHeight(SCREEN_HEIGHT/7);
			
			Label dispMealsLabel = new Label("Displaying 0 of 0 meals"); // # of total meals disp.
			ObservableList<String> filterOptions2 = FXCollections.observableArrayList(
					"Calories",
					"Fat",
					"Carbohydrates",
					"Fiber",
					"Protein"
				); // Options to filter meal list by
			
			ComboBox<String> mealFiltersCBox = new ComboBox<String>(filterOptions2); // Filter CBox
			
			// Set default value of foodFilterCBox to prompt user to select a filter
			mealFiltersCBox.setValue("Select a filter");
			
			ObservableList<String> mealCompOptions = FXCollections.observableArrayList(
					"=",
					">=",
					"<="
				); // Types of comparisons available for filters
			
			// ComboBox of comparison operators to determine meal filter criteria 
			ComboBox<String> mealCompFiltersCBox = new ComboBox<String>(mealCompOptions);  
			
			// Set default value of foodCompFiltersCBox to "="
			mealCompFiltersCBox.setValue("=");
			
			TextField mealFilterValue = new TextField(""); // user inputs filter value
			
			// Set Width of mealFilterValue 
			mealFilterValue.setPrefWidth(100);
			
			Button mealFilterAddButton = new Button("Add"); // Applies filter and adds to list
			HBox mealFilterHBox = new HBox(10); // HBox to hold group of meal filter GUI objects
			ListView<String> mealFilterLView = new ListView<String>(); // list of applied filters
			
			// Set Height of mealFilterLView to ~9% of screen height
			mealFilterLView.setPrefHeight(SCREEN_HEIGHT/11);
			
			HBox buttonsHBox2 = new HBox(10); // HBox to hold "Edit" and "Delete" buttons
			Button mealEditFilterButton = new Button("Edit"); 		// Button to edit a filter
			Button mealDeleteFilterButton = new Button("Delete"); 	// Button to delete a filter
			Button displayMealButton = new Button("Analyze Meal"); 	// Button to analyze/disp. meal
			Label blankLabel = new Label(""); // Blank label for formatting
			Label mealPaneLabel = new Label("Meals"); // Heading for meal pane (right pane)
			
			// Add food filter GUI objects to HBox
			mealFilterHBox.getChildren().addAll(mealCompFiltersCBox, mealFilterValue, mealFilterAddButton); 
			
			// Add "Edit" and "Delete" buttons to HBox
			buttonsHBox2.getChildren().addAll(mealEditFilterButton, mealDeleteFilterButton);
			
			// Set GUI Objects on Food Pane
			mealPaneVBox.getChildren().addAll(newMealButton, queryMealField, mealListView, dispMealsLabel, mealFiltersCBox, 
					mealFilterHBox, mealFilterLView, buttonsHBox2, displayMealButton, blankLabel, mealPaneLabel);
			
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
}
