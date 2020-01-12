package net.ejrbuss.func;

@FunctionalInterface
public interface Func3<A, B, C, D> extends Func2<A, B, Func<C, D>> {

    D apply(A a, B b, C c);

    // default methods

    @Override
    default Func<C, D> apply(A a, B b) {
        return c -> apply(a, b, c);
    }
}
