package in.nickma.costants;

import in.nickma.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Codes {

    //Scanning in.nickma.costants.Codes

    public static final int BOF = 1000;

    public static final int INTEGER = 1001;
    public static final int FLOAT = 1002;
    public static final int IDENTIFIER = 1003;
    public static final int STRING = 1004;

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

    public static final int SET = 1200;
    public static final int DEFINE = 1201;
    public static final int ARRAY = 1202;
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
    public static final int FOR = 1216;
    public static final int END_FOR = 1217;
    public static final int TO = 1218;
    public static final int INPUT = 1219;

    //New codes as of 11/22 3:35pm
    public static final int AND = 1220;
    public static final int IN = 1221;
    public static final int REPEAT = 1222;
    public static final int END = 1223;
    public static final int BREAK = 1224;
    public static final int FALSE = 1225;
    public static final int TRUE = 1226;
    public static final int LOCAL = 1227;
    public static final int BREAK = 1228;
    public static final int NIL = 1229;
    public static final int ELSE = 1230;
    public static final int NOT = 1231;
    public static final int ELSEIF = 1232;
    public static final int OR = 1233;
    public static final int UNTIL = 1234;
    //

    public static final int END_IF = 1300;
    public static final int END_WHILE = 1301;
    public static final int END_FUNCTION = 1302;

    public static final int INTEGER_TYPE = 1400;

    public static final int EOF = 1999;

    //Additional grammar / parsing codes

    public static final int STATEMENT = 2000;
    public static final int EXPRESSION = 2001;
    public static final int ARRAY_IDENTIFIER = 2002;

    public static final int IF_START = 2100;
    public static final int FOR_START = 2101;
    public static final int WHILE_START = 2102;

    public static final int VARIABLE_TYPE = 2200;

    public static final int FUNCTION_START = 2500;
    public static final int PARAMETER = 2501;
    public static final int FUNCTION_PARAMETERS = 2502;
    public static final int VARIABLE = 2503;
    public static final int FUNCTION_VARIABLES = 2504;
    public static final int FUNCTION_HEADER = 2505;

    public static final int FILE = 2999;

    private Codes() {

    }

    private static Map<TokenType, Integer> codeLookup = new HashMap<>();

    static {
        codeLookup.put(TokenType.BOF, BOF);

        codeLookup.put(TokenType.INTEGER, INTEGER);
        codeLookup.put(TokenType.FLOAT, FLOAT);
        codeLookup.put(TokenType.IDENTIFIER, IDENTIFIER);
        codeLookup.put(TokenType.STRING, STRING);

        codeLookup.put(TokenType.LEFT_PARENTHESIS, LEFT_PARENTHESIS);
        codeLookup.put(TokenType.RIGHT_PARENTHESIS, RIGHT_PARENTHESIS);
        codeLookup.put(TokenType.LEFT_BRACKET, LEFT_BRACKET);
        codeLookup.put(TokenType.RIGHT_BRACKET, RIGHT_BRACKET);
        codeLookup.put(TokenType.EQUAL_SIGN, EQUAL_SIGN);
        codeLookup.put(TokenType.ADDITION_OPERATOR, ADDITION_OPERATOR);
        codeLookup.put(TokenType.SUBTRACTION_OPERATOR, SUBTRACTION_OPERATOR);
        codeLookup.put(TokenType.MULTIPLICATION_OPERATOR, MULTIPLICATION_OPERATOR);
        codeLookup.put(TokenType.DIVISION_OPERATOR, DIVISION_OPERATOR);
        codeLookup.put(TokenType.EXPONENT_OPERATOR, EXPONENT_OPERATOR);
        codeLookup.put(TokenType.LESS_THAN_OPERATOR, LESS_THAN_OPERATOR);
        codeLookup.put(TokenType.GREATER_THAN_OPERATOR, GREATER_THAN_OPERATOR);
        codeLookup.put(TokenType.COMMA, COMMA);

        codeLookup.put(TokenType.SET, SET);
        codeLookup.put(TokenType.DEFINE, DEFINE);
        codeLookup.put(TokenType.ARRAY, ARRAY);
        codeLookup.put(TokenType.FUNCTION, FUNCTION);
        codeLookup.put(TokenType.RETURN, RETURN);
        codeLookup.put(TokenType.OF, OF);
        codeLookup.put(TokenType.TYPE, TYPE);
        codeLookup.put(TokenType.PARAMETERS, PARAMETERS);
        codeLookup.put(TokenType.IS, IS);
        codeLookup.put(TokenType.VARIABLES, VARIABLES);
        codeLookup.put(TokenType.BEGIN, BEGIN);
        codeLookup.put(TokenType.DISPLAY, DISPLAY);
        codeLookup.put(TokenType.WHILE, WHILE);
        codeLookup.put(TokenType.DO, DO);
        codeLookup.put(TokenType.IF, IF);
        codeLookup.put(TokenType.THEN, THEN);
        codeLookup.put(TokenType.TO, TO);
        codeLookup.put(TokenType.FOR, FOR);
        codeLookup.put(TokenType.END_FOR, END_FOR);
        codeLookup.put(TokenType.INPUT, INPUT);

        codeLookup.put(TokenType.END_IF, END_IF);
        codeLookup.put(TokenType.END_WHILE, END_WHILE);
        codeLookup.put(TokenType.END_FUNCTION, END_FUNCTION);

        codeLookup.put(TokenType.INTEGER_TYPE, INTEGER_TYPE);

        codeLookup.put(TokenType.EOF, EOF);

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
