package net.ejrbuss.func;

@FunctionalInterface
public interface Func<A, B> {

    B apply(A a);

    // static methods

    static <A> A id(A a) {
        return a;
    }

    // default methods

    default <C> Func<C, B> of(Func<? super C, ? extends A> func) {
        return c -> apply(func.apply(c));
    }

    default <C> Func<A, C> then(Func<? super B, ? extends C> func) {
        return a -> func.apply(apply(a));
    }

}
