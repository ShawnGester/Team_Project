package application;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
		Set<String> tempName;
		
		for(int i=0; i<food.size(); i++){
			HashMap<String, Double> map = food.get(i).getNutrients();
			tempName = map.keySet(); //returns list of nutrient names
			Object[] nutrientNames = tempName.toArray();
			for(int j=0; j<nutrientNames.length; j++){
				Object temp = nutrientNames[j];
				if(name.contains(temp)){
					int index = name.indexOf(temp);
					values.set(index, values.get(index) + map.get(temp));
				}else{
					name.add(temp.toString());
					values.add(map.get(temp));
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
	 * returns the value of a given nutrient for this meal
	 * @param name
	 * @return
	 */
	public double getNutrientValue(String name){
		if(nutrients.containsKey(name))
			return nutrients.get(name);
		return 0;
	}
	
	public FoodItem getFood(String name){
		if(food.contains(name)){
			return food.get(food.indexOf(name));
		}
		return null;
	}
	
	public List<FoodItem> getFoodList(){
		return food;
	}
}
