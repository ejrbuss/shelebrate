package net.ejrbuss.shelebrate.parser;

import java.util.ArrayList;
import java.util.List;

public final class Tokenizer {

    public static List<Token> tokenize(String source) throws ParserException {
        Tokenizer tokenizer = new Tokenizer(source);
        return tokenizer.tokenize();
    }

    private static final String NEWLINE = "\n";
    private static final String WHITESPACE = " \r\t\f" + NEWLINE;
    private static final String PUNC = "()[]{}";
    private static final String QUOTES = "'\"";
    private static final String SLASHES = "/\\";
    private static final String ESCAPES = "tbnrf\"$\\";

    private List<Token> tokens;
    private String source;
    private int line;
    private int column;
    private int position;
    private int offset;

    private Tokenizer(String source) {
        this.source = source;
        this.line = 1;
        this.column = 1;
        this.position = 0;
        this.offset = 0;
        this.tokens = new ArrayList<Token>();
    }

    private List<Token> tokenize() throws ParserException {
        while (tokenizeNext());
        return tokens;
    }

    private boolean tokenizeNext() throws ParserException {
        switch (next()) {
            case '$':
                tokenizeVariable();
                break;
            case '#':
                consumeComment();
                break;
            case ' ':
            case '\r':
            case '\f':
            case '\t':
                consume();
                break;
            case '\n':
                line += 1;
                column = 1;
                consume();
                break;
            case '(':
                addToken(Token.Type.LEFT_PAREN);
                break;
            case ')':
                addToken(Token.Type.RIGHT_PAREN);
                break;
            case '{':
                addToken(Token.Type.LEFT_BRACKET);
                break;
            case '}':
                addToken(Token.Type.RIGHT_BRACKET);
                break;
            case '[':
                addToken(Token.Type.LEFT_BRACE);
                break;
            case ']':
                addToken(Token.Type.RIGHT_BRACE);
                break;
            case '"':
                tokenizeString();
                break;
            case '`':
                tokenizeStrictString();
            default:
                if (eof()) {
                    return false;
                }
                tokenizeWord();
                break;
        }
        return true;
    }

    private void tokenizeVariable() {
        final String variableTerminators = WHITESPACE + PUNC + QUOTES + SLASHES;
        while (!charIn(next(), variableTerminators, true)) {}
        prev();
        addToken(Token.Type.VARIABLE);
    }

    private void consumeComment() {
        final String commentTerminators = NEWLINE;
        while (!charIn(next(), commentTerminators, true)) {}
        prev();
        consume();
    }

    private void tokenizeString() throws ParserException {
        boolean escape = false;
        while (true) {
            char c = next();
            if (escape == true) {
                escape = false;
                if (!charIn(c, ESCAPES, false)) {
                    throw new ParserException(
                        ParserException.Type.INVALID_ESCAPE_SEQUENCE, 
                        source, 
                        line, 
                        column, 
                        position
                    );
                }
                continue;
            }
            if (c == '\\') {
                escape = true;
            }
            if (c == '$') {
                prev();
                addToken(Token.Type.STRING_SEGMENT);
                next();
                tokenizeSubGroup();
            }
            if (c == '"') {
                addToken(Token.Type.STRING);
                return;
            }
            if (c == '\0') {
                throw new ParserException(
                    ParserException.Type.UNTERMINATED_STRING, 
                    source, 
                    line, 
                    column, 
                    position
                );
            }
        }
    }

    private void tokenizeStrictString() {
        final String strictStringTerminators = "`";
        while (!charIn(next(), strictStringTerminators, true)) {}
        addToken(Token.Type.STRING);
    }

    private void tokenizeWord() throws ParserException {
        final String wordTerminators = WHITESPACE + PUNC + QUOTES;
        while (true) {
            char c = next();
            if (charIn(c, wordTerminators, true)) {
                prev();
                addToken(Token.Type.WORD);
                return;
            }
            if (c == '$') {
                prev();
                addToken(Token.Type.WORD_SEGMENT);
                next();
                tokenizeSubGroup();
            }
        }
    }

    private void tokenizeSubGroup() throws ParserException {
        if (next() != '(') {
            prev();
            prev();
            tokenizeVariable();
            return;
        }
        prev();
        consume();
        int parenCount = 0;
        while (true) {
            if (!tokenizeNext()) {
                throw new ParserException(
                    ParserException.Type.UNTERMINATED_SUB_GROUP, 
                    source, 
                    line, 
                    column, 
                    position
                );
            }
            Token last = lastToken();
            if (last.type == Token.Type.LEFT_PAREN) {
                parenCount += 1;
            } else if (last.type == Token.Type.RIGHT_PAREN) {
                parenCount -= 1;
            }
            if (parenCount == 0) {
                return;
            }
        }
    }

    private boolean eof() {
        return position + offset >= source.length();
    }

    private char current() {
        if (eof()) {
            return '\0';
        }
        return source.charAt(position + offset);
    }

    private char next() {
        char c = current();
        offset++;
        return c;
    }

    private char prev() {
        offset--;
        return current();
    }

    private void addToken(Token.Type type) {
        tokens.add(
            new Token(
                type, 
                line, 
                column, 
                position, 
                source.substring(position, position + offset)
            )
        );
        consume();
    }

    private Token lastToken() {
        return tokens.get(tokens.size() - 1);
    }

    private void consume() {
        position += offset;
        column += offset;
        offset = 0;
    }

    private boolean charIn(char c, String set, boolean includeEof) {
        if (includeEof && c == '\0') {
            return true;
        }
        return set.indexOf(c) != -1;
    }

}