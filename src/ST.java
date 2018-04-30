/**
 * Created by Shangwen on 2018/4/30.
 * Interface for implementation of tree data structure.
 * API adapted from Princeton Algorithms 4th edition.
 */
public interface ST<Key extends Comparable<Key>, Value> {
    public void put(Key key, Value val);
    public Value get(Key key);
    public void delete(Key key);
    public boolean contains(Key key);
    public boolean isEmpty();
    public int size();
    public Key min();
    public Key max();
    public Key floor(Key key);
    public Key ceiling(Key key);
    public int rank(Key key);
    public Key select(int k);
    public void deleteMin();
    public void deleteMax();
    public int size(Key lo, Key hi);
    public Iterable<Key> keys(Key lo, Key hi);
    public Iterable<Key> keys();
}
