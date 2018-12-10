package application;

import java.util.List;

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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PopUpFood {
	Stage foodWindow;
	
	public PopUpFood(){
		foodWindow = new Stage();
		BorderPane food = new BorderPane();
		
		Label foodTitle = new Label("Create Food!");
		foodTitle.setFont(new Font(24));
		
		Label upload = new Label("Upload New Food File");
		
		TextField filePath = new TextField();
		Label fileLabel = new Label("Input Filename");
		
		Button uploadButton = new Button("Upload");
		
		
		Label newFood = new Label("Add New Food to List");
		
		Label foodName = new Label("Name of Food");
		TextField foodInput = new TextField("e.g. \"Apple\"");
		foodInput.setFocusTraversable(false);
		
		Label calories = new Label("calories");
		TextField calorieInput = new TextField();
		
		Label protein = new Label("protein in grams");
		TextField proteinInput = new TextField();
		
		Label carb = new Label("carbohydrates in grams");
		TextField carbInput = new TextField();
		
		Label fat = new Label("fat in grams");
		TextField fatInput = new TextField();
		
		Label fiber = new Label("fiber in grams");
		TextField fiberInput = new TextField();
		
		Button addFood = new Button("Add Food");
		
	}

}
