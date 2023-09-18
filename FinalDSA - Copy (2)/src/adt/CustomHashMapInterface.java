package adt;

/**
 *
 * @author Tneh Kai Qing
 */
public interface CustomHashMapInterface<K, T> {
    void put(K key, T value);
    T get(K key);
    void remove(K key);
    boolean containsKey(K key);
    boolean isEmpty();
    int size();
}