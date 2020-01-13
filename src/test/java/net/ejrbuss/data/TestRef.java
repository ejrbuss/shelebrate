package net.ejrbuss.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestRef {

    @Test
    public void testFrom() {
        assertEquals(Ref.of(1), Ref.from(Maybe.some(1)));
        assertEquals(Ref.empty(), Ref.from(Maybe.none()));
        assertEquals(Ref.of(1), Ref.from(Seq.of(1, 2, 2)));
        assertEquals(Ref.empty(), Ref.from(Seq.empty()));
    }

    @Test
    public void testGetAndSet() {
        Ref<String> ref = Ref.of("a");
        assertEquals("a", ref.get());
        ref.set("b");
        assertEquals("b", ref.get());
    }

    @Test
    public void testSwap() {
        Ref<String> ref = Ref.of("a");
        assertEquals("a", ref.swap("b"));
        assertEquals("b", ref.get());
    }

    @Test
    public void testGetOrSet() {
        Ref<String> ref = Ref.empty();
        assertEquals("a", ref.getOrSet("a"));
        assertEquals("a", ref.getOrSet("b"));
        assertEquals("a", ref.get());
    }

    @Test
    public void testMap() {
        Ref<Integer> ref = Ref.of(0);
        ref.map(i -> i + 1);
        assertEquals(Integer.valueOf(1), ref.get());
    }

    @Test
    public void testFlatMap() {
        Object unique = new Object();
        Ref<Object> ref = Ref.of(new Object());
        assertNotEquals(unique, ref.get());
        ref.flatMap(_s -> Ref.of(unique));
        assertTrue(unique == ref.get());
    }

    @Test
    public void testEquals() {
        assertEquals(Ref.empty(), Ref.empty());
        assertEquals(Ref.of(Seq.range(10)), Ref.of(Seq.range(10)));
        assertNotEquals(Ref.of(1), Ref.of(2));
        assertNotEquals(Ref.of(1), Maybe.some(1));
        assertNotEquals(Ref.empty(), Ref.of(1));
        assertNotEquals(Ref.of(1), Ref.empty());
    }

    @Test
    public void testHashCode() {
        assertEquals(Ref.empty().hashCode(), Ref.empty().hashCode());
        assertEquals("a".hashCode(), Ref.of("a").hashCode());
    }

    @Test
    public void testToString() {
        assertTrue(Ref.of(1).toString().endsWith("(1)"));
    }

}
