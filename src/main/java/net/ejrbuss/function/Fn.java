package net.ejrbuss.function;

@FunctionalInterface
public interface Fn<A, B> {

    B apply(A arg);

    default Thunk<B> thunk(A arg) {
        return () -> apply(arg);
    }

    default <C> Fn<C, B> of(Fn<C, ? extends A> fn) {
        return c -> apply(fn.apply(c));
    }

    default <C> Fn<A, C> then(Fn<? super B, C> fn) {
        return a -> fn.apply(apply(a));
    }

}
