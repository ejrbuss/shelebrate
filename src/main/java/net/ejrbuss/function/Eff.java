package net.ejrbuss.function;

@FunctionalInterface
public interface Eff<A> {

    void apply(A arg);

    static Eff<?> nothing() {
        return arg -> {};
    }

    default <B> Eff<B> of(Fn<B, ? extends A> fn) {
        return arg -> apply(fn.apply(arg));
    }

}
