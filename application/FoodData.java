import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;	
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class represents the backend for managing all 
 * the operations associated with FoodItems
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
public class FoodData implements FoodDataADT<FoodItem> {
    
    // List of all the food items.
    private List<FoodItem> foodItemList;

    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, FoodItem>> indexes;
    
    
    /**
     * Public constructor
     */
    public FoodData() {
        foodItemList = new ArrayList<FoodItem>();
        indexes = new HashMap<String, BPTree<Double, FoodItem>>();
        BPTree<Double, FoodItem> calories = new BPTree<Double, FoodItem>(3);
        BPTree<Double, FoodItem> carbs = new BPTree<Double, FoodItem>(3);
        BPTree<Double, FoodItem> fat = new BPTree<Double, FoodItem>(3);
        BPTree<Double, FoodItem> fiber = new BPTree<Double, FoodItem>(3);
        BPTree<Double, FoodItem> protein = new BPTree<Double, FoodItem>(3);
	    indexes.put("calories", calories);
	    indexes.put("carbohydrate", carbs);
	    indexes.put("fat", fat);
	    indexes.put("fiber", fiber);
	    indexes.put("protein", protein);
    }
     
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
     */
    @Override
    public void loadFoodItems(String filePath) {
    	Boolean error = false; //keeps track of whether or not all food items were in correct format
    	foodItemList = new ArrayList<FoodItem>(); //list cleared whenever this method is called
    	Scanner scanner = null;
    	try{
    		File file = new File(filePath);
    		scanner = new Scanner(file);
    		while(scanner.hasNext()) {
    			String line = scanner.next();
    			String[] splitLine = line.split(",");
    			if(splitLine.length == 0) {
    				break; //skipping empty lines with no food
    			}
    			String foodId = splitLine[0];
    			String foodName = splitLine[1];
    			FoodItem newFood = new FoodItem(foodId, foodName);
    			String cals = splitLine[2].toUpperCase();
    			if(cals.equals("CALORIES")) { //checking for appropriate format of data input
    				double cal_val = Double.parseDouble(splitLine[3]);
    				newFood.addNutrient("calories", cal_val);
    			}
    			else {
    				error = true; //error is set to true if format other than expected
    			}
    			String fat = splitLine[4].toUpperCase(); //check the indexes in the array for accuracy
    			if(fat.equals("FAT")) {
    				double fat_val = Double.parseDouble(splitLine[5]);
    				newFood.addNutrient("fat", fat_val);
    			}
    			else {
    				error = true;
    			}
    			String carb = splitLine[6].toUpperCase();
    			if(carb.equals("CARBOHYDRATE")) {
    				double carb_val = Double.parseDouble(splitLine[7]);
    				newFood.addNutrient("carbohydrate", carb_val);
    			}
    			else {
    				error = true;
    			}
    			String fiber = splitLine[8].toUpperCase();
    			if(fiber.equals("FIBER")) {
    				double fiber_val = Double.parseDouble(splitLine[9]);
    				newFood.addNutrient("fiber", fiber_val);
    			}
    			else {
    				error = true;
    			}
    			String protein = splitLine[10].toUpperCase();
    			if(protein.equals("PROTEIN")) {
    				double protein_val = Double.parseDouble(splitLine[11]);
    				newFood.addNutrient("protein", protein_val);
    			}
    			else {
    				error = true;
    			} 
    			foodItemList.add(newFood);
    			//adding each food to each BPTree of nutrients
    			indexes.get("calories").insert(Double.parseDouble(splitLine[3]), newFood);
    			indexes.get("fat").insert(Double.parseDouble(splitLine[5]), newFood);
    			indexes.get("carbohydrate").insert(Double.parseDouble(splitLine[7]), newFood);
    			indexes.get("fiber").insert(Double.parseDouble(splitLine[9]), newFood);
    			indexes.get("protein").insert(Double.parseDouble(splitLine[11]), newFood);
    		}
    	}
    	catch(NumberFormatException e){
    		error = true; //catching potential exception from parsing nutrient values to doubles
    	}
    	catch (IOException e) {
			//not sure what to do here, talk to Dani about error handling again.
		}
    	finally {
    		if(null != scanner) {
    			scanner.close();
    		}
    	}
    	//sorting food item list alphabetically by name
    	foodItemList.sort((FoodItem f1, FoodItem f2) -> f1.getName().toLowerCase().compareTo(f2.getName().toLowerCase()));
        return;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByName(java.lang.String)
     */
    @Override
    public List<FoodItem> filterByName(String substring) {
        List<FoodItem> filterList = new ArrayList<FoodItem>();
        for(FoodItem foodItem : foodItemList) {
        	String name = foodItem.getName();
        	if(name.toUpperCase().contains(substring.toUpperCase())) {
        		filterList.add(foodItem);
        	}
        }
        Collections.sort(filterList, new Comparator<FoodItem>() {
        	@Override
        	public int compare(FoodItem f1, FoodItem f2) {
        		return f1.getName().compareTo(f2.getName());
        	}
        	});
        return filterList;
        }
   
    public List<FoodItem> filterByNutrientsTwo(List<String> rules) {
    	
    	return null;
    }
    
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
     */
    @Override
    public List<FoodItem> filterByNutrients(List<String> rules) {
    	//need to add a try catch block for parsing doubles for the value of the rules
    	//rules in the format "nutrient comparator value"
    	List<List<FoodItem>> mergeList = new ArrayList<List<FoodItem>>();
    	for(String rule : rules) {
    		String[] splitList = rule.split(" ");		
    			if(splitList[0].toLowerCase().equals("calories")) {
    				mergeList.add(indexes.get("calories").rangeSearch(Double.parseDouble(splitList[2]), splitList[1]));
    			}
    			else if(splitList[0].toLowerCase().equals("fat")) {
    				mergeList.add(indexes.get("fat").rangeSearch(Double.parseDouble(splitList[2]), splitList[1]));
    			}
    			else if(splitList[0].toLowerCase().equals("carbohydrate")) {
    				mergeList.add(indexes.get("carbohydrate").rangeSearch(Double.parseDouble(splitList[2]), splitList[1]));
    			}
    			else if(splitList[0].toLowerCase().equals("fiber")) {
    				mergeList.add(indexes.get("fiber").rangeSearch(Double.parseDouble(splitList[2]), splitList[1]));
    			}
    			else if(splitList[0].toLowerCase().equals("protein")) {
    				mergeList.add(indexes.get("protein").rangeSearch(Double.parseDouble(splitList[2]), splitList[1]));
    			}
    	}
    	List<FoodItem> retList = foodItemList;
    	for(List<FoodItem> filterList : mergeList) {
    		retList.retainAll(filterList);
    	}
    	retList.sort((FoodItem f1, FoodItem f2)-> f1.getName().toUpperCase().compareTo(f2.getName().toUpperCase()));
    	return retList;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
     */
    @Override
    public void addFoodItem(FoodItem foodItem) {
        foodItemList.add(foodItem);
        foodItemList.sort((FoodItem f1, FoodItem f2) -> f1.getName().toUpperCase().compareTo(f2.getName().toUpperCase()));
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoodItems()
     */
    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemList;
    }


	@Override
	public void saveFoodItems(String filename) {
		try {
			File file = new File(filename);
			PrintWriter outFile = new PrintWriter(file);
			for(FoodItem food : foodItemList) {
				String id = food.getID();
				String name = food.getName();
				Double calories = food.getNutrientValue("calories");
				Double fat = food.getNutrientValue("fat");
				Double protein = food.getNutrientValue("protein");
				Double carbs = food.getNutrientValue("carbohydrate");
				Double fiber = food.getNutrientValue("fiber");
				String output = id+","+name+","+"calories"+","+calories.toString()
						+","+"fat"+","+fat.toString()+","+"carbohydrate"+","+carbs.toString()+","+
						"fiber"+","+fiber.toString()+","+"protein"+","+protein.toString();
				outFile.println(output);
			}
			outFile.close();
		}
		catch(Exception e) {
			//not sure what to do here
		}
	}
}