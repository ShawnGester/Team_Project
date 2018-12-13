package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;	
import java.io.File;
import java.io.PrintWriter;

/**
 * This class represents the backend for managing all
 * the operations associated with Meal. This class
 * has methods capable of sorting meal items alphabetically. 
 * It also throws exceptions to communicate events
 * to outside classes and allows a user to add a single meal one at a time to
 * the complete meal list in addition to file uploading.
 *
 * @author sapan (sapan@cs.wisc.edu)
 * @author Shawn Ge
 * @author Danica Fliss
 * @author Alex Fusco
 * @author Cole Thompson
 * @author Leah Witt
 */
public class MealData {
	
	//list of all meals
	private List<Meal> mealList;
	
	//map of nutrients and trees
	private HashMap<String, BPTree<Double, Meal>> trees;
	
	/**
	 * public constructor
	 */
	public MealData(){
		mealList = new ArrayList<Meal>();
		setUpBPTrees();
	}
	
	/**
    	 * This method gets called by the constructor 
  	 * to reset and initialize the hashmap of BPTrees
 	 */
  	 private void setUpBPTrees() {
		 trees = new HashMap<String, BPTree<Double, FoodItem>>();
      		 BPTree<Double, FoodItem> calories = new BPTree<Double, FoodItem>(3);
   		 BPTree<Double, FoodItem> carbs = new BPTree<Double, FoodItem>(3);
    		 BPTree<Double, FoodItem> fat = new BPTree<Double, FoodItem>(3);
     		 BPTree<Double, FoodItem> fiber = new BPTree<Double, FoodItem>(3);
      		 BPTree<Double, FoodItem> protein = new BPTree<Double, FoodItem>(3);
		 trees.put("calories", calories);
		 trees.put("carbohydrate", carbs);
		 trees.put("fat", fat);
		 trees.put("fiber", fiber);
		 trees.put("protein", protein);
	 }
	
	/**
	 * This method adds a meal to all the BPTrees in the Hashmap trees
	 * and the list that stores all the meals
	 *
	 * @param Meal
	 */
	public void addMeal(Meal meal){
		//add meal
		mealList.add(meal);
		//sort new meal to proper place
		mealList.sort((Meal f1, Meal f2)-> f1.getName().toUpperCase().compareTo(f2.getName().toUpperCase()));
		//add to BPTrees
		trees.get("calories").insert(meal.getNutrientValue("calories"), meal);
		trees.get("carbohydrate").insert(meal.getNutrientValue("carbohydrate"), meal);
		trees.get("fat").insert(meal.getNutrientValue("fat"), meal);
		trees.get("fiber").insert(meal.getNutrientValue("fiber"), meal);
		trees.get("protein").insert(meal.getNutrientValue("protein"), meal);
	}
	
	/**
	 * This method returns list of all meals in alphabetical order
	 *
	 * @return List of meals
	 */
	public List<Meal> getMealList(){
		return mealList;
	}
	
	/**
    	 * This method iterated through the list of all of the meals in
    	 * mealList and checks the name of each meal for the inputted substring.
    	 * The method then returns this filtered list of meals in alphabetical
    	 * order.
    	 * @param a string to search for within the food item names
    	 * @return a sorted list of all meals with the name containing that substring
    	 */
	public List<Meal> filterByName(String substring) {
    		List<Meal> filterList = new ArrayList<Meal>();
    		for(Meal meal : mealList) {
    			String name = meal.getName();
    			if(name.toUpperCase().contains(substring.toUpperCase())) {
    				filterList.add(meal);
    			}
    		}
    		Collections.sort(filterList, new Comparator<Meal>() {
    			@Override
    			public int compare(Meal f1, Meal f2) {
    				return f1.getName().compareTo(f2.getName());
    			}
    		});
    		return filterList;
  	  }

	/**
   	 * This method uses the BPTree for each nutrient to find a list of meals
   	 * that meet certain filter requirements. It takes in a list of strings,
   	 * parses the strings according to their expected format and calls the BPTree
   	 * method for the matching nutrient that returns a list of applicable meals.
    	 * The method generates a list for each object in the argument and finds the
   	 * intersection of them all.
   	 * @param a list of strings containing the filter rules
   	 * @return a list of all of the meals meeting the filter requirements
   	 */
	public List<Meal> filterByNutrient(List<String> rules){
    	    	//rules in the format "nutrient comparator value"
    		List<List<Meal>> mergeList = new ArrayList<List<Meal>>(); //storing the food lists from each filter
		try {
    			for(String rule : rules) {
    				String[] splitList = rule.split(" ");
    				if(splitList[0].toLowerCase().equals("calories")) {
    					mergeList.add(trees.get("calories").rangeSearch(Double.parseDouble(splitList[2]), 
											splitList[1]));
    				}
    				else if(splitList[0].toLowerCase().equals("fat")) {
    					mergeList.add(trees.get("fat").rangeSearch(Double.parseDouble(splitList[2]), 
										   splitList[1]));
    				}
    				else if(splitList[0].toLowerCase().equals("carbohydrate")) {
    					mergeList.add(trees.get("carbohydrate").rangeSearch(Double.parseDouble(splitList[2]), 
											    splitList[1]));
    				}
    				else if(splitList[0].toLowerCase().equals("fiber")) {
    					mergeList.add(trees.get("fiber").rangeSearch(Double.parseDouble(splitList[2]), 
										     splitList[1]));
    				}
    				else if(splitList[0].toLowerCase().equals("protein")) {
    					mergeList.add(trees.get("protein").rangeSearch(Double.parseDouble(splitList[2]), 
										       splitList[1]));
    				}
    			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
    		List<FoodItem> retList = new ArrayList<FoodItem>(foodItemList);
    		for(List<FoodItem> filterList : mergeList) { //takes the intersection of the lists of food from each filter
    			retList.retainAll(filterList);
    		}
    		retList.sort((FoodItem f1, FoodItem f2)-> f1.getName().toUpperCase().compareTo(f2.getName().toUpperCase()));
    		return retList;
		}
	}

