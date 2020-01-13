package net.ejrbuss.data;

import net.ejrbuss.func.Effect2;
import net.ejrbuss.func.Func2;

public interface Pair<A, B> {

    // required methods

    A left();
    B right();

    // static methods

    static <A, B> Pair<A, B> of(A left, B right) {
        return StrictPair.of(left, right);
    }

    static <A> A leftOf(Pair<A, ?> pair) {
        return pair.left();
    }

    static <A> A rightOf(Pair<?, A> pair) {
        return pair.right();
    }

    static <A> Pair<A, A> from(PureSeq<A> seq) {
        Pair<A, PureSeq<A>> pair = seq.next();
        return StrictPair.of(pair.left(), pair.right().first());
    }

    // default methods

    default <C> C apply(Func2<A, B, C> func) {
        return func.apply(left(), right());
    }

    default void apply(Effect2<A, B> effect) {
        effect.apply(left(), right());
    }

    default StrictPair<B, A> swap() {
        return StrictPair.of(right(), left());
    }

}
