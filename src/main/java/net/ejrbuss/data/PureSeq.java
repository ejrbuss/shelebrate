package net.ejrbuss.data;

import net.ejrbuss.func.Func;
import net.ejrbuss.func.Thunk;
import net.ejrbuss.func.Func2;

@FunctionalInterface
public interface PureSeq<A> {

    Pair<A, PureSeq<A>> next();

    // static methods

    static <A> PureSeq<A> repeat(A element) {
        return () -> Pair.of(element, PureSeq.repeat(element));
    }

    static <A> PureSeq<A> repeat(Thunk<A> generator) {
        return () -> Pair.of(generator.apply(), repeat(generator));
    }

    static <A> PureSeq<A> repeat(A accumulator, Func<? super A, ? extends A> generator) {
        return () -> Pair.of(accumulator, PureSeq.repeat(generator.apply(accumulator), generator));
    }

    static <A, B> PureSeq<Pair<A, B>> zip(PureSeq<A> seq1, PureSeq<B> seq2) {
        return zipWith(Pair::of, seq1, seq2);
    }

    static <A, B, C> PureSeq<C> zipWith(Func2<? super A, ? super B, C> combiner, PureSeq<A> seq1, PureSeq<B> seq2) {
        return () -> {
            Pair<A, PureSeq<A>> pair1 = seq1.next();
            Pair<B, PureSeq<B>> pair2 = seq2.next();
            return Pair.of(
                    combiner.apply(pair1.left(), pair2.left()),
                    PureSeq.zipWith(combiner, pair1.right(), pair2.right())
            );
        };
    }

    // default methods

    default A first() {
        return next().left();
    }

    default PureSeq<A> rest() {
        return next().right();
    }

    default Seq<A> take(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0! Found: " + n);
        }
        return takeWhile((_element, i) -> i < n);
    }

    default Seq<A> takeWhile(Func<? super A, Boolean> predicate) {
        return takeWhile((e, _i) -> predicate.apply(e));
    }

    default Seq<A> takeWhile(Func2<? super A, Integer, Boolean> predicate) {
        return takeWhile(0, (i, e) -> Pair.of(i + 1, predicate.apply(e, i)));
    }

    default <B> Seq<A> takeWhile(B accumulator, Func2<B, ? super A, Pair<B, Boolean>> predicate) {
        PureSeq<A> seq = this;
        Pair<A, PureSeq<A>> pair1 = seq.next();
        Pair<B, Boolean> pair2 = predicate.apply(accumulator, pair1.left());
        if (!pair2.right()) {
            return Seq.empty();
        }
        return LazySeq.from(() ->
                Pair.of(pair1.left(), pair1.right().takeWhile(pair2.left(), predicate))
        );
    }

    default PureSeq<A> drop(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0! Found: " + n);
        }
        return dropWhile((_element, i) -> i < n);
    }

    default PureSeq<A> dropWhile(Func<? super A, Boolean> predicate) {
        return dropWhile((e, _i) -> predicate.apply(e));
    }

    default PureSeq<A> dropWhile(Func2<? super A, Integer, Boolean> predicate) {
        return dropWhile(0, (i, e) -> Pair.of(i + 1, predicate.apply(e, i)));
    }

    default <B> PureSeq<A> dropWhile(B accumulator, Func2<B, ? super A, Pair<B, Boolean>> predicate) {
        PureSeq<A> seq = this;
        return () -> {
            Pair<A, PureSeq<A>> pair1 = next();
            Pair<B, Boolean> pair2 = predicate.apply(accumulator, pair1.left());
            if (pair2.right()) {
                return pair1.right().dropWhile(pair2.left(), predicate).next();
            } else {
                return seq.next();
            }
        };
    }

    default A nth(int n) {
        return drop(n).first();
    }

    default PureSeq<A> prepend(A element) {
        PureSeq<A> seq = this;
        return () -> Pair.of(element, seq);
    }

    default PureSeq<A> prependAll(Seq<A> seq) {
        if (seq.isEmpty()) {
            return this;
        }
        PureSeq<A> seq2 = this;
        return () -> Pair.of(seq.first(), seq2.prependAll(seq.rest()));
    }

    default <B> PureSeq<B> map(Func<? super A, B> transform) {
        return map((e, _i) -> transform.apply(e));
    }

    default <B> PureSeq<B> map(Func2<?super A, Integer, B> transform) {
        return map(0, (i, e) -> Pair.of(i + 1, transform.apply(e, i)));
    }

    default <B, C> PureSeq<B> map(C accumulator, Func2<C, ? super A, Pair<C, B>> transform) {
        return () -> {
            Pair<A, PureSeq<A>> pair1 = next();
            Pair<C, B> pair2 = transform.apply(accumulator, pair1.left());
            return Pair.of(
                    pair2.right(),
                    pair1.right().map(pair2.left(), transform)
            );
        };
    }

    default <B> PureSeq<B> flatMap(Func<? super A, Seq<B>> transform) {
        return flatMap((e, _i) -> transform.apply(e));
    }

    default <B> PureSeq<B> flatMap(Func2<? super A, Integer, Seq<B>> transform) {
        return flatMap(0, (i, e) -> Pair.of(i + 1, transform.apply(e, i)));
    }

    default <B, C> PureSeq<B> flatMap(C accumulator, Func2<C, ? super A, Pair<C, Seq<B>>> transform) {
        PureSeq<A> seq1 = this;
        return () -> {
            Pair<A, PureSeq<A>> pair1 = next();
            Pair<C, Seq<B>> pair2 = transform.apply(accumulator, pair1.left());
            Seq<B> seq2 = pair2.right();
            return pair1.right().flatMap(pair2.left(), transform).prependAll(seq2).next();
        };
    }

    default PureSeq<A> filter(Func<? super A, Boolean> predicate) {
        return filter((e, _i) -> predicate.apply(e));
    }

    default PureSeq<A> filter(Func2<? super A, Integer, Boolean> predicate) {
        return filter(0, (i, e) -> Pair.of(i + 1, predicate.apply(e, i)));
    }

    default <B> PureSeq<A> filter(B accumulator, Func2<B, ? super A, Pair<B, Boolean>> predicate) {
        return () -> {
            Pair<A, PureSeq<A>> pair1 = next();
            Pair<B, Boolean> pair2 = predicate.apply(accumulator, pair1.left());
            PureSeq<A> seq = pair1.right().filter(pair2.left(), predicate);
            if (pair2.right()) {
                return Pair.of(pair1.left(), seq);
            } else {
                return seq.next();
            }
        };
    }

    default PureSeq<Seq<A>> chunk(int n) {
        PureSeq<A> seq = this;
        return () -> {
            PureSeq<A> seq1 = seq;
            Seq<A> seq2 = Seq.empty();
            for (int i = 0; i < n; i++) {
                Pair<A, PureSeq<A>> pair = seq1.next();
                seq2 = seq2.append(pair.left());
                seq1 = pair.right();
            }
            return Pair.of(seq2, seq1.chunk(n));
        };
    }

    default PureSeq<Pair<A, A>> pairUp() {
        return chunk(2).map(Pair::from);
    }

}
