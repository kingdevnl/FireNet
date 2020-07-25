package nl.kingdev.firenet.server.interfaces;

public interface IRegistry<K, V> {
    void register(K key, V value);

}
