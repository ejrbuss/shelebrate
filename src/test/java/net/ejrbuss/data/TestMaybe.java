package net.ejrbuss.data;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class TestMaybe {

    @Test
    public void testSome() {
        assertTrue(Maybe.some(1).isSome());
        assertFalse(Maybe.some(1).isNone());
    }

    @Test
    public void testNone() {
        assertFalse(Maybe.none().isSome());
        assertTrue(Maybe.none().isNone());
    }

    @Test
    public void testFrom() {
        assertEquals(Maybe.some(1), Maybe.from(Seq.of(1)));
        assertEquals(Maybe.none(), Maybe.from(Seq.empty()));
    }

    @Test
    public void testMap() {
        assertEquals(Maybe.some(2), Maybe.some(1).map(i -> i + 1));
        assertEquals(Maybe.none(), Maybe.none().map(i -> i + ""));
    }

    @Test
    public void testFlatMap() {
        assertEquals(Maybe.some(2), Maybe.some(1).flatMap(i -> Maybe.some(i + 1)));
        assertEquals(Maybe.none(), Maybe.none().flatMap(i -> Maybe.some(i)));
    }

    @Test
    public void testGet() {
        assertEquals("a", Maybe.some("a").get());
        assertEquals("a", Maybe.none().get("a"));
        assertEquals("a", Maybe.some("a").get("b"));
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetOnNone() {
        Maybe.none().get();
    }

    @Test
    public void testHashCode() {
        assertEquals(Maybe.some(Pair.of(1, 2)).hashCode(), Maybe.some(Pair.of(1, 2)).hashCode());
        assertEquals(Maybe.none().hashCode(), Maybe.none().hashCode());
        assertNotEquals(Maybe.some(1).hashCode(), Maybe.some(2).hashCode());
    }

    @Test
    public void testEquals() {
        assertEquals(Maybe.some(Seq.of(1, 2, 3)), Maybe.some(Seq.of(1, 2, 3)));
        assertNotEquals(Maybe.some(1), Seq.of(1));
    }

    @Test
    public void testToString() {
        assertTrue(Maybe.some(2).toString().endsWith("(2)"));
    }

}
