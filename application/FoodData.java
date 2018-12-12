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
 * the operations associated with FoodItems. This class
 * has methods capable of parsing through text data to generate food items,
 * sort food items alphabetically and generate a text file with all of the food
 * data that has been inputted. It also throws exceptions to communicate events
 * to outside classes and allows a user to add a single food one at a time to
 * the complete food list in addition to file uploading.
 *
 * @author sapan (sapan@cs.wisc.edu)
 * @author Leah
 */
public class FoodData implements FoodDataADT<FoodItem> {

    // List of all the food items.
    private List<FoodItem> foodItemList;

    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, FoodItem>> indexes;

    //BPTree to keep track of food ID's and avoid duplicates
    private BPTree<String, FoodItem> idTree;

    /**
     * This constructor generates the BPTrees for each nutrient
     * and the list for all of the food items in the program. It also
     * sets up the HashMap that holds each of the BPTrees
     */
    public FoodData() {
        foodItemList = new ArrayList<FoodItem>();
        setUpBPTrees();
    }

    /**
     * This method gets called by the constructor and by loadFoodItems
     * to reset and initialize the hashmap of BPTrees
     */
    private void setUpBPTrees() {
    	indexes = new HashMap<String, BPTree<Double, FoodItem>>();
        BPTree<Double, FoodItem> calories = new BPTree<Double, FoodItem>(3);
        BPTree<Double, FoodItem> carbs = new BPTree<Double, FoodItem>(3);
        BPTree<Double, FoodItem> fat = new BPTree<Double, FoodItem>(3);
        BPTree<Double, FoodItem> fiber = new BPTree<Double, FoodItem>(3);
        BPTree<Double, FoodItem> protein = new BPTree<Double, FoodItem>(3);
        idTree = new BPTree<String, FoodItem>(3);
	    indexes.put("calories", calories);
	    indexes.put("carbohydrate", carbs);
	    indexes.put("fat", fat);
	    indexes.put("fiber", fiber);
	    indexes.put("protein", protein);
    }

    /**
     * This method takes in a filepath and uploads a set of foods from the file. It uploads only
     * the foods in the correct format and skips those in an improper format without exiting. Each
     * food is added to a BPTree sorted by nutrients, and to a list of all of the foods in the program.
     * @param a string with the file name of the file with the food data to be uploaded
     */
    @Override
    public void loadFoodItems(String filePath){
    	Boolean allFoodError = false; //keeps track of whether or not all food items were in correct format
    	Boolean error2 = false; //keeps track of IOException
    	Boolean thisFoodError = false; //keeps track of whether or not to add each food to the list
    	foodItemList = new ArrayList<FoodItem>(); //list cleared whenever this method is called
    	setUpBPTrees();
    	Scanner scanner = null;
    	try{
    		File file = new File(filePath);
    		scanner = new Scanner(file);
    		while(scanner.hasNext()) {
    			thisFoodError = false;
    			String line = scanner.next();
    			String[] splitLine = line.split(",");
    			if(splitLine.length == 0) {
    				break; //skipping empty lines with no food
    			}
    			//check for duplicate ID and mark as error to not add if true
    			List<FoodItem> dupList = idTree.rangeSearch(splitLine[0], "==");
    			if(dupList.size() != 0) {
    				thisFoodError = true;
    				allFoodError = true;
    			}
    			String foodId = splitLine[0];
    			String foodName = splitLine[1];
    			FoodItem newFood = new FoodItem(foodId, foodName);
    			String cals = splitLine[2].toUpperCase();
    			if(cals.equals("CALORIES")) { //checking for appropriate format of data input
    				try{
	    				double cal_val = Double.parseDouble(splitLine[3]);
	    				if(cal_val>=0) {
	    					newFood.addNutrient("calories", cal_val);
	    				}
	    				else {
	    					allFoodError = true;
	    					thisFoodError = true;
	    				}
    				}
    				catch(Exception e) {
    					allFoodError = true;
    					thisFoodError = true;
    				}
    			}
    			else {
    				allFoodError = true; //error is set to true if format other than expected
    				thisFoodError = true;
    			}
    			String fat = splitLine[4].toUpperCase(); //check the indexes in the array for accuracy
    			if(fat.equals("FAT")) {
    				try {
    					double fat_val = Double.parseDouble(splitLine[5]);
    					if(fat_val>=0) {
	    					newFood.addNutrient("fat", fat_val);
	    				}
	    				else {
	    					allFoodError = true;
	    					thisFoodError = true;
	    				}
    				}
    				catch(Exception e) {
    					allFoodError = true;
    					thisFoodError = true;
    				}
    			}
    			else {
    				allFoodError = true;
    				thisFoodError = true;
    			}
    			String carb = splitLine[6].toUpperCase();
    			if(carb.equals("CARBOHYDRATE")) {
    				try {
	    				double carb_val = Double.parseDouble(splitLine[7]);
	    				if(carb_val>=0) {
	    					newFood.addNutrient("carbohydrate", carb_val);
	    				}
	    				else {
	    					allFoodError = true;
	    					thisFoodError = true;
	    				}
    				}
					catch(Exception e) {
						allFoodError = true;
						thisFoodError = true;
					}
    			}
    			else {
    				allFoodError = true;
    				thisFoodError = true;
    			}
    			String fiber = splitLine[8].toUpperCase();
    			if(fiber.equals("FIBER")) {
    				try {
	    				double fiber_val = Double.parseDouble(splitLine[9]);
	    				if(fiber_val>=0) {
	    					newFood.addNutrient("fiber", fiber_val);
	    				}
	    				else {
	    					allFoodError = true;
	    					thisFoodError = true;
	    				}
	    			}
					catch(Exception e) {
						allFoodError = true;
						thisFoodError = true;
					}
    			}
    			else {
    				allFoodError = true;
    				thisFoodError = true;
    			}
    			String protein = splitLine[10].toUpperCase();
    			if(protein.equals("PROTEIN")) {
    				try {
	    				double protein_val = Double.parseDouble(splitLine[11]);
	    				if(protein_val>=0) {
	    					newFood.addNutrient("protein", protein_val);
	    				}
	    				else {
	    					allFoodError = true;
	    					thisFoodError = true;
	    				}
	    			}
					catch(Exception e) {
						allFoodError = true;
						thisFoodError = true;
					}
    			}
    			else {
    				allFoodError = true;
    				thisFoodError = true;
    			}
    			if(thisFoodError == false) {
	    			foodItemList.add(newFood);
	    			//adding each food to each BPTree of nutrients
	    			indexes.get("calories").insert(Double.parseDouble(splitLine[3]), newFood);
	    			indexes.get("fat").insert(Double.parseDouble(splitLine[5]), newFood);
	    			indexes.get("carbohydrate").insert(Double.parseDouble(splitLine[7]), newFood);
	    			indexes.get("fiber").insert(Double.parseDouble(splitLine[9]), newFood);
	    			indexes.get("protein").insert(Double.parseDouble(splitLine[11]), newFood);
	    			idTree.insert(newFood.getID(), newFood); //keep track of existing ID's
    			}
    		}
    	}
    	catch(Exception e) { //throw IOException to communicate file error to Dani's code
    		error2 = true;
    	}
    	finally { //close scanner unless never opened
    		if(null != scanner) {
    			scanner.close();
    		}
    	}
    	if(allFoodError == true) {//catching potential exception from parsing nutrient values to doubles
    		numberFormatException();
    	}
    	if(error2 == true) {
    		throwNullPointerException();
    	}
    	//sorting food item list alphabetically by name
    	foodItemList.sort((FoodItem f1, FoodItem f2) -> f1.getName().toLowerCase().compareTo(f2.getName().toLowerCase()));
        return;
    }

