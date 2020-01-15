package net.ejrbuss.function.fn;


import net.ejrbuss.data.Pair;
import net.ejrbuss.data.Ref;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestEffects {

    @Test
    public void testOf() {
        Ref<Integer> ref = Ref.of(0);
        Eff<Integer> eff1 = ref::set;
        eff1.apply(2);
        assertEquals(Integer.valueOf(2), ref.get());
        Fn<String, Integer> fn = String::length;
        Eff<String> eff2 = eff1.of(fn);
        eff2.$("test");
        assertEquals(Integer.valueOf(4), ref.get());
    }

    @Test
    public void testFrom() {
        Ref<String> ref = Ref.of("a");
        Eff<Pair<String, String>> eff1 = pair -> ref.set(pair.left() + pair.right());
        eff1.$(Pair.of("a", "b"));
        assertEquals("ab", ref.get());
        Eff2<String, String> eff2 = Eff2.from(eff1);
        eff2.$("c", "d");
        assertEquals("cd", ref.get());
    }

    @Test
    public void testApply() {
        Ref<String> ref = Ref.of("a");
        Eff2<String, String> eff1 = (a, b) -> ref.set(a + b);
        Eff<String> eff2 = eff1.$("b");
        assertEquals("a", ref.get());
        eff2.$("c");
        assertEquals("bc", ref.get());
    }

    @Test
    public void testSwap() {
        Ref<String> ref = Ref.of("a");
        ((Eff2<String, String>) (a, b) -> ref.set(a + b)).swap().$("b", "c");
        assertEquals("cb", ref.get());
    }

    @Test
    public void testToEffectOfPairs() {
        Ref<String> ref = Ref.of("a");
        Eff2<String, String> eff1 = (a, b) -> ref.set(a + b);
        eff1.$("a", "b");
        assertEquals("ab", ref.get());
        Eff<Pair<String, String>> eff2 = eff1.toEffectOfPairs();
        eff2.$(Pair.of("c", "d"));
        assertEquals("cd", ref.get());
    }

}
