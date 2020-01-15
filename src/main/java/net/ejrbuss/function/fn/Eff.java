package net.ejrbuss.function.fn;

@FunctionalInterface
public interface Eff<A> {

    void $(A a);

    // default methods

    default <B> Eff<B> of(Fn<B, A> fn) {
        return b -> $(fn.$(b));
    }

}
