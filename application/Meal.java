package application;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * This class represents a meal item with all its properties
 * 
 * @author Shawn Ge
 * @author Danica Fliss
 * @author Alex Fusco
 * @author Cole Thompson
 * @author Leah Witt
 *
 */
public class Meal {
	//name of meal
	private String name;
	
	//Map of nutrients and value
	private HashMap<String, Double> nutrients;
	
	//List of foods in meal
	private List<FoodItem> food;
	
	/**
	 * Constructor
	 * @param name
	 * @param food
	 */
	public Meal(String name, List<FoodItem> food){
		//initialize variables
		this.name = name;
		this.food = food; 
		nutrients = new HashMap<String, Double>();
		sumNutrients();
	}
	
	/**
	 * Gets the name of the meal
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Sums all nutrients of all the foodItems in this meal
	 */
	private void sumNutrients(){
		//fields
		ArrayList<String> name = new ArrayList<String>();
		ArrayList<Double> values = new ArrayList<Double>();
		Set<String> tempName;
		
		//parse through all foods in the meal
		for(int i=0; i<food.size(); i++){
			//create a hash map to hold food nutrients of particular food
			HashMap<String, Double> map = food.get(i).getNutrients();
			//get list of nutrient names
			tempName = map.keySet(); 
			//convert list into an array
			Object[] nutrientNames = tempName.toArray();
			//parse through array to add food nutrients to meal nutrients
			for(int j=0; j<nutrientNames.length; j++){
				Object temp = nutrientNames[j];
				//already has nutrient, add to existing value
				if(name.contains(temp)){
					int index = name.indexOf(temp);
					values.set(index, values.get(index) + map.get(temp));
				}else{
					//must create new nutrient to add
					name.add(temp.toString());
					values.add(map.get(temp));
				}
			}
		}
		
		//add nutrient values to meal hashmap
		for(int i=0; i<name.size(); i++){
			this.nutrients.put(name.get(i), values.get(i));
		}
	}
	
	/**
	 * Gets the nutrients of the meal
	 * @return nutrients of the meal
	 */
	public HashMap<String, Double> getNutrients(){
		return nutrients;
	}
	
	
	/**
	 * returns the value of a given nutrient for this meal
	 * returns 0 if nutrient does not exist
	 * @param name
	 * @return
	 */
	public double getNutrientValue(String name){
		if(nutrients.containsKey(name))
			return nutrients.get(name);
		return 0;
	}
	
	/**
	 * returns the corresponding FoodItem of the food in
	 * the meal. Returns null if Meal does not contain food
	 *
	 * @param String
	 */
	public FoodItem getFood(String name){
		if(food.contains(name)){
			return food.get(food.indexOf(name));
		}
		return null;
	}
	
	/*
	 * returns the list of FoodItems that are in the Meal
	 */
	public List<FoodItem> getFoodList(){
		return food;
	}
}
