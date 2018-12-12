package application;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

/**
 * This class will test the BPTree class to make sure it functions correctly.
 * @author Alex Fusco
 *
 */
public class BPTreeTest {

	@Test
	public void testBPTree() {
		boolean passed = true;
		
		//Try with argument 2;
		try {
			BPTree<Integer, Object> test = new BPTree<>(2);
			passed = false;
			fail("Constructor did not throw IllgalArgumentException with argument \"2\".");
		} catch(IllegalArgumentException e) {
			//Success.
		} catch(Exception e) {
			passed = false;
			fail("Unexpectedly threw" + e.getClass());
		}
		
		//Try with argument 0;
		try {
			BPTree<Integer, Object> test = new BPTree<>(0);
			passed = false;
			fail("Constructor did not throw IllgalArgumentException with argument \"0\".");
		} catch(IllegalArgumentException e) {
			//Success.
		} catch(Exception e) {
			passed = false;
			fail("Unexpectedly threw" + e.getClass());
		}
				
		//Try with argument -1;
		try {
			BPTree<Integer, Object> test = new BPTree<>(-1);
			passed = false;
			fail("Constructor did not throw IllgalArgumentException with argument \"-1\".");
		} catch(IllegalArgumentException e) {
			//Success.
		} catch(Exception e) {
			passed = false;
			fail("Unexpectedly threw" + e.getClass());
		}
		
		try {
			BPTree<Integer, Object> test = new BPTree<>(3);
			//Success.
		} catch(Exception e) {
			passed = false;
			fail("Unexpectedly threw" + e.getClass());
		}
		assertTrue(passed);
	}

	@Test
	public void testInsertToString() {
		boolean passed = true;
		String[] names = new String[10];
		names[0] = "Shawn Ge";
		names[1] = "Cole Thomson";
		names[2] = "Alex Fusco";
		names[3] = "Danica Fliss";
		names[4] = "Leah Witt";
		names[5] = "Leah Vukmir";
		names[6] = "Sean Je";
		names[7] = "Gattica Bliss";
		names[8] = "Pole Compton";
		names[9] = "Flex Ausco";
		
		try {
			BPTree<Integer, String> tree = new BPTree<>(3);
			ArrayList<Integer> list = new ArrayList<>();
			Random rndgen = new Random();
			int index;
			while(list.size() < 10) {
				index = rndgen.nextInt(10);
				if(!list.contains((Integer)index)) {
					tree.insert(index, new String(names[index]));
					list.add((Integer)index);
				}
			}
			
			//Doesn't truly test the structure of the tree, but makes sure all the keys get in 
			//there.
			String output = tree.toString();
			for(int i = 0; i < names.length; i++) {
				boolean test = output.contains(String.valueOf(i));
				passed &= output.contains(String.valueOf(i));
				if(!test) {
					System.out.println("FAILED: Does not contain: " + String.valueOf(i));
				}
			}
			if(!passed) {
				System.out.println(tree);//FIXME
				System.out.println(list);//FIXME
				fail("toString method did not produce expected result.");
			}
			assertTrue(passed);
		} catch(Exception e) {
			e.printStackTrace();//FIXME
			fail("Unexpectedly threw " + e.getClass());
		}
	}
	
