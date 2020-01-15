package net.ejrbuss.data;

import net.ejrbuss.function.fn.Fn;
import net.ejrbuss.function.fn.NumOps;
import net.ejrbuss.function.fn.Thunk;
import net.ejrbuss.function.fn.Fn2;

@FunctionalInterface
public interface InfSeq<A> {

    Pair<A, InfSeq<A>> next();

    // static methods

    static <A> InfSeq<A> repeat(A element) {
        return () -> Pair.of(element, InfSeq.repeat(element));
    }

    static <A> InfSeq<A> repeat(Thunk<A> generator) {
        return () -> Pair.of(generator.$(), repeat(generator));
    }

    static <A> InfSeq<A> repeat(A accumulator, Fn<? super A, ? extends A> generator) {
        return () -> Pair.of(accumulator, InfSeq.repeat(generator.$(accumulator), generator));
    }

    static <A, B> InfSeq<Pair<A, B>> zip(InfSeq<A> seq1, InfSeq<B> seq2) {
        return zipWith(Pair::of, seq1, seq2);
    }

    static <A, B, C> InfSeq<C> zipWith(Fn2<? super A, ? super B, C> combiner, InfSeq<A> seq1, InfSeq<B> seq2) {
        return () -> {
            Pair<A, InfSeq<A>> pair1 = seq1.next();
            Pair<B, InfSeq<B>> pair2 = seq2.next();
            return Pair.of(
                    combiner.$(pair1.left(), pair2.left()),
                    InfSeq.zipWith(combiner, pair1.right(), pair2.right())
            );
        };
    }

    static InfSeq<Integer> naturals() {
        return repeat(0, NumOps::inc);
    }

    // default methods

    default A first() {
        return next().left();
    }

    default InfSeq<A> rest() {
        return next().right();
    }

    default Seq<A> take(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0! Found: " + n);
        }
        return takeWhile((_element, i) -> i < n);
    }

    default Seq<A> takeWhile(Fn<? super A, Boolean> predicate) {
        return takeWhile((e, _i) -> predicate.$(e));
    }

    default Seq<A> takeWhile(Fn2<? super A, Integer, Boolean> predicate) {
        return takeWhile(0, (i, e) -> Pair.of(i + 1, predicate.$(e, i)));
    }

    default <B> Seq<A> takeWhile(B accumulator, Fn2<B, ? super A, Pair<B, Boolean>> predicate) {
        InfSeq<A> seq = this;
        Pair<A, InfSeq<A>> pair1 = seq.next();
        Pair<B, Boolean> pair2 = predicate.$(accumulator, pair1.left());
        if (!pair2.right()) {
            return Seq.empty();
        }
        return LazySeq.from(() ->
                Pair.of(pair1.left(), pair1.right().takeWhile(pair2.left(), predicate))
        );
    }

    default InfSeq<A> drop(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0! Found: " + n);
        }
        return dropWhile((_element, i) -> i < n);
    }

    default InfSeq<A> dropWhile(Fn<? super A, Boolean> predicate) {
        return dropWhile((e, _i) -> predicate.$(e));
    }

    default InfSeq<A> dropWhile(Fn2<? super A, Integer, Boolean> predicate) {
        return dropWhile(0, (i, e) -> Pair.of(i + 1, predicate.$(e, i)));
    }

    default <B> InfSeq<A> dropWhile(B accumulator, Fn2<B, ? super A, Pair<B, Boolean>> predicate) {
        InfSeq<A> seq = this;
        return () -> {
            Pair<A, InfSeq<A>> pair1 = next();
            Pair<B, Boolean> pair2 = predicate.$(accumulator, pair1.left());
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

    default InfSeq<A> prepend(A element) {
        InfSeq<A> seq = this;
        return () -> Pair.of(element, seq);
    }

    default InfSeq<A> prependAll(Seq<A> seq) {
        if (seq.isEmpty()) {
            return this;
        }
        InfSeq<A> seq2 = this;
        return () -> Pair.of(seq.first(), seq2.prependAll(seq.rest()));
    }

    default <B> InfSeq<B> map(Fn<? super A, B> transform) {
        return map((e, _i) -> transform.$(e));
    }

    default <B> InfSeq<B> map(Fn2<?super A, Integer, B> transform) {
        return map(0, (i, e) -> Pair.of(i + 1, transform.$(e, i)));
    }

    default <B, C> InfSeq<B> map(C accumulator, Fn2<C, ? super A, Pair<C, B>> transform) {
        return () -> {
            Pair<A, InfSeq<A>> pair1 = next();
            Pair<C, B> pair2 = transform.$(accumulator, pair1.left());
            return Pair.of(
                    pair2.right(),
                    pair1.right().map(pair2.left(), transform)
            );
        };
    }

    default <B> InfSeq<B> flatMap(Fn<? super A, Seq<B>> transform) {
        return flatMap((e, _i) -> transform.$(e));
    }

    default <B> InfSeq<B> flatMap(Fn2<? super A, Integer, Seq<B>> transform) {
        return flatMap(0, (i, e) -> Pair.of(i + 1, transform.$(e, i)));
    }

    default <B, C> InfSeq<B> flatMap(C accumulator, Fn2<C, ? super A, Pair<C, Seq<B>>> transform) {
        InfSeq<A> seq1 = this;
        return () -> {
            Pair<A, InfSeq<A>> pair1 = next();
            Pair<C, Seq<B>> pair2 = transform.$(accumulator, pair1.left());
            Seq<B> seq2 = pair2.right();
            return pair1.right().flatMap(pair2.left(), transform).prependAll(seq2).next();
        };
    }

    default InfSeq<A> filter(Fn<? super A, Boolean> predicate) {
        return filter((e, _i) -> predicate.$(e));
    }

    default InfSeq<A> filter(Fn2<? super A, Integer, Boolean> predicate) {
        return filter(0, (i, e) -> Pair.of(i + 1, predicate.$(e, i)));
    }

    default <B> InfSeq<A> filter(B accumulator, Fn2<B, ? super A, Pair<B, Boolean>> predicate) {
        return () -> {
            Pair<A, InfSeq<A>> pair1 = next();
            Pair<B, Boolean> pair2 = predicate.$(accumulator, pair1.left());
            InfSeq<A> seq = pair1.right().filter(pair2.left(), predicate);
            if (pair2.right()) {
                return Pair.of(pair1.left(), seq);
            } else {
                return seq.next();
            }
        };
    }

    default InfSeq<Seq<A>> chunk(int n) {
        InfSeq<A> seq = this;
        return () -> {
            InfSeq<A> seq1 = seq;
            Seq<A> seq2 = Seq.empty();
            for (int i = 0; i < n; i++) {
                Pair<A, InfSeq<A>> pair = seq1.next();
                seq2 = seq2.append(pair.left());
                seq1 = pair.right();
            }
            return Pair.of(seq2, seq1.chunk(n));
        };
    }

    default InfSeq<Pair<A, A>> pairUp() {
        return chunk(2).map(Pair::from);
    }

}
