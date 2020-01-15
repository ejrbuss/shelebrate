package net.ejrbuss.data;

import net.ejrbuss.function.fn.NumOps;
import net.ejrbuss.function.fn.Predicates;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestInfSeq {

    @Test
    public void testRepeat() {
        assertEquals(Seq.of(1, 1, 1), InfSeq.repeat(1).take(3));
        assertEquals(Seq.of(1, 1, 1), InfSeq.repeat(() -> 1).take(3));
        assertEquals(Seq.range(5), InfSeq.repeat(0, NumOps::inc).take(5));
    }

    @Test
    public void testZip() {
        assertEquals(
                Seq.zip(Seq.of("a", "a"), Seq.of("b", "b")),
                InfSeq.zip(InfSeq.repeat("a"), InfSeq.repeat("b")).take(2)
        );
        assertEquals(
                Seq.range(100),
                InfSeq.zip(InfSeq.naturals(), InfSeq.repeat("a")).map(Pair::left).take(100)
        );
        assertEquals(
                InfSeq.repeat(3).take(100),
                InfSeq.zipWith(NumOps::add, InfSeq.repeat(1), InfSeq.repeat(2)).take(100)
        );
    }

    @Test
    public void testFirst() {
        assertEquals(Integer.valueOf(0), InfSeq.naturals().first());
    }

    @Test
    public void testRest() {
        assertEquals(Seq.range(1, 100), InfSeq.naturals().rest().take(99));
    }

    @Test
    public void testTake() {
        assertEquals(Seq.range(10), InfSeq.naturals().take(10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTakeWithNegative() {
        InfSeq.naturals().take(-5);
    }

    @Test
    public void testTakeWhile() {
        assertEquals(Seq.range(10), InfSeq.naturals().takeWhile(e -> e < 10));
    }

    @Test
    public void testDrop() {
        assertEquals(Seq.range(5, 10), InfSeq.naturals().drop(5).take(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDropWithNegative() {
        InfSeq.naturals().drop(-12);
    }

    @Test
    public void testDropWhile() {
        assertEquals(Seq.range(5, 10), InfSeq.naturals().dropWhile(e -> e < 5).take(5));
    }

    @Test
    public void testNth() {
        assertEquals(Integer.valueOf(32), InfSeq.naturals().nth(32));
    }

    @Test
    public void testPrepend() {
        assertEquals(Seq.of("a", "b", "b", "b"), InfSeq.repeat("b").prepend("a").take(4));
    }

    @Test
    public void testPrependAll() {
        assertEquals(Seq.of(1, 3, 2, 2, 2), InfSeq.repeat(2).prependAll(Seq.of(1, 3)).take(5));
    }

    @Test
    public void testMap() {
        assertEquals(Seq.of(4, 4, 4), InfSeq.repeat("test").map(e -> e.length()).take(3));
    }

    @Test
    public void testFlatMap() {
        assertEquals(Seq.of(1, 2, 1, 2), InfSeq.repeat("a").flatMap(e -> Seq.of(1, 2)).take(4));
    }

    @Test
    public void testFilter() {
        assertEquals(Seq.of(0, 2, 4, 6, 8), InfSeq.naturals().filter(Predicates::even).take(5));
    }

    @Test
    public void testChunk() {
        assertEquals(
                Seq.of(Seq.of(0, 1, 2), Seq.of(3, 4, 5), Seq.of(6, 7, 8)),
                InfSeq.naturals().chunk(3).take(3)
        );
    }

    @Test
    public void testPairUp() {
        assertEquals(
                Seq.of(Pair.of(0, 1), Pair.of(2, 3)),
                InfSeq.naturals().pairUp().take(2)
        );
    }

}
