package net.ejrbuss.data;

public final class StrictPair<A, B> implements Pair<A, B> {

    public static <A, B> StrictPair<A, B> of(A left, B right) {
        return new StrictPair<A, B>(left, right);
    }

    private final A left;
    private final B right;

    private StrictPair(A left, B right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public A left() {
        return left;
    }

    @Override
    public B right() {
        return right;
    }

    @Override
    public int hashCode() {
        return left.hashCode() ^ right.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof StrictPair) {
            StrictPair<?, ?> otherPair = (StrictPair<?, ?>) other;
            return otherPair.left.equals(left) && otherPair.right.equals(right);
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + "(" + left + ", " + right + ")";
    }
}
