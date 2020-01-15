package net.ejrbuss.function.fn;

@FunctionalInterface
public interface Fn<A, B> {

    B $(A a);

    // static methods

    static <A> A id(A a) {
        return a;
    }

    static <A> void call(Eff<A> eff, A arg) {
        eff.$(arg);
    }

    static <A, B> void call(Eff2<A, B> eff, A arg1, B arg2) {
        eff.$(arg1, arg2);
    }

    static <A> A call(Thunk<A> thunk) {
        return thunk.$();
    }

    static <A, B> B call(Fn<A, B> fn, A arg) {
        return fn.$(arg);
    }

    static <A, B, C> C call(Fn2<A, B, C> fn, A arg1, B arg2) {
        return fn.$(arg1, arg2);
    }

    static <A, B, C, D> D call(Fn3<A, B, C, D> fn, A arg1, B arg2, C arg3) {
        return fn.$(arg1, arg2, arg3);
    }

    @SafeVarargs
    static <A, B> B call(VarFn<A, B> fn, A... args) {
        return fn.$(args);
    }

    static <A, B> Thunk<B> curry(Fn<A, B> fn, A arg) {
        return () -> fn.$(arg);
    }

    static <A, B, C> Thunk<C> curry(Fn2<A, B, C> fn, A arg1, B arg2) {
        return () -> fn.$(arg1, arg2);
    }

    static <A, B, C, D> Thunk<D> curry(Fn3<A, B, C, D> fn, A arg1, B arg2, C arg3) {
        return () -> fn.$(arg1, arg2, arg3);
    }

    static <A, B, C> Fn<B, C> curry(Fn2<A, B, C> fn, A arg1) {
        return fn.$(arg1);
    }

    static <A, B, C, D> Fn<C, D> curry(Fn3<A, B, C, D> fn, A arg1, B arg2) {
        return fn.$(arg1).$(arg2);
    }

    static <A, B, C, D> Fn2<B, C, D> curry(Fn3<A, B, C, D> fn, A arg1) {
        return (arg2, arg3) -> fn.$(arg1, arg2, arg3);
    }

    // default methods

    default <C> Fn<C, B> of(Fn<? super C, ? extends A> fn) {
        return c -> $(fn.$(c));
    }

    default <C> Fn<A, C> then(Fn<? super B, ? extends C> fn) {
        return a -> fn.$($(a));
    }

}