    /**
     * This method is called by loadFoodItems to communicate
     * a specific error to other classes that may call loadFoodItems
     * @throws NullPointerException
     */
    private void throwNullPointerException() throws NullPointerException {
    	throw new NullPointerException();
    }

    /**
     * This method is called by loadFoodItems to communicate
     * a specific error to other classes that may call loadFoodItems
     * @throws NumberFormatException
     */
    private void numberFormatException() throws NumberFormatException {
    	throw new NumberFormatException();
    }


    /**
     * This method iterated through the list of all of the foods in
     * foodItemList and checks the name of each food for the inputted substring.
     * The method then returns this filtered list of foods in alphabetical
     * order.
     * @param a string to search for within the food item names
     * @return a sorted list of all foods with the name containing that substring
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

   /**
    * This method uses the BPTree for each nutrient to find a list of foods
    * that meet certain filter requirements. It takes in a list of strings,
    * parses the strings according to their expected format and calls the BPTree
    * method for the matching nutrient that returns a list of applicable foods.
    * The method generates a list for each object in the argument and finds the
    * intersection of them all.
    * @param a list of strings containing the filter rules
    * @return a list of all of the foods meeting the filter requirements
    */
    @Override
    public List<FoodItem> filterByNutrients(List<String> rules) {
    	//need to add a try catch block for parsing doubles for the value of the rules
    	//rules in the format "nutrient comparator value"
    	List<List<FoodItem>> mergeList = new ArrayList<List<FoodItem>>(); //storing the food lists from each filter
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
    	List<FoodItem> retList = new ArrayList<FoodItem>(foodItemList);
    	for(List<FoodItem> filterList : mergeList) { //takes the intersection of the lists of food from each filter
    		retList.retainAll(filterList);
    	}
    	retList.sort((FoodItem f1, FoodItem f2)-> f1.getName().toUpperCase().compareTo(f2.getName().toUpperCase()));
    	return retList;
    }

    /**
     * This method adds a food item to the foodITemList
     * and sorts the new list. It checks to ensure
     * there is not a duplicate ID being passed in and
     * throws an exception to communicate this error if there is.
     * @param is the food item to be added
     */
    @Override
    public void addFoodItem(FoodItem foodItem) {
        //check for duplicate ID before adding
		List<FoodItem> dupList = idTree.rangeSearch(foodItem.getID(), "==");
		if(dupList.size() == 0) {
			idTree.insert(foodItem.getID(), foodItem);
			foodItemList.add(foodItem);
	        foodItemList.sort((FoodItem f1, FoodItem f2) -> f1.getName().toUpperCase().compareTo(f2.getName().toUpperCase()));
	        indexes.get("calories").insert(foodItem.getNutrientValue("calories"), foodItem);
			indexes.get("fat").insert(foodItem.getNutrientValue("fat"), foodItem);
			indexes.get("carbohydrate").insert(foodItem.getNutrientValue("carbohydrate"), foodItem);
			indexes.get("fiber").insert(foodItem.getNutrientValue("fiber"), foodItem);
			indexes.get("protein").insert(foodItem.getNutrientValue("protein"), foodItem);
		}
		else {
			throwNullPointerException();
		}
    }

   /**
    * This method returns the sorted list of all of the food items in
    * the foodItemList;
    * @return the sorted food item list
    */
    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemList;
    }

    /**
     * This method generates a comma separated string of all of the data
     * for each food. The strings are then each written to the given file path
     * to generate a file containing all of the food nutrition information.
     * @param the filename of where to save the data
     */
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
			e.printStackTrace(); //Error "handled" in calling method/class
		}
	}
}
