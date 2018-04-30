/**
 * Created by Shangwen on 2018/4/30.
 * Interface for implementation of tree data structure.
 * API adapted from Princeton Algorithms 4th edition.
 */
public interface ST<Key extends Comparable<Key>, Value> {
    /* Put key-value pair into the table.
    * Remove key from table if value is null. */
    public void put(Key key, Value val);

    /* Get value paired with key. Null if key is absent. */
    public Value get(Key key);

    /* Remove key and its value from table. */
    public void delete(Key key);

    /* Is there a value paired with key? */
    public boolean contains(Key key);

    /* Is the table empty? */
    public boolean isEmpty();

    /* Number of key-value pairs. */
    public int size();

    /* Smallest key. */
    public Key min();

    /* Largest key. */
    public Key max();

    /* Largest key less than or equal to key. */
    public Key floor(Key key);

    /* Smallest key greater than or equal to key. */
    public Key ceiling(Key key);

    /* Number of keys less than key. */
    public int rank(Key key);

    /* Key of rank k. */
    public Key select(int k);

    /* Delete smallest key. */
    public void deleteMin();

    /* Delete largest key. */
    public void deleteMax();

    /* Number of keys in range [lo...hi] */
    public int size(Key lo, Key hi);

    /* Keys in range [lo..hi], in sorted order. */
    public Iterable<Key> keys(Key lo, Key hi);

    /* All keys in the table, in sorted order. */
    public Iterable<Key> keys();
}
