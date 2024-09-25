/**
 * Implemention file for CS 3345.HON.24F Programming Project #2.
 * <p>
 * Student name:  Ky Anh Vo
 * Student NetID: kxv220016
 *
 * @param <E> The element's type.
 */
public class MyRedBlackTree<E extends Comparable<? super E>> {
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
   * its color in paranethes.
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
    printNode(root, 0);
  }

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
   * Returns whether or not the tree contains the given element.
   * <p>
   * Implementation should run in O(log n) time for a tree of n elements.
   *
   * @param element The element to find.
   * @returns true if the tree contains the element or false otherwise.
   */
  public boolean contains(E element) {

  }

  /**
   * Returns the minimum element of the tree.
   * <p>
   * Implementation should run in O(log n) time for a tree of n elements.
   *
   * @returns The minimum element of the tree or null if tree is empty.
   */
  public E findMin() {
    Node<E> curr = root;
    while (curr != null)
      curr = curr.left;
    return curr.val
  }

  /**
   * Returns the maximum element of the tree.
   * <p>
   * Implementation should run in O(log n) time for a tree of n elements.
   *
   * @returns The maximum element of the tree or null if tree is empty.
   */
  public E findMax() {
    Node<E> curr = root;
    while (curr != null)
      curr = curr.right;
    return curr.val;
  }

  /**
   * Inserts a new element into the tree.
   * If the element already exists in the tree, this method makes no changes.
   * <p>
   * Implementation should run in O(log n) time for a tree of n elements.
   *
   * @param element The element to be inserted.
   */
  public void insert(E element) {

  }

  /**
   * Removes the element from the tree.
   * If the element does not exist in the tree, this method makes no changes.
   * <p>
   * Implementation should run in O(log n) time for a tree of n elements.
   *
   * @param element The element to be removed.
   */
  public void remove(E element) {

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
   * @param child
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

  public static enum Color { red, black }
}