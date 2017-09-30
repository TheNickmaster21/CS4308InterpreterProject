package in.nickma;

public class Token {

    private TokenType tokenType;
    private String lexeme;

    private int rowNumber;
    private int columnNumber;

    public Token(
            final TokenType tokenType,
            final String lexeme,
            final int rowNumber,
            final int columnNumber) {
        this.tokenType = tokenType;
        this.lexeme = lexeme;

        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    public TokenType getTokenType() {
        return tokenType;
    }


    public String getLexeme() {
        return lexeme;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void display() {
        System.out.format(
                "%-10s%-10s%-30s%-60s%n",
                "row: " + getRowNumber(),
                "col: " + getColumnNumber(),
                "token: " + getTokenType().name(),
                "lexeme: " + getLexeme());
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenType=" + tokenType +
                ", lexeme='" + lexeme + '\'' +
                ", rowNumber=" + rowNumber +
                ", columnNumber=" + columnNumber +
                '}';
    }
}
