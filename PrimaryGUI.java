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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PrimaryGUI {
	public FoodData foodData;
	//	public FoodItemAddForm foodAddForm;
	private static final double SCREEN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth(); 
	private static final double SCREEN_HEIGHT = Screen.getPrimary().getVisualBounds().getWidth();
	private static final double LEFT_INSET = 10;
	private static final double RIGHT_INSET = 0;
	
	
	public PrimaryGUI(FoodData foodData, Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,
					Screen.getPrimary().getVisualBounds().getWidth(),
					Screen.getPrimary().getVisualBounds().getHeight());
					//Bases sizing off of screen size.
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); //FIXME: Alex's
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Button newFood = new Button("+ New Food");
			Label topLabel = new Label("Meal Planner");
			
			Label centerLabel = new Label("Details");
			BorderPane top = new BorderPane(topLabel);
			top.setId("top");
			BorderPane center = new BorderPane(centerLabel);
			center.setId("center");
			
			root.setTop(top);
//			root.setBottom(bottom);
			root.setCenter(center);
			BorderPane.setAlignment(centerLabel, Pos.TOP_LEFT); //FIXME: fix left and right borderpane boundaries
			
			FlowPane flow = new FlowPane(Orientation.VERTICAL, 0, 10); //gives vertical spacing between 
			flow.setId("left");//ID for CSS
			flow.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.27);

			flow.setAlignment(Pos.TOP_LEFT); //left aligns all members of flow pane
			flow.setPadding(new Insets(0,RIGHT_INSET,0,LEFT_INSET));
			flow.setPrefWidth(SCREEN_WIDTH / 4);
			root.setLeft(flow);
			flow.setColumnHalignment(HPos.LEFT); // align labels on left
			TextField queryFood = new TextField("Search for a food...");
			
			ListView<String> foodList = new ListView<String>();
			foodList.prefWidthProperty().set(SCREEN_WIDTH/4.2);
//			foodList.prefWidthProperty().bind(flow.widthProperty());
			foodList.setPrefHeight(SCREEN_HEIGHT/6);
			//FIXME: event handler to foods
			Label selectedFoods = new Label("Displaying 0 of 0 foods"); //number of foods out of total after filter
			
			//FILTERGUI START
			ObservableList<String> filterOptions = FXCollections.observableArrayList(
					"Calories",
					"Protein",
					"Fat"
				); //FIXME:
			ComboBox filters = new ComboBox(filterOptions);
			filters.setValue("Select a filter");
			ObservableList<String> compOptions = FXCollections.observableArrayList(
					"=",
					">=",
					"<="
				); //FIXME:
			ComboBox compFilters = new ComboBox(compOptions);
			compFilters.setValue("=");
			TextField queryValue = new TextField("");
			Button add = new Button("Add");
			HBox filter = new HBox(10);
			filter.getChildren().addAll(compFilters, queryValue, add); //FIXME: queryValue and add not staying on same line??
			
			ListView<String> filterList = new ListView<String>(); 
			//filterList.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.20);
			filterList.setPrefHeight(SCREEN_HEIGHT/10);
			HBox buttons = new HBox(20);
			Button edit = new Button("EDIT");
			Button delete = new Button("DELETE");
			buttons.getChildren().addAll(edit, delete);
			//FILTERGUI END
			
			Button displayFood = new Button("Display Food");
			Button downloadFood = new Button("Download Food List");
			Label foods = new Label("Foods");
			
			
//			ObservableList<String> items = FXCollections.observableArrayList (); //MILESTONE 3 READ IN FROM FOODDATA
//			List.setItems(items);
			flow.getChildren().addAll(newFood, queryFood, foodList, selectedFoods, filters, 
					filter, filterList, buttons, displayFood, downloadFood, foods);
			
			
			//MEAL

			Button newMeal = new Button("+ New Meal");
			
			FlowPane mealFlow = new FlowPane(Orientation.VERTICAL, 0, 10); //gives vertical spacing between
			mealFlow.setId("right");
			mealFlow.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.27);
			mealFlow.setAlignment(Pos.TOP_LEFT); //left aligns all members of flow pane
			mealFlow.setPadding(new Insets(0,0,0,10));
			root.setRight(mealFlow);
			mealFlow.setColumnHalignment(HPos.LEFT); // align labels on left
			TextField queryMeal = new TextField("Search for a meal...");
			
			ListView<String> mealList = new ListView<String>();
//			mealList.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.25);
			mealList.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight() * 0.35);
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
			mealFilterList.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.20);
			mealFilterList.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight() * 0.25);
			HBox mealButtons = new HBox(20);
			Button mealEdit = new Button("EDIT");
			Button mealDelete = new Button("DELETE");
			mealButtons.getChildren().addAll(mealEdit, mealDelete);
			//FILTERGUI END
			
			Button displayMeals = new Button("Display Meals");
			Label meals = new Label("Meals");
			
			mealFlow.getChildren().addAll(newMeal, queryMeal, mealList, selectedMeals, mealFilters,
					mealFilter, mealFilterList, mealButtons, displayMeals, meals);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
