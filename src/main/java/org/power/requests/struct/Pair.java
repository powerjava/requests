package org.power.requests.struct;

/**
 * @author Kuo Hong
 */
public class Pair<K, V> {
    private final K name;
    private final V value;

    public Pair(K name, V value) {
        this.name = name;
        this.value = value;
    }

    public K getName() {
        return name;
    }

    public V getValue() {
        return value;
    }

}
