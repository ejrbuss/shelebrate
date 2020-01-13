//package net.ejrbuss.data;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//public class StrictMap<K, V> implements Map<K, V> {
//
//    @SafeVarargs
//    public static <K, V> StrictMap<K, V> of(Pair<K, V>... entries) {
//        HashMap<K, V> map = new HashMap<K, V>(entries.length);
//        for (Pair<K, V> pair : entries) {
//            map.put(pair.left(), pair.right());
//        }
//        return new StrictMap<K, V>(map);
//    }
//
//    public static <K, V> StrictMap<K, V> from(Seq<Pair<K, V>> seq) {
//        HashMap<K, V> map = new HashMap<K, V>();
//        for (Pair<K, V> pair : seq.iter()) {
//            map.put(pair.left(), pair.right());
//        }
//        return new StrictMap<K, V>(map);
//    }
//
//    private final HashMap<K, V> map;
//
//    private StrictMap(HashMap<K, V> map) {
//        this.map = map;
//    }
//
//    @Override
//    public Pair<Pair<K, V>, Seq<Pair<K, V>>> next() {
//        List<Pair<K, V>> list = new ArrayList<Pair<K, V>>(map.size());
//        for (java.util.Map.Entry<K, V> entry : map.entrySet()) {
//            list.add(Pair.of(entry.getKey(), entry.getValue()));
//        }
//        return StrictSeq.from(list).next();
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return map.isEmpty();
//    }
//
//    @Override
//    public int count() {
//        return map.size();
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        return Map.mapEqual(this, other);
//    }
//
//    @Override
//    public int hashCode() {
//        return map.hashCode();
//    }
//
//    @Override
//    public String toString() {
//        return super.toString() + "(" + map(pair -> pair.left() + " -> " + pair.right()).join(", ") + ")";
//    }
//}
