//package net.ejrbuss.data;
//
//import net.ejrbuss.func.Func;
//
//@FunctionalInterface
//public interface Map<K, V> extends Seq<Pair<K, V>>, Func<K, V> {
//
//    Maybe<V> maybeGet(K key);
//
//    // Static methods
//
//    static <K, V> Map<K, V> of(K k1, V v1) {
//        return StrictMap.of(
//                Pair.of(k1, v1)
//        );
//    }
//
//    static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2) {
//        return StrictMap.of(
//                Pair.of(k1, v1),
//                Pair.of(k2, v2)
//        );
//    }
//
//    static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
//        return StrictMap.of(
//                Pair.of(k1, v1),
//                Pair.of(k2, v2),
//                Pair.of(k3, v3)
//        );
//    }
//
//    static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
//        return StrictMap.of(
//                Pair.of(k1, v1),
//                Pair.of(k2, v2),
//                Pair.of(k3, v3),
//                Pair.of(k4, v4)
//        );
//    }
//
//    static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
//        return StrictMap.of(
//                Pair.of(k1, v1),
//                Pair.of(k2, v2),
//                Pair.of(k3, v3),
//                Pair.of(k4, v4),
//                Pair.of(k5, v5)
//        );
//    }
//
//    static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
//        return StrictMap.of(
//                Pair.of(k1, v1),
//                Pair.of(k2, v2),
//                Pair.of(k3, v3),
//                Pair.of(k4, v4),
//                Pair.of(k5, v5),
//                Pair.of(k6, v6)
//        );
//    }
//
//    static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {
//        return StrictMap.of(
//                Pair.of(k1, v1),
//                Pair.of(k2, v2),
//                Pair.of(k3, v3),
//                Pair.of(k4, v4),
//                Pair.of(k5, v5),
//                Pair.of(k6, v6),
//                Pair.of(k7, v7)
//            );
//    }
//
//    static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8) {
//        return StrictMap.of(
//                Pair.of(k1, v1),
//                Pair.of(k2, v2),
//                Pair.of(k3, v3),
//                Pair.of(k4, v4),
//                Pair.of(k5, v5),
//                Pair.of(k6, v6),
//                Pair.of(k7, v7),
//                Pair.of(k8, v8)
//        );
//    }
//
//    @SafeVarargs
//    static <K, V>  Map<K, V> of(Pair<K, V>... entries) {
//        return StrictMap.of(entries);
//    }
//
//    static <K, V> Map<K, V> from(Seq<Pair<K, V>> seq) {
//        return LazyMap.from(seq::next);
//    }
//
//    @SuppressWarnings("unchecked")
//    static <K, V> boolean mapEqual(Map<K, V> map, Object other) {
//        if (other instanceof Map) {
//            Map<Object, ?> otherMap = (Map<Object, ?>) other;
//            if (StrictSet.from(map.keys()).equals(StrictSet.from(otherMap.keys()))) {
//                for (Pair<K, V> pair : map.iter()) {
//                    if (!otherMap.get(pair.left()).equals(pair.right())) {
//                        return false;
//                    }
//                }
//                return true;
//            }
//        }
//        return false;
//    }
//
//    // Default methods
//
//    @Override
//    default Pair<K, V> first() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    default Seq<Pair<K, V>> rest() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    default V apply(K key) {
//        return get(key);
//    }
//
//    default V get(K key) {
//        return maybeGet(key).get();
//    }
//
//    default V get(K key, V defaultValue) {
//        return maybeGet(key).get(defaultValue);
//    }
//
//    default Map<K, V> assoc(Pair<K, V> pair) {
//        return assoc(pair.left(), pair.right());
//    }
//
//    default Map<K, V> assoc(K key, V val) {
//        return LazyMap.from(() -> Pair.of(Pair.of(key, val), this));
//    }
//
//    default Map<K, V> dissoc(K key) {
//        return LazyMap.from(filter(entry -> !entry.left().equals(key))::next);
//    }
//
//    default Set<K> keys() {
//        return map(Pair::leftOf);
//        // return map(Pair::left);
//    }
//
//    default Set<V> vals() {
//        return map(Pair::rightOf);
//    }
//
//}
