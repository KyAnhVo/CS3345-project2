/**
 * Implementation file for CS 3345.HON.24F Programming Project #2.
 * <p>
 * Student name:  Ky Anh Vo
 * Student NetID: kxv220016
 *
 * @param <E> The element's type.
 */

 import java.lang.Math;

 public class MyRedBlackTree<E extends Comparable<? super E>> {
 
   /**
    * Debug main
    */
   public static void main(String[] args)
   {
     MyRedBlackTree<Integer> tree = new MyRedBlackTree<>();
     for (int n = 0; n <= 10; n++)
     {
       int ins = (int) (Math.random() * 20);
       System.out.print("Insert: ");
       System.out.println(ins);
       System.out.println();
 
       tree.insert(ins);
       System.out.printf("size = %d%n", tree.size());
       tree.printAll();
 
       System.out.print("\n\n=============================\n\n");
     }
   }
 
   /**
    * Instantiate an empty red-black tree.
    */
   MyRedBlackTree() {
     this.root = null;
     this.size = 0;
   }
 
   /**
    * Print all elements of tree in sorted order with the color of each element's node.
    * Elements are printed one line at a time, each followed by a space and then
    * its color in parentheses.
    * Also, each element is indented a number of '=' equal to twice the node's depth.
    * <p>
    * For example, a tree containing 5, 10, 15, 20, 25, and 30 might be printed as
    * ```
    * ==5 (black)
    * 10 (black)
    * ====15 (black)
    * ==20 (red)
    * ====25 (black)
    * ======30 (red)
    * ```
    * <p>
    * Implementation should run in O(n) time for a tree of n elements.
    */
   public void printAll() {
     printNode(this.root, 0);
   }
 
   /**
    * Print 2*depth ='s followed by node's value and color
    * in an inorder traversal order.
    * @param node node to print and recurse
    * @param depth depth of current node
    */
   private void printNode(Node<E> node, int depth)
   {
     if (node == null) return;
 
     // traverse left subtree
     printNode(node.left, depth + 1);
 
     // print ==...==val
 
     for (int i = 0; i < depth; i++)
     {
       System.out.print("==");
     }
     System.out.print(node.val);
 
     // print (color)
 
     System.out.print(" (");
     System.out.print(node.color);
     System.out.println(")");
 
     // traverse right subtree
     printNode(node.right, depth + 1);
   }
 
   /**
    * Returns whether the tree contains the given element.
    * <p>
    * Implementation should run in O(log n) time for a tree of n elements.
    *
    * @param element The element to find.
    * @returns true if the tree contains the element or false otherwise.
    */
   public boolean contains(E element) {
     return findElement(element) != null;
   }
 
   private Node<E> findElement(E element)
   {
     Node<E> curr = this.root;
 
     while (curr != null) {
       int compare = element.compareTo(curr.val);
       if (compare == 0)
         return curr;
       else if (compare > 0)
         curr = curr.right;
       else
         curr = curr.left;
     }
 
     return null;
   }
 
   /**
    * Returns the minimum element of the tree.
    * <p>
    * Implementation should run in O(log n) time for a tree of n elements.
    *
    * @returns The minimum element of the tree or null if tree is empty.
    */
   public E findMin() {
     if (this.root == null) return null;
 
     Node<E> curr = this.root;
     while (curr.left != null)
       curr = curr.left;
     return curr.val;
   }
 
   /**
    * Returns the maximum element of the tree.
    * <p>
    * Implementation should run in O(log n) time for a tree of n elements.
    *
    * @returns The maximum element of the tree or null if tree is empty.
    */
   public E findMax() {
     if (this.root == null) return null;
 
     Node<E> curr = this.root;
     while (curr.right != null)
       curr = curr.right;
     return curr.val;
   }
 
   /**
    * Inserts a new element into the tree.
    * If the element already exists in the tree, this method makes no changes.
    * <p>
    * Implementation should run in O(log n) time for a tree of n elements.
    * will throw NullPointerException when element == null.
    *
    * @param element The element to be inserted.
    * @returns node that have been inserted, or null if element is already
    * in the tree.
    */
   public void insert(E element) {
 
     // null check element
     if (element == null)
       throw new NullPointerException();
     
     Node<E> curr = this.insertNoBalance(element);
     if (curr == null) return;
     this.balance(curr);
   }
 
   /**
    * Used for insert before balancing in inset()
    * @param element element to insert
    * @return node of newly inserted element
    */
   private Node<E> insertNoBalance(E element)
   {
     Node<E> curr = root;
 
     // CASE 1: EMPTY TREE
 
     if (curr == null)
     {
       this.root = new Node<>(element, Color.black, null);
       this.size++;
       return this.root;
     }
 
     // Insert
 
     while (true)
     {
       int compare = element.compareTo(curr.val);
 
       if (compare == 0) // do not need to insert
         return null;
       
       if (compare > 0) // element > curr.val
       {
         if (curr.right != null) // go to right node
         { 
           curr = curr.right;
           continue;
         }
         else // insert new node
         { 
           curr.right = new Node<>(element, Color.red, curr);
           curr = curr.right;
           this.size++;
           return curr;
         }
       }
       // compare < 0
       if (curr.left != null) // go to left node
       {
         curr = curr.left;
       }
       else // insert new node
       {
         curr.left = new Node<>(element, Color.red, curr);
         curr = curr.left;
         this.size++;
         return curr;
       }
       
     }
   }
 
   private void balance(Node<E> curr)
   {
     // Case: curr is root
     if (curr == this.root)
     {
       curr.color = Color.black;
       return;
     }
 
 
 
     Node<E> parent = curr.parent;
 
     // Case: valid tree
     if (curr.parent.color == Color.black) return;
 
     Node<E> grandparent = parent.parent;
 
 
 
     // Case: parent's sibling is black or null (invalid 4-node)
     Node<E> uncle = (grandparent.left == parent) ? grandparent.right : grandparent.left;
     if (uncle == null || uncle.color != parent.color)
     {
       boolean currIsLeft = (parent.left == curr), parentIsLeft = (grandparent.left == parent);
 
       if (currIsLeft == parentIsLeft) // outer child
       {
         singleRotation(parent);
       }
       else // inner child
       {
         doubleRotation(curr);
       }
 
       return;
     } 
 
     // Case: parent's sibling is red (overflown "5-node")
     grandparent.right.color = Color.black;
     grandparent.left.color = Color.black;
     grandparent.color = Color.red;
     balance(grandparent);
   }
 
   /**
    * Removes the element from the tree.
    * If the element does not exist in the tree, this method makes no changes.
    * <p>
    * Implementation should run in O(log n) time for a tree of n elements.
    *
    * @param element The element to be removed.
    */
   public void remove(E element)
   {
     Node<E> curr = findElement(element);
     if (curr == null) return;
 
     this.size--;
 
     // swap if not a <= 1 child node
     if (curr.left != null && curr.right != null)
       curr = this.swapWithSuccessor(curr);
       
   }
 
   private Node<E> swapWithSuccessor(Node<E> node)
   {
     if (node == null) return null;
     if (node.right == null) return null;
 
     Node<E> successor = node.right;
     while (successor.left != null)
     {
       successor = successor.left;
     }
 
     E nodeVal = node.val;
     node.val = successor.val;
     successor.val = nodeVal;
 
     Color nodeColor = node.color;
     node.color = successor.color;
     successor.color = nodeColor;
 
     return successor;
   }
 
   private void removeRecurse(Node<E> curr)
   {
     Node<E> parent = curr.parent, child = (curr.left == null) ? curr.right : curr.left;
     boolean currIsLeft;
     
     if (curr == root) // bring child up to be root, change child's color to black
     {
       root = child;
       child.parent = null;
       child.color = Color.black;
     }
     
     /* FROM THIS POINT, ASSUME PARENT NOT NULL */
 
     currIsLeft = (parent.left == curr);
 
     if (curr.color == Color.red) // remove curr, bring child up
     {
       if (currIsLeft)
         parent.left = child;
       else
         parent.right = child;
 
       if (child != null)
         child.parent = parent;
     }
 
     /* FROM THIS POINT, ASSUME CURR BLACK */
 
     Node<E> sibling  = currIsLeft ? parent.right : parent.left;
   }
 
   /**
    * Returns number of elements in tree.
    * <p>
    * Implementation should run in O(1) time for a tree of n elements.
    *
    * @returns Number of elements in list.
    */
   public int size() {
     return this.size;
   }
 
   // ROTATION OPERATIONS
 
   /**
    * rotate child up and parent down
    * @param child: node to be rotated / pushed up
    */
 
   private void singleRotation(Node<E> child)
   {
     Node<E> parent = child.parent, grandparent = parent.parent, grandchild;
 
     // change left / right nodes
 
     if (child == parent.left)
     {
       grandchild = child.right;
       child.right = parent;
       parent.left = grandchild;
     }
     else
     {
       grandchild = child.left;
       child.left = parent;
       parent.right = grandchild;
     }
 
     // change parent nodes
 
     parent.parent = child;
     child.parent = grandparent;
     if (grandchild != null)
       grandchild.parent = parent;
     
     // change grandparent's child
     
     if (grandparent != null)
     {
       if (grandparent.left == parent)
         grandparent.left = child;
       else
         grandparent.right = child;
     }
 
     // swap color of parent and child
 
     Color parentColor = parent.color;
     parent.color = child.color;
     child.color = parentColor;
 
     if (this.root == parent)
     {
       this.root = child;
     }
   }
 
   /**
    * double rotate grandchild to be grandparent (and make it black)
    * @param grandchild: node to be pushed up
    */
   private void doubleRotation(Node<E> grandchild)
   {
     singleRotation(grandchild);
     singleRotation(grandchild);
   }
 
   // properties
 
   private int size;
   private Node<E> root;
 
   /**
    * Node and Enum
    */
 
   private static class Node<E>
   {
     public E val;
     public Node<E> left, right, parent;
     public Color color;
 
     public Node(E val, Color color, Node<E> parent)
     {
       this.val = val;
       this.color = color;
       this.parent = parent;
       this.left = null;
       this.right = null;
     }
   }
 
   public enum Color { red, black }
 }
 