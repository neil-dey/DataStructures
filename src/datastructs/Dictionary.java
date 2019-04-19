package datastructs;


/**
 * Represents the Dictionary abstract data type
 * 
 * @param <K>
 *            The generic type for keys in the dictionary
 * @param <V>
 *            The generic type for values in the dictionary
 * 
 * @author Neil Dey
 */
public interface Dictionary<K, V> {
    /**
     * Inserts a new entry into the dictionary with the given key and value
     * 
     * @param key
     *            The key of the entry
     * @param value
     *            The value of the entry
     */
    void insert(K key, V value);

    /**
     * Returns the value associated with a given key
     * 
     * @param key
     *            The key to search for
     * @return The value associated with the key
     */
    V lookUp(K key);

    /**
     * Removes the entry from the dictionary with the given key
     * 
     * @param key
     *            The key of the entry to remove
     */
    V remove(K key);
    
    V update(K key, V value);
}
