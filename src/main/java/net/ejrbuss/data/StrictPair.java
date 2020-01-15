package net.ejrbuss.data;

public final class StrictPair<A, B> implements Pair<A, B> {

    private final A left;
    private final B right;

    public StrictPair(A left, B right) {
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
        if (other instanceof Pair) {
            Pair<?, ?> otherPair = (Pair<?, ?>) other;
            return otherPair.left().equals(left) && otherPair.right().equals(right);
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + "(" + left + ", " + right + ")";
    }

}
