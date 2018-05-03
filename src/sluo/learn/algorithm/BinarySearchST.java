package sluo.learn.algorithm; /**
 * Created by Shangwen on 2018/4/30.
 * Array implementation of binary search.
 * Code adapted from https://algs4.cs.princeton.edu/31elementary/BinarySearchST.java.html
 * My own comments are added for better understanding of the code.
 */
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;

public class BinarySearchST<Key extends Comparable<Key>, Value> implements ST<Key, Value> {
    private static final int INIT_CAPACITY = 2;
    private Key[] keys;
    private Value[] vals;
    // number of key-value pairs in this symbol table
    private int n = 0;

    /* Constructor; Initializes an empty symbol table. */
    public BinarySearchST() {
        this    (INIT_CAPACITY);
    }
    public BinarySearchST(int capacity){
        keys = (Key[]) new Comparable[capacity];
        vals = (Value[]) new Object[capacity];
    }

    // resize the underlying arrays
    private void resize(int capacity) {
        assert capacity >= n;
        Key[] tempk = (Key[]) new Comparable[capacity];
        Value[] tempv = (Value[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            tempk[i] = keys[i];
            tempv[i] = vals[i];
        }
        vals = tempv;
        keys = tempk;
    }

    @Override
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

        // if inserted value is null, delete that key
        if (val == null) {
            delete(key);
            return;
        }

        int i = rank(key);

        // key is already in table, replace old value with new value
        if (i < n && keys[i].compareTo(key) == 0) {
            vals[i] = val;
            return;
        }

        // insert new keey-value pair
        // if array is full, resize to two times of old length
        if (n == keys.length) resize(2*keys.length);

        // Move elements in the array so that index[i] is reserved for the newly inserted key-value pair
        for (int j = n; j > i; j--) {
            keys[j] = keys[j-1];
            vals[j] = vals[j-1];
        }
        // insert the new key-value pair into index[i]
        keys[i] = key;
        vals[i] = val;
        // number of key-value pair of the table increase by one
        n++;

        assert check();
    }

    @Override
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        if (isEmpty()) return null;
        int i = rank(key);
        if (i < n && keys[i].compareTo(key) == 0) return vals[i];
        return null;
    }

    @Override
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (isEmpty()) return;

        // compute rank
        int i = rank(key);

        // key not in table
        if (i == n || keys[i].compareTo(key) != 0) {
            return;
        }

        for (int j = i; j < n-1; j++) {
            keys[j] = keys[j+1];
            vals[j] = vals[j+1];
        }

        n--;
        keys[n] = null; // to avoid loitering
        vals[n] = null;

        // resize if 1/4 full
        if (n > 0 && n == keys.length/4) resize(keys.length/2);

        assert check();
    }

    @Override
    public boolean contains(Key key) {
    if (key == null) throw new IllegalArgumentException("argument to contains() is null");
    return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("called min() with empty symbol table");
        return keys[0];
    }

    @Override
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("called max() with empty symbol table");
        return keys[n-1];
    }

    // return the largest key in this symbol table less than or equal to key
    @Override
    public Key floor(Key key) {
       if (key == null) throw new IllegalArgumentException("argument to floor() is null") ;
       int i = rank(key);
       if (i < n && key.compareTo(keys[i]) == 0) return keys[i];
       if (i == 0) return null;
       else return keys[i-1];
    }

    // Returns the smallest key in this symbol table greater than or equal to key
    @Override
    public Key ceiling(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        int i = rank(key);
        if (i == n) return null;
        else return keys[i];
    }

    // Returns the number of keys in this symbol table STRICTLY less than key
    // starts with 0, end with n-1
    @Override
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");

        // This is the core for BINARY search
        int lo = 0, hi = n-1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;
        }
        // this method will converge at 'mid' or 'lo'
        return lo;
    }


    @Override
    public Key select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("called select() with invalid argument: " + k);
        }
        return keys[k];
    }

    @Override
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
        delete(min());
    }

    @Override
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("symbol table underflow error");
        delete(max());
    }

    @Override
    public int size(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }

    // Returns all keys in this symbol table in the given range.
    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        Queue<Key> queue = new PriorityQueue<>();
        if (lo.compareTo(hi) > 0) return queue;
        for (int i = rank(lo); i < rank(hi); i++)
            queue.add(keys[i]);
        if (contains(hi)) queue.add(keys[rank(hi)]);
        return queue;
    }

    // Returns all keys in this symbol table as an Iterable.
    // To iterate over all the keys in the symble table named st, use the foreach notation:
    // {for (Key key : st.keys())}
    @Override
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    /********************************************
     *  Check internal invariants.
     ********************************************/
    private boolean check() {
        return isSorted() && rankCheck();
    }

    // are the items in the array in ascending order?
    private boolean isSorted() {
        for (int i = 1; i < size(); i++)
            if (keys[i].compareTo(keys[i-1]) < 0) return false;
        return true;
    }

    // check that rank(select(i)) = i
    private boolean rankCheck() {
        // check index
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        // check actual key value
        for (int i = 0; i < size(); i++)
            if (keys[i].compareTo(select(rank(keys[i]))) != 0) return false;
        return true;
    }
}
