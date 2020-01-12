package net.ejrbuss.data;

public class LazySeq<A> implements Seq<A> {

    public static <A> Seq<A> from(Seq<A> seq) {
        return new LazySeq<A>(seq);
    }

    private final Seq<A> seq;

    private LazySeq(Seq<A> seq) {
        this.seq = seq;
    }

    @Override
    public Pair<A, Seq<A>> next() {
        return seq.next();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Seq) {
            Seq<?> otherSeq = (Seq<?>) other;
            return Seq.equals(this, otherSeq);
        }
        return false;
    }

}
