package net.ejrbuss.func;

import net.ejrbuss.data.Ref;
import net.ejrbuss.data.StrictPair;
import net.ejrbuss.data.Seq;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestFuncs {

    Thunk<String> thunk = Thunk.of("test");
    Func<String, Integer> func = s -> s.length();
    Func<Integer, Seq<Integer>> funcAlt = i -> Seq.range(i);
    Func2<String, Integer, String> func2 = (s, i) -> s + i;
    Func3<String, String, String, String> func3 = (a, b, c) -> a + b + c;

    @Test
    public void testId() {
        Object unique = new Object();
        assertEquals(unique, Func.id(unique));
    }

    @Test
    public void testOf() {
        Seq.range(100).forEach(_i -> assertEquals("test", thunk.apply()));
        assertEquals(Seq.range(4), funcAlt.of(func).apply("test"));
    }

    @Test
    public void testFrom() {
        assertEquals(StrictPair.of("a", 1), Func2.from(Func::id).apply("a", 1));
    }

    @Test
    public void testChached() {
        Ref<Integer> ref = Ref.of(0);
        Thunk<Integer> thunk = () -> ref.swap(ref.get() + 1);
        assertEquals(Integer.valueOf(0), thunk.apply());
        assertEquals(Integer.valueOf(1), thunk.apply());
        assertEquals(Integer.valueOf(2), thunk.apply());
        thunk = thunk.cached();
        assertEquals(Integer.valueOf(3), thunk.apply());
        assertEquals(Integer.valueOf(3), thunk.apply());
        assertEquals(Integer.valueOf(3), thunk.apply());
    }

    @Test
    public void testApply() {
        assertEquals("a1", func2.apply("a").apply(1));
        assertEquals("abc", func3.apply("a").apply("b").apply("c"));
    }

    @Test
    public void testThen() {
        assertEquals(Integer.valueOf(4), thunk.then(func).apply());
        assertEquals(Seq.range(4), func.then(funcAlt).apply("test"));
    }

    @Test
    public void testSwap() {
        assertEquals("a1", func2.swap().apply(1, "a"));
    }

    @Test
    public void testToFuncOfPairs() {
        assertEquals("a1", func2.toFuncOfPairs().apply(StrictPair.of("a", 1)));
    }

}
