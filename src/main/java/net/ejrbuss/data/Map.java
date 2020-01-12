package net.ejrbuss.data;

public interface Map<K, V> extends Seq<Pair<K, V>>  {

    // Required methods

    V get(K key);

    // Static methods

    @SafeVarargs
    static <K, V>  Map<K, V> of(Pair<K, V>... entires) {
        return null;
    }

    // Default methods

    default V get(K key, V defaultValue) {
        return null;
    }

    default Set<K> keys() {
        return null;
    }

    default Seq<V> values() {
        return null;
    }

}
