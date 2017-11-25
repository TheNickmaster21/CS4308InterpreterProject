package in.nickma;

import java.util.Map;

import static in.nickma.costants.Codes.*;

public class Executor {

    private Map<Integer, Integer> literalLookup;
    private Map<Integer, Integer> identifierValueLookup;

    public Executor(
            final Map<Integer, Integer> literalLookup,
            final Map<Integer, Integer> identifierValueLookup) {
        this.literalLookup = literalLookup;
        this.identifierValueLookup = identifierValueLookup;
    }

    public void execute(final TokenBranch tokenBranch) {
        System.out.println();
        System.out.println("Executing program");
        if (doesTypeEqual(tokenBranch, PROGRAM)) {
            block(tokenBranch.getChildren().get(4));
        }
    }

    public void block(final TokenBranch tokenBranch) {
        tokenBranch.getChildren().forEach(
                childBranch -> {
                    if (doesTypeEqual(tokenBranch, BLOCK)) {
                        block(childBranch);
                    } else { //We know it is a "statement"
                        statement(childBranch);
                    }
                }
        );
    }

    public void statement(final TokenBranch tokenBranch) {
        if (doesTypeEqual(tokenBranch, IF_STATEMENT)) {
            ifStatement(tokenBranch);
        } else if (doesTypeEqual(tokenBranch, ASSIGNMENT_STATEMENT)) {
            assignmentStatement(tokenBranch);
        } else if (doesTypeEqual(tokenBranch, WHILE_STATEMENT)) {
            whileStatement(tokenBranch);
        } else if (doesTypeEqual(tokenBranch, PRINT_STATEMENT)) {
            printStatement(tokenBranch);
        } else if (doesTypeEqual(tokenBranch, REPEAT_STATEMENT)) {
            repeatStatement(tokenBranch);
        }
    }

    public void ifStatement(final TokenBranch tokenBranch) {
        if (booleanExpression(tokenBranch.getChildren().get(1))) {
            block(tokenBranch.getChildren().get(3));
        } else if (doesTypeEqual(tokenBranch.getChildren().get(4), ELSE)) {
            block(tokenBranch.getChildren().get(5));
        }
    }

    public void assignmentStatement(final TokenBranch tokenBranch) {
        identifierValueLookup.put(
                tokenBranch.getChildren().get(0).getToken().getParameterCode(),
                arithmeticExpressionOrIdentifier(tokenBranch.getChildren().get(2)));
    }

    public void whileStatement(final TokenBranch tokenBranch) {
        while (booleanExpression(tokenBranch.getChildren().get(1))) {
            block(tokenBranch.getChildren().get(3));
        }
    }

    public void printStatement(final TokenBranch tokenBranch) {
        System.out.println(
                arithmeticExpressionOrIdentifier(tokenBranch.getChildren().get(2)));
    }

    public void repeatStatement(final TokenBranch tokenBranch) {
        do {
            block(tokenBranch.getChildren().get(1));
        } while (!booleanExpression(tokenBranch.getChildren().get(3)));
    }

    public Boolean booleanExpression(final TokenBranch tokenBranch) {
        Integer arg0 = arithmeticExpressionOrIdentifier(tokenBranch.getChildren().get(0));
        Integer arg1 = arithmeticExpressionOrIdentifier(tokenBranch.getChildren().get(2));
        switch (tokenBranch.getChildren().get(1).getChildren().get(0).getToken().getTypeCode()) {
            case LE_OPERATOR:
                return arg0 <= arg1;
            case LT_OPERATOR:
                return arg0 < arg1;
            case GE_OPERATOR:
                return arg0 >= arg1;
            case GT_OPERATOR:
                return arg0 > arg1;
            case EQ_OPERATOR:
                return arg0.equals(arg1);
            case NE_OPERATOR:
                return !arg0.equals(arg1);
            default:
                return null;
        }
    }

    public Integer arithmeticExpressionOrIdentifier(final TokenBranch tokenBranch) {
        if (doesTypeEqual(tokenBranch, IDENTIFIER)) {
            return identifierValueLookup.get(tokenBranch.getToken().getParameterCode());
        } else if (doesTypeEqual(tokenBranch.getChildren().get(0), LITERAL_INTEGER)) {
            return literalLookup.get(tokenBranch.getChildren().get(0).getToken().getLiteralCode());
        } else if (doesTypeEqual(tokenBranch, ARITHMETIC_EXPRESSION)) {
            Integer arg0 = arithmeticExpressionOrIdentifier(tokenBranch.getChildren().get(0));
            Integer arg1 = arithmeticExpressionOrIdentifier(tokenBranch.getChildren().get(2));
            switch (tokenBranch.getChildren().get(1).getChildren().get(0).getToken().getTypeCode()) {
                case ADD_OPERATOR:
                    return arg0 + arg1;
                case SUB_OPERATOR:
                    return arg0 - arg1;
                case MUL_OPERATOR:
                    return arg0 * arg1;
                case DIV_OPERATOR:
                    return arg0 / arg1;
                default:
                    return null;
            }
        }
        return null;
    }


    //Utility method
    private boolean doesTypeEqual(final TokenBranch tokenBranch, final Integer code) {
        return tokenBranch.getToken().getTypeCode() == code;
    }
}