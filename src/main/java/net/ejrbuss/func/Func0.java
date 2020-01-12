package net.ejrbuss.func;

@FunctionalInterface
public interface Func0<A> {

    A apply();

    // static methods

    static <A> Func0<A> of(A a) {
        return () -> a;
    }

    // default methods

    default <B> Func0<B> then(Func<A, B> func) {
        return () -> func.apply(apply());
    }

}
