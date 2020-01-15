package net.ejrbuss.data;

import net.ejrbuss.function.fn.Fn;

import java.util.NoSuchElementException;

public class None<A> implements Maybe<A> {

    @SuppressWarnings({"rawtypes"})
    private static final None none = new None();

    @SuppressWarnings("unchecked")
    public static <A> None<A> none() {
        return none;
    }

    private None() {}

    @Override
    public boolean isNone() {
        return true;
    }

    @Override
    public boolean isSome() {
        return false;
    }

    @Override
    public <B> Maybe<B> map(Fn<A, B> fn) {
        return Maybe.none();
    }

    @Override
    public <B> Maybe<B> flatMap(Fn<A, Maybe<B>> fn) {
        return Maybe.none();
    }

    @Override
    public A get() {
        throw new NoSuchElementException();
    }

    @Override
    public A get(A a) {
        return a;
    }

}
