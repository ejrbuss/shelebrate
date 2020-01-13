//package net.ejrbuss.data;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//
//public class StrictSet<A> implements Seq<A> {
//
//    @SafeVarargs
//    public static <A> StrictSet<A> of(A... as) {
//        HashSet<A> set = new HashSet<A>(as.length);
//        for (A a : as) {
//            set.add(a);
//        }
//        return new StrictSet<A>(set);
//    }
//
//    public static <A> StrictSet<A> from(Seq<A> seq) {
//        HashSet<A> set = new HashSet<A>();
//        for (A a : seq.iter()) {
//            set.add(a);
//        }
//        return new StrictSet<A>(set);
//    }
//
//    private final HashSet<A> set;
//
//    private StrictSet(HashSet<A> set) {
//        this.set = set;
//    }
//
//    @Override
//    public StrictPair<A, Seq<A>> next() {
//        return StrictSeq.from(new ArrayList<A>(set)).next();
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return set.isEmpty();
//    }
//
//    @Override
//    public Iterable<A> iter() {
//        return set;
//    }
//
//    @Override
//    public int count() {
//        return set.size();
//    }
//
//    @Override
//    public boolean has(A a) {
//        return set.contains(a);
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        if (other instanceof StrictSet) {
//            StrictSet<?> otherSet = (StrictSet<?>) other;
//            return set.equals(otherSet.set);
//        }
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        return set.hashCode();
//    }
//
//    @Override
//    public String toString() {
//        return super.toString() + "(" + join(", ") + ")";
//    }
//
//}
