package sluo.learn.algorithm;

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

    // is node x red; false if x is null
    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    /**
     * Put key-value pair into the table.
     * Remove key from table if value is null.
     *
     * @param key
     * @param val
     */
    @Override
    public void put(Key key, Value val) {

    }

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
     * Remove key and its value from table.
     *
     * @param key
     */
    @Override
    public void delete(Key key) {

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

    /**
     * Is the table empty?
     */
    @Override
    public boolean isEmpty() {
        return root == null;
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
     * Smallest key.
     */
    @Override
    public Key min() {
        return null;
    }

    /**
     * Largest key.
     */
    @Override
    public Key max() {
        return null;
    }

    /**
     * Largest key less than or equal to key.
     *
     * @param key
     */
    @Override
    public Key floor(Key key) {
        return null;
    }

    /**
     * Smallest key greater than or equal to key.
     *
     * @param key
     */
    @Override
    public Key ceiling(Key key) {
        return null;
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
