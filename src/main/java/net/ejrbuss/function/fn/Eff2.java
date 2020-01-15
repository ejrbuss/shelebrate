package net.ejrbuss.function.fn;

import net.ejrbuss.data.Pair;

@FunctionalInterface
public interface Eff2<A, B> extends Fn<A, Eff<B>> {

    void $(A a, B b);

    // static methods

    static <A, B> Eff2<A, B> from(Eff<Pair<A, B>> eff) {
        return (a, b) -> eff.$(Pair.of(a, b));
    }

    // default methods

    @Override
    default Eff<B> $(A a) {
        return b -> $(a, b);
    }

    default Eff2<B, A> swap() {
        return (b, a) -> $(a, b);
    }

    default Eff<Pair<A, B>> toEffectOfPairs() {
        return pair -> $(pair.left(), pair.right());
    }

}
