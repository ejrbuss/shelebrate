package net.ejrbuss.function.fn;

import net.ejrbuss.data.Ref;

@FunctionalInterface
public interface Thunk<A> {

    A $();

    // static methods

    static <A> Thunk<A> of(A a) {
        return () -> a;
    }

    // default methods

    default <B> Thunk<B> then(Fn<A, B> fn) {
        return () -> fn.$($());
    }

    default Thunk<A> cached() {
        Thunk<A> thunk = this;
        Ref<A> ref = Ref.empty();
        return () -> ref.getOrSet(thunk);
    }

}
