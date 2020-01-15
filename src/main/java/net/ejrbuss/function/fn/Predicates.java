package net.ejrbuss.function.fn;

public final class Predicates {

    private Predicates() {}

    public static boolean even(int n) {
        return n % 2 == 0;
    }

    public static boolean odd(int n) {
        return n % 2 != 0;
    }

    public static boolean zero(int n) {
        return n == 0;
    }

    public static boolean even(long n) {
        return n % 2 == 0;
    }

    public static boolean odd(long n) {
        return n % 2 != 0;
    }

    public static boolean zero(long n) {
        return n == 0;
    }

    public static <A extends Comparable<A>> Fn<A, Boolean> eq(A a) {
        return b -> a.equals(b);
    }

    public static <A extends Comparable<A>> Fn<A, Boolean> lt(A a) {
        return b -> b.compareTo(a) < 0;
    }

    public static <A extends Comparable<A>> Fn<A, Boolean> lte(A a) {
        return b -> b.compareTo(a) <= 0;
    }

    public static <A extends Comparable<A>> Fn<A, Boolean> gt(A a) {
        return b -> b.compareTo(a) > 0;
    }

    public static <A extends Comparable<A>> Fn<A, Boolean> gte(A a) {
        return b -> b.compareTo(a) >= 0;
    }

    public static boolean always(Object _o) {
        return true;
    }

    public static boolean never(Object _o) {
        return false;
    }

    public static <A> Fn<A, Boolean> and(Fn<A, Boolean> pred1, Fn<A, Boolean> pred2) {
        return a -> pred1.$(a) && pred2.$(a);
    }

    public static <A> Fn<A, Boolean> or(Fn<A, Boolean> pred1, Fn<A, Boolean> pred2) {
        return a -> pred1.$(a) || pred2.$(a);
    }

    public static <A> Fn<A, Boolean> xor(Fn<A, Boolean> pred1, Fn<A, Boolean> pred2) {
        return a -> pred1.$(a) != pred2.$(a);
    }

    public static <A> Fn<A, Boolean> not(Fn<A, Boolean> predicate) {
        return a -> !predicate.$(a);
    }

}
