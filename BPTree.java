package application;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Implementation of a B+ tree to allow efficient access to
 * many different indexes of a large data set. 
 * BPTree objects are created for each type of index
 * needed by the program.  BPTrees provide an efficient
 * range search as compared to other types of data structures
 * due to the ability to perform log_m N lookups and
 * linear in-order traversals of the data items.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * @author Alex Fusco
 *
 * @param <K> key - expect a string that is the type of id for each item
 * @param <V> value - expect a user-defined type that stores all data for a food item
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {

    // Root of the tree
    private Node root;
    
    // Branching factor is the number of children nodes 
    // for internal nodes of the tree
    private int branchingFactor;
    
    /**
     * Public constructor
     * 
     * @param branchingFactor 
     */
    public BPTree(int branchingFactor) {
        if (branchingFactor <= 2) {
            throw new IllegalArgumentException(
               "Illegal branching factor: " + branchingFactor);
        }
        this.root = new LeafNode();
        this.branchingFactor = branchingFactor;
    }
    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
     */
    @Override
    public void insert(K key, V value) {
    	this.root.insert(key, value);
    	if(this.root.isOverflow()) {
    		this.root = this.root.split();
    	}
    	System.out.println("Attempted to insert key: " + key);//FIXME
    	System.out.println("\n" + this);//FIXME
    }
    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
     */
    @Override
    public List<V> rangeSearch(K key, String comparator) {
        if ((!comparator.contentEquals(">=") && 
            !comparator.contentEquals("==") && 
            !comparator.contentEquals("<=")) || 
        	(key == null)) {
        	return new ArrayList<V>();
        }
        return this.root.rangeSearch(key, comparator);
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        Queue<List<Node>> queue = new LinkedList<List<Node>>();
        queue.add(Arrays.asList(root));
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
            while (!queue.isEmpty()) {
                List<Node> nodes = queue.remove();
                sb.append('{');
                Iterator<Node> it = nodes.iterator();
                while (it.hasNext()) {
                    Node node = it.next();
                    sb.append(node.toString());
                    if (it.hasNext())
                        sb.append(", ");
                    if (node instanceof BPTree.InternalNode)
                        nextQueue.add(((InternalNode) node).children);
                }
                sb.append('}');
                if (!queue.isEmpty())
                    sb.append(", ");
                else {
                    sb.append('\n');
                }
            }
            queue = nextQueue;
        }
        return sb.toString();
    }
    
    
    /**
     * This abstract class represents any type of node in the tree
     * This class is a super class of the LeafNode and InternalNode types.
     * 
     * @author sapan
     */
    private abstract class Node {
        
        // List of keys
        List<K> keys;
        
        /**
         * Package constructor
         */
        Node() {
            this.keys = new ArrayList<K>();
        }
        
        /**
         * Inserts key and value in the appropriate leaf node 
         * and balances the tree if required by splitting
         *  
         * @param key
         * @param value
         */
        abstract void insert(K key, V value);

        /**
         * Gets the first leaf key of the tree
         * 
         * @return key
         */
        abstract K getFirstLeafKey();
        
        /**
         * Gets the new sibling created after splitting the node
         * 
         * @return Node
         */
        abstract Node split();
        
        /*
         * (non-Javadoc)
         * @see BPTree#rangeSearch(java.lang.Object, java.lang.String)
         */
        abstract List<V> rangeSearch(K key, String comparator);

        /**
         * 
         * @return boolean
         */
        abstract boolean isOverflow();
        
        public String toString() {
            return keys.toString();
        }
    
    } // End of abstract class Node
    
    /**
     * This class represents an internal node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations
     * required for internal (non-leaf) nodes.
     * 
     * @author sapan
     * @author Alex Fusco
     */
    private class InternalNode extends Node {

        // List of children nodes
        List<Node> children;
        
        /**
         * Package constructor.
         */
        InternalNode() {
            super();
            this.children = new ArrayList<Node>();
        }
        
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
        	Node node = this;
			while(node.getClass().getName().equals("InternalNode")) {
				//Works down internal nodes. Terminates when a leaf node is reached.
				node = ((InternalNode)node).children.get(0);
			}
			if(node.getClass().getName().equals("LeafNode")) {
				//FIXME: This may be unnecessary, but could make debugging easier.
				System.out.println("Error: Bottom node was not a leaf node.");
			}
			return node.getFirstLeafKey();
			//Last node should be a leaf node. Will return first leaf node.
			
        	//FIXME: This program will crash if the leaf nodes are not saved as type LeafNode.
        	//       Keep as is for now, because if the program crashes then we'll know that we
        	//       aren't saving the types correctly.
        }
        
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
        	return (this.keys.size() >= branchingFactor);
        }
        
        
        /*
         * (non-Javadoc)
         * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
         */
        void insert(K key, V value) {
        	//Inserts node into this instance's children. Places according to the given key.
        	//If the child is not a leaf node, then the method is recursively calling itself.
        	//However, it's really not recursive, because it's a new instance.
        	InternalNode node = null;//Node will contain key that may need to be added if there's splitting.
        	
        	if(key.compareTo(this.keys.get(0)) < 0) {//Belongs in first child.
        		this.children.get(0).insert(key, value);
        		if(this.children.get(0).isOverflow()) {
        			node = (InternalNode)this.children.get(0).split();
        		}
        	} else if(key.compareTo(this.keys.get(this.keys.size() - 1)) >= 0) {
        		//Belongs in last child.
        		this.children.get(this.keys.size()).insert(key, value);
        		if(this.children.get(this.keys.size()).isOverflow()) {
        			node = (InternalNode)this.children.get(this.keys.size()).split();
        		}
        	} else {//Belongs somewhere in between.
        		int i = 0;
        		for(i = 0; i < this.keys.size(); i++) {
        			if(key.compareTo(this.keys.get(i)) < 0) {
        				this.children.get(i).insert(key, value);
        				if(this.children.get(i).isOverflow()) {
        					node = (InternalNode)this.children.get(i).split();
                		}
        				break;
        			}
        		}
        	}
        	
        	//Handles adding new node if there was overflow.
        	if(node != null) {//Splitting occurred.
        		K newKey = node.keys.get(0);
        		if(newKey.compareTo(this.keys.get(0)) < 0) {//Belongs in beginning.
        			this.keys.add(0, newKey);//Adds new key to node.
        			this.children.add(0, node.children.get(0));//Add child.
            	} else if(newKey.compareTo(this.keys.get(this.keys.size() - 1)) > 0) {
            		//Belongs at end.
            		this.keys.add(newKey);//Adds new key to node.
            		this.children.add(this.children.size() - 1, node.children.get(0));//Adds child.
            		//FIXME: Check if it should really be added as second to last child.
            	} else {//Belongs somewhere in between.
            		for(int i = 1; i < this.keys.size(); i++) {
            			if(newKey.compareTo(this.keys.get(i)) < 0) {
            				this.keys.add(i, newKey);//Adds new key to node.
            				this.children.add(i, node.children.get(0));//Add child.
            				break;
            			}
            		}
            	}
        	}
        }
        
        
        /*
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
        	int size = this.keys.size();//Number of keys.
        	int parentIndex = (size - 1) / 2;//Index of item that will be added to parent node.
        	InternalNode leftChild = new InternalNode();//Will become left child of parent node.
        	//Note: this instance will become the right child, and the parent will be returned.
        	
        	for(int i = 0; i < parentIndex; i++) {
        		//Removes from original node and adds keys to left child.
        		leftChild.keys.add(this.keys.remove(0));
        	}
        	
        	//Puts half of the children into the left child, and leaves the other half in the right
        	//child. If the number of children is odd, then there is one more child put in the left
        	//child.
        	for(int i = 0; i < this.children.size() / 2 + 1; i++) {
        		leftChild.children.add(this.children.remove(i));
        	}
        	//FIXME: I think I didn't handle dividing up children.
        	//Configure internal node which will be returned.
        	InternalNode parentNode = new InternalNode();
        	parentNode.keys.add(this.keys.remove(0));//Remove for internal node, get for leaf.
        	parentNode.children.add(leftChild);
        	parentNode.children.add(this);
            return parentNode;
        }
        
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
         */
        List<V> rangeSearch(K key, String comparator) {
        	ArrayList<V> list = new ArrayList<V>();
        	if(comparator.equals("<=")) {
        		int i = 0;
        		//If i wasn't checked first, an ArrayIndexOutOfBoundsException could be thrown.
        		while((i < this.keys.size()) && (key.compareTo(this.keys.get(i)) >= 0)) {
        			i++;
        		}
        		return this.children.get(i).rangeSearch(key, comparator);
        	} else if(comparator.equals(">=")) {
        		int i = this.keys.size() - 1;
        		//If i wasn't checked first, an ArrayIndexOutOfBoundsException could be thrown.
				while ((i >= 0) && (key.compareTo(this.keys.get(i)) < 0)) {
					i--;
				}
        		return this.children.get(i + 1).rangeSearch(key, comparator);
        	} else if(comparator.equals("==")){
        		int i = 0;
        		//If i wasn't checked first, an ArrayIndexOutOfBoundsException could be thrown.
        		while((i < this.keys.size()) && (key.compareTo(this.keys.get(i)) >= 0)) {
        			i++;
        		}
        		return this.children.get(i).rangeSearch(key, comparator);
        	}
            return list;
        }
    
        
    } // End of class InternalNode
    
    
    /**
     * This class represents a leaf node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations that
     * required for leaf nodes.
     * 
     * @author sapan
     * @author Alex Fusco
     */
    private class LeafNode extends Node {
        
        // List of values
        List<V> values;
        
        // Reference to the next leaf node
        LeafNode next;
        
        // Reference to the previous leaf node
        LeafNode previous;
        
        /**
         * Package constructor
         */
        LeafNode() {
            super();
            this.values = new ArrayList<V>();
        }
        
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            return this.keys.get(0);
        }
        
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
        	return (this.keys.size() >= branchingFactor);
        }
        
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(Comparable, Object)
         */
        void insert(K key, V value) {
        	if((this.keys.size() == 0) 
        			|| (key.compareTo(this.keys.get(this.keys.size() - 1)) >= 0)) {
        		//Belongs in last index, or nothing has been added yet.
        		this.keys.add(key);
        		this.values.add(value);
        	} else if(key.compareTo(this.keys.get(0)) < 0) {
        		//Belongs in first index.
        		this.keys.add(0, key);
        		this.values.add(0, value);
        	} else {
        		//Belongs somewhere in between.
        		for(int i = 1; i < this.keys.size(); i++) {
        			if(key.compareTo(this.keys.get(i)) < 0) {//Is less than key at index i.
        				this.keys.add(i, key);
        				this.values.add(i, value);
        				return;
        			}
        		}
        	}
        }
        
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
        	int size = this.keys.size();//Number of keys.
        	int parentIndex = (size - 1) / 2;//Index of item that will be added to parent node.
        	LeafNode leftChild = new LeafNode();//
        	
        	for(int i = 0; i < parentIndex; i++) {
        		//Removes from original node and adds keys to left child.
        		leftChild.keys.add(this.keys.remove(0));
        		leftChild.values.add(this.values.remove(0));
        	}
        	
        	//Configure leaf node in linked list.
        	leftChild.next = this;
        	leftChild.previous = this.previous;
        	this.previous = leftChild;
        	
        	//Configure internal node which will be returned.
        	InternalNode parentNode = new InternalNode();
        	parentNode.keys.add(this.keys.get(0));//Does not remove node because this is a B+Tree.
        	parentNode.children.add(leftChild);
        	parentNode.children.add(this);
            return parentNode;
        }
        
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(Comparable, String)
         */
        List<V> rangeSearch(K key, String comparator) {
        	ArrayList<V> list = new ArrayList<V>();
        	int i = 0;
        	if(comparator.equals("<=")) {
        		
        		//Finds where list should start.
        		for(i = 0; i < this.keys.size(); i++) {
        			if(this.keys.get(i).compareTo(key) > 0) {
        				break;
        			}
        		}
        		
        		//Adds items from this node to list.
        		for(i -= 1; (i >= 0) && (i != -1); i--) {
        			list.add(this.values.get(i));
        		}
        		
        		//Adds items from previous nodes to list.
        		LeafNode node = this.previous;
        		while(node != null) {
        			for(i = node.values.size() - 1; i >= 0; i--) {
        				list.add(node.values.get(i));
        			}
        			node = node.previous;
        		}
        		
        	} else if(comparator.equals(">=")) {
        		
        		//Finds where list should start.
        		for(i = 0; i < this.keys.size(); i++) {
        			if(this.keys.get(i).compareTo(key) < 0) {
        				break;
        			}
        		}
        		
        		//Adds items from this node to list.
        		for(i -= 1; (i < this.values.size()) && (i != -1); i++) {
        			list.add(this.values.get(i));
        		}
        		
        		//Adds items from previous nodes to list.
        		LeafNode node = this.next;
        		while(node != null) {
        			for(i = 0; i < node.values.size(); i++) {
        				list.add(node.values.get(i));
        			}
        			node = node.next;
        		}
        		
        	} else if(comparator.equals("==")) {

        		//Finds where list should start.
        		for(i = 0; i < this.keys.size(); i++) {
        			if(this.keys.get(i).compareTo(key) > 0) {
        				break;
        			}
        		}
        		
        		//Adds items from this node to list.
        		for(i -= 1; (i < this.values.size()) && (i != -1); i++) {
        			if(key.compareTo(this.keys.get(i)) == 0) {
        				list.add(this.values.get(i));
        			} else {
        				break;
        			}
        		}

				//Adds items from previous nodes to list.
        		if(i == this.values.size()) {
					LeafNode node = this.next;
					boolean end = false;
					while ((node != null) && !end) {
						for (i = 0; i < node.values.size(); i++) {
							if(key.compareTo(node.keys.get(i)) == 0) {
								list.add(node.values.get(i));
							} else {//End of list has been reached.
								end = true;
								break;
							}
						}
						node = node.next;
					}
				}
        		
        	}
            return list;
            //Will return empty list if comparator isn't valid, but this shouldn't happen based on 
            //how this method is implemented.
        }
        
        
    } // End of class LeafNode
    
    
    /**
     * Contains a basic test scenario for a BPTree instance.
     * It shows a simple example of the use of this class
     * and its related types.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // create empty BPTree with branching factor of 3
        BPTree<Double, Double> bpTree = new BPTree<>(3);

        // create a pseudo random number generator
        Random rnd1 = new Random();

        // some value to add to the BPTree
        Double[] dd = {0.0d, 0.5d, 0.2d, 0.8d};

        // build an ArrayList of those value and add to BPTree also
        // allows for comparing the contents of the ArrayList 
        // against the contents and functionality of the BPTree
        // does not ensure BPTree is implemented correctly
        // just that it functions as a data structure with
        // insert, rangeSearch, and toString() working.
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < 400; i++) {
            Double j = dd[rnd1.nextInt(4)];
            list.add(j);
            bpTree.insert(j, j);
            System.out.println("\n\nTree structure:\n" + bpTree.toString());
        }
        List<Double> filteredValues = bpTree.rangeSearch(0.2d, ">=");
        System.out.println("Filtered values: " + filteredValues.toString());
    }

} // End of class BPTree