	@Test
	public void testInsertRangeSearch() {
		boolean passed = true;
		String[] names = new String[10];
		names[0] = "Shawn Ge";
		names[1] = "Cole Thomson";
		names[2] = "Alex Fusco";
		names[3] = "Danica Fliss";
		names[4] = "Leah Witt";
		names[5] = "Leah Vukmir";
		names[6] = "Sean Je";
		names[7] = "Gattica Bliss";
		names[8] = "Pole Compton";
		names[9] = "Flex Ausco";
		
		try {
			BPTree<Integer, String> tree = new BPTree<>(3);
			ArrayList<Integer> list = new ArrayList<>();
			Random rndgen = new Random();
			int index;
			//Inserts into tree in random order.
			while(list.size() < 10) {
				index = rndgen.nextInt(10);
				if(!list.contains((Integer)index)) {
					tree.insert(index, new String(names[index]));
					list.add((Integer)index);
				}
			}
			
			//Check range search with condition "<=".
			List<String> rangeList = tree.rangeSearch(5, "<=");
			if(rangeList.size() != 6) {
				passed = false;
				System.out.println("\n\n\nFAILED: Range search returned " + rangeList.size() + 
						" values. Expected 6.");//FIXME
				System.out.println(rangeList + "\n");//FIXME
				System.out.println(tree + "\n\n");//FIXME
				fail("Range search did not return correct number of values.");
			}
			
			//Checks if correct values are returned from rangeSearch();
			for(int i = 0; i < 6; i++) {
				boolean test = rangeList.contains(names[i]);
				passed &= test;
				if(!test) {
					System.out.println("\nFAILED: Doesn't contain" + names[i]);
				}
			}
			if(!passed) {
				System.out.println(tree);
			}
			
			//Check range search with condition ">=".
			rangeList = tree.rangeSearch(5, ">=");
			if(rangeList.size() != 5) {
				passed = false;
				System.out.println("\n\n\nFAILED: Range search returned " + rangeList.size() + 
						" values. Expected 5.");//FIXME
				System.out.println(rangeList + "\n");//FIXME
				System.out.println(tree);//FIXME
				fail("Range search did not return correct number of values.");
			}
			
			//Checks if correct values are returned from rangeSearch();
			for(int i = 5; i < 10; i++) {
				boolean test = rangeList.contains(names[i]);
				passed &= test;
				if(!test) {
					System.out.println("FAILED: Doesn't contain" + names[i]);
				}
			}
			
			//Check range search with condition "==".
			rangeList = tree.rangeSearch(5, "==");
			if(rangeList.size() != 1) {
				passed = false;
				System.out.println("\n\n\nFAILED: Range search returned " + rangeList.size() + 
						" values. Expected 1.");//FIXME
				System.out.println(rangeList + "\n");//FIXME
				System.out.println(tree);//FIXME
				fail("Range search did not return correct number of values.");
			}
			
			//Checks if correct values are returned from rangeSearch();
			passed &= rangeList.contains(names[5]);
			assertTrue(passed);
			
		} catch(Exception e) {
			passed = false;
			e.printStackTrace();//FIXME
			fail("Unexpectedly threw " + e.getClass());
		}
	}

	@Test
	public void testMain() {
		try {
			//Main method will run some basic tests.
			//BPTree.main(null);
		} catch(Exception e) {
			e.printStackTrace();//FIXME
			fail("Unexpectedly threw " + e.getClass());
		}
		assertTrue(true);
	}
	
	@Test
	public void testLargeBrachingFactor() {
		BPTree<Integer, Integer> tree = new BPTree<Integer, Integer>(10);
		Random rndgen = new Random();
		boolean passed = true;//Null assumption: Assume to pass until found to fail.
		int size = 10000;//TODO: Change size for different tests.
		
		try {
			//Puts numbers into list.
			ArrayList<Integer> listKeys = new ArrayList<Integer>();
			//ArrayList<Integer> listValues = new ArrayList<Integer>();
			while(listKeys.size() < size) {
				int num = rndgen.nextInt(size);
				if(!listKeys.contains(num) && (num != Integer.MIN_VALUE)) {
					listKeys.add(new Integer(num));
				}
			}
			
			//Puts numbers into tree.
			for(int i = 0; i < listKeys.size(); i++) {
				tree.insert(listKeys.get(i), new Integer(i));
				//listValues.add(new Integer(i));
			}
			
			String treeStr = tree.toString();
			String[] treeArr = treeStr.split("\n");
			int bttmLvl = treeArr.length - 1;
			int previous = Integer.MIN_VALUE;
			for(int i = 0; i < listKeys.size(); i++) {
				int keyIndex = treeArr[bttmLvl].indexOf(String.valueOf(i));
				if (!((keyIndex != -1) && (keyIndex > previous))) {
					System.out.println("\nFAILED: key " + i + " was not found in correct spot.");
					//System.out.println("Was added as " + listValues.get(i) + " element.\n");
					passed = false;
				}
				previous = keyIndex;
			}
			
			
			if(!passed) {//Test failed.
				System.out.println(tree);
			}
		} catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.printStackTrace();
		}
		
		assertTrue(passed);
	}

}
