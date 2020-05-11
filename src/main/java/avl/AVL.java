/* Author: Kaitlyn Rice
   Date: 5/11/20
   Purpose: Creating AVL tree
*/

/* Enhancement explaination: used insert code as a base
   set up for remove then drew a random trees to find
   the different otucomes. Additional minNode method to
   assist finding the smallest node. */

package avl;

public class AVL {

  public Node root;

  private int size;

  public int getSize() {
    return size;
  }

  /** find w in the tree. return the node containing w or
  * null if not found */
  public Node search(String w) {
    return search(root, w);
  }
  private Node search(Node n, String w) {
    if (n == null) {
      return null;
    }
    if (w.equals(n.word)) {
      return n;
    } else if (w.compareTo(n.word) < 0) {
      return search(n.left, w);
    } else {
      return search(n.right, w);
    }
  }

  /** insert w into the tree as a standard BST, ignoring balance */
  public void bstInsert(String w) {
    if (root == null) {
      root = new Node(w);
      size = 1;
      return;
    }
    bstInsert(root, w);
  }

  /* insert w into the tree rooted at n, ignoring balance
   * pre: n is not null */
  private void bstInsert(Node n, String w) {
    // TODO
	if (w.compareTo(n.word) < 0) {
	    if (n.left != null)
		bstInsert(n.left, w);
	    else {
		n.left = new Node(w, n);
		size++;
		return;
	    }
	} else if (w.compareTo(n.word) > 0) {
	    if (n.right != null)
		bstInsert(n.right, w);
	    else {
		n.right = new Node(w, n);
		size++;
		return;
	    }
	} else
	    return;
  }

  /** insert w into the tree, maintaining AVL balance
  *  precondition: the tree is AVL balanced */
  public void avlInsert(String w) {
    // TODO
	if (root == null) {
	    root = new Node(w);
	    size = 1;
	    return;
	}
	avlInsert(root, w);
  }

  /* insert w into the tree, maintaining AVL balance
   *  precondition: the tree is AVL balanced and n is not null */
  private void avlInsert(Node n, String w) {
    // TODO
	if (w.compareTo(n.word) < 0) {
	    if (n.left != null)
		avlInsert(n.left, w);
	    else {
		n.left = new Node(w, n);
		size++;
	    }
	} else if (w.compareTo(n.word) > 0) {
	    if (n.right != null)
		avlInsert(n.right, w);
	    else {
		n.right = new Node(w, n);
		size++;
	    }
	} else
	    return;
	rebalance(n);
  }

  /** do a left rotation: rotate on the edge from x to its right child.
  *  precondition: x has a non-null right child */
  public void leftRotate(Node x) {
    // TODO
	Node y = x.right;
	x.right = y.left;
	if (y.left != null)
	    y.left.parent = x;
	y.parent = x.parent;
	if (x.parent == null)
	    root = y;
	else if (x == x.parent.left)
	    x.parent.left = y;
	else
	    x.parent.right = y;
	y.left = x;
	x.parent = y;
	x.height = Math.max(height(x.left), height(x.right)) + 1;
	y.height = Math.max(height(y.left), height(y.right)) + 1;
  }

  /** do a right rotation: rotate on the edge from x to its left child.
  *  precondition: y has a non-null left child */
  public void rightRotate(Node y) {
    // TODO
	Node x = y.left;
	y.left = x.right;
	if (x.right != null)
	    x.right.parent = y;
	x.parent = y.parent;
	if (y.parent == null)
	    root = x;
	else if (y == y.parent.right)
	    y.parent.right = x;
	else
	    y.parent.left = x;
	x.right = y;
	y.parent = x;
	x.height = Math.max(height(x.left), height(x.right)) + 1;
	y.height = Math.max(height(y.left), height(y.right)) + 1;
  }

  /** rebalance a node N after a potentially AVL-violoting insertion.
  *  precondition: none of n's descendants violates the AVL property */
  public void rebalance(Node n) {
    // TODO
	n.height = Math.max(height(n.left), height(n.right)) + 1;
	int bf = bal(n);
	if (bf < -1) {
	    if (bal(n.left) > 0)
		leftRotate(n.left);
	    rightRotate(n);
	}else if (bf > 1) {
	    if (bal(n.right) < 0)
		rightRotate(n.right);
	    leftRotate(n);
	}
  }

  /* returns balence factor of n */
  public int bal(Node n) {
	if (n == null)
	    return 0;
	return height(n.right) - height(n.left);
  }

  /* returns height of n */
  public int height(Node n) {
	if (n == null)
	    return -1;
	return Math.max(height(n.left), height(n.right)) + 1;
  }

  /** remove the word w from the tree */
  public void remove(String w) {
    if (root == null) {
	return;
    }
    remove(root, w);
  }

  /* remove v from the tree rooted at n */
  private void remove(Node n, String w) {
	if (w.compareTo(n.word) < 0) {
	    if (n.left != null)
	        remove(n.left, w);
	}
	else if (w.compareTo(n.word) > 0) {
	    if (n.right != null)
		remove(n.right, w);
	}
	else {
	    Node temp = null;
	    if (n.left == null || n.right == null) {
		if (n.left == null) {
		    temp = n.right;
		    n = temp;
		} else {
		    temp = n.left;
		    n = temp;
		}
	    }
	    else {
		temp = minNode(n.right);
		n.word = temp.word;
		remove(n.right, temp.word);
	    }
	}
	if (n != null)
	    rebalance(n);
    return; // (enhancement TODO - do the base assignment first)
  }

  /* Returns smallest node */
  private Node minNode (Node n) {
	Node cur = n;
	while (cur.left != null)
	    cur = cur.left;
	return cur;
  }

  /** print a sideways representation of the tree - root at left,
  * right is up, left is down. */
  public void printTree() {
    printSubtree(root, 0);
  }
  private void printSubtree(Node n, int level) {
    if (n == null) {
      return;
    }
    printSubtree(n.right, level + 1);
    for (int i = 0; i < level; i++) {
      System.out.print("        ");
    }
    System.out.println(n);
    printSubtree(n.left, level + 1);
  }

  /** inner class representing a node in the tree. */
  public class Node {
    public String word;
    public Node parent;
    public Node left;
    public Node right;
    public int height;

    public String toString() {
      return word + "(" + height + ")";
    }

    /** constructor: gives default values to all fields */
    public Node() { }

    /** constructor: sets only word */
    public Node(String w) {
      word = w;
    }

    /** constructor: sets word and parent fields */
    public Node(String w, Node p) {
      word = w;
      parent = p;
    }

    /** constructor: sets all fields */
    public Node(String w, Node p, Node l, Node r) {
      word = w;
      parent = p;
      left = l;
      right = r;
    }
  }
}
