package net.ejrbuss.data;

import static org.junit.Assert.*;

import net.ejrbuss.func.*;
import org.junit.Test;

import java.util.Arrays;

public class TestSeq {

    @Test
    public void testEmpty() {
        assertTrue(Seq.empty().isEmpty());
    }

    @Test(expected = EmptyException.class)
    public void testEmptyThrows() {
        Seq.empty().next();
    }

    @Test
    public void testOf() {
        assertEquals(Integer.valueOf(1), Seq.of(1).first());
        assertTrue(Seq.of(1).rest().isEmpty());
        Seq<Integer> seq = Seq.of(1, 2, 3);
        assertEquals(Integer.valueOf(1), seq.first());
        seq = seq.rest();
        assertEquals(Integer.valueOf(2), seq.first());
        seq = seq.rest();
        assertEquals(Integer.valueOf(3), seq.first());
    }

    @Test
    public void testFrom() {
        assertEquals(Seq.of(1, 2), Seq.from(Pair.of(1, 2)));
        assertEquals(Seq.of(1), Seq.from(Maybe.some(1)));
        assertEquals(Seq.empty(), Seq.from(Maybe.none()));
        assertEquals(Seq.of(1, 2, 3), StrictSeq.from(Seq.of(1, 2, 3)));
        assertEquals(Seq.of(1, 2, 3), Seq.from(Arrays.asList(1, 2, 3)));
        assertEquals(Seq.of(1), Seq.from(() -> Pair.of(1, Seq.empty())));
        Seq<Integer> seq1 = StrictSeq.from(Seq.of(1, 2, 3));
        assertTrue(seq1 == StrictSeq.from(seq1));
        assertTrue(seq1 == seq1.strict());
        Seq<Integer> seq2 = LazySeq.from(Seq.of(1, 2, 3));
        assertTrue(seq2 == LazySeq.from(seq2));
    }
/*
    @Test
    public void testRepeat() {
        Seq<Integer> naturals = Seq.repeat(0, n -> n + 1);
        naturals.take(1000).forEach((n, i) -> assertEquals(n, i));

        Seq<Boolean> swaps = Seq.repeat(true, b -> !b);
        swaps.take(1000).forEach((b, i) -> assertEquals(i % 2 == 0, b));

        Seq.repeat(() -> "Hi!").take(1000).forEach(greeting -> assertEquals("Hi!", greeting));
        Seq.repeat("Hi!").take(1000).forEach(greeting -> assertEquals("Hi!", greeting));
    }
 */

    @Test
    public void testConcat() {
        assertEquals(Seq.of(1, 2, 3), Seq.concat(Seq.empty(), Seq.of(1, 2, 3)));
        assertEquals(Seq.of(1, 2, 3), Seq.concat(Seq.of(1, 2, 3), Seq.empty()));
        assertEquals(Seq.of(1, 2, 3, 4, 5, 6), Seq.concat(Seq.of(1, 2, 3), Seq.of(4, 5, 6)));
        assertEquals(Seq.of(1, 2, 3), Seq.concat(Seq.of(1), Seq.of(2), Seq.of(3), Seq.empty()));
    }

    @Test
    public void testZip() {
        assertEquals(
                Seq.of(Pair.of(1, "a"), Pair.of(2, "b"), Pair.of(3, "c")),
                Seq.zip(Seq.of(1, 2, 3), Seq.of("a", "b", "c"))
        );
        assertEquals(
                Seq.of(Pair.of(1, 1), Pair.of(2, 2)),
                Seq.zip(Seq.of(1, 2, 3), Seq.of(1, 2))
        );
        assertEquals(
                Seq.of(Pair.of(1, 1), Pair.of(2, 2)),
                Seq.zip(Seq.of(1, 2), Seq.of(1, 2, 3))
        );
        /*
        assertEquals(
                Seq.range(100),
                Seq.zip(Seq.range(100), Seq.repeat(Thunk.of("a"))).map(Pair::left)
        );
        */
    }

    @Test
    public void testEquals() {
        assertNotEquals(Seq.of(1, 2, 3, 4), Seq.of(1, 2, 3));
        assertNotEquals(Seq.of(1, 2, 3, 4), Seq.of(1, 2, 3, 5));
        assertNotEquals(Seq.of(1, 2), Pair.of(1, 2));
    }

