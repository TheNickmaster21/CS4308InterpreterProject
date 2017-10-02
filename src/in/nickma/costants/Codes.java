package in.nickma.costants;

import in.nickma.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Codes {

    public static final int INTEGER_CODE = 1000;
    public static final int FLOAT_CODE = 1001;
    public static final int IDENTIFIER_CODE = 1002;
    public static final int STRING_CODE = 1003;

    public static final int LEFT_PARENTHESIS = 1100;
    public static final int RIGHT_PARENTHESIS = 1101;
    public static final int LEFT_BRACKET = 1102;
    public static final int RIGHT_BRACKET = 1103;
    public static final int EQUAL_SIGN = 1104;
    public static final int ADDITION_OPERATOR = 1105;
    public static final int SUBTRACTION_OPERATOR = 1106;
    public static final int MULTIPLICATION_OPERATOR = 1107;
    public static final int DIVISION_OPERATOR = 1108;
    public static final int EXPONENT_OPERATOR = 1109;
    public static final int LESS_THAN_OPERATOR = 1110;
    public static final int GREATER_THAN_OPERATOR = 1111;
    public static final int COMMA = 1112;

    public static final int SET_CODE = 1200;
    public static final int DEFINE_CODE = 1201;
    public static final int ARRAY_CODE = 1202;
    public static final int FUNCTION = 1203;
    public static final int RETURN = 1204;
    public static final int OF = 1205;
    public static final int TYPE = 1206;
    public static final int PARAMETERS = 1207;
    public static final int IS = 1208;
    public static final int VARIABLES = 1209;
    public static final int BEGIN = 1210;
    public static final int DISPLAY = 1211;
    public static final int WHILE = 1212;
    public static final int DO = 1213;
    public static final int IF = 1214;
    public static final int THEN = 1215;

    public static final int END_IF = 1300;
    public static final int END_WHILE = 1301;
    public static final int END_FUNCTION = 1302;

    public static final int INTEGER_TYPE = 1400;

    public static final int EOF = 9999;

    private Codes() {

    }

    private static Map<TokenType, Integer> codeLookup = new HashMap<>();

    static {
        codeLookup.put(TokenType.INTEGER, INTEGER_CODE);
        codeLookup.put(TokenType.FLOAT, FLOAT_CODE);
        codeLookup.put(TokenType.IDENTIFIER, IDENTIFIER_CODE);
        codeLookup.put(TokenType.STRING, STRING_CODE);
        
        codeLookup.put(TokenType.LEFT_PARENTHESIS, LEFT_PARENTHESIS_CODE);
        codeLookup.put(TokenType.RIGHT_PARENTHESIS, RIGHT_PARENTHESIS_CODE);
        codeLookup.put(TokenType.LEFT_BRACKET, LEFT_BRACKET_CODE);
        codeLookup.put(TokenType.RIGHT_BRACKET, RIGHT_BRACKET_CODE);
        codeLookup.put(TokenType.EQUAL_SIGN, EQUAL_SIGN_CODE);
        codeLookup.put(TokenType.ADDITION_OPERATOR, ADDITION_OPERATOR_CODE);
        codeLookup.put(TokenType.SUBTRACTION_OPERATOR, SUBTRACTION_OPERATOR_CODE);
        codeLookup.put(TokenType.MULTIPLICATION_OPERATOR, MULTIPLICATION_OPERATOR_CODE);
        codeLookup.put(TokenType.DIVISION_OPERATOR, DIVISION_OPERATOR_CODE);
        codeLookup.put(TokenType.EXPONENT_OPERATOR, EXPONENT_OPERATOR_CODE);
        codeLookup.put(TokenType.LESS_THAN_OPERATOR, LESS_THAN_OPERATOR_CODE);
        codeLookup.put(TokenType.GREATER_THAN_OPERATOR, GREATER_THAN_OPERATOR_CODE);
        codeLookup.put(TokenType.COMMA, COMMA_CODE);
        
        codeLookup.put(TokenType.SET, SET_CODE);
        codeLookup.put(TokenType.DEFINE, DEFINE_CODE);
        codeLookup.put(TokenType.ARRAY, ARRAY_CODE);
        codeLookup.put(TokenType.FUNCTION, FUNCTION_CODE);
        codeLookup.put(TokenType.RETURN, RETURN_CODE);
        codeLookup.put(TokenType.OF, OF_CODE);
        codeLookup.put(TokenType.TYPE, TYPE_CODE);
        codeLookup.put(TokenType.PARAMETERS, PARAMETERS_CODE);
        codeLookup.put(TokenType.IS, IS_CODE);
        codeLookup.put(TokenType.VARIABLES, VARIABLES_CODE);
        codeLookup.put(TokenType.BEGIN, BEGIN_CODE);
        codeLookup.put(TokenType.DISPLAY, DISPLAY_CODE);
        codeLookup.put(TokenType.WHILE, WHILE_CODE);
        codeLookup.put(TokenType.DO, DO_CODE);
        codeLookup.put(TokenType.IF, IF_CODE);
        codeLookup.put(TokenType.THEN, THEN_CODE);
        
        codeLookup.put(TokenType.END_IF, END_IF_CODE);
        codeLookup.put(TokenType.END_WHILE, END_WHILE_CODE);
        codeLookup.put(TokenType.END_FUNCTION, END_FUNCTION_CODE);

        codeLookup.put(TokenType.INTEGER_TYPE, INTEGER_TYPE_CODE);
        
        codeLookup.put(TokenType.EOF, EOF_CODE);
        
    }

    public static Integer getCodeFromTokenType(final TokenType tokenType) {
        return codeLookup.get(tokenType);
    }
}
