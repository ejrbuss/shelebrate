package net.ejrbuss.function;

import net.ejrbuss.data2.Maybe;
import net.ejrbuss.data2.Ref;

@FunctionalInterface
public interface Thunk<A> {

    A apply();

    static <A> Thunk<A> of(A thing) {
        return () -> thing;
    }

    default <B> Thunk<B> then(Fn<A, B> fn) {
        return () -> fn.apply(apply());
    }

    default Thunk<A> cached() {
        Ref<A> cache = Ref.of(null);
        return () -> {
            cache.compareAndSet(null, this);
            return cache.get();
        };
    }

}
