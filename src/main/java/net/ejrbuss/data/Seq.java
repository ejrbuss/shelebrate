package net.ejrbuss.data;

import net.ejrbuss.func.*;

import java.util.*;

public interface Seq<A> extends PureSeq<A> {

    boolean isEmpty();

    A first();

    Seq<A> rest();

    // static methods

    static <A> Seq<A> empty() {
        return Empty.empty();
    }

    static <A> Seq<A> of(A a) {
        return StrictSeq.of(a);
    }

    @SafeVarargs
    static <A> Seq<A> of(A... as) {
        return StrictSeq.of(as);
    }

    static <A> Seq<A> from(Thunk<Pair<A, Seq<A>>> generator) {
        return LazySeq.from(generator);
    }

    static <A> Seq<A> from(Maybe<A> maybe) {
        if (maybe.isNone()) {
            return empty();
        } else {
            return Seq.of(maybe.get());
        }
    }

    static <A> Seq<A> from(Pair<A, A> pair) {
        return Seq.of(pair.left(), pair.right());
    }

    static <A> Seq<A> from(List<A> collection) {
        return StrictSeq.from(collection);
    }

    static <A> Seq<A> concat(Seq<A> seq1, Seq<A> seq2) {
        if (seq1.isEmpty()) {
            return seq2;
        }
        return LazySeq.from(() -> Pair.of(seq1.first(), Seq.concat(seq1.rest(), seq2)));
    }

    @SafeVarargs
    static <A> Seq<A> concat(Seq<A>... sequences) {
        return Variadic.from(empty(), (Func2<Seq<A>, Seq<A>, Seq<A>>) Seq::concat).apply(sequences);
    }

    static <A, B> Seq<Pair<A, B>> zip(Seq<A> seq1, Seq<B> seq2) {
        return zipWith(Pair::of, seq1, seq2);
    }

    static <A, B, C> Seq<C> zipWith(Func2<? super A, ? super B, C> combiner, Seq<A> seq1, Seq<B> seq2) {
        if (seq1.isEmpty() || seq2.isEmpty()) {
            return empty();
        }
        return LazySeq.from(() -> Pair.of(
                combiner.apply(seq1.first(), seq2.first()),
                zipWith(combiner, seq1.rest(), seq2.rest())
        ));
    }

    static Seq<Integer> range(int end) {
        return Seq.range(0, end);
    }

    static Seq<Integer> range(int begin, int end) {
        if (begin <= end) {
            return Seq.range(begin, end, 1);
        } else {
            return Seq.range(begin, end, -1);
        }
    }

    static Seq<Integer> range(int begin, int end, int step) {
        if (step > 0 && begin >= end) {
            return empty();
        }
        if (step < 0 && begin <= end) {
            return empty();
        }
        return LazySeq.from(() -> Pair.of(begin, Seq.range(begin + step, end, step)));
    }

    static Seq<Double> range(double begin, double end, double step) {
        if (step > 0 && begin >= end) {
            return empty();
        }
        if (step < 0 && begin <= end) {
            return empty();
        }
        return LazySeq.from(() -> Pair.of(begin, Seq.range(begin + step, end, step)));
    }

    static <A> boolean seqEqual(Seq<A> seq, Object other) {
        if (other instanceof Seq) {
            Seq<?> otherSeq = (Seq<?>) other;
            while (!seq.isEmpty() && !otherSeq.isEmpty()) {
                if (!seq.first().equals(otherSeq.first())) {
                    return false;
                }
                seq = seq.rest();
                otherSeq = otherSeq.rest();
            }
            return seq.isEmpty() == otherSeq.isEmpty();
        }
        return false;
    }

    // Default methods

    @Override
    default Pair<A, PureSeq<A>> next() {
        return Pair.of(first(), rest());
    }

    default Iterable<A> iter() {
        return () -> {
            final Seq<A> that = this;
            return new Iterator<A>() {
                Seq<A> seq = that;

                @Override
                public boolean hasNext() {
                    return !seq.isEmpty();
                }

                @Override
                public A next() {
                    A element = seq.first();
                    seq = seq.rest();
                    return element;
                }
            };
        };
    }

