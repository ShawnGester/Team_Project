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
		trees.put("carbs", new BPTree<Double, Meal>(3));
		trees.put("fat", new BPTree<Double, Meal>(3));
		trees.put("fiber", new BPTree<Double, Meal>(3));
		trees.put("protein", new BPTree<Double, Meal>(3));
	}
	
	public void addMeal(Meal meal){
		mealList.add(meal);
		trees.get("calories").insert(meal.getNutrientValue("calories"), meal);
		trees.get("carbs").insert(meal.getNutrientValue("carbs"), meal);
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

}
