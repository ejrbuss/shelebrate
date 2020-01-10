package net.ejrbuss.shelebrate.parser;

import java.util.regex.Pattern;

public class TokenMatcher {

    public final Token.Type type;
    public final String source;
    public final Pattern pattern;

    public TokenMatcher(Token.Type type) {
        this.type = type;
        this.source = null;
        this.pattern = null;
    }

    public TokenMatcher(String source) {
        this.type = null;
        this.source = source;
        this.pattern = null;
    }

    public TokenMatcher(Pattern pattern) {
        this.type = null;
        this.source = null;
        this.pattern = pattern;
    }

    public TokenMatcher(Token.Type type, String source) {
        this.type = type;
        this.source = source;
        this.pattern = null;
    }

    public TokenMatcher(Token.Type type, Pattern pattern) {
        this.type = type;
        this.source = null;
        this.pattern = pattern;
    }

    public boolean match(Token token) {
        if (type != null && type != token.type) {
            return false;
        }
        if (source != null && !source.equals(token.source)) {
            return false;
        }
        if (pattern != null && !pattern.matcher(token.source).find()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TokenMatcher(" 
            + (type != null ? type : "*") + ", "
            + (source != null ? "\"" + source + "\"" : (pattern != null ? pattern : "*")) + ")";
    }

}