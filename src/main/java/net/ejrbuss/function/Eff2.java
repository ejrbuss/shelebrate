package net.ejrbuss.function;

import net.ejrbuss.data2.Pair;

@FunctionalInterface
public interface Eff2<A, B> extends Fn<A, Eff<B>> {

    void apply(A arg1, B arg2);

    static <A, B> Eff2<A, B> from(Eff<Pair<A, B>> eff) {
        return (arg1, arg2) -> eff.apply(Pair.of(arg1, arg2));
    }

    @Override
    default Eff<B> apply(A arg1) {
        return arg2 -> apply(arg1, arg2);
    }

    default void spread(Pair<A, B> args) {
        apply(args.left(), args.right());
    }

    default Eff2<B, A> swap() {
        return (arg2, arg1) -> apply(arg1, arg2);
    }

}
