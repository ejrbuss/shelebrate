package net.ejrbuss.data;

import net.ejrbuss.function.fn.Fn;

public interface Set<A> extends PureSet<A> {

    // static methods

    static <A> Set<A> union(Set<A> set1, Set<A> set2) {
        return null;
    }

    static <A> Set<A> intersect(Set<A> set1, Set<A> set2) {
        return null;
    }

    static <A> Set<A> disjunct(Set<A> a, Set<A> b) {
        return Set.union(a.difference(b), b.difference(a));
    }

    static <A> boolean disjoint(Set<A> a, Set<A> b) {
        return false;
    }

    static <A, B> Set<StrictPair<A, B>> product(Set<A> a, Set<B> b) {
        return null;
    }

    // default methods

    default <B> Set<B> map(Fn<? super A, B> transform) {
        return null;
    }

    default <B> Set<B> flatMap(Fn<? super A, Seq<B>> transform) {
        return null;
    }

    default Set<A> filter(Fn<? super A, Boolean> predicate) {
        return null;
    }

    default Set<A> difference(Set<A> set) {
        return null;
    }

    default Set<Set<A>> powerSet() {
        return null;
    }

}
