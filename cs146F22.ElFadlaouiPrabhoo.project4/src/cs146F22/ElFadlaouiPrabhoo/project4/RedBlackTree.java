package cs146F22.ElFadlaouiPrabhoo.project4;

public class RedBlackTree {
	public Node root;
	public static final int RED = 0;
	public static final int BLACK = 1;

	public class Node implements Comparable<Node> {
		String key;
		Node parent;
		Node leftChild;
		Node rightChild;
		// boolean isRed;
		int color;

		public Node(String key, int color) {
			this.key = key;
			this.color = color;
			leftChild = null;
			rightChild = null;
		}

		public boolean isLeaf() {
			if (this.equals(root) && this.leftChild == null && this.rightChild == null)
				return true;
			if (this.equals(root))
				return false;
			if (this.leftChild == null && this.rightChild == null) {
				return true;
			}
			return false;
		}

		@Override
		public int compareTo(Node n) {
			return key.compareTo(n.key);
		}
		
		
		

	}

	public RedBlackTree() {
		// super();
		root = null;
	}

	
	public boolean isLeaf(Node n){
		  if (n.equals(root) && n.leftChild == null && n.rightChild == null) { 
			  return true;
		  }
		  if (n.equals(root)) {
			  return false;
		  }
		  if (n.leftChild == null && n.rightChild == null){
		  return true;
		  }
		  
		  return false;
		  }
	
	
	
	public interface Visitor{
		/**
		This method is called at each node.
		@param n the visited node
		*/
		void visit(Node n);  
		}
	
	
	
	// visit a node
	public void visit(Node n) {
		System.out.println(n.key);
	}

	// checks if node is red, if not returns false
	public boolean isRed(Node x) {
		if (x.color == RED) {
			return true;
		}
		return false;
	}

	
	// Start at the root node and traverse the tree using preorder
	public void printTree() { // preorder: visit, go left, go right
		Node currentNode = root;
		printTree(currentNode);
	}

	public void printTree(Node node) {
		System.out.print(node.key + " ");
		if (node.isLeaf()) {
			return;
		}
		printTree(node.leftChild);
		printTree(node.rightChild);
	}


	// place a new node in the binary search tree with data the
	// parameter and color it red
	public void addNode(Node z) {
		Node x = root;
		Node y = null;

		// traverse the tree until x is the leaf
		while (x != null) {
			y = x; // start at root
			if ((z.key.compareTo(x.key)) < 0) {
				x = x.leftChild;
			} else { // move right
				x = x.rightChild;
			}
		}

		z.parent = y; // make new nodes parent bottom most node

		if (y == null) { // if no tree, make n root
			root = z;
		} else if ((z.key.compareTo(y.key)) < 0) {
			y.leftChild = z;
		} else {
			y.rightChild = z;
		}

		z.leftChild = null; // add attributes for new node
		z.rightChild = null;
		z.color = RED;

		fixTree(z);
	}

	// insert a new node
	public void insert(String key) {
		Node z = new Node(key, RED);
		addNode(z);
	}

	
	// returns node associated with the key
	public String lookup(String key) {
		if (key == null) {
			return "key is null";
		}
		return lookup(root, key);
	}

	public String lookup(Node node, String key) {
		while (node != null) {
			if ((key.compareTo(node.key)) < 0) {
				node = node.leftChild;
			} else if ((key.compareTo(node.key)) > 0) {
				node = node.rightChild;
			} else {
				return node.key;
			}
		}
		return null;
	}

	// returns the sibling node of the parameter if the sibling
	// does not exist, then returns null
	public Node getSibling(Node node) {
		if (node.parent != null && node.parent.leftChild != null && node.parent.rightChild != null) {
			if (node == node.parent.rightChild) {
				return node.parent.rightChild;
			} else {
				return node.parent.rightChild;
			}
		}
		return null;
	}

	// return the aunt of the parameter or the sibling of the
	// parent node. If the aunt node does not exist, then return null
	public Node getAunt(Node node) {
		// check left child of grandparent then right child, return the color of it
		Node grandparent = getGrandparent(node);
		if (grandparent != null) {
			if (node.parent == grandparent.leftChild)
				return grandparent.rightChild;
			else
				return grandparent.leftChild;
		}

		return null;
	}

	// returns the parent of your parent node, if the doesn't exist
	// return null
	public Node getGrandparent(Node node) {
		return node.parent.parent;
	}
	
	

