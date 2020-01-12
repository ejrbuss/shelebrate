package net.ejrbuss.data;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPair {

    @Test
    public void testLeft() {
        assertEquals(Integer.valueOf(1), Pair.left(Pair.of(1, 2)));
    }
    @Test
    public void testRight() {
        assertEquals(Integer.valueOf(2), Pair.right(Pair.of(1, 2)));
    }

    @Test
    public void testFrom() {
        assertEquals(Pair.of(0, 1), Pair.from(Seq.range(10)));
    }

    @Test
    public void testApply() {
        Pair.of("a", "b").apply((a, b) -> {
            assertEquals("a", a);
            assertEquals("b", b);
        });
        assertEquals(Integer.valueOf(3), Pair.of(1, 2).apply((a, b) -> a + b));
    }

    @Test
    public void testSwap() {
        assertEquals(Pair.of(2, 1), Pair.of(1, 2).swap());
    }

    @Test
    public void testEquals() {
        assertEquals(Pair.of("one", "two"), Pair.of("one", "two"));
        assertNotEquals(Pair.of(1, 2), Pair.of(2, 1));
        assertNotEquals(Pair.of(1, 2), Seq.of(1, 2));
    }

    @Test
    public void testHashCode() {
        assertEquals(Pair.of(1, 2).hashCode(), Pair.of(1, 2).hashCode());
        assertNotEquals(Pair.of(1, 2).hashCode(), Pair.of(1, 3).hashCode());
    }

    @Test
    public void testToString() {
        assertTrue(Pair.of(1, 2).toString().endsWith("(1, 2)"));
    }

}
