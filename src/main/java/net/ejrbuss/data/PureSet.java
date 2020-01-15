package net.ejrbuss.data;

import net.ejrbuss.function.fn.Fn;
import net.ejrbuss.function.fn.Predicates;

@FunctionalInterface
public interface PureSet<A> extends Fn<A, Boolean> {

    boolean member(A member);

    // static methods

    static <A> PureSet<A> from(Fn<A, Boolean> predicate) {
        return predicate::$;
    }

    static <A> PureSet<A> union(PureSet<A> set1, PureSet<A> set2) {
        return Predicates.or(set1, set2)::$;
    }

    static <A> PureSet<A> intersect(PureSet<A> set1, PureSet<A> set2) {
        return Predicates.and(set1, set2)::$;
    }

    static <A> PureSet<A> disjunct(PureSet<A> set1, PureSet<A> set2) {
        return Predicates.xor(set1, set2)::$;
    }

    // default methods

    @Override
    default Boolean $(A member) {
        return member(member);
    }

    default PureSet<A> compliment() {
        return Predicates.not(this)::$;
    }

}
