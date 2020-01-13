//package net.ejrbuss.data;
//
//import net.ejrbuss.func.Func;
//
//@FunctionalInterface
//public interface Set<A> extends Seq<A>, Func<A, Boolean> {
//
//    // required methods
//
//    boolean member(A a);
//
//    // static methods
//
//    static <A> Set<A> compliment(Set<A> a) {
//        return null;
//    }
//
//    static <A> Set<A> union(Set<A> set1, Set<A> set2) {
//        return null;
//    }
//
//    static <A> Set<A> intersect(Set<A> set1, Set<A> set2) {
//        return null;
//    }
//
//    static <A> Set<A> disjunct(Set<A> a, Set<A> b) {
//        return Set.union(a.difference(b), b.difference(a));
//    }
//
//    static <A> boolean disjoint(Set<A> a, Set<A> b) {
//        return Set.intersect(a, b).isEmpty();
//    }
//
//    static <A, B> Set<StrictPair<A, B>> product(Set<A> a, Set<B> b) {
//        return null;
//    }
//
//    // default methods
//
//    @Override
//    default Boolean apply(A a) {
//        return member(a);
//    }
//
//    default boolean isEmpty() {
//        return false;
//    }
//
//    default Set<A> difference(Set<A> set) {
//        return null;
//    }
//
//    default Set<Set<A>> powerSet() {
//        return null;
//    }
//
//    default <B> Set<B> map(Func<A, B> transform) {
//        return null;
//    }
//
//    default <B> Set<B> flatMap(Func<A, Set<B>> transform) {
//        return null;
//    }
//
//    default Set<A> filter(Func<A, Boolean> predicate) {
//        return null;
//    }
//
//}