	/*
	 *           |                                |
	 *           x                                y
	 *         /   \                            /   \
	 *  (x.left)     y         ----->         x      (y.right)
	 *             /   \                    /   \
	 *        (y.left) (y.right)       (x.left) (y.left)
	 */    
	// left rotate around the node parameter
	public void rotateLeft(Node x) {
		// assign y to variable
		Node y = x.rightChild;

		// make x's right child to y's left child
		x.rightChild = y.leftChild;

		// y's left child parent to x
		if (y.leftChild != null) {
			y.leftChild.parent = x;
		}
		
		//	switching the parents 
		y.parent = x.parent;

		//	if x is root
		if (x.parent == null) {
			root = y;
			y.parent = null;
		}else if( x == x.parent.leftChild) {		//	 if x is the left child
			x.parent.leftChild = y;					//	then we make y the left child
		}else {
			x.parent.rightChild = y;
		}
		// y's leftChild to x
		y.leftChild = x;
		// x's parent to y
		x.parent = y;

	}

	/*
	 *            |                              |                      
	 *            x                              y                      
	 *          /   \                          /   \                    
	 *        y      (x.right)  ----->  (y.left)     x           
	 *      /   \                                  /   \                
	 *  (y.left) (y.right)                    (y.right) (x.right)   
	 */ 
	// right rotate around the node parameter
	public void rotateRight(Node x) {

		// assign y to variable
		Node y = x.leftChild;

		// make x's left child to y's right child
		x.leftChild = y.rightChild;

		// y's right child parent to x
		if (y.rightChild != null) {
			y.rightChild.parent = x;
		}
		
		//	switching the parents 
		y.parent = x.parent;

		//	if x is root
		if (x.parent == null) {
			root = y;
			y.parent = null;
		}else if( x == x.parent.rightChild) {			//	 if x is the right child
			x.parent.rightChild = y;					//	then we make y the right child
		}else {
			x.parent.leftChild = y;
		}
		// y's rightChild to x
		y.rightChild = x;
		// x's parent to y
		x.parent = y;
	}


	
	public void fixTree(Node z) {
		while(z.parent != null && z.parent.color == RED) {
			//	if z's parent is a leftChild
			if(z.parent == z.parent.parent.leftChild) {
				//	making y z's uncle
				Node y = z.parent.parent.rightChild;
				//	if the uncle is red flip colors 
				if(y != null && y.color == RED) {				// case 1
					z.parent.color = BLACK;						// case 1
					y.color = BLACK;							//	case 1
					z.parent.parent.color = RED;				//	case 1
					z = z.parent.parent;
					// if z is the rightChild
				} else {
					if (z == z.parent.rightChild) {
						z = z.parent;							// case 2
						rotateLeft(z);							//	case 2
					}
					z.parent.color = BLACK;						//	case 3
					z.parent.parent.color = RED;				//	case 3
					rotateRight(z.parent.parent);				//	case 3
				}
			}else {
				//	making y z's uncle 
				Node y = z.parent.parent.leftChild;
				//	if the uncle is red flip colors 
				if(y != null && y.color == RED) {				// case 4
					z.parent.color = BLACK;						// case 4
					y.color = BLACK;							//	case 4
					z.parent.parent.color = RED;				//	case 4
					z = z.parent.parent;
					// if z is the leftChild
				} else {
					if (z == z.parent.leftChild) {
						z = z.parent;							// case 5
						rotateRight(z);							//	case 5
					}
					z.parent.color = BLACK;						//	case 6
					z.parent.parent.color = RED;				//	case 6
					rotateLeft(z.parent.parent);				//	case 6
				}  
			}
		}
		root.color = BLACK;
	}
	
	
	// checks if tree is empty
	public boolean isEmpty(Node n) {
		if (n.key == null){
			return true;
		}
		return false;
	}

	public boolean isLeftChild(Node parent, Node child) {
		if (child.compareTo(parent) < 0 ) {//child is less than parent
			return true;
			}
			return false;
	}
	
	public void preOrderVisit(Visitor v) {
	    preOrderVisit(root, v);
	}
	 

	
	private static void preOrderVisit(Node n, Visitor v) {
	   if (n == null) {
	   return;
	   }
	   v.visit(n);
	   preOrderVisit(n.leftChild, v);
	   preOrderVisit(n.rightChild, v);
	}

	

}
