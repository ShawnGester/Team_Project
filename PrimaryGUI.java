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
	private static final double LEFT_INSET = 10;	// Left Insets for formatting GUI Elements
	private static final double RIGHT_INSET = 0;	// Right Insets for formatting GUI Elements
	private static final double TOP_INSET = 0;		// Top Insets for formatting GUI Elements
	private static final double BOTTOM_INSET = 0;	// Bottom Insets for formatting GUI Elements
	
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
			Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
			
			root.setContent(boarderPane);	// Set BoarderPane on ScrollPane
			root.setFitToWidth(true);		// Fit contents of ScrollPane to width of pane
			root.setPannable(true);			// Allow ScrollPane to be pannable
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);	// Set the Scence on the Stage
			
			
			
			
			
			Button newFood = new Button("+ New Food");
			Label topLabel = new Label("Meal Planner");
			Label centerLabel = new Label("Details");
			
			BorderPane top = new BorderPane(topLabel);
			BorderPane center = new BorderPane(centerLabel);
			
			
			boarderPane.setTop(top);
			boarderPane.setCenter(center);
			
			BorderPane.setAlignment(centerLabel, Pos.TOP_LEFT); 
			

			/*
			 * FOOD PANE 
			 */
			VBox foodPane = new VBox(10.0); //gives vertical spacing between 
			
			foodPane.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.27);
			foodPane.setAlignment(Pos.TOP_LEFT); //left aligns all members of flow pane
			foodPane.setPadding(new Insets(0,0,0,10));
			foodPane.setPrefWidth(SCREEN_WIDTH / 4);
			
			
			
			TextField queryFood = new TextField("Search for a food...");
			
			ListView<String> foodList = new ListView<String>();
			foodList.prefWidthProperty().set(SCREEN_WIDTH/4.2);
			foodList.setPrefHeight(SCREEN_HEIGHT/8);
			

			Label selectedFoods = new Label("Displaying 0 of 0 foods"); 
			

			ObservableList<String> filterOptions = FXCollections.observableArrayList(
					"Calories",
					"Protein",
					"Fat"
				); 

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
			filter.getChildren().addAll(compFilters, queryValue, add); //FIXME: queryValue and add not staying on same line??
			
			ListView<String> filterList = new ListView<String>(); 
			filterList.setPrefHeight(SCREEN_HEIGHT/10);
			
			HBox buttons = new HBox(10);
			Button edit = new Button("Edit");
			Button delete = new Button("Delete");
			
			buttons.getChildren().addAll(edit, delete);
			
			//FILTERGUI END
			
			Button displayFood = new Button("Display Food");
			Button downloadFood = new Button("Download Food List");
			Label foods = new Label("Foods");
			
			// Set GUI Objects on Food Pane
			foodPane.getChildren().addAll(newFood, queryFood, foodList, selectedFoods, filters, 
					filter, filterList, buttons, displayFood, downloadFood, foods);
			
			
			/**
			 * Meal Pane
			 */

			Button newMeal = new Button("+ New Meal");
			

			VBox mealFlow = new VBox(10.0); //gives vertical spacing between
			
			mealFlow.setPrefWidth(SCREEN_WIDTH / 4);
			mealFlow.setAlignment(Pos.TOP_LEFT); //left aligns all members of flow pane
			mealFlow.setPadding(new Insets(0,RIGHT_INSET,0,LEFT_INSET));
			boarderPane.setRight(mealFlow);
			TextField queryMeal = new TextField("Search for a meal...");
			
			ListView<String> mealList = new ListView<String>();
			mealList.setPrefHeight(SCREEN_HEIGHT/8);
//			mealList.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.25);
			mealList.prefWidthProperty().set(SCREEN_WIDTH/4.2);
			//FIXME: event handler to foods
			Label selectedMeals = new Label("Displaying 0 of 0 meals"); //number of foods out of total after filter
			
			//FILTERGUI START
			ObservableList<String> mealFilterOptions = FXCollections.observableArrayList(
					"Calories",
					"Protein",
					"Fat"
				); //FIXME:
			ComboBox mealFilters = new ComboBox(mealFilterOptions);
			mealFilters.setValue("Select a filter");
			ObservableList<String> mealCompOptions = FXCollections.observableArrayList(
					"=",
					">=",
					"<="
				); //FIXME:
			ComboBox mealCompFilters = new ComboBox(mealCompOptions);
			mealCompFilters.setValue("=");
			TextField mealQueryValue = new TextField("");
			Button mealAdd = new Button("Add");
			HBox mealFilter = new HBox(10);
			mealFilter.getChildren().addAll(mealCompFilters, mealQueryValue, mealAdd); 
			
			ListView<String> mealFilterList = new ListView<String>();
//			mealFilterList.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.20);
			mealFilterList.setPrefHeight(SCREEN_HEIGHT/10);
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
			
			
			
			boarderPane.setLeft(foodPane);
			
			
			// Set IDs for application.css to use for formatting
			boarderPane.setId("root");
			root.setId("scrollPane");
			meals.setId("meals");
			mealFlow.setId("right");
			foods.setId("foods");
			foodPane.setId("left");
			centerLabel.setId("centerLabel");
			top.setId("top");
			center.setId("center");
			
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
