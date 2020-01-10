package net.ejrbuss.shelebrate.parser;

public final class Token {

    public enum Type {
        VARIABLE,
        WORD,
        WORD_SEGMENT,
        STRING,
        STRING_SEGMENT,
        LEFT_PAREN,
        RIGHT_PAREN,
        LEFT_BRACKET,
        RIGHT_BRACKET,
        LEFT_BRACE,
        RIGHT_BRACE,
    }

    public final Type type;
    public final int line;
    public final int column;
    public final int position;
    public final String source;

    public Token(Type type, int line, int column, int position, String source) {
        this.type = type;
        this.line = line;
        this.column = column;
        this.position = position;
        this.source = source;
    }



    @Override
    public String toString() {
        return "Token(" 
            + this.type + ", " 
            + this.line + ", " 
            + this.column + ", " 
            + this.position + ", \""
            + this.source + "\")";
    }

}