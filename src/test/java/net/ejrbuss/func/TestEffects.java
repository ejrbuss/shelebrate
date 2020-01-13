package net.ejrbuss.func;


import net.ejrbuss.data.Pair;
import net.ejrbuss.data.Ref;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestEffects {

    @Test
    public void testOf() {
        Ref<Integer> ref = Ref.of(0);
        Effect<Integer> eff1 = i -> ref.set(i);
        eff1.apply(2);
        assertEquals(Integer.valueOf(2), ref.get());
        Func<String, Integer> func = s -> s.length();
        Effect<String> eff2 = eff1.of(func);
        eff2.apply("test");
        assertEquals(Integer.valueOf(4), ref.get());
    }

    @Test
    public void testFrom() {
        Ref<String> ref = Ref.of("a");
        Effect<Pair<String, String>> eff1 = pair -> ref.set(pair.left() + pair.right());
        eff1.apply(Pair.of("a", "b"));
        assertEquals("ab", ref.get());
        Effect2<String, String> eff2 = Effect2.from(eff1);
        eff2.apply("c", "d");
        assertEquals("cd", ref.get());
    }

    @Test
    public void testApply() {
        Ref<String> ref = Ref.of("a");
        Effect2<String, String> eff1 = (a, b) -> ref.set(a + b);
        Effect<String> eff2 = eff1.apply("b");
        assertEquals("a", ref.get());
        eff2.apply("c");
        assertEquals("bc", ref.get());
    }

    @Test
    public void testSwap() {
        Ref<String> ref = Ref.of("a");
        ((Effect2<String, String>) (a, b) -> ref.set(a + b)).swap().apply("b", "c");
        assertEquals("cb", ref.get());
    }

    @Test
    public void testToEffectOfPairs() {
        Ref<String> ref = Ref.of("a");
        Effect2<String, String> eff1 = (a, b) -> ref.set(a + b);
        eff1.apply("a", "b");
        assertEquals("ab", ref.get());
        Effect<Pair<String, String>> eff2 = eff1.toEffectOfPairs();
        eff2.apply(Pair.of("c", "d"));
        assertEquals("cd", ref.get());
    }

}
