package net.ejrbuss.shelebrate.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestTokenizer {

    public void testTokensMatch(List<Token> tokens, List<TokenMatcher> matchers) {
        for (int i = 0; i < tokens.size(); i++) {
            if (i >= matchers.size()) {
                System.out.println("null did not match " + tokens.get(i));
                assertTrue(false);
            }
            if (!matchers.get(i).match(tokens.get(i))) {
                System.out.println(matchers.get(i) + " did not match " + tokens.get(i));
                assertTrue(false);
            }
        }
    }

    @Test
    public void testTokenizeEof() throws ParserException {
        testTokensMatch(Tokenizer.tokenize(""), new ArrayList<TokenMatcher>());
    }

    @Test
    public void testTokenizeWhitespace() throws ParserException {
        testTokensMatch(Tokenizer.tokenize(" \t\n# this is a comment"), new ArrayList<TokenMatcher>());
    }

    @Test
    public void testTokenizeVariable() throws ParserException {
        testTokensMatch(Tokenizer.tokenize("$variable"), Arrays.asList(
                new TokenMatcher(Token.Type.VARIABLE, "$variable")
        ));
    }

    @Test
    public void testTokenizeParentheses() throws ParserException {
        testTokensMatch(Tokenizer.tokenize("(){}[]"), Arrays.asList(
                Token.Type.LEFT_PAREN.matcher(),
                Token.Type.RIGHT_PAREN.matcher(),
                Token.Type.LEFT_BRACKET.matcher(),
                Token.Type.RIGHT_BRACKET.matcher(),
                Token.Type.LEFT_BRACE.matcher(),
                Token.Type.RIGHT_BRACE.matcher()
        ));
    }

    @Test
    public void testTokenizeString() throws ParserException {
        testTokensMatch(Tokenizer.tokenize("\"a string\""), Arrays.asList(
                new TokenMatcher(Token.Type.STRING, "\"a string\"")
        ));
    }

    @Test
    public void testTokenizeStringWithVariable() throws ParserException {
        testTokensMatch(Tokenizer.tokenize("\"a string with a $variable in it\""), Arrays.asList(
                new TokenMatcher(Token.Type.STRING_SEGMENT, "\"a string with a "),
                new TokenMatcher(Token.Type.VARIABLE, "$variable"),
                new TokenMatcher(Token.Type.STRING, " in it\"")
        ));
    }

    @Test
    public void testTokenizeStringWithSubGroup() throws ParserException {
        testTokensMatch(Tokenizer.tokenize("\"a string with a $variable and $(sub $expr \"inner string\") in it\""), Arrays.asList(
                new TokenMatcher(Token.Type.STRING_SEGMENT, "\"a string with a "),
                new TokenMatcher(Token.Type.VARIABLE, "$variable"),
                new TokenMatcher(Token.Type.STRING_SEGMENT, " and "),
                Token.Type.LEFT_PAREN.matcher(),
                new TokenMatcher(Token.Type.WORD, "sub"),
                new TokenMatcher(Token.Type.VARIABLE, "$expr"),
                new TokenMatcher(Token.Type.STRING, "\"inner string\""),
                Token.Type.RIGHT_PAREN.matcher(),
                new TokenMatcher(Token.Type.STRING, " in it\"")
        ));
    }

    @Test(expected = ParserException.class)
    public void testTokenizeStringThrowsOnUnterminatedString() throws ParserException {
        Tokenizer.tokenize("\"this string is not closed");
    }

    @Test(expected = ParserException.class)
    public void testTokenizeStringThrowsUnterminatedSubGroup() throws ParserException {
        Tokenizer.tokenize("\"this string has an unterminated $(subgroup) $(right here");
    }

    @Test
    public void testTokenizeStrictString() throws ParserException {

    }

    @Test
    public void testTokenizeWord() throws ParserException {

    }

    @Test
    public void testTokennizeSample() throws ParserException {

    }

}
