package application;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class represents a meal item with all its properties
 * 
 * @author danica
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
		this.name = name;
		this.food = food; //worried about shallow copies here
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
		ArrayList<String> name = new ArrayList<String>();
		ArrayList<Double> values = new ArrayList<Double>();
		List<String> tempName;
		List<Double> tempValue;
		
		for(int i=0; i<food.size(); i++){
			HashMap<String, Double> map = food.get(i).getNutrients();
			tempName = (List<String>) map.keySet(); //returns list of nutrient names
			tempValue = (List<Double>) map.values(); //returns list of nutrient values
			for(int j=0; j<tempName.size(); j++){
				if(name.contains(tempName.get(j))){
					int index = name.indexOf(tempName.get(j));
					values.set(index, values.get(index) + tempValue.get(j));
				}else{
					name.add(tempName.get(j));
					values.add(tempValue.get(j));
				}
			}
		}
		
		for(int i=0; i<name.size(); i++){
			this.nutrients.put(name.get(i), values.get(i));
			//testing
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
	 * Adds a nutrient and its value to this meal
	 * If the nutrient already exists, updates its value
	 * @param name
	 * @param value
	 */
	public void addNutrient(String name, double value){
		//necessary?
	}
	
	/**
	 * returns the value of a given nutrient for this meal
	 * @param name
	 * @return
	 */
	public double getNutrientValue(String name){
		if(nutrients.containsKey(name))
			return nutrients.get(name);
		return 0;
	}
}