    default A last() {
        return reduce(first(), (_a, a) -> a);
    }

    default int count() {
        return reduce(0, (count, _a) -> count + 1);
    }

    default A nth(int n) {
        if (n < 0) {
            return reverse().nth(-(n + 1));
        }
        int i = 0;
        for (A element : iter()) {
            if (i++ == n) {
                return element;
            }
        }
        throw new IndexOutOfBoundsException("index: " + n + " bounds: " + i);
    }

    default Seq<A> prepend(A element) {
        Seq<A> seq = this;
        return LazySeq.from(() -> Pair.of(element, seq));
    }

    default Seq<A> prependAll(Seq<A> seq) {
        return concat(seq, this);
    }

    default Seq<A> append(A element) {
        Seq<A> seq = this;
        return LazySeq.from(() -> {
           if (seq.isEmpty()) {
               return Pair.of(element, empty());
           }
           return Pair.of(first(), rest().append(element));
        });
    }

    default <B> Seq<A> takeWhile(B accumulator, Func2<B, ? super A, Pair<B, Boolean>> predicate) {
        if (isEmpty()) {
            return empty();
        }
        Seq<A> seq = this;
        Pair<B, Boolean> pair = predicate.apply(accumulator, first());
        if (!pair.right()) {
            return Seq.empty();
        }
        return LazySeq.from(() ->
                Pair.of(seq.first(), seq.rest().takeWhile(pair.left(), predicate))
        );
    }

