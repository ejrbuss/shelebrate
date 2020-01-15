package net.ejrbuss.function.fn;

import static org.junit.Assert.*;

import net.ejrbuss.data.Seq;
import org.junit.Test;

public class TestVarFn {

    @Test
    public void testApply() {
        VarFn<Integer, Integer> add = VarFn.from(0, NumOps::add);
        assertEquals(Integer.valueOf(0), add.$());
        assertEquals(Integer.valueOf(42), add.$(42));
        assertEquals(Integer.valueOf(7), add.$(3, 4));
        assertEquals(Integer.valueOf(6), add.$(new Integer[]{}, Seq.of(1, 2, 3)));
    }

}
