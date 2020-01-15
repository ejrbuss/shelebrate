package net.ejrbuss.data;

import net.ejrbuss.function.fn.Fn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class StrictSeq<A> implements Seq<A> {

    @SafeVarargs
    public static <A> StrictSeq<A> of(A... as) {
        return new StrictSeq<A>(Arrays.asList(as), 0);
    }

    public static <A> StrictSeq<A> from(Seq<A> seq) {
        if (seq instanceof StrictSeq) {
            return (StrictSeq<A>) seq;
        }
        List<A> vec = new ArrayList<A>();
        for (A a : seq) {
            vec.add(a);
        }
        return new StrictSeq<A>(vec, 0);
    }

    public static <A> StrictSeq<A> from(List<A> list) {
        return new StrictSeq<A>(list, 0);
    }

    private final List<A> list;
    private final int i;

    private StrictSeq(List<A> list, int i) {
        this.list = list;
        this.i = i;
    }

    @Override
    public boolean isEmpty() {
        return i >= list.size();
    }

    @Override
    public Iterator<A> iterator() {
        return list.listIterator(i);
    }

    @Override
    public A first() {
        if (isEmpty()) {
            throw new EmptyException();
        }
        return list.get(i);
    }

    @Override
    public Seq<A> rest() {
        if (isEmpty()) {
            throw new EmptyException();
        }
        return new StrictSeq<A>(list, i + 1);
    }

    @Override
    public A last() {
        return list.get(list.size() - 1);
    }

    @Override
    public int count() {
        return list.size() - i;
    }

    @Override
    public A nth(int n) {
        if (n < 0) {
            n = list.size() + n;
        }
        if (i + n >= 0 && i + n < list.size()) {
            return list.get(i + n);
        }
        throw new IndexOutOfBoundsException("index: " + n + " bounds " + i);
    }

    public <B extends Comparable<B>> Seq<A> sortBy(Fn<A, B> selector) {
        List<A> copy = new ArrayList<A>(list);
        copy.sort((A a, A b) -> selector.$(a).compareTo(selector.$(b)));
        return Seq.from(copy);
    }

    @Override
    public boolean equals(Object other) {
        return Seq.seqEqual(this, other);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + "(" + join(", ") + ")";
    }
}
