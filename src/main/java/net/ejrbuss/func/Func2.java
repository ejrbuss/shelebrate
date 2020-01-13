package net.ejrbuss.func;

import net.ejrbuss.data.Pair;

@FunctionalInterface
public interface Func2<A, B, C> extends Func<A, Func<B, C>> {

    C apply(A a, B b);

    // static methods

    static <A, B, C> Func2<A, B, C> from(Func<Pair<A, B>, C> func) {
        return (a, b) -> func.apply(Pair.of(a, b));
    }

    // default methods

    @Override
    default Func<B, C> apply(A a) {
        return b -> apply(a, b);
    }

    default Func2<B, A, C> swap() {
        return (b, a) -> apply(a, b);
    }

    default Func<Pair<A, B>, C> toFuncOfPairs() {
        return pair -> apply(pair.left(), pair.right());
    }

}
