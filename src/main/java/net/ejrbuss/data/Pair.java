package net.ejrbuss.data;

import net.ejrbuss.function.fn.Eff2;
import net.ejrbuss.function.fn.Fn2;

public interface Pair<A, B> {

    // required methods

    A left();
    B right();

    // static methods

    static <A, B> Pair<A, B> of(A left, B right) {
        return new StrictPair<>(left, right);
    }

    static <A> A leftOf(Pair<A, ?> pair) {
        return pair.left();
    }

    static <A> A rightOf(Pair<?, A> pair) {
        return pair.right();
    }

    static <A> Pair<A, A> from(InfSeq<A> seq) {
        return Pair.of(seq.first(), seq.rest().first());
    }

    static <A> Pair<A, A> from(Seq<A> seq) {
        return Pair.of(seq.first(), seq.rest().first());
    }

    // default methods

    default <C> C apply(Fn2<A, B, C> func) {
        return func.$(left(), right());
    }

    default void apply(Eff2<A, B> effect) {
        effect.$(left(), right());
    }

    default Pair<B, A> swap() {
        return Pair.of(right(), left());
    }

}
