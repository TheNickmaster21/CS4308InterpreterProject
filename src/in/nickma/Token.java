package in.nickma;

public class Token {

    private TokenType tokenType;
    private String lexeme;

    public Token(final TokenType tokenType, final String lexeme) {
        this.tokenType = tokenType;
        this.lexeme = lexeme;
    }

    public TokenType getTokenType() {
        return tokenType;
    }


    public String getLexeme() {
        return lexeme;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenType=" + tokenType +
                ", lexeme='" + lexeme + '\'' +
                '}';
    }
}
