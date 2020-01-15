package net.ejrbuss.function.fn;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestPredicates {

    public void assertAlways(Fn<Boolean, Boolean> always) {
        assertTrue(always.$(true));
        assertTrue(always.$(false));
    }

    public void assertNever(Fn<Boolean, Boolean> never) {
        assertFalse(never.$(true));
        assertFalse(never.$(false));
    }

    @Test
    public void testEven() {
        assertTrue(Predicates.even(0));
        assertFalse(Predicates.even(1));
    }

    @Test
    public void testOdd() {
        assertFalse(Predicates.odd(0));
        assertTrue(Predicates.odd(1));
    }

    @Test
    public void testZero() {
        assertTrue(Predicates.zero(0));
        assertFalse(Predicates.zero(1));
    }

    @Test
    public void testEq() {
        assertTrue(Predicates.eq(true).$(true));
        assertFalse(Predicates.eq(true).$(false));
    }

    @Test
    public void testLt() {
        assertTrue(Predicates.lt(0).$(-1));
        assertFalse(Predicates.lt(0).$(0));
        assertFalse(Predicates.lt(0).$(1));
    }

    @Test
    public void testLte() {
        assertTrue(Predicates.lte(0).$(-1));
        assertTrue(Predicates.lte(0).$(0));
        assertFalse(Predicates.lte(0).$(1));
    }

    @Test
    public void testGt() {
        assertTrue(Predicates.gt(0).$(1));
        assertFalse(Predicates.gt(0).$(0));
        assertFalse(Predicates.gt(0).$(-1));
    }

    @Test
    public void testGte() {
        assertTrue(Predicates.gte(0).$(1));
        assertTrue(Predicates.gte(0).$(0));
        assertFalse(Predicates.gte(0).$(-1));
    }

    @Test
    public void testAlways() {
        assertAlways(Predicates::always);
    }

    @Test
    public void testNever() {
        assertNever(Predicates::never);
    }

    @Test
    public void testAnd() {
        assertAlways(Predicates.and(Predicates::always, Predicates::always));
        assertNever(Predicates.and(Predicates::always, Predicates::never));
        assertNever(Predicates.and(Predicates::never, Predicates::always));
        assertNever(Predicates.and(Predicates::never, Predicates::never));
    }

    @Test
    public void testOr() {
        assertAlways(Predicates.or(Predicates::always, Predicates::always));
        assertAlways(Predicates.or(Predicates::always, Predicates::never));
        assertAlways(Predicates.or(Predicates::never, Predicates::always));
        assertNever(Predicates.or(Predicates::never, Predicates::never));
    }

    @Test
    public void testXor() {
        assertNever(Predicates.xor(Predicates::always, Predicates::always));
        assertAlways(Predicates.xor(Predicates::always, Predicates::never));
        assertAlways(Predicates.xor(Predicates::never, Predicates::always));
        assertNever(Predicates.xor(Predicates::never, Predicates::never));
    }

    @Test
    public void testNot() {
        assertAlways(Predicates.not(Predicates::never));
        assertNever(Predicates.not(Predicates::always));
    }

}
