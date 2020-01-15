package net.ejrbuss.data2;

public class Pair<A, B> {

    public static <A, B> Pair<A, B> of(A left, B right) {
        return new Pair<>(left, right);
    }

    private final A left;
    private final B right;

    private Pair(A left, B right) {
        this.left = left;
        this.right = right;
    }

    public A left() {
        return left;
    }

    public B right() {
        return right;
    }

}
