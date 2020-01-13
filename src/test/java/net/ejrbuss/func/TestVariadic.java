package net.ejrbuss.func;

import static org.junit.Assert.*;

import net.ejrbuss.data.Seq;
import org.junit.Test;

public class TestVariadic {

    @Test
    public void testApply() {
        Variadic<Integer, Integer> add = Variadic.from(0, NumOps::add);
        assertEquals(Integer.valueOf(0), add.apply());
        assertEquals(Integer.valueOf(42), add.apply(42));
        assertEquals(Integer.valueOf(7), add.apply(3, 4));
        assertEquals(Integer.valueOf(6), add.apply(new Integer[]{}, Seq.of(1, 2, 3)));
    }

}
