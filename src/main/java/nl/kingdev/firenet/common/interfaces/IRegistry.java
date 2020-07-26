package nl.kingdev.firenet.common.interfaces;

public interface IRegistry<K, V> {
    void register(K key, V value);

}
