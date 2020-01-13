package net.ejrbuss.data;

import net.ejrbuss.func.Thunk;

public class LazySeq<A> implements Seq<A> {

    public static <A> LazySeq<A> from(Thunk<Pair<A, Seq<A>>> gen) {
        return new LazySeq<A>(gen);
    }

    public static <A> LazySeq<A> from(Seq<A> seq) {
        if (seq instanceof LazySeq) {
            return (LazySeq<A>) seq;
        }
        return new LazySeq<A>(() -> Pair.of(seq.first(), seq.rest()));
    }

    private final Thunk<Pair<A, Seq<A>>> generator;
    private final Thunk<Integer> hash;

    private LazySeq(Thunk<Pair<A, Seq<A>>> generator) {
        this.generator = generator.cached();
        this.hash = ((Thunk<Integer>) () -> strict().hashCode()).cached();
    }

    @Override
    public boolean isEmpty() {
        return generator.apply() == null;
    }

    @Override
    public A first() {
        return generator.apply().left();
    }

    @Override
    public Seq<A> rest() {
        return generator.apply().right();
    }

    @Override
    public int hashCode() {
        return hash.apply();
    }

    @Override
    public boolean equals(Object other) {
        return Seq.seqEqual(this, other);
    }

}
