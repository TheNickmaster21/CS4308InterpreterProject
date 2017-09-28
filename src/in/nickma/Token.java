package in.nickma;

import java.util.HashMap;

public enum Token {

    INTEGER(null),
    FLOAT(null),
    IDENTIFIER(null),
    STRING(null),

    LEFT_PARENTHESIS("("),
    RIGHT_PARENTHESIS(")"),
    EQUAL_SIGN("="),
    ADDITION_OPERATOR("+"),
    SUBTRACTION_OPERATOR("-"),
    MULTIPLICATION_OPERATOR("*"),
    DIVISION_OPERATOR("/"),

    EOF(null);

    private static HashMap<String, Token> lookUpTable = new HashMap<>();

    static {
        for (Token token : values()) {
            if (token.text != null) {
                lookUpTable.put(token.text, token);
            }
        }
    }

    private String text;

    Token(final String text) {
        this.text = text;
    }

    public static Token getMatchingToken(final String lexeme) {
        return lookUpTable.get(lexeme);
    }

}
