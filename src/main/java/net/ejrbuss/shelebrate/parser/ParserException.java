package net.ejrbuss.shelebrate.parser;

public class ParserException extends Exception {

    public enum Type {
        INVALID_ESCAPE_SEQUENCE,
        UNTERMINATED_STRING,
        UNTERMINATED_SUB_GROUP,
    }

    public final Type type;
    public final String source;
    public final int line;
    public final int column;
    public final int position;

    public ParserException(Type type, String source, int line, int column, int position) {
        super("");
        this.type     = type;
        this.source   = source;
        this.line     = line;
        this.column   = column;
        this.position = position;
    }

    public ParserException(Type type, String source, Token token) {
        this(type, source, token.line, token.column, token.position);
    }

}