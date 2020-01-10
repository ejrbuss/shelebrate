package net.ejrbuss.shelebrate.parser;

import org.junit.Test;
import org.junit.Assert;
import java.util.regex.Pattern;

public class TestTokenMatcher {

    private Token tokenA = new Token(Token.Type.STRING, 1, 1, 0, "\"tokenA\"");
    private Token tokenB = new Token(Token.Type.VARIABLE, 1, 1, 0, "$tokenB");
    private Token tokenC = new Token(Token.Type.LEFT_PAREN, 1, 1, 0, "(");
    private Token tokenD = new Token(Token.Type.WORD_SEGMENT, 1, 1, 0, "tokenD");

    @Test
    public void testMatchType() {
        Assert.assertTrue((new TokenMatcher(Token.Type.STRING)).match(tokenA));
        Assert.assertTrue((new TokenMatcher(Token.Type.VARIABLE)).match(tokenB));
        Assert.assertTrue((new TokenMatcher(Token.Type.LEFT_PAREN)).match(tokenC));
        Assert.assertTrue((new TokenMatcher(Token.Type.WORD_SEGMENT)).match(tokenD));

        Assert.assertFalse((new TokenMatcher(Token.Type.STRING_SEGMENT)).match(tokenA));
        Assert.assertFalse((new TokenMatcher(Token.Type.WORD)).match(tokenB));
        Assert.assertFalse((new TokenMatcher(Token.Type.RIGHT_BRACE)).match(tokenC));
        Assert.assertFalse((new TokenMatcher(Token.Type.VARIABLE)).match(tokenD));
    }

    @Test
    public void testMatchSource() {
        Assert.assertTrue((new TokenMatcher("\"tokenA\"")).match(tokenA));
        Assert.assertTrue((new TokenMatcher("$tokenB")).match(tokenB));
        Assert.assertTrue((new TokenMatcher("(")).match(tokenC));
        Assert.assertTrue((new TokenMatcher("tokenD")).match(tokenD));

        Assert.assertFalse((new TokenMatcher("tokenA")).match(tokenA));
        Assert.assertFalse((new TokenMatcher("$")).match(tokenB));
        Assert.assertFalse((new TokenMatcher(")")).match(tokenC));
        Assert.assertFalse((new TokenMatcher("Dtoken")).match(tokenD));
    }

    @Test 
    public void testMatchPattern() {
        Assert.assertTrue((new TokenMatcher(Pattern.compile("^\"tokenA\"$"))).match(tokenA));
        Assert.assertTrue((new TokenMatcher(Pattern.compile("^.*tok.*$"))).match(tokenB));
        Assert.assertTrue((new TokenMatcher(Pattern.compile("\\(|\\)"))).match(tokenC));
        Assert.assertTrue((new TokenMatcher(Pattern.compile("^.+D$"))).match(tokenD));

        Assert.assertFalse((new TokenMatcher(Pattern.compile("^aa+bb+$"))).match(tokenA));
        Assert.assertFalse((new TokenMatcher(Pattern.compile("^.?$"))).match(tokenB));
        Assert.assertFalse((new TokenMatcher(Pattern.compile("^[a-z]*$"))).match(tokenC));
        Assert.assertFalse((new TokenMatcher(Pattern.compile("^[^D]*$"))).match(tokenD));
    }

    @Test 
    public void testMatchTypeAndSource() {
        Assert.assertTrue((new TokenMatcher(Token.Type.STRING, "\"tokenA\"")).match(tokenA));
        Assert.assertTrue((new TokenMatcher(Token.Type.VARIABLE, "$tokenB")).match(tokenB));
        Assert.assertTrue((new TokenMatcher(Token.Type.LEFT_PAREN, "(")).match(tokenC));
        Assert.assertTrue((new TokenMatcher(Token.Type.WORD_SEGMENT, "tokenD")).match(tokenD));

        Assert.assertFalse((new TokenMatcher(Token.Type.STRING, "tokenA")).match(tokenA));
        Assert.assertFalse((new TokenMatcher(Token.Type.VARIABLE, "$")).match(tokenB));
        Assert.assertFalse((new TokenMatcher(Token.Type.RIGHT_BRACE, "(")).match(tokenC));
        Assert.assertFalse((new TokenMatcher(Token.Type.VARIABLE, "tokenD")).match(tokenD));
    }

    @Test 
    public void testMatchTypeAndPattern() {
        Assert.assertTrue((new TokenMatcher(Token.Type.STRING, Pattern.compile("^\"tokenA\"$"))).match(tokenA));
        Assert.assertTrue((new TokenMatcher(Token.Type.VARIABLE, Pattern.compile("^.*tok.*$"))).match(tokenB));
        Assert.assertTrue((new TokenMatcher(Token.Type.LEFT_PAREN, Pattern.compile("\\(|\\)"))).match(tokenC));
        Assert.assertTrue((new TokenMatcher(Token.Type.WORD_SEGMENT, Pattern.compile("^.+D$"))).match(tokenD));

        Assert.assertFalse((new TokenMatcher(Token.Type.STRING, Pattern.compile("^aa+bb+$"))).match(tokenA));
        Assert.assertFalse((new TokenMatcher(Token.Type.VARIABLE, Pattern.compile("^.?$"))).match(tokenB));
        Assert.assertFalse((new TokenMatcher(Token.Type.RIGHT_BRACE, Pattern.compile("\\(|\\)"))).match(tokenC));
        Assert.assertFalse((new TokenMatcher(Token.Type.VARIABLE, Pattern.compile("^.+D$"))).match(tokenD));
    }

}
