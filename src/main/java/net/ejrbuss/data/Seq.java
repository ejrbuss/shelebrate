package net.ejrbuss.data;

import net.ejrbuss.func.*;

import java.util.*;

@FunctionalInterface
public interface Seq<A> {

    // Required methods

    Pair<A, Seq<A>> next();

    // Static Methods

    static <A> Seq<A> empty() {
        return Empty.empty();
    }

    static <A> Seq<A> of(A a) {
        return LazySeq.from(() -> Pair.of(a, empty()));
    }

    @SafeVarargs
    static <A> Seq<A> of(A... as) {
        Seq<A> seq = empty();
        for (A a : as) {
            seq = seq.append(a);
        }
        return seq;
    }

    static <A> Seq<A> from(Maybe<A> maybe) {
        if (maybe.isNone()) {
            return empty();
        } else {
            return Seq.of(maybe.get());
        }
    }

    static <A> Seq<A> from(Pair<A, A> pair) {
        return Seq.of(pair.left, pair.right);
    }

    static <A> Seq<A> continually(Func0<A> supplier) {
        return LazySeq.from(() -> Pair.of(supplier.apply(), Seq.continually(supplier)));
    }

    static <A> Seq<A> continually(A a, Func<A, A> generator) {
        return LazySeq.from(() -> Pair.of(a, Seq.continually(generator.apply(a), generator)));
    }

    @SafeVarargs
    static <A> Seq<A> concat(Seq<A>... seqs) {
        return Variadic.from(empty(), (Func2<Seq<A>, Seq<A>, Seq<A>>) Seq::concat).apply(seqs);
    }

    static <A> Seq<A> concat(Seq<A> seq1, Seq<A> seq2) {
        if (seq1.isEmpty()) {
            return seq2;
        }
        return LazySeq.from(() -> {
            Pair<A, Seq<A>> pair = seq1.next();
            return Pair.of(pair.left, Seq.concat(pair.right, seq2));
        });
    }

