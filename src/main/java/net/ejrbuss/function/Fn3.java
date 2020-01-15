package net.ejrbuss.function;

@FunctionalInterface
public interface Fn3<A, B, C, D> extends Fn2<A, B, Fn<C, D>> {

    D apply(A arg1, B arg2, C arg3);

    @Override
    default Fn<C, D> apply(A arg1, B arg2) {
        return arg3 -> apply(arg1, arg2, arg3);
    }

    default Fn2<B, C, D> apply(A arg1) {
        return (arg2, arg3) -> apply(arg1, arg2, arg3);
    }

}
