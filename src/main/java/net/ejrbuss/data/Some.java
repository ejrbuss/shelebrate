package net.ejrbuss.data;

import net.ejrbuss.function.fn.Fn;

public class Some<A> implements Maybe<A> {

    private final A val;

    public Some(A val) {
        this.val = val;
    }

    @Override
    public boolean isNone() {
        return false;
    }

    @Override
    public boolean isSome() {
        return true;
    }

    @Override
    public <B> Maybe<B> map(Fn<A, B> fn) {
        return new Some<B>(fn.$(val));
    }

    @Override
    public <B> Maybe<B> flatMap(Fn<A, Maybe<B>> fn) {
        return fn.$(val);
    }

    @Override
    public A get() {
        return val;
    }

    @Override
    public A get(A defaultValue) {
        return val;
    }

    @Override
    public int hashCode() {
        return val.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Some) {
            Some<?> otherSome = (Some<?>) other;
            return otherSome.val.equals(val);
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + "(" + val + ")";
    }

}
