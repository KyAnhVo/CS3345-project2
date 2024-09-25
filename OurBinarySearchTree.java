/**
 * A simple binary search tree implementation for CS 3345 at UTD.
 *
 * @param <E> The element's type.
 */
public class OurBinarySearchTree<E extends Comparable<? super E>> {
  /**
   * Instantiate an empty binary search tree.
   */
  OurBinarySearchTree() {
    root = null;
    size = 0;
  }

  /**
   * Print all elements of tree in sorted order.
   * Elements are printed one line at a time, and each is indented a number of
   * '=' equal to twice the node's depth.
   * <p>
   * Runs in O(n) time for a tree of n elements.
   */
  public void printAll() {
    printAll(root, 0);
  }

  /**
   * Returns whether or not the tree contains the given element.
   * <p>
   * Runs in worst-case O(n) time and "average case" O(log n) time for a tree of n elements.
   *
   * @param element The element to find.
   * @returns true if the tree contains the element or false otherwise.
   */
  public boolean contains(E element) {
    Node<E> node = root;

    while (node != null) {
      int compareResult = element.compareTo(node.element);

      if (compareResult < 0) { // element < node.element
        node = node.left;
      } else if (compareResult > 0) { // element > node.element
        node = node.right;
      } else { // element == node.element
        return true;
      }
    }

    return false;
  }

  /**
   * Returns the minimum element of the tree.
   * <p>
   * Runs in worst-case O(n) time and "average case" O(log n) time for a tree of n elements.
   *
   * @returns The minimum element of the tree or null if tree is empty.
   */
  public E findMin() {
    Node<E> minNode = findMinNode(root);
    if (minNode == null) {
      return null;
    } else {
      return minNode.element;
    }
  }

  /**
   * Returns the maximum element of the tree.
   * <p>
   * Runs in worst-case O(n) time and "average case" O(log n) time for a tree of n elements.
   *
   * @returns The maximum element of the tree or null if tree is empty.
   */
  public E findMax() {
    Node<E> maxNode = findMaxNode(root);
    if (maxNode == null) {
      return null;
    } else {
      return maxNode.element;
    }
  }

  /**
   * Inserts a new element into the tree.
   * If the element already exists in the tree, this method makes no changes.
   * <p>
   * Runs in worst-case O(n) time and "average case" O(log n) time for a tree of n elements.
   *
   * @param element The element to be inserted.
   */
  public void insert(E element) {
    Node<E> newNode = new Node<E>(element);
    Node<E> node = root;
    if (node == null) {
      root = newNode;
      size++;
      node = newNode;
    }

    while (node != newNode) {
      int compareResult = element.compareTo(node.element);

      if (compareResult < 0) { // element < node.element
        if (node.left == null) {
          node.left = newNode;
          size++;
        }
        node = node.left;
      } else if (compareResult > 0) { // element > node.element
        if (node.right == null) {
          node.right = newNode;
          size++;
        } 
        node = node.right;
      } else { // element == node.element
        newNode = node; // Duplicate; do nothing.
      }
    }
  }

  /**
   * Removes the element from the tree.
   * If the element does not exist in the tree, this method makes no changes.
   * <p>
   * Runs in worst-case O(n) time and "average case" O(log n) time for a tree of n elements.
   *
   * @param element The element to be removed.
   */
  public void remove(E element) {
    Node<E> parent = null;
    Node<E> node = root;
    if (node == null) {
      return; // tree is already empty
    }

    while (node != null) { // will return when done
      int compareResult = element.compareTo(node.element);

      if (compareResult < 0) { // element < node.element
        parent = node;
        node = node.left;
      } else if (compareResult > 0) { // element > node.element
        parent = node;
        node = node.right;
      } else if (node.left != null && node.right != null) { // two children
        element = findMinNode(node.right).element; // will now search for successor
        node.element = element;
        parent = node;
        node = node.right;
      } else { // at most one child
        size--; // this branch actually removes the node
        Node<E> child = null;
        if (node.left != null) { // only child on left
          child = node.left;
        } else {
          child = node.right;
        }
        if (parent != null) {
          if (parent.left == node) {
            parent.left = child;
          } else {
            parent.right = child;
          }
        } else {
          root = child;
        }
        node = null;
      }
    }
  }

  /**
   * Returns number of elements in tree.
   * <p>
   * Runs in O(1) time.
   *
   * @returns Number of elements in list.
   */
  public int size() {
    return size;
  }

  /**
   * A binary search tree node.
   *
   * @param <E> The element's type.
   */
  private static class Node<E> {
    public E element; // public to simplify remove
    public Node<E> left, right;

    /**
     * Instantiate a new node.
     *
     * @param element The node's element.
     */
    Node(E element) {
      this.element = element;
    }
  }

  private Node<E> root;
  private int size;

  /**
   * Print all elements of node's subtree in sorted order.
   * Members are printed one line at a time, and each is indented a number of spaces equal to its
   * node's depth.
   * <p>
   * Runs in O(n) time for a tree of n elements.
   *
   * @param node The root the subtree to be printed. If null, nothing is printed.
   * @param depth The depth of the node being printed. Assumed to be non-negative.
   */
  private void printAll(Node<E> node, int depth) {
    assert (depth >= 0);

    if (node != null) {
      printAll(node.left, depth + 1);

      StringBuilder builder = new StringBuilder();
      for (int spaces = 0; spaces < depth; spaces++) {
        builder.append("==");
      }
      builder.append(node.element);
      System.out.println(builder.toString());

      printAll(node.right, depth + 1);
    }
  }

  /**
   * Returns the _node_ containing the minimum element of the subtree.
   * <p>
   * Runs in worst-case O(n) time and "average case" O(log n) time for a tree of n elements.
   *
   * @param node The root of the subtree to be searched. If null, method will return null.
   * @returns The node containing the minimum element of the subtree or null if the subtree is empty.
   */
  private Node<E> findMinNode(Node<E> node) {
    if (node == null) {
      return null;
    }

    while (node.left != null) {
      node = node.left;
    }

    return node;
  }

  /**
   * Returns the _node_ containing the maximum element of the subtree.
   * <p>
   * Runs in worst-case O(n) time and "average case" O(log n) time for a tree of n elements.
   *
   * @param node The root of the subtree to be searched. If null, method will return null.
   * @returns The node containing the maximum element of the subtree or null if the subtree is empty.
   */
  private Node<E> findMaxNode(Node<E> node) {
    if (node == null) {
      return null;
    }

    while (node.right != null) {
      node = node.right;
    }

    return node;
  }
}
