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

public class PrimaryGUI {
	public FoodData foodData;
	//	public FoodItemAddForm foodAddForm;
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
			
			/*
			 *FIXME Stopped here 
			 */
			ComboBox<String> filters = new ComboBox<String>(filterOptions);
			filters.setValue("Select a filter");
			
			ObservableList<String> compOptions = FXCollections.observableArrayList(
					"=",
					">=",
					"<="
				);
			ComboBox<String> compFilters = new ComboBox<String>(compOptions);
			compFilters.setValue("=");
			
			TextField queryValue = new TextField("");
			queryValue.setPrefWidth(100);
			
			Button add = new Button("Add");
			
			HBox filter = new HBox(10);
			filter.getChildren().addAll(compFilters, queryValue, add); 
			
			ListView<String> filterList = new ListView<String>(); 
			filterList.setPrefHeight(SCREEN_HEIGHT/11);
			
			HBox buttons = new HBox(10);
			Button edit = new Button("Edit");
			Button delete = new Button("Delete");
			
			buttons.getChildren().addAll(edit, delete);
			
			//FILTERGUI END
			
			Button displayFood = new Button("Display Food");
			Button downloadFood = new Button("Download Food List");
			Label foods = new Label("Foods");
			
			// Set GUI Objects on Food Pane
			foodPaneVBox.getChildren().addAll(newFoodButton, queryFoodField, foodListView, dispFoodsLabel, filters, 
					filter, filterList, buttons, displayFood, downloadFood, foods);
			
			
			/**
			 * Meal Pane
			 */

			Button newMeal = new Button("+ New Meal");
			

			VBox mealFlow = new VBox(10.0); //gives vertical spacing between
			
			mealFlow.setPrefWidth(SCREEN_WIDTH / 4);
			mealFlow.setAlignment(Pos.TOP_LEFT); //left aligns all members of flow pane
			mealFlow.setPadding(new Insets(0,0,0,10));
			
			TextField queryMeal = new TextField("Search for a meal...");
			
			ListView<String> mealList = new ListView<String>();
			mealList.setPrefHeight(SCREEN_HEIGHT/7);
//			mealList.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.25);
			mealList.prefWidthProperty().set(SCREEN_WIDTH/4.2);
			//FIXME: event handler to foods
			Label selectedMeals = new Label("Displaying 0 of 0 meals"); //number of foods out of total after filter
			
			//FILTERGUI START
			ObservableList<String> mealFilterOptions = FXCollections.observableArrayList(
					"Calories",
					"Fat",
					"Carbohydrates",
					"Fiber",
					"Protein"
				); // Options to filter food list by
			ComboBox mealFilters = new ComboBox(mealFilterOptions);
			mealFilters.setValue("Select a filter");
			ObservableList<String> mealCompOptions = FXCollections.observableArrayList(
					"=",
					">=",
					"<="
				); 
			ComboBox mealCompFilters = new ComboBox(mealCompOptions);
			mealCompFilters.setValue("=");
			TextField mealQueryValue = new TextField("");
			mealQueryValue.setPrefWidth(100);
			
			Button mealAdd = new Button("Add");
			HBox mealFilter = new HBox(10);
			mealFilter.getChildren().addAll(mealCompFilters, mealQueryValue, mealAdd); 
			
			ListView<String> mealFilterList = new ListView<String>();
//			mealFilterList.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.20);
			mealFilterList.setPrefHeight(SCREEN_HEIGHT/11);
			HBox mealButtons = new HBox(20);
			Button mealEdit = new Button("EDIT");
			Button mealDelete = new Button("DELETE");
			mealButtons.getChildren().addAll(mealEdit, mealDelete);
			//FILTERGUI END
			
			Button displayMeals = new Button("Display Meals");
			Label meals = new Label("Meals");
			
			Label empty = new Label(""); //FIXME is this ok?
			
			mealFlow.getChildren().addAll(newMeal, queryMeal, mealList, selectedMeals, mealFilters,
					mealFilter, mealFilterList, mealButtons, displayMeals, empty, meals);
			
			
			// Set Food (left) and Meal (right) pane in main BoarderPane
			boarderPane.setLeft(foodPaneVBox);
			boarderPane.setRight(mealFlow);
			
			
			// Set IDs for application.css to use for formatting
			boarderPane.setId("root");
			root.setId("scrollPane");
			meals.setId("meals");
			mealFlow.setId("right");
			foods.setId("foods");
			foodPaneVBox.setId("left");
			displayPaneLabel.setId("centerLabel");
			topBPane.setId("top");
			centerBPane.setId("center");
			
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