    @Test
    public void testRange() {
        assertEquals(Seq.of(0, 1, 2, 3, 4), Seq.range(5));
        assertEquals(Seq.of(1, 2, 3), Seq.range(1, 4));
        assertEquals(Seq.of(3, 2, 1), Seq.range(3, 0));

        assertEquals(Seq.of(0.0, 0.5, 1.0, 1.5), Seq.range(0.0, 2.0, 0.5));
        assertEquals(Seq.of(2.0, 1.5, 1.0, 0.5), Seq.range(2.0, 0.0, -0.5));
    }

    @Test
    public void testIterator() {
        int j = 0;
        for (int i : Seq.range(100).iter()) {
            assertEquals(j++, i);
        }
        assertEquals(j, 100);
    }

    @Test
    public void testIsEmpty() {
        assertTrue(Seq.empty().isEmpty());
        assertTrue(Seq.of().isEmpty());
        assertTrue(Seq.of(1).rest().isEmpty());
        assertFalse(Seq.of(1, 2, 3).isEmpty());
        assertFalse(Seq.of(1, 2, 3).rest().isEmpty());
    }

    @Test
    public void testFirst() {
        assertEquals(Integer.valueOf(1), Seq.of(1, 2, 3).first());
    }

    @Test(expected = EmptyException.class)
    public void testFirstOnEmpty() {
        Seq.empty().first();
    }

    @Test(expected = EmptyException.class)
    public void testFirstOnEmptyStrictSeq() {
        StrictSeq.of().first();
    }

    @Test
    public void testRest() {
        assertEquals(Seq.of(2, 3), Seq.of(1, 2, 3).rest());
    }

    @Test(expected = EmptyException.class)
    public void testRestOnEmpty() {
        Seq.empty().rest();
    }

    @Test(expected = EmptyException.class)
    public void testRestOnEmptyStrictSeq() {
        StrictSeq.of().rest();
    }

    @Test
    public void testLast() {
        assertEquals(Integer.valueOf(3), Seq.of(1, 2, 3).last());
        assertEquals(Integer.valueOf(3), LazySeq.from(Seq.of(1, 2, 3)).last());
    }

    @Test
    public void testCount() {
        assertEquals(0, Seq.empty().count());
        assertEquals(1, Seq.of(1).count());
        assertEquals(4, Seq.of("a", "b", "c", "d").count());
        assertEquals(10, Seq.range(10).count());
    }

