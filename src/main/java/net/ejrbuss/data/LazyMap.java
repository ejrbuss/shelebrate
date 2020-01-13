//package net.ejrbuss.data;
//
//public class LazyMap<K, V> implements Map<K, V> {
//
//    public static <K, V> LazyMap<K, V> from(Map<K, V> map) {
//        return new LazyMap<K, V>(map);
//    }
//
//    private final Seq<Pair<K, V>> entries;
//
//    private LazyMap(Seq<Pair<K, V>> entries) {
//        this.entries = entries;
//    }
//
//    @Override
//    public Pair<Pair<K, V>, Seq<Pair<K, V>>> next() {
//        return entries.next();
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        return Map.mapEqual(this, other);
//    }
//}
