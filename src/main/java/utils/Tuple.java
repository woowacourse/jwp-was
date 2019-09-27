package utils;

import java.util.Map;

public class Tuple<K, V> implements Map.Entry<K, V> {

    private final K key;
    private V value;

    public Tuple(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V updateValue) {
        V originValue = value;
        value = updateValue;
        return originValue;
    }
}