    @Test
    public void testNth() {
        assertEquals(Integer.valueOf(1), Seq.of(1, 2, 3).nth(0));
        assertEquals(Integer.valueOf(3), Seq.of(1, 2, 3).nth(2));
        assertEquals(Integer.valueOf(3), Seq.of(1, 2, 3).nth(-1));

        assertEquals(Integer.valueOf(1), LazySeq.from(Seq.of(1, 2, 3)).nth(0));
        assertEquals(Integer.valueOf(3), LazySeq.from(Seq.of(1, 2, 3)).nth(2));
        assertEquals(Integer.valueOf(3), LazySeq.from(Seq.of(1, 2, 3)).nth(-1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testNthOutOfBounds() {
        Seq.of(1, 2, 3).nth(5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testNthOutOfBoundsOnLazySeq() {
        LazySeq.from(Seq.of(1, 2, 3)).nth(5);
    }

    @Test
    public void testPrepend() {
        assertEquals(Seq.of(1, 2, 3), Seq.of(2, 3).prepend(1));
    }

    @Test
    public void testAppend() {
        assertEquals(Seq.of(1, 2, 3), Seq.of(1, 2).append(3));
    }

    @Test
    public void testTake() {
        assertEquals(Seq.empty(), Seq.of(1).take(10).rest());
        assertEquals(Seq.range(10), Seq.range(100).take(10));
        assertEquals(Seq.range(3), Seq.range(3).take(100));
    }

    @Test
    public void testTakeWhile() {
        assertEquals(Seq.range(50), Seq.range(100).takeWhile(Predicates.lt(50)));
        assertEquals(Seq.range(50), Seq.range(50).takeWhile(Predicates::always));
    }

    @Test
    public void testDrop() {
        assertEquals(Seq.range(5, 10), Seq.range(10).drop(5));
    }

    @Test
    public void testDropWhile() {
        assertEquals(Seq.range(50, 100), Seq.range(100).dropWhile(Predicates.lt(50)));
        assertEquals(Seq.empty(), Seq.range(50).dropWhile(Predicates::always));
    }

    @Test
    public void testForEach() {
        Seq.range(10).forEach((i, j) -> assertEquals(i, j));
    }

    @Test
    public void testMap() {
        assertEquals(Seq.of(1, 2, 3), Seq.range(3).map(i -> i + 1));
        assertEquals(Seq.range(3), Seq.of("a", "b", "c").map((_a, i) -> i));
    }

    @Test
    public void testFlatMap() {
        assertEquals(
                Seq.of(1, 1, 1, 2, 2, 2, 3, 3, 3),
                Seq.of(1, 2, 3).flatMap(i -> Seq.of(i, i, i))
        );
    }

    @Test
    public void testFilter() {
        assertEquals(
                Seq.of(0, 2, 4, 6, 8),
                Seq.range(10).filter(Predicates::even)
        );
        assertEquals(
                Seq.of(1, 2, 3),
                Seq.of(1, 2, 3).filter(Predicates::always)
        );
        assertTrue(Seq.of(1, 2, 3).filter(Predicates::never).isEmpty());
    }

    @Test
    public void testReduce() {
        assertEquals(Integer.valueOf(6), Seq.of(1, 2, 3).reduce(NumOps::add));
        assertEquals("abcd", Seq.of("b", "c", "d").reduce("a", (a, b) -> a + b));

        assertEquals(Integer.valueOf(3), Seq.of("a", "b", "c").reduce(0, (acc, _a, i) -> acc + i));
        assertEquals(Integer.valueOf(10), Seq.range(5).reduce((acc, _a, i) -> acc + i));
    }

    @Test
    public void testJoin() {
        assertEquals("123", Seq.of(1, 2, 3).join());
        assertEquals("1, 2, 3", Seq.of(1, 2, 3).join(", "));
    }

    @Test
    public void testReverse() {
        assertEquals(Seq.range(10), Seq.range(9, -1).reverse());
    }

    @Test
    public void testAll() {
        assertTrue(Seq.empty().all(Predicates::never));
        assertTrue(Seq.range(100).all(Predicates::always));
        assertFalse(Seq.range(100).all(a -> a < 50));
    }

    @Test
    public void testAny() {
        assertFalse(Seq.empty().any(Predicates::always));
        assertFalse(Seq.range(100).any(Predicates::never));
        assertTrue(Seq.range(100).any(a -> a < 50));
    }

    @Test
    public void testIndex() {
        assertEquals(Maybe.some(1), Seq.range(10).index(1));
        assertEquals(Maybe.none(), Seq.of("a", "b", "c").index("d"));
    }

    @Test
    public void testHas() {
        assertTrue(Seq.range(10).has(5));
        assertFalse(Seq.range(10).has(100));
    }

    @Test
    public void testPairUp() {
        assertEquals(
                Seq.of(Pair.of(0, 1), Pair.of(2, 3)),
                Seq.range(5).pairUp()
        );
    }

    @Test
    public void testChunk() {
        assertEquals(
                Seq.of(Seq.of(0, 1, 2), Seq.of(3, 4)),
                Seq.range(5).chunk(3).strict()
        );
    }

    @Test
    public void testSort() {
        assertEquals(Seq.of(1, 2, 3), Seq.of(3, 2, 1).sortBy(Func::id));
        assertEquals(Seq.of("a", "b", "c"), LazySeq.from(Seq.of("b", "a", "c")).sortBy(Func::id));
    }

    @Test
    public void testHashCode() {
        assertEquals(Seq.of(1, 2, 3).hashCode(), Seq.of(1, 2, 3).hashCode());
        assertEquals(Seq.of(1, 2, 3).hashCode(), Seq.of(1).append(2).append(3).hashCode());
    }

    @Test
    public void testToString() {
        assertTrue(Seq.of(1, 2, 3).toString().endsWith("(1, 2, 3)"));
    }

}