    static <A, B> Seq<Pair<A, B>> zip(Seq<A> seq1, Seq<B> seq2) {
        if (seq1.isEmpty() || seq2.isEmpty()) {
            return empty();
        }
        return LazySeq.from(() -> {
            Pair<A, Seq<A>> pair1 = seq1.next();
            Pair<B, Seq<B>> pair2 = seq2.next();
            return Pair.of(Pair.of(pair1.left, pair2.left), Seq.zip(pair1.right, pair2.right));
        });
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

    static Seq<Long> range(long end) {
        return Seq.range(0L, end);
    }

    static Seq<Long> range(long begin, long end) {
        return Seq.range(begin, end, 1);
    }

    static Seq<Long> range(long begin, long end, long step) {
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

    static <A, B> boolean equals(Seq<A> seq1, Seq<B> seq2) {
        while (!seq1.isEmpty() && !seq2.isEmpty()) {
            Pair<A, Seq<A>> pair1 = seq1.next();
            Pair<B, Seq<B>> pair2 = seq2.next();
            if (!pair1.left.equals(pair2.left)) {
                return false;
            }
            seq1 = pair1.right;
            seq2 = pair2.right;
        }
        return seq1.isEmpty() == seq2.isEmpty();
    }

    // Default methods

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
                    Pair<A, Seq<A>> pair = seq.next();
                    seq = pair.right;
                    return pair.left;
                }
            };
        };
    }

    default boolean isEmpty() {
        return false;
    }

    default A first() {
        return next().left;
    }

    default Seq<A> rest() {
        return next().right;
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
        for (Seq<A> seq = this; !seq.isEmpty(); seq = seq.rest()) {
            if (i == n) {
                return seq.first();
            }
            i++;
        }
        throw new IndexOutOfBoundsException("index: " + n + " bounds " + i);
    }

    default Seq<A> prepend(A a) {
        return Seq.concat(Seq.of(a), this);
    }

    default Seq<A> append(A a) {
        return Seq.concat(this, Seq.of(a));
    }

    default void forEach(Effect<? super A> consumer) {
        forEach((a, _i) -> consumer.apply(a));
    }

    default void forEach(Effect2<? super A, Integer> consumer) {
        Pair<A, Seq<A>> p = next();
        Seq<A> seq = this;
        int i = 0;
        while (!seq.isEmpty()) {
            Pair<A, Seq<A>> pair = seq.next();
            consumer.apply(pair.left, i);
            seq = pair.right;
            i++;
        }
    }

    default <B> Seq<B> map(Func<? super A, B> transformer) {
        return map((a, _i) -> transformer.apply(a));
    }

    default <B> Seq<B> map(Func2<? super A, Integer, B> transformer) {
        return map(0, transformer);
    }

    default <B> Seq<B> map(int start, Func2<? super A, Integer, B> transformer) {
        if (isEmpty()) {
            return empty();
        }
        return LazySeq.from(() -> {
            Pair<A, Seq<A>> pair = next();
            return Pair.of(transformer.apply(pair.left, start), pair.right.map(start + 1, transformer));
        });
    }

    default <B> Seq<B> flatMap(Func<? super A, Seq<B>> transformer) {
        return flatMap((a, _i) -> transformer.apply(a));
    }

    default <B> Seq<B> flatMap(Func2<? super A, Integer, Seq<B>> transformer) {
        return flatMap(0, transformer);
    }

    default <B> Seq<B> flatMap(int start, Func2<? super A, Integer, Seq<B>> transformer) {
        if (isEmpty()) {
            return empty();
        }
        return LazySeq.from(() -> {
            Pair<A, Seq<A>> pair = next();
            Seq<B> newSeq = transformer.apply(pair.left, start);
            Pair<B, Seq<B>> newPair = newSeq.next();
            return Pair.of(newPair.left, Seq.concat(newPair.right, pair.right.flatMap(start + 1, transformer)));
        });
    }

    default Seq<A> filter(Func<A, Boolean> predicate) {
        return filter((a, _i) -> predicate.apply(a));
    }

    default Seq<A> filter(Func2<A, Integer, Boolean> predicate) {
        return filter(0, predicate);
    }

    default Seq<A> filter(int start, Func2<A, Integer, Boolean> predicate) {
        Seq<A> seq = this;
        while (!seq.isEmpty()) {
            Pair<A, Seq<A>> pair = seq.next();
            if (predicate.apply(pair.left, start)) {
                return () -> Pair.of(pair.left, pair.right.filter(start + 1, predicate));
            }
            seq = pair.right;
        }
        return empty();
    }

    default A reduce(Func2<A, ? super A, A> reducer) {
        Pair<A, Seq<A>> pair = next();
        return pair.right.reduce(pair.left, reducer);
    }

    default A reduce(Func3<A, ? super A, Integer, A> reducer) {
        Pair<A, Seq<A>> pair = next();
        return pair.right.reduce(pair.left, reducer);
    }

    default <B> B reduce(B accumulator, Func2<B, ? super A, B> reducer) {
        return reduce(accumulator, (acc, a, _i) -> reducer.apply(acc, a));
    }

    default <B> B reduce(B accumulator, Func3<B, ? super A, Integer, B> reducer) {
        Seq<A> seq = this;
        int i = 0;
        while (!seq.isEmpty()) {
            Pair<A, Seq<A>> pair = seq.next();
            accumulator = reducer.apply(accumulator, pair.left, i);
            seq = pair.right;
            i++;
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
        Pair<A, Seq<A>> pair = next();
        return pair.right.reverse().append(pair.left);
    }

    default Seq<A> limit(int n) {
        if (isEmpty() || n <= 0) {
            return empty();
        }
        return LazySeq.from(() -> {
            Pair<A, Seq<A>> pair = next();
            return Pair.of(pair.left, pair.right.limit(n - 1));
        });
    }

    // TODO
    default <B extends Comparable<B>> Seq<A> sortBy(Func<A, B> selector) {
        return null;
    }

}
