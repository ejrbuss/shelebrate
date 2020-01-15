package net.ejrbuss.function;

import net.ejrbuss.data2.Seq;

import java.util.ArrayList;
import java.util.List;

public interface VarFn<A, B> {

    @SuppressWarnings("unchecked")
    B apply(A... as);

    static <A> net.ejrbuss.function.fn.VarFn<A, A> from(A a, Fn2<A, A, A> combiner) {
        return null;
        // return as -> Seq.of(as).reduce(a, combiner);
    }

    default B apply(A[] buffer, Seq<A> seq) {
        List<A> list = new ArrayList<A>();
        for (A a : seq) {
            list.add(a);
        }
        return apply(list.toArray(buffer));
    }

}