    default Seq<A> drop(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0! Found: " + n);
        }
        return dropWhile((_element, i) -> i < n);
    }

    default Seq<A> dropWhile(Func<? super A, Boolean> predicate) {
        return dropWhile((e, _i) -> predicate.apply(e));
    }

    default Seq<A> dropWhile(Func2<? super A, Integer, Boolean> predicate) {
        return dropWhile(0, (i, e) -> Pair.of(i + 1, predicate.apply(e, i)));
    }

    default <B> Seq<A> dropWhile(B accumulator, Func2<B, ? super A, Pair<B, Boolean>> predicate) {
        if (isEmpty()) {
            return empty();
        }
        return LazySeq.from(() -> {
            Pair<B, Boolean> result = predicate.apply(accumulator, first());
            if (result.right()) {
                Seq<A> droppedRest = rest().dropWhile(result.left(), predicate);
                if (droppedRest.isEmpty()) {
                    return null;
                }
                return Pair.of(droppedRest.first(), droppedRest.rest());
            } else {
                return Pair.of(first(), rest());
            }
        });
    }

    default void forEach(Effect<? super A> effect) {
        forEach((element, _i) -> effect.apply(element));
    }

    default void forEach(Effect2<? super A, Integer> effect) {
        int i = 0;
        for (A element : iter()) {
            effect.apply(element, i++);
        }
    }

    default <B> Seq<B> map(Func<? super A, B> transform) {
        return map((e, _i) -> transform.apply(e));
    }

    default <B> Seq<B> map(Func2<?super A, Integer, B> transform) {
        return map(0, (i, e) -> Pair.of(i + 1, transform.apply(e, i)));
    }

    default <B, C> Seq<B> map(C accumulator, Func2<C, ? super A, Pair<C, B>> transform) {
        if (isEmpty()) {
            return empty();
        }
        return LazySeq.from(() -> {
            Pair<C, B> pair2 = transform.apply(accumulator, first());
            return Pair.of(
                    pair2.right(),
                    rest().map(pair2.left(), transform)
            );
        });
    }

    default <B> Seq<B> flatMap(Func<? super  A, Seq<B>> transform) {
        return flatMap((e, _i) -> transform.apply(e));
    }

    default <B> Seq<B> flatMap(Func2<? super A, Integer, Seq<B>> transform) {
        return flatMap(0, (i, e) -> Pair.of(i + 1, transform.apply(e, i)));
    }

    default <B, C> Seq<B> flatMap(C accumulator, Func2<C, ? super A, Pair<C, Seq<B>>> transform) {
        if (isEmpty()) {
            return empty();
        }
        Pair<C, Seq<B>> pair = transform.apply(accumulator, first());
        if (pair.right().isEmpty()) {
            return rest().flatMap(pair.left(), transform);
        }
        Seq<A> seq = this;
        return LazySeq.from(() -> {
            Seq<B> seq1 = seq.rest().flatMap(pair.left(), transform);
            if (seq1.isEmpty()) {
                return null;
            }
            return Pair.of(seq1.first(), seq1.rest());
        }).prependAll(pair.right());
    }

    default Seq<A> filter(Func<? super A, Boolean> predicate) {
        return filter((e, _i) -> predicate.apply(e));
    }

    default Seq<A> filter(Func2<? super A, Integer, Boolean> predicate) {
        return filter(0, (i, e) -> Pair.of(i + 1, predicate.apply(e, i)));
    }

    default <B> Seq<A> filter(B accumulator, Func2<B, ? super A, Pair<B, Boolean>> predicate) {
        return flatMap(accumulator, (acc, e) -> {
            Pair<B, Boolean> pair = predicate.apply(acc, e);
            if (pair.right()) {
                return Pair.of(pair.left(), Seq.of(e));
            } else {
                return Pair.of(pair.left(), empty());
            }
        });
    }

    default A reduce(Func2<A, ? super A, A> reducer) {
        return rest().reduce(first(), (acc, e, _i) -> reducer.apply(acc, e));
    }

    default A reduce(Func3<A, ? super A, Integer, A> reducer) {
        return rest().reduce(first(), (acc, e, i) -> reducer.apply(acc, e, i + 1));
    }

    default <B> B reduce(B accumulator, Func2<B, ? super A, B> reducer) {
        return reduce(accumulator, (acc, a, _i) -> reducer.apply(acc, a));
    }

    default <B> B reduce(B accumulator, Func3<B, ? super A, Integer, B> reducer) {
        int i = 0;
        for (A element : iter()) {
            accumulator = reducer.apply(accumulator, element, i++);
        }
        return accumulator;
    }

    default String join() {
        return join("");
    }

    default String join(String separator) {
        return reduce(new StringBuilder(), (builder, a, i) -> {
            if (i != 0) {
                builder.append(separator);
            }
            return builder.append(a.toString());
        }).toString();
    }

    default Seq<A> reverse() {
        if (isEmpty()) {
            return this;
        }
        return rest().reverse().append(first());
    }

    default boolean all(Func<? super A, Boolean> predicate) {
        for (A a : iter()) {
            if (!predicate.apply(a)) {
                return false;
            }
        }
        return true;
    }

    default boolean any(Func<? super A, Boolean> predicate) {
        for (A a : iter()) {
            if (predicate.apply(a)) {
                return true;
            }
        }
        return false;
    }

    default Seq<A> strict() {
        return StrictSeq.from(this);
    }

    default Maybe<Integer> index(A a) {
        int i = 0;
        for (A other : iter()) {
            if (a.equals(other)) {
                return Maybe.some(i);
            }
            i++;
        }
        return Maybe.none();
    }

    default boolean has(A a) {
        return any(a::equals);
    }

    default Seq<Seq<A>> chunk(int n) {
        if (isEmpty()) {
            return empty();
        }
        Seq<A> seq = this;
        return LazySeq.from(() -> {
            Seq<A> seq1 = seq;
            Seq<A> seq2 = Seq.empty();
            for (int i = 0; i < n && !seq1.isEmpty(); i++) {
                seq2 = seq2.append(seq1.first());
                seq1 = seq1.rest();
            }
            return Pair.of(seq2, seq1.chunk(n));
        });
    }

    default Seq<Pair<A, A>> pairUp() {
        return chunk(2).takeWhile(e -> e.count() == 2).map(Pair::from);
    }

    default <B extends Comparable<B>> Seq<A> sortBy(Func<A, B> selector) {
        return StrictSeq.from(this).sortBy(selector);
    }

}
