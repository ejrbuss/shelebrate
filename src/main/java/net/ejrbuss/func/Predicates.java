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

    public static <A extends Comparable<A>> Func<A, Boolean> eq(A a) {
        return b -> a.equals(b);
    }

    public static <A extends Comparable<A>> Func<A, Boolean> lt(A a) {
        return b -> b.compareTo(a) < 0;
    }

    public static <A extends Comparable<A>> Func<A, Boolean> lte(A a) {
        return b -> b.compareTo(a) <= 0;
    }

    public static <A extends Comparable<A>> Func<A, Boolean> gt(A a) {
        return b -> b.compareTo(a) > 0;
    }

    public static <A extends Comparable<A>> Func<A, Boolean> gte(A a) {
        return b -> b.compareTo(a) >= 0;
    }

    public static boolean always(Object _o) {
        return true;
    }

    public static boolean never(Object _o) {
        return false;
    }

    public static <A> Func<A, Boolean> and(Func<A, Boolean> pred1, Func<A, Boolean> pred2) {
        return a -> pred1.apply(a) && pred2.apply(a);
    }

    public static <A> Func<A, Boolean> or(Func<A, Boolean> pred1, Func<A, Boolean> pred2) {
        return a -> pred1.apply(a) || pred2.apply(a);
    }

    public static <A> Func<A, Boolean> not(Func<A, Boolean> predicate) {
        return a -> !predicate.apply(a);
    }

}
