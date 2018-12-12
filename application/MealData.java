package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;	
import java.io.File;
import java.io.PrintWriter;

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
		trees = new HashMap<String, BPTree<Double, Meal>>();
		trees.put("calories", new BPTree<Double, Meal>(3));
		trees.put("carbohydrate", new BPTree<Double, Meal>(3));
		trees.put("fat", new BPTree<Double, Meal>(3));
		trees.put("fiber", new BPTree<Double, Meal>(3));
		trees.put("protein", new BPTree<Double, Meal>(3));
	}
	
	public void addMeal(Meal meal){
		mealList.add(meal);
		mealList.sort((Meal f1, Meal f2)-> f1.getName().toUpperCase().compareTo(f2.getName().toUpperCase()));
		trees.get("calories").insert(meal.getNutrientValue("calories"), meal);
		trees.get("carbohydrate").insert(meal.getNutrientValue("carbohydrate"), meal);
		trees.get("fat").insert(meal.getNutrientValue("fat"), meal);
		trees.get("fiber").insert(meal.getNutrientValue("fiber"), meal);
		trees.get("protein").insert(meal.getNutrientValue("protein"), meal);
	}
	
	public List<Meal> getMealList(){
		return mealList;
	}
	
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

	public List<Meal> filterByNutrient(List<String> rules){
    	//rules in the format "nutrient comparator value"
    	List<Meal> filterListIterate = new ArrayList<Meal>();
    	filterListIterate = mealList;
    	List<Meal> filterListFiltered = new ArrayList<Meal>();
    	for(String rule : rules) {
    		String[] splitList = rule.split(" ");
    		for(Meal meal : filterListIterate) {		
    			if(splitList[0].toLowerCase().equals("calories")) {
    				if(splitList[1].equals("==")) {
    					Double value = Double.parseDouble(splitList[2]);
    					if(meal.getNutrientValue("calories")==value){
    						filterListFiltered.add(meal);
    					}
    				}
    				else if(splitList[1].equals("<=")) {
    					Double value = Double.parseDouble(splitList[2]);
    					if(meal.getNutrientValue("calories")<=value){
    						filterListFiltered.add(meal);
    					}
    				}
    				else if(splitList[1].equals(">=")) {
    					Double value = Double.parseDouble(splitList[2]);
    					if(meal.getNutrientValue("calories")>=value){
    						filterListFiltered.add(meal);
    					}
    				}
    			}
    			else if(splitList[0].toLowerCase().equals("fat")) {
    				if(splitList[1].equals("==")) {
    					Double value = Double.parseDouble(splitList[2]);
    					if(meal.getNutrientValue("fat")==value){
    						filterListFiltered.add(meal);
    					}
    				}
    				else if(splitList[1].equals("<=")) {
    					Double value = Double.parseDouble(splitList[2]);
    					if(meal.getNutrientValue("fat")<=value){
    						filterListFiltered.add(meal);
    					}
    				}
    				else if(splitList[1].equals(">=")) {
    					Double value = Double.parseDouble(splitList[2]);
    					if(meal.getNutrientValue("fat")>=value){
    						filterListFiltered.add(meal);
    					}
    				}
    			}
    			else if(splitList[0].toLowerCase().equals("protein")) {
    				if(splitList[1].equals("==")) {
    					Double value = Double.parseDouble(splitList[2]);
    					if(meal.getNutrientValue("protein")==value){
    						filterListFiltered.add(meal);
    					}
    				}
    				else if(splitList[1].equals("<=")) {
    					Double value = Double.parseDouble(splitList[2]);
    					if(meal.getNutrientValue("protein")<=value){
    						filterListFiltered.add(meal);
    					}
    				}
    				else if(splitList[1].equals(">=")) {
    					Double value = Double.parseDouble(splitList[2]);
    					if(meal.getNutrientValue("protein")>=value){
    						filterListFiltered.add(meal);
    					}
    				}
    			}
    			else if(splitList[0].toLowerCase().equals("fiber")) {
    				//System.out.println("do you get here?");
    				if(splitList[1].equals("==")) {
    					Double value = Double.parseDouble(splitList[2]);
    					if(meal.getNutrientValue("fiber")==value){
    						filterListFiltered.add(meal);
    					}
    				}
    				else if(splitList[1].equals("<=")) {
    					Double value = Double.parseDouble(splitList[2]);
    					if(meal.getNutrientValue("fiber")<=value){
    						filterListFiltered.add(meal);
    					}
    				}
    				else if(splitList[1].equals(">=")) {
    					Double value = Double.parseDouble(splitList[2]);
    					if(meal.getNutrientValue("fiber")>=value){
    						filterListFiltered.add(meal);
    					}
    				}
    			}
    			else if(splitList[0].toLowerCase().equals("carbohydrate")) {
    				if(splitList[1].equals("==")) {
    					Double value = Double.parseDouble(splitList[2]);
    					if(meal.getNutrientValue("carbohydrate")==value){
    						filterListFiltered.add(meal);
    					}
    				}
    				else if(splitList[1].equals("<=")) {
    					Double value = Double.parseDouble(splitList[2]);
    					if(meal.getNutrientValue("carbohydrate")<=value){
    						filterListFiltered.add(meal);
    					}
    				}
    				else if(splitList[1].equals(">=")) {
    					Double value = Double.parseDouble(splitList[2]);
    					if(meal.getNutrientValue("carbohydrate")>=value){
    						filterListFiltered.add(meal);
    					}
    				}
    			}
    		}
    		filterListIterate = filterListFiltered;
    		filterListFiltered = new ArrayList<Meal>();
    	}
    	filterListIterate.sort((Meal f1, Meal f2)-> f1.getName().toUpperCase().compareTo(f2.getName().toUpperCase()));
    	return filterListIterate;
    }
}

