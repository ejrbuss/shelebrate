package net.ejrbuss.shelebrate.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import java.util.Arrays;

public class TestTokenizer {

    @Test
    public void testTokenizeEof() throws ParserException {
        assertEquals(Arrays.asList(), Tokenizer.tokenize(""));
    }

}
