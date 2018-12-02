package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Danica Fliss
 * @author Leah Witt
 * @author Cole Thomson
 * @author Shawn Ge
 * @author Alexander Fusco
 */
public class FilterGUI {
	String fileType;
	private double SCREEN_HEIGHT;
	private double SCREEN_WIDTH;
	
	public FilterGUI(String fileType, double SCREEN_HEIGHT, double SCREEN_WIDTH) {
		if (fileType.equals("Food") || fileType.equals("Meal")) {
			this.fileType = fileType;
			this.SCREEN_HEIGHT = SCREEN_HEIGHT;
			this.SCREEN_WIDTH = SCREEN_WIDTH;
		} else {
			// unrecognizable fileType
			//FIXME:
		}
	}
	
	/**
	 * 
	 * @return VBox of filter section of Meal or Food VBox in PrimaryGUI
	 */
	public VBox makeFilterVBox() {
		VBox filterVBox = new VBox(10.0);
		
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
		
		// Add food filter GUI objects to HBox
		foodFilterHBox.getChildren().addAll(foodCompFiltersCBox, foodFilterValue, foodFilterAddButton); 
		
		// Add "Edit" and "Delete" buttons to HBox
		buttonsHBox.getChildren().addAll(foodEditFilterButton, foodDeleteFilterButton);
		
		// Set all elements onto filter GUI to return
		filterVBox.getChildren().addAll(foodFiltersCBox, foodFilterHBox, foodFilterLView, buttonsHBox);
		
		return filterVBox;
	}
}
