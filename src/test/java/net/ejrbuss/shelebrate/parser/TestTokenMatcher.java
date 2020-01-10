package net.ejrbuss.shelebrate.parser;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

public class TestTokenMatcher {

    private Token tokenA = new Token(Token.Type.STRING, 1, 1, 0, "\"tokenA\"");
    private Token tokenB = new Token(Token.Type.VARIABLE, 1, 1, 0, "$tokenB");
    private Token tokenC = new Token(Token.Type.LEFT_PAREN, 1, 1, 0, "(");
    private Token tokenD = new Token(Token.Type.WORD_SEGMENT, 1, 1, 0, "tokenD");

    @Test
    public void testMatchType() {
        assertTrue((new TokenMatcher(Token.Type.STRING)).match(tokenA));
        assertTrue((new TokenMatcher(Token.Type.VARIABLE)).match(tokenB));
        assertTrue((new TokenMatcher(Token.Type.LEFT_PAREN)).match(tokenC));
        assertTrue((new TokenMatcher(Token.Type.WORD_SEGMENT)).match(tokenD));

        assertFalse((new TokenMatcher(Token.Type.STRING_SEGMENT)).match(tokenA));
        assertFalse((new TokenMatcher(Token.Type.WORD)).match(tokenB));
        assertFalse((new TokenMatcher(Token.Type.RIGHT_BRACE)).match(tokenC));
        assertFalse((new TokenMatcher(Token.Type.VARIABLE)).match(tokenD));
    }

    @Test
    public void testMatchSource() {
        assertTrue((new TokenMatcher("\"tokenA\"")).match(tokenA));
        assertTrue((new TokenMatcher("$tokenB")).match(tokenB));
        assertTrue((new TokenMatcher("(")).match(tokenC));
        assertTrue((new TokenMatcher("tokenD")).match(tokenD));

        assertFalse((new TokenMatcher("tokenA")).match(tokenA));
        assertFalse((new TokenMatcher("$")).match(tokenB));
        assertFalse((new TokenMatcher(")")).match(tokenC));
        assertFalse((new TokenMatcher("Dtoken")).match(tokenD));
    }

    @Test 
    public void testMatchPattern() {
        assertTrue((new TokenMatcher(Pattern.compile("^\"tokenA\"$"))).match(tokenA));
        assertTrue((new TokenMatcher(Pattern.compile("^.*tok.*$"))).match(tokenB));
        assertTrue((new TokenMatcher(Pattern.compile("\\(|\\)"))).match(tokenC));
        assertTrue((new TokenMatcher(Pattern.compile("^.+D$"))).match(tokenD));

        assertFalse((new TokenMatcher(Pattern.compile("^aa+bb+$"))).match(tokenA));
        assertFalse((new TokenMatcher(Pattern.compile("^.?$"))).match(tokenB));
        assertFalse((new TokenMatcher(Pattern.compile("^[a-z]*$"))).match(tokenC));
        assertFalse((new TokenMatcher(Pattern.compile("^[^D]*$"))).match(tokenD));
    }

    @Test 
    public void testMatchTypeAndSource() {
        assertTrue((new TokenMatcher(Token.Type.STRING, "\"tokenA\"")).match(tokenA));
        assertTrue((new TokenMatcher(Token.Type.VARIABLE, "$tokenB")).match(tokenB));
        assertTrue((new TokenMatcher(Token.Type.LEFT_PAREN, "(")).match(tokenC));
        assertTrue((new TokenMatcher(Token.Type.WORD_SEGMENT, "tokenD")).match(tokenD));

        assertFalse((new TokenMatcher(Token.Type.STRING, "tokenA")).match(tokenA));
        assertFalse((new TokenMatcher(Token.Type.VARIABLE, "$")).match(tokenB));
        assertFalse((new TokenMatcher(Token.Type.RIGHT_BRACE, "(")).match(tokenC));
        assertFalse((new TokenMatcher(Token.Type.VARIABLE, "tokenD")).match(tokenD));
    }

    @Test 
    public void testMatchTypeAndPattern() {
        assertTrue((new TokenMatcher(Token.Type.STRING, Pattern.compile("^\"tokenA\"$"))).match(tokenA));
        assertTrue((new TokenMatcher(Token.Type.VARIABLE, Pattern.compile("^.*tok.*$"))).match(tokenB));
        assertTrue((new TokenMatcher(Token.Type.LEFT_PAREN, Pattern.compile("\\(|\\)"))).match(tokenC));
        assertTrue((new TokenMatcher(Token.Type.WORD_SEGMENT, Pattern.compile("^.+D$"))).match(tokenD));

        assertFalse((new TokenMatcher(Token.Type.STRING, Pattern.compile("^aa+bb+$"))).match(tokenA));
        assertFalse((new TokenMatcher(Token.Type.VARIABLE, Pattern.compile("^.?$"))).match(tokenB));
        assertFalse((new TokenMatcher(Token.Type.RIGHT_BRACE, Pattern.compile("\\(|\\)"))).match(tokenC));
        assertFalse((new TokenMatcher(Token.Type.VARIABLE, Pattern.compile("^.+D$"))).match(tokenD));
    }

}
