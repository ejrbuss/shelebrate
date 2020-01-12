package net.ejrbuss.data;

import static org.junit.Assert.*;

import net.ejrbuss.func.Func0;
import net.ejrbuss.func.Predicates;
import org.junit.Test;

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
    public void testContinually() {
        Seq<Integer> naturals = Seq.continually(0, n -> n + 1);
        naturals.limit(1000).forEach((n, i) -> assertEquals(n, i));

        Seq<Boolean> swaps = Seq.continually(true, b -> !b);
        swaps.limit(1000).forEach((b, i) -> assertEquals(i % 2 == 0, b));

        Seq<String> greetings = Seq.continually(() -> "Hi!");
        greetings.limit(1000).forEach(greeting -> assertEquals("Hi!", greeting));
    }

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
        assertEquals(
                Seq.range(100),
                Seq.zip(Seq.range(100), Seq.continually(Func0.of("a"))).map(Pair::left)
        );
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
        assertEquals(Seq.of(3, 2, 1), Seq.range(3, 0, -1));
        assertEquals(Seq.of(0.0, 0.5, 1.0, 1.5), Seq.range(0.0, 2.0, 0.5));
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

    @Test
    public void testRest() {
        assertEquals(Seq.of(2, 3), Seq.of(1, 2, 3).rest());
    }

    @Test
    public void testLast() {
        assertEquals(Integer.valueOf(3), Seq.of(1, 2, 3).last());
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
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testNthOutOfBounds() {
        Seq.of(1, 2, 3).nth(5);
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
        assertEquals(Integer.valueOf(6), Seq.of(1, 2, 3).reduce(NumericOps::add));
        assertEquals("abcd", Seq.of("b", "c", "d").reduce("a", (a, b) -> a + b));
    }

    @Test
    public void testJoin() {
        assertEquals("123", Seq.of(1, 2, 3).join());
        assertEquals("1, 2, 3", Seq.of(1, 2, 3).join(", "));
    }

    @Test
    public void testReverse() {

    }

}
