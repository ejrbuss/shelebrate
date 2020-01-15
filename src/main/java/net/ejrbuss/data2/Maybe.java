package net.ejrbuss.data2;

import net.ejrbuss.function.*;

import java.util.NoSuchElementException;

public abstract class Maybe<A> {

    public static <A> Maybe<A> some(A value) {
        return new Some<>(value);
    }

    @SuppressWarnings("unchecked")
    public static <A> Maybe<A> none() {
        return None.none;
    }

    public static <A> Maybe<A> or(Maybe<A> maybe1, Maybe<A> maybe2) {
        if (maybe1.isNone()) {
            return maybe2;
        } else {
            return maybe1;
        }
    }

    public abstract boolean isNone();

    public abstract boolean isSome();

    public abstract A force();

    public abstract A get(A defaultValue);

    public abstract A get(Thunk<A> defaultValue);

    public abstract <B> B match(Fn<A, B> someMatch, Thunk<B> noneMatch);

    public abstract void each(Eff<A> eff);

    public abstract <B> Maybe<B> map(Fn<A, B> transform);

    public abstract <B> Maybe<B> flatMap(Fn<A, Maybe<B>> transform);

    public abstract Maybe<A> filter(Predicate<A> predicate);

    private static class Some<A> extends Maybe<A> {

        A value;

        public Some(A value) {
            this.value = value;
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
        public A force() {
            return value;
        }

        @Override
        public A get(A defaultValue) {
            return value;
        }

        @Override
        public A get(Thunk<A> defaultValue) {
            return value;
        }

        @Override
        public <B> B match(Fn<A, B> someMatch, Thunk<B> noneMatch) {
            return someMatch.apply(value);
        }

        @Override
        public void each(Eff<A> eff) {
            eff.apply(value);
        }

        @Override
        public <B> Maybe<B> map(Fn<A, B> transform) {
            return new Some(transform.apply(value));
        }

        @Override
        public <B> Maybe<B> flatMap(Fn<A, Maybe<B>> transform) {
            return transform.apply(value);
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof Some) {
                Some<?> otherSome = (Some) other;
                return value.equals(otherSome.value);
            }
            return false;
        }

        @Override
        public Maybe<A> filter(Predicate<A> predicate) {
            if (predicate.apply(value)) {
                return this;
            } else {
                return none();
            }
        }

        @Override
        public String toString() {
            return super.toString() + "(" + value + ")";
        }

    }

    private static class None<A> extends Maybe<A> {

        @SuppressWarnings("rawtypes")
        public static None none = new None<>();

        @Override
        public boolean isNone() {
            return true;
        }

        @Override
        public boolean isSome() {
            return false;
        }

        @Override
        public A force() {
            throw new NoSuchElementException("force on None!");
        }

        @Override
        public A get(A defaultValue) {
            return defaultValue;
        }

        @Override
        public A get(Thunk<A> defaultValue) {
            return defaultValue.apply();
        }

        @Override
        public <B> B match(Fn<A, B> someMatch, Thunk<B> noneMatch) {
            return noneMatch.apply();
        }

        @Override
        public void each(Eff<A> eff) {}

        @Override
        @SuppressWarnings("unchecked")
        public <B> Maybe<B> map(Fn<A, B> transform) {
            return (Maybe<B>) this;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <B> Maybe<B> flatMap(Fn<A, Maybe<B>> transform) {
            return (Maybe<B>) this;
        }

        @Override
        public Maybe<A> filter(Predicate<A> predicate) {
            return this;
        }

    }

}
