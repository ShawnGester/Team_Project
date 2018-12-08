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
			
			String expOut = "{[";
			for(int i = 0; i < names.length; i++) {//FIXME:This may be wrong.
				expOut += names[i];
				if(i < names.length - 1) {
					expOut += ", ";
				} else {
					expOut += "]}";
				}
			}
			passed &= tree.toString().equals(expOut);
			if(!passed) {
				//System.out.println(tree);//FIXME
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
				//System.out.println(tree);//FIXME
				fail("Range search did not return correct number of values.");
			}
			
			//Checks if correct values are returned from rangeSearch();
			for(int i = 0; i < 6; i++) {
				passed &= rangeList.contains(names[i]);
			}
			
			//Check range search with condition ">=".
			rangeList = tree.rangeSearch(5, ">=");
			if(rangeList.size() != 5) {
				passed = false;
				//System.out.println(tree);//FIXME
				fail("Range search did not return correct number of values.");
			}
			
			//Checks if correct values are returned from rangeSearch();
			for(int i = 5; i < 10; i++) {
				passed &= rangeList.contains(names[i]);
			}
			
			//Check range search with condition "==".
			rangeList = tree.rangeSearch(5, "==");
			if(rangeList.size() != 1) {
				passed = false;
				//System.out.println(tree);//FIXME
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
			BPTree.main(null);
		} catch(Exception e) {
			e.printStackTrace();//FIXME
			fail("Unexpectedly threw " + e.getClass());
		}
		assertTrue(true);
	}

}
