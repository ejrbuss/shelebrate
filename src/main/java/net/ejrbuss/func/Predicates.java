package net.ejrbuss.func;

import net.ejrbuss.data.Seq;

public final class Predicates {

    private Predicates () {}

    public static boolean even(long n) {
        return n % 2 == 0;
    }

    public static boolean odd(long n) {
        return n % 2 != 0;
    }

    public static boolean zero(long n) {
        return n == 0;
    }

    public static boolean always(Object _o) {
        return true;
    }

    public static boolean never(Object _o) {
        return false;
    }

    @SafeVarargs
    public static <A> Func<A, Boolean> and(Func<A, Boolean>... preds) {
        return Variadic.from(Predicates::always, (Func2<Func<A, Boolean>, Func<A, Boolean>, Func<A, Boolean>>) Predicates::and).apply(preds);
    }

    public static <A> Func<A, Boolean> and(Func<A, Boolean> pred1, Func<A, Boolean> pred2) {
        return a -> pred1.apply(a) && pred2.apply(a);
    }

    @SafeVarargs
    public static <A> Func<A, Boolean> or(Func<A, Boolean>... preds) {
        return Variadic.from(Predicates::never, (Func2<Func<A, Boolean>, Func<A, Boolean>, Func<A, Boolean>>) Predicates::or).apply(preds);
    }

    public static <A> Func<A, Boolean> or(Func<A, Boolean> pred1, Func<A, Boolean> pred2) {
        return a -> pred1.apply(a) || pred2.apply(a);
    }

    public static <A> Func<Seq<A>, Boolean> every(Func<A, Boolean> predicate) {
        return seq -> {
            for (A a : seq.iter()) {
                if (!predicate.apply(a)) {
                    return false;
                }
            }
            return true;
        };
    }

    public static <A> Func<Seq<A>, Boolean> some(Func<A, Boolean> predicate) {
        return seq -> {
            for (A a : seq.iter()) {
                if (predicate.apply(a)) {
                    return true;
                }
            }
            return false;
        };
    }

    public static <A> Func<A, Boolean> not(Func<A, Boolean> predicate) {
        return a -> !predicate.apply(a);
    }

}
