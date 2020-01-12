package net.ejrbuss.data;

import net.ejrbuss.func.Func;

import java.util.NoSuchElementException;

public abstract class Maybe<A> {

    @SuppressWarnings("rawtypes")
    private static None none;

    public static <A> Maybe<A> some(A a) {
        return new Some<A>(a);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <A> Maybe<A> none() {
        if (none == null) {
            none = new None();
        }
        return none;
    }

    public static <A> Maybe<A> from(Seq<A> seq) {
        if (seq.isEmpty()) {
            return none();
        } else {
            return some(seq.first());
        }
    }

    abstract boolean isNone();
    abstract boolean isSome();
    abstract <B> Maybe<B> map(Func<A, B> func);
    abstract <B> Maybe<B> flatMap(Func<A, Maybe<B>> func);
    abstract A get();
    abstract A get(A a);

    private static final class Some<A> extends Maybe<A> {

        private final A a;

        Some(A a) {
            this.a = a;
        }

        @Override
        boolean isNone() {
            return false;
        }

        @Override
        boolean isSome() {
            return true;
        }

        @Override
        <B> Maybe<B> map(Func<A, B> func) {
            return new Some<B>(func.apply(a));
        }

        @Override
        <B> Maybe<B> flatMap(Func<A, Maybe<B>> func) {
            return func.apply(a);
        }

        @Override
        A get() {
            return a;
        }

        @Override
        A get(A _a) {
            return a;
        }

        @Override
        public int hashCode() {
            return a.hashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof Some) {
                Some<?> otherSome = (Some<?>) other;
                return otherSome.a.equals(a);
            }
            return false;
        }

        @Override
        public String toString() {
            return super.toString() + "(" + a + ")";
        }

    }

    private static final class None<A> extends Maybe<A> {

        @Override
        boolean isNone() {
            return true;
        }

        @Override
        boolean isSome() {
            return false;
        }

        @Override
        <B> Maybe<B> map(Func<A, B> func) {
            return none();
        }

        @Override
        <B> Maybe<B> flatMap(Func<A, Maybe<B>> func) {
            return none();
        }

        @Override
        A get() {
            throw new NoSuchElementException();
        }

        @Override
        A get(A a) {
            return a;
        }

    }

}
