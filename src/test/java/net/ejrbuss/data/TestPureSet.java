package net.ejrbuss.data;

import net.ejrbuss.function.fn.Predicates;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPureSet {

    PureSet<Integer> evens = PureSet.from(Predicates::even);
    PureSet<Integer> odds = PureSet.from(Predicates::odd);

    @Test
    public void testMember() {
        assertTrue(evens.member(16));
        assertTrue(odds.member(101));
        assertFalse(evens.member(-3));
        assertFalse(odds.member(2));
    }

    @Test
    public void testUnion() {
        assertTrue(Seq.range(100).all(PureSet.union(evens, odds)));
        PureSet<Integer> set = PureSet.union(PureSet.from(Predicates.eq(3)), evens);
        assertTrue(set.member(0));
        assertTrue(set.member(3));
        assertFalse(set.member(5));
    }

    @Test
    public void testIntersect() {
        PureSet<Integer> set = PureSet.intersect(PureSet.from(Predicates.eq(4)), evens);
        assertTrue(set.member(4));
        assertFalse(set.member(2));
    }

    @Test
    public void testDisjunct() {
        assertTrue(Seq.range(100).all(PureSet.disjunct(evens, odds)));
        PureSet<Integer> set = PureSet.disjunct(PureSet.from(Predicates.eq(4)), evens);
        assertFalse(set.member(4));
        assertTrue(set.member(2));
    }

    @Test
    public void testCompliment() {
        assertTrue(odds.compliment().member(16));
        assertTrue(evens.compliment().member(101));
        assertFalse(odds.compliment().member(-3));
        assertFalse(evens.compliment().member(2));
    }

}
