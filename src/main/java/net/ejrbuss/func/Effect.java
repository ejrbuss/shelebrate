package net.ejrbuss.func;

@FunctionalInterface
public interface Effect<A> {

    void apply(A a);

    // default methods

    default <B> Effect<B> of(Func<B, A> func) {
        return b -> apply(func.apply(b));
    }

}
