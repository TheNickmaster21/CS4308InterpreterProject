package in.nickma;

public class ParseNode {

    private Token token;
    private String lexeme;

    public ParseNode(final Token token, final String lexeme) {
        this.token = token;
        this.lexeme = lexeme;
    }

    public Token getToken() {
        return token;
    }


    public String getLexeme() {
        return lexeme;
    }

    @Override
    public String toString() {
        return "ParseNode{" +
                "token=" + token +
                ", lexeme='" + lexeme + '\'' +
                '}';
    }
}
