package application;

import java.util.ArrayList;
import java.util.List;

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
		
		ComboBox<String> filtersCBox = new ComboBox<String>(filterOptions); // Filter CBox
		
		// Set default value of foodFilterCBox to prompt user to select a filter
		filtersCBox.setValue("Select a filter");
		
		ObservableList<String> foodCompOptions = FXCollections.observableArrayList(
				"=",
				">=",
				"<="
			); // Types of comparisons available for filters
		
		// ComboBox of comparison operators to determine food filter criteria 
		ComboBox<String> compFiltersCBox = new ComboBox<String>(foodCompOptions);  
		
		// Set default value of foodCompFiltersCBox to "="
		compFiltersCBox.setValue("=");
		
		TextField filterValue = new TextField(""); // user inputs filter value
		
		// Set Width of foodFilterValue 
		filterValue.setPrefWidth(100);
		
		Button filterAddButton = new Button("Add"); // Applies filter and adds to list
		HBox filterHBox = new HBox(10); // HBox to hold group of food filter GUI objects
		ListView<String> filterLView = new ListView<String>(); // list of applied filters
		
		// Set Height of foodFilterLView to ~9% of screen height
		filterLView.setPrefHeight(SCREEN_HEIGHT/11);
		
		HBox buttonsHBox = new HBox(10); // HBox to hold "Edit" and "Delete" buttons
		Button foodEditFilterButton = new Button("Edit"); 		// Button to edit a filter
		Button foodDeleteFilterButton = new Button("Delete"); 	// Button to delete a filter
		
		/**
		 * EVENT HANDLERS
		 */
		
		
		
		// Add food filter GUI objects to HBox
		filterHBox.getChildren().addAll(compFiltersCBox, filterValue, filterAddButton); 
		
		// Add "Edit" and "Delete" buttons to HBox
		buttonsHBox.getChildren().addAll(foodEditFilterButton, foodDeleteFilterButton);
		
		// Set all elements onto filter GUI to return
		filterVBox.getChildren().addAll(filtersCBox, filterHBox, filterLView, buttonsHBox);
		
		return filterVBox;
	}
}
