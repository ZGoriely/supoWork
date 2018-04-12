package supoWork.supo7.rbtree;

import java.util.HashMap;
import java.util.LinkedList;

public class RedBlackTree {

	// Private classes

	private enum NodeType { NODE, LEAF }

	private enum Color { RED, BLACK }

	private class DuplicateKey extends Exception {
		public DuplicateKey (int x) {
			super("Duplicate Key: " + x);
		}
	}

	private class Node {

		public int key;
		public int value;
		public Node left;
		public Node right;
		public Node parent;

		private NodeType type;
		private Color color;

		public Node(Node p) {
			// Creates leaf
			left = null;
			right = null;
			parent = p;
			type = NodeType.LEAF;
			color = Color.BLACK;
		}

		public Node(Node p, int k, int v) {
			// Creates node
			key = k;
			value = v;
			left = new Node(this);
			right = new Node(this);
			parent = p;
			type = NodeType.NODE;
			color = Color.RED;
		}

		public boolean isLeaf() {
			return (type == NodeType.LEAF);
		}

		public boolean isRed() {
			return (color == Color.RED);
		}
		
		public void setRed() {
			color = Color.RED;
		}
		
		public void setBlack() {
			color = Color.BLACK;
		}
	}

	// RB Tree implementation

	private Node root;

	public RedBlackTree () {
		root = null;
	}

	public boolean isEmpty () {
		return root == null;
	}

	private Node LeftRotate (Node x) {
		if (x.isLeaf()) return x;
		Node y = x.right;
		if (y.isLeaf()) return x;
		x.right = y.left;
		x.right.parent = x;
		y.parent = x.parent;
		x.parent = y;
		y.left = x;
		if (y.parent != null) {
			if (y.parent.left == x) y.parent.left = y;
			else y.parent.right = y;
		}
		x = y;
		return x;
	}

	private Node RightRotate (Node x) {
		if (x.isLeaf()) return x;
		Node y = x.left;
		if (y.isLeaf()) return x;
		x.left = y.right;
		x.left.parent = x;
		y.parent = x.parent;
		x.parent = y;
		y.right = x;
		if (y.parent != null) {
			if (y.parent.left == x) y.parent.left = y;
			else y.parent.right = y;
		}
		x = y;
		return x;
	}

	public void Insert (int k, int v) throws DuplicateKey{
		// Set as root if tree empty
		if (isEmpty()) {
			root = new Node(null, k, v);
			root.color = Color.BLACK;
			return;
		}
		
		// Insert into correct position
		Node current = root;
		while (!current.isLeaf()) {
			if (k < current.key) {
				if (current.left.isLeaf()) {
					current.left = new Node(current, k, v);
					current = current.left;
					break;
				}
				else current = current.left;
			}
			else if (k == current.key) {
				throw new DuplicateKey(k);
			}
			else {
				if (current.right.isLeaf()) {
					current.right = new Node(current, k, v);
					current = current.right;
					break;
				}
				else current = current.right;
			}
		}
		
		// Rotate to solve any invariants
		while (current.isRed() && (current.parent != null && current.parent.isRed())) {
	
			// If no grandparent then parent must be root, root shouldn't be red.
			Node parent = current.parent;
			Node grandparent = parent.parent;
			Node uncle = (grandparent.right == parent) ? grandparent.left : grandparent.right;
			
			// Case 1 : Red uncle
			if (uncle.isRed()) {
				parent.setBlack();
				uncle.setBlack();
				if (grandparent == root) break;
				grandparent.setRed();
				current = grandparent;
				continue;
			}
			// Case 2 : Black uncle inner
			if (!uncle.isRed() && (current == parent.left && parent == grandparent.right)) {
				current = RightRotate(parent).right;
			}
			else if (!uncle.isRed() && (current == parent.right && parent == grandparent.left)) {
				current = LeftRotate(parent).left;
			}
			//Case 3 : Black uncle outer
			if (!uncle.isRed() && (current == parent.right && parent == grandparent.right)) {
				grandparent.setRed();
				parent.setBlack();
				if (grandparent == root) {
					root = LeftRotate(grandparent);
					root.setBlack();
				}
				else {
					LeftRotate(grandparent);
				}
				break;
			}
			else if (!uncle.isRed() && (current == parent.left && parent == grandparent.left)) {
				grandparent.setRed();
				parent.setBlack();
				if (grandparent == root) {
					root = RightRotate(grandparent);
					root.setBlack();
				}
				else {
					RightRotate(grandparent);
				}
				break;
			}
		}
	}

	// Invariant tests

	private boolean testIfRootBlack() {
		if (isEmpty()) return true;
		return root.color == Color.BLACK;
	}

	private boolean testNoDoubleReds(Node v) {
		if (v.type == NodeType.LEAF) return true;
		else if (v.isRed()) {
			if (v.left.isRed() || v.right.isRed()) return false;
		}
		return testNoDoubleReds(v.left) || testNoDoubleReds(v.right);
	}

	private boolean testConstantBlackDepth(Node v) {
		return testGetBlackDepth(root) != -1;	
	}

	private int testGetBlackDepth(Node v) {
		if (v.isLeaf()) return 1;
		int depthLeft = v.isRed() ? testGetBlackDepth(v.left) : 1 + testGetBlackDepth(v.left);
		int depthRight = v.isRed() ? testGetBlackDepth(v.right) : 1 + testGetBlackDepth(v.right);
		if (depthLeft <= 0 || depthRight <= 0 || depthLeft != depthRight) return -1;
		else return depthLeft;
	}
	
	private int getNumNodes(Node v) {
		if (v.isLeaf()) return 0;
		return 1 + getNumNodes(v.left) + getNumNodes(v.right);
	}
	
	private boolean testNumLeaves() {
		return getNumNodes(root) == getNumLeaves(root)-1;
	}
	
	private int getNumLeaves(Node v) {
		if (v.isLeaf()) return 1;
		return getNumLeaves(v.left) + getNumLeaves(v.right);
	}
	
	private String inorder(Node v) {
		if (v.isLeaf()) return "";
		return inorder(v.left) + "(" + v.key + ":" + v.value + ")" + inorder(v.right);
	}
	
	private String inorderFull(Node v) {
		if (v.isLeaf()) return "";
		String rb = ((v.isRed()) ? "R" : "B");
		return inorderFull(v.left) + "(" + v.key + ":" + v.value + "," + rb + ", l: " + v.left.key + " r: " + v.right.key + ")" + inorderFull(v.right);
	}

	public void runUnitTests() {
		boolean rootBlack = testIfRootBlack();
		boolean noDouble = testNoDoubleReds(root);
		boolean blackDepth = testConstantBlackDepth(root);
		boolean enoughLeaves = testNumLeaves();
		if (rootBlack && noDouble && blackDepth && enoughLeaves) System.out.println("All tests passed");
		else {
			System.out.println("Root black: " + rootBlack);
			System.out.println("Black follows red: " + noDouble);
			System.out.println("Black depth constant: " + blackDepth);
			System.out.println("Correct number of leaves " + enoughLeaves);
		}
	}
	
	public void getInfo() {
		System.out.println("Number of nodes: " + getNumNodes(root));
		System.out.println("All nodes: ");
		System.out.println("   " + inorderFull(root));
	}

	public static void main(String[] args) {
		RedBlackTree t = new RedBlackTree();
		try {
			for (int i = 1; i <= 20; i++) {
				t.Insert(i, 0);
			}
			t.runUnitTests();
			t.getInfo();
		}
		catch (DuplicateKey e) {
			System.out.println(e.getMessage());
		}
	}

}
