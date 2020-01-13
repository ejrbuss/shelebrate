package net.ejrbuss.func;

import net.ejrbuss.data.Pair;

@FunctionalInterface
public interface Effect2<A, B> {

    void apply(A a, B b);

    // static methods

    static <A, B> Effect2<A, B> from(Effect<Pair<A, B>> effect) {
        return (a, b) -> effect.apply(Pair.of(a, b));
    }

    // default methods

    default Effect<B> apply(A a) {
        return b -> apply(a, b);
    }

    default Effect2<B, A> swap() {
        return (b, a) -> apply(a, b);
    }

    default Effect<Pair<A, B>> toEffectOfPairs() {
        return pair -> apply(pair.left(), pair.right());
    }

}
