package net.ejrbuss.function.fn;

import net.ejrbuss.data.Seq;

import java.util.ArrayList;
import java.util.List;


public interface VarFn<A, B> {

    @SuppressWarnings("unchecked")
    B $(A... as);

    // static methods

    static <A> VarFn<A, A> from(A a, Fn2<A, A, A> combiner) {
        return as -> Seq.of(as).reduce(a, combiner);
    }

    // default methods

    default B $(A[] buffer, Seq<A> seq) {
        List<A> list = new ArrayList<A>();
        for (A a : seq) {
            list.add(a);
        }
        return $(list.toArray(buffer));
    }

}
