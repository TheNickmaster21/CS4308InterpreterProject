package in.nickma;

import java.util.HashMap;

public enum TokenType {

    INTEGER(null),
    FLOAT(null),
    IDENTIFIER(null),
    STRING(null),

    LEFT_PARENTHESIS("("),
    RIGHT_PARENTHESIS(")"),
    LEFT_BRACKET("["),
    RIGHT_BRACKET("]"),
    EQUAL_SIGN("="),
    ADDITION_OPERATOR("+"),
    SUBTRACTION_OPERATOR("-"),
    MULTIPLICATION_OPERATOR("*"),
    DIVISION_OPERATOR("/"),
    EXPONENT_OPERATOR("^"),
    LESS_THAN_OPERATOR("<"),
    GREATER_THAN_OPERATOR(">"),
    COMMA(","),

    SET("set"),
    DEFINE("define"),
    ARRAY("array"),
    FUNCTION("function"),
    RETURN("return"),
    OF("of"),
    TYPE("type"),
    PARAMETERS("parameters"),
    IS("is"),
    VARIABLES("variables"),
    BEGIN("begin"),
    DISPLAY("display"),
    WHILE("while"),
    DO("do"),
    IF("if"),
    THEN("then"),

    END_IF("endif"),
    END_WHILE("endwhile"),
    END_FUNCTION("endfun"),

    INTEGER_TYPE("integer"),

    BOF(null),
    EOF(null);

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
