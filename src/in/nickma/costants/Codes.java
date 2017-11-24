package in.nickma.costants;

import in.nickma.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Codes {

    //Scanning in.nickma.costants.Codes

    public static final int LITERAL_INTEGER = 1000;
    public static final int IDENTIFIER = 1001;

    public static final int LEFT_PARENTHESIS = 1100;
    public static final int RIGHT_PARENTHESIS = 1101;
    public static final int ASSIGNMENT_OPERATOR = 1104;
    public static final int ADD_OPERATOR = 1105;
    public static final int SUB_OPERATOR = 1106;
    public static final int MUL_OPERATOR = 1107;
    public static final int DIV_OPERATOR = 1108;
    public static final int LE_OPERATOR = 1109;
    public static final int LT_OPERATOR = 1110;
    public static final int GE_OPERATOR = 1111;
    public static final int GT_OPERATOR = 1112;
    public static final int EQ_OPERATOR = 1113;
    public static final int NE_OPERATOR = 1114;

    public static final int FUNCTION = 1200;
    public static final int WHILE = 1201;
    public static final int DO = 1202;
    public static final int IF = 1203;
    public static final int THEN = 1204;
    public static final int ELSE = 1205;
    public static final int REPEAT = 1206;
    public static final int UNTIL = 1207;
    public static final int END = 1208;
    public static final int PRINT = 1209;

    //Additional grammar / parsing codes

    public static final int PROGRAM = 2000;
    public static final int BLOCK = 2001;
    public static final int STATEMENT = 2002;
    public static final int IF_STATEMENT = 2003;
    public static final int WHILE_STATEMENT = 2004;
    public static final int ASSIGNMENT_STATEMENT = 2005;
    public static final int REPEAT_STATEMENT = 2006;
    public static final int PRINT_STATEMENT = 2007;
    public static final int BOOLEAN_EXPRESSION = 2008;
    public static final int RELATIVE_OPERATOR = 2009;
    public static final int ARITHMETIC_OPERATOR = 2010;
    public static final int ARITHMETIC_EXPRESSION = 2011;

    private Codes() {

    }

    private static Map<TokenType, Integer> codeLookup = new HashMap<>();

    static {
        codeLookup.put(TokenType.LITERAL_INTEGER, LITERAL_INTEGER);
        codeLookup.put(TokenType.IDENTIFIER, IDENTIFIER);

        codeLookup.put(TokenType.LEFT_PARENTHESIS, LEFT_PARENTHESIS);
        codeLookup.put(TokenType.RIGHT_PARENTHESIS, RIGHT_PARENTHESIS);
        codeLookup.put(TokenType.ASSIGNMENT_OPERATOR, ASSIGNMENT_OPERATOR);
        codeLookup.put(TokenType.ADD_OPERATOR, ADD_OPERATOR);
        codeLookup.put(TokenType.SUB_OPERATOR, SUB_OPERATOR);
        codeLookup.put(TokenType.MUL_OPERATOR, MUL_OPERATOR);
        codeLookup.put(TokenType.DIV_OPERATOR, DIV_OPERATOR);
        codeLookup.put(TokenType.LE_OPERATOR, LE_OPERATOR);
        codeLookup.put(TokenType.LT_OPERATOR, LT_OPERATOR);
        codeLookup.put(TokenType.GE_OPERATOR, GE_OPERATOR);
        codeLookup.put(TokenType.GT_OPERATOR, GT_OPERATOR);
        codeLookup.put(TokenType.EQ_OPERATOR, EQ_OPERATOR);
        codeLookup.put(TokenType.NE_OPERATOR, NE_OPERATOR);

        codeLookup.put(TokenType.FUNCTION, FUNCTION);
        codeLookup.put(TokenType.WHILE, WHILE);
        codeLookup.put(TokenType.DO, DO);
        codeLookup.put(TokenType.IF, IF);
        codeLookup.put(TokenType.THEN, THEN);
        codeLookup.put(TokenType.ELSE, ELSE);
        codeLookup.put(TokenType.REPEAT, REPEAT);
        codeLookup.put(TokenType.UNTIL, UNTIL);
        codeLookup.put(TokenType.END, END);
        codeLookup.put(TokenType.PRINT, PRINT);
    }

    public static Integer getCodeFromTokenType(final TokenType tokenType) {
        return codeLookup.get(tokenType);
    }

    public static TokenType getTokenTypeFromCode(final Integer code) {
        for (Map.Entry<TokenType, Integer> entry : codeLookup.entrySet()) {
            if (entry.getValue().equals(code)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
