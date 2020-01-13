package net.ejrbuss.func;

import net.ejrbuss.data.Seq;

@FunctionalInterface
public interface Effect<A> {

    void apply(A a);

    // default methods

    default <B> Effect<B> of(Func<B, A> func) {
        return b -> apply(func.apply(b));
    }

}
