package net.ejrbuss.func;

import net.ejrbuss.data.NumericOps;
import net.ejrbuss.data.Seq;

import java.util.ArrayList;
import java.util.List;


public interface Variadic<A, B> {

    @SuppressWarnings("unchecked")
    B apply(A... as);

    // static methods

    static <A> Variadic<A, A> from(A a, Func2<A, A, A> combiner) {
        return as -> Seq.of(as).reduce(a, combiner);
    }

    // default methods

    default B apply(A[] buffer, Seq<A> seq) {
        List<A> list = new ArrayList<A>();
        for (A a : seq.iter()) {
            list.add(a);
        }
        return apply(list.toArray(buffer));
    }

}
