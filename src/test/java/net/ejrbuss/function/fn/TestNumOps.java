package net.ejrbuss.function.fn;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestNumOps {

    @Test
    public void testStandardOperations() {
        double epsilon = 0.001;
        assertEquals(1, NumOps.inc(0));
        assertEquals(1L, NumOps.inc(0L));
        assertEquals(1.0, NumOps.inc(0.0), epsilon);

        assertEquals(-1, NumOps.dec(0));
        assertEquals(-1L, NumOps.dec(0L));
        assertEquals(-1.0, NumOps.dec(0.0), epsilon);

        assertEquals(3, NumOps.add(1, 2));
        assertEquals(3L, NumOps.add(1L, 2L));
        assertEquals(0.3, NumOps.add(0.1, 0.2), epsilon);

        assertEquals(-1, NumOps.sub(1, 2));
        assertEquals(-1L, NumOps.sub(1L, 2L));
        assertEquals(-0.1, NumOps.sub(0.1, 0.2), epsilon);

        assertEquals(2, NumOps.mul(1, 2));
        assertEquals(2L, NumOps.mul(1L, 2L));
        assertEquals(0.02, NumOps.mul(0.1, 0.2), epsilon);

        assertEquals(0, NumOps.div(1, 2));
        assertEquals(0L, NumOps.div(1L, 2L));
        assertEquals(0.5, NumOps.div(0.1, 0.2), epsilon);

        assertEquals(1, NumOps.mod(1, 2));
        assertEquals(1L, NumOps.mod(1L, 2L));

        assertEquals(0.63095, NumOps.exp(0.1, 0.2), epsilon);
    }

}
