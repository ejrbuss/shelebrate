package net.ejrbuss.function.fn;

import net.ejrbuss.data.Pair;

@FunctionalInterface
public interface Fn2<A, B, C> extends Fn<A, Fn<B, C>> {

    C $(A a, B b);

    // static methods

    static <A, B, C> Fn2<A, B, C> from(Fn<Pair<A, B>, C> fn) {
        return (a, b) -> fn.$(Pair.of(a, b));
    }

    // default methods

    @Override
    default Fn<B, C> $(A a) {
        return b -> $(a, b);
    }

    default Fn2<B, A, C> swap() {
        return (b, a) -> $(a, b);
    }

    default Fn<Pair<A, B>, C> toFuncOfPairs() {
        return pair -> $(pair.left(), pair.right());
    }

}
