package sluo.learn.algorithm;

import java.util.NoSuchElementException;

/**
 * Created by Shangwen on 2018/5/02.
 * Implementation of a Red-Black binary search tree.
 * Code adpated from https://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html
 */

public class RedBlackBST<Key extends Comparable<Key>, Value> implements ST<Key, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;    // root of the BST

    // BST helper node data type
    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private boolean color;
        private int size;

        public Node(Key key, Value val, boolean color, int size) {
            this.key = key;
            this.val = val;
            this.color = color;
            this.size = size;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public RedBlackBST() {}

    /******************************************************
     * Node helper methods.
     *****************************************/

    // is node x red; false if x is null
    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    /**
     * Number of key-value pairs.
     */
    @Override
    public int size() {
        return size(root);
    }

    // number of node in subtree rooted at x; 0 if x is null
    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    }

    /**
     * Is the table empty?
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**********************************************
     * Standard BST search
     ************************************************/


    /**
     * Get value paired with key. Null if key is absent.
     *
     * @param key
     */
    @Override
    public Value get(Key key) {
        if (key == null)  throw new IllegalArgumentException("argument to get() is null");
        return get(root, key);
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private Value get(Node x, Key key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.val;
        }
        return null;
    }

    /**
     * Does this symbol table contain the given key ?
     *
     * @param key
     */
    @Override
    public boolean contains(Key key) {
        return get(key) != null;
    }

    /*****************************************************
     * Red-black tree insertion.
     *****************************************************/


    /**
     * Put key-value pair into the table.
     * Remove key from table if value is null.
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    @Override
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        root.color = BLACK;
    }

    // insert the key-value pair in the subtree rooted at h
    private Node put(Node h, Key key, Value val) {
        if (h == null) return new Node(key, val, RED, 1);
        int cmp = key.compareTo(h.key);
        if (cmp < 0) h.left = put(h.left, key, val);
        else if (cmp > 0) h.right = put(h.right, key, val);
        else h.val = val;

        // core red-black operations
        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);
        h.size = size(h.left) + size(h.right) + 1;
        return h;
    }

    /***********************************************
     * Red-black tree deletion.
     **********************************************/

    /**
     * Delete smallest key.
     */
    @Override
    public void deleteMin() {

    }

    /**
     * Delete largest key.
     */
    @Override
    public void deleteMax() {

    }

    /**
     * Remove key and its value from table.
     *
     * @param key
     */
    @Override
    public void delete(Key key) {

    }

    /***********************************************
     * Red-black tree helper functions.
     ************************************************/

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    private void flipColors(Node h) {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    private Node moveRedLeft(Node h) {
        return h;
    }

    private Node moveRedRight(Node h) {
        return h;
    }

    private Node balance(Node h) {
        return h;
    }

    /**********************************************************
     * Utility functions.
     **********************************************************/
    public int height() {
        return height(root);
    }

    public int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    /**********************************************************
     * Ordered symbol table methods.
     **********************************************************/

    /**
     * Smallest key.
     */
    @Override
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        else return min(x.left);
    }

    /**
     * Largest key.
     */
    @Override
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        else return max(x.right);
    }

    /**
     * Largest key less than or equal to key.
     *
     * @param key
     */
    @Override
    public Key floor(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty()) throw new NoSuchElementException("calls floor() with empty symbol table");
        Node x = floor(root, key);
        if (x == null) return null;
        else return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }
    /**
     * Smallest key greater than or equal to key.
     *
     * @param key
     */
    @Override
    public Key ceiling(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        if (isEmpty()) throw new 
    }

    /**
     * Number of keys less than key.
     *
     * @param key
     */
    @Override
    public int rank(Key key) {
        return 0;
    }

    /**
     * Key of rank k.
     *
     * @param k
     */
    @Override
    public Key select(int k) {
        return null;
    }


    /**
     * Number of keys in range [lo...hi]
     *
     * @param lo
     * @param hi
     */
    @Override
    public int size(Key lo, Key hi) {
        return 0;
    }

    /**
     * Keys in range [lo..hi], in sorted order.
     *
     * @param lo
     * @param hi
     */
    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        return null;
    }

    /**
     * All keys in the table, in sorted order.
     */
    @Override
    public Iterable<Key> keys() {
        return null;
    }
}
