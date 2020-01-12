package net.ejrbuss.data;

import net.ejrbuss.func.Effect2;
import net.ejrbuss.func.Func2;

import java.util.function.BiFunction;

public final class Pair<A, B> {

    public static <A, B> Pair<A, B> of(A left, B right) {
        return new Pair<A, B>(left, right);
    }

    public static <A> A left(Pair<A, ?> pair) {
        return pair.left;
    }

    public static <A> A right(Pair<?, A> pair) {
        return pair.right;
    }

    public static <A> Pair<A, A> from(Seq<A> seq) {
        Pair<A, Seq<A>> pair = seq.next();
        return Pair.of(pair.left, pair.right.first());
    }

    public final A left;
    public final B right;

    private Pair(A left, B right) {
        this.left = left;
        this.right = right;
    }

    public <C> C apply(Func2<A, B, C> func) {
        return func.apply(left, right);
    }

    public void apply(Effect2<A, B> effect) {
        effect.apply(left, right);
    }

    public Pair<B, A> swap() {
        return Pair.of(right, left);
    }

    @Override
    public int hashCode() {
        return left.hashCode() ^ right.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Pair) {
            Pair<?, ?> otherPair = (Pair<?, ?>) other;
            return otherPair.left.equals(left) && otherPair.right.equals(right);
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + "(" + left + ", " + right + ")";
    }
}
