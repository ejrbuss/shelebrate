package net.ejrbuss.function.fn;

import net.ejrbuss.data.Pair;
import net.ejrbuss.data.Ref;
import net.ejrbuss.data.Seq;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestFns {

    Thunk<String> thunk = Thunk.of("test");
    Fn<String, Integer> fn = s -> s.length();
    Fn<Integer, Seq<Integer>> fnAlt = i -> Seq.range(i);
    Fn2<String, Integer, String> func2 = (s, i) -> s + i;
    Fn3<String, String, String, String> func3 = (a, b, c) -> a + b + c;

    @Test
    public void testId() {
        Object unique = new Object();
        assertEquals(unique, Fn.id(unique));
    }

    @Test
    public void testOf() {
        Seq.range(100).each(_i -> assertEquals("test", thunk.$()));
        assertEquals(Seq.range(4), fnAlt.of(fn).$("test"));
    }

    @Test
    public void testFrom() {
        assertEquals(Pair.of("a", 1), Fn2.from(Fn::id).$("a", 1));
    }

    @Test
    public void testChached() {
        Ref<Integer> ref = Ref.of(0);
        Thunk<Integer> thunk = () -> ref.swap(ref.get() + 1);
        assertEquals(Integer.valueOf(0), thunk.$());
        assertEquals(Integer.valueOf(1), thunk.$());
        assertEquals(Integer.valueOf(2), thunk.$());
        thunk = thunk.cached();
        assertEquals(Integer.valueOf(3), thunk.$());
        assertEquals(Integer.valueOf(3), thunk.$());
        assertEquals(Integer.valueOf(3), thunk.$());
    }

    @Test
    public void testApply() {
        assertEquals("a1", func2.$("a").$(1));
        assertEquals("abc", func3.$("a").$("b").$("c"));
    }

    @Test
    public void testThen() {
        assertEquals(Integer.valueOf(4), thunk.then(fn).$());
        assertEquals(Seq.range(4), fn.then(fnAlt).$("test"));
    }

    @Test
    public void testSwap() {
        assertEquals("a1", func2.swap().$(1, "a"));
    }

    @Test
    public void testToFuncOfPairs() {
        assertEquals("a1", func2.toFuncOfPairs().$(Pair.of("a", 1)));
    }

}
