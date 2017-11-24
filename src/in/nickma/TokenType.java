package in.nickma;

import java.util.HashMap;

public enum TokenType {

    LITERAL_INTEGER(null),
    IDENTIFIER(null),

    LEFT_PARENTHESIS("("),
    RIGHT_PARENTHESIS(")"),
    ASSIGNMENT_OPERATOR("="),
    ADD_OPERATOR("+"),
    SUB_OPERATOR("-"),
    MUL_OPERATOR("*"),
    DIV_OPERATOR("/"),
    LE_OPERATOR("<="),
    LT_OPERATOR("<"),
    GE_OPERATOR(">="),
    GT_OPERATOR(">"),
    EQ_OPERATOR("=="),
    NE_OPERATOR("~="),

    FUNCTION("function"),
    WHILE("while"),
    DO("do"),
    IF("if"),
    THEN("then"),
    ELSE("else"),
    REPEAT("repeat"),
    UNTIL("until"),
    END("end"),
    PRINT("print");

    private static HashMap<String, TokenType> lookUpTable = new HashMap<>();

    static {
        for (TokenType tokenType : values()) {
            if (tokenType.text != null) {
                lookUpTable.put(tokenType.text, tokenType);
            }
        }
    }

    private String text;

    TokenType(final String text) {
        this.text = text;
    }

    public static TokenType getMatchingToken(final String lexeme) {
        return lookUpTable.get(lexeme);
    }

}
