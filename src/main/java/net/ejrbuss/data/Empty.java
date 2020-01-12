package net.ejrbuss.data;

public final class Empty<A> implements Seq<A> {

    @SuppressWarnings("rawtypes")
    private static final Empty empty = new Empty();

    @SuppressWarnings("unchecked")
    public static <A> Empty<A> empty() {
        return empty;
    }

    private Empty() {}

    @Override
    public Pair<A, Seq<A>> next() {
        throw new EmptyException();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

}
