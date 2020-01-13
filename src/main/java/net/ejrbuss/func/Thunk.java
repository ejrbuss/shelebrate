package net.ejrbuss.func;

import net.ejrbuss.data.Maybe;
import net.ejrbuss.data.Ref;

@FunctionalInterface
public interface Thunk<A> {

    A apply();

    // static methods

    static <A> Thunk<A> of(A a) {
        return () -> a;
    }

    // default methods

    default <B> Thunk<B> then(Func<A, B> func) {
        return () -> func.apply(apply());
    }

    default Thunk<A> cached() {
        Thunk<A> thunk = this;
        Ref<A> ref = Ref.empty();
        return () -> ref.getOrSet(thunk);
    }

}
