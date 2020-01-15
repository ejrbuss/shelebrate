package net.ejrbuss.function;

import net.ejrbuss.data2.Pair;

@FunctionalInterface
public interface Fn2<A, B, C> extends Fn<A, Fn<B, C>> {

    C apply(A arg1, B arg2);

    static <A, B, C> Fn2<A, B, C> from(Fn<Pair<A, B>, C> fn) {
        return (arg1, arg2) -> fn.apply(Pair.of(arg1, arg2));
    }

    @Override
    default Fn<B, C> apply(A arg1) {
        return arg2 -> apply(arg1, arg2);
    }

    default Thunk<C> thunk(A arg1, B arg2) {
        return () -> apply(arg1, arg2);
    }

    default C spread(Pair<A, B> args) {
        return apply(args.left(), args.right());
    }

    default Fn2<B, A, C> swap() {
        return (arg2, arg1) -> apply(arg1, arg2);
    }

}
