package net.ejrbuss.data;

final class Empty<A> implements Seq<A> {

    @SuppressWarnings("rawtypes")
    private static final Empty empty = new Empty();

    @SuppressWarnings("unchecked")
    public static <A> Empty<A> empty() {
        return empty;
    }

    private Empty() {}

    @Override
    public A first() {
        throw new EmptyException();
    }

    @Override
    public Seq<A> rest() {
        throw new EmptyException();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object other) {
        return Seq.seqEqual(this, other);
    }
}
