package net.ejrbuss.data;

import net.ejrbuss.function.fn.Fn;

public interface Maybe<A> {

    boolean isNone();

    boolean isSome();

    <B> Maybe<B> map(Fn<A, B> fn);

    <B> Maybe<B> flatMap(Fn<A, Maybe<B>> fn);

    A get();

    A get(A a);

    static <A> Maybe<A> some(A a) {
        return new Some<A>(a);
    }

    static <A> Maybe<A> none() {
        return None.none();
    }

    static <A> Maybe<A> from(Ref<A> ref) {
        if (ref.isEmpty()) {
            return none();
        } else {
            return some(ref.get());
        }
    }

    static <A> Maybe<A> from(Seq<A> seq) {
        if (seq.isEmpty()) {
            return none();
        } else {
            return some(seq.first());
        }
    }

}
