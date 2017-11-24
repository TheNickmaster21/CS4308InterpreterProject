package in.nickma;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static in.nickma.costants.Codes.*;

public class Parser {

    private List<TokenBranch> remainingTokenBranches;

    private Parser(final List<TokenBranch> remainingTokenBranches) {
        this.remainingTokenBranches = remainingTokenBranches;
    }

    // Make a parser using a list of parsable tokens
    public static Parser getParser(final List<ParsableToken> parsableTokens) {
        List<TokenBranch> tokenBranches = parsableTokens.stream()
                .map(TokenBranch::new)
                .collect(Collectors.toList());

        return new Parser(tokenBranches);
    }

    // The parse method builds a parse tree ending at the root node at the top of the tree
    public TokenBranch parse() {

        // iterate through the remaining tokens slowly shrinking the list as children are replaced by parents
        boolean branchesChanged;
        while (remainingTokenBranches.size() > 1) {
            branchesChanged = false;
            printDerivationStep();
            for (int i = remainingTokenBranches.size() - 1; i >= 0 && !branchesChanged; i--) {
                branchesChanged = buildParent(i);
            }
            if (!branchesChanged) {
                throw new RuntimeException("The whole tree was traversed and it was not simplified."
                        + " There must be a syntactical error!");
            }
        }

        System.out.println("Successfully parsed input!");

        return remainingTokenBranches.get(0);
    }

    //Check through all the possible grammar that is acceptable
    private boolean buildParent(final int index) {
        if (program(index))
            return true;

        if (block(index))
            return true;

        if (statement(index))
            return true;

        if (ifStatement(index))
            return true;

        if (whileStatement(index))
            return true;

        if (assignmentStatement(index))
            return true;

        if (repeatStatement(index))
            return true;

        if (printStatement(index))
            return true;

        if (booleanExpression(index))
            return true;

        if (relativeOperator(index))
            return true;

        if (arithmeticExpression(index))
            return true;

        if (arithmeticOperator(index))
            return true;

        return false;
    }

    private boolean program(final int index) {
        if (tokenBranchAtMatchesCode(index, FUNCTION)
                && tokenBranchAtMatchesCode(index + 1, IDENTIFIER)
                && tokenBranchAtMatchesCode(index + 2, LEFT_PARENTHESIS)
                && tokenBranchAtMatchesCode(index + 3, RIGHT_PARENTHESIS)
                && tokenBranchAtMatchesCode(index + 4, BLOCK)
                && tokenBranchAtMatchesCode(index + 5, END)) {
            createAndAddTokenBranchObjectFromIndices(PROGRAM, index, index + 5);
            return true;
        }
        return false;
    }

    private boolean block(final int index) {
        if (tokenBranchAtMatchesCode(index, STATEMENT)
                && tokenBranchAtMatchesCode(index + 1, BLOCK)) {
            createAndAddTokenBranchObjectFromIndices(BLOCK, index, index + 1);
            return true;
        } else if (tokenBranchAtMatchesCode(index, STATEMENT)) {
            createAndAddTokenBranchObjectFromIndices(BLOCK, index, index);
            return true;
        }
        return false;
    }

    private boolean statement(final int index) {
        if (tokenBranchAtMatchesCode(index, IF_STATEMENT)
                || tokenBranchAtMatchesCode(index, ASSIGNMENT_STATEMENT)
                || tokenBranchAtMatchesCode(index, WHILE_STATEMENT)
                || tokenBranchAtMatchesCode(index, PRINT_STATEMENT)
                || tokenBranchAtMatchesCode(index, REPEAT_STATEMENT)) {
            createAndAddTokenBranchObjectFromIndices(STATEMENT, index, index);
            return true;
        }
        return false;
    }

    private boolean ifStatement(final int index) {
        if (tokenBranchAtMatchesCode(index, IF)
                && tokenBranchAtMatchesCode(index + 1, BOOLEAN_EXPRESSION)
                && tokenBranchAtMatchesCode(index + 2, THEN)
                && tokenBranchAtMatchesCode(index + 3, BLOCK)
                && tokenBranchAtMatchesCode(index + 4, ELSE)
                && tokenBranchAtMatchesCode(index + 5, BLOCK)
                && tokenBranchAtMatchesCode(index + 6, END)) {
            createAndAddTokenBranchObjectFromIndices(IF_STATEMENT, index, index + 6);
            return true;
        } else if (tokenBranchAtMatchesCode(index, IF)
                && tokenBranchAtMatchesCode(index + 1, BOOLEAN_EXPRESSION)
                && tokenBranchAtMatchesCode(index + 2, THEN)
                && tokenBranchAtMatchesCode(index + 3, BLOCK)
                && tokenBranchAtMatchesCode(index + 4, END)) {
            createAndAddTokenBranchObjectFromIndices(IF_STATEMENT, index, index + 4);
            return true;
        }
        return false;
    }

    private boolean whileStatement(final int index) {
        if (tokenBranchAtMatchesCode(index, WHILE)
                && tokenBranchAtMatchesCode(index + 1, BOOLEAN_EXPRESSION)
                && tokenBranchAtMatchesCode(index + 2, DO)
                && tokenBranchAtMatchesCode(index + 3, BLOCK)
                && tokenBranchAtMatchesCode(index + 4, END)) {
            createAndAddTokenBranchObjectFromIndices(WHILE_STATEMENT, index, index + 4);
            return true;
        }
        return false;
    }

    private boolean assignmentStatement(final int index) {
        if (tokenBranchAtMatchesCode(index, IDENTIFIER)
                && tokenBranchAtMatchesCode(index + 1, ASSIGNMENT_OPERATOR)
                && tokenBranchAtMatchesCode(index + 2, ARITHMETIC_EXPRESSION)) {
            createAndAddTokenBranchObjectFromIndices(ASSIGNMENT_STATEMENT, index, index + 2);
            return true;
        } else if (tokenBranchAtMatchesCode(index, IDENTIFIER)
                && tokenBranchAtMatchesCode(index + 1, ASSIGNMENT_OPERATOR)
                && tokenBranchAtMatchesCode(index + 2, IDENTIFIER)) {
            createAndAddTokenBranchObjectFromIndices(ASSIGNMENT_STATEMENT, index, index + 2);
            return true;
        }
        return false;
    }

    private boolean repeatStatement(final int index) {
        if (tokenBranchAtMatchesCode(index, REPEAT)
                && tokenBranchAtMatchesCode(index + 1, BLOCK)
                && tokenBranchAtMatchesCode(index + 2, UNTIL)
                && tokenBranchAtMatchesCode(index + 3, BOOLEAN_EXPRESSION)) {
            createAndAddTokenBranchObjectFromIndices(REPEAT_STATEMENT, index, index + 3);
            return true;
        }
        return false;
    }

    private boolean printStatement(final int index) {
        if (tokenBranchAtMatchesCode(index, PRINT)
                && tokenBranchAtMatchesCode(index + 1, LEFT_PARENTHESIS)
                && tokenBranchAtMatchesCode(index + 2, ARITHMETIC_EXPRESSION)
                && tokenBranchAtMatchesCode(index + 3, RIGHT_PARENTHESIS)) {
            createAndAddTokenBranchObjectFromIndices(PRINT_STATEMENT, index, index + 3);
            return true;
        } else if (tokenBranchAtMatchesCode(index, PRINT)
                && tokenBranchAtMatchesCode(index + 1, LEFT_PARENTHESIS)
                && tokenBranchAtMatchesCode(index + 2, IDENTIFIER)
                && tokenBranchAtMatchesCode(index + 3, RIGHT_PARENTHESIS)) {
            createAndAddTokenBranchObjectFromIndices(PRINT_STATEMENT, index, index + 3);
            return true;
        }
        return false;
    }

    private boolean booleanExpression(final int index) {
        if (tokenBranchAtMatchesCode(index, ARITHMETIC_EXPRESSION)
                && tokenBranchAtMatchesCode(index + 1, RELATIVE_OPERATOR)
                && tokenBranchAtMatchesCode(index + 2, ARITHMETIC_EXPRESSION)) {
            createAndAddTokenBranchObjectFromIndices(BOOLEAN_EXPRESSION, index, index + 2);
            return true;
        } else if (tokenBranchAtMatchesCode(index, IDENTIFIER)
                && tokenBranchAtMatchesCode(index + 1, RELATIVE_OPERATOR)
                && tokenBranchAtMatchesCode(index + 2, ARITHMETIC_EXPRESSION)) {
            createAndAddTokenBranchObjectFromIndices(BOOLEAN_EXPRESSION, index, index + 2);
            return true;
        } else if (tokenBranchAtMatchesCode(index, ARITHMETIC_EXPRESSION)
                && tokenBranchAtMatchesCode(index + 1, RELATIVE_OPERATOR)
                && tokenBranchAtMatchesCode(index + 2, IDENTIFIER)) {
            createAndAddTokenBranchObjectFromIndices(BOOLEAN_EXPRESSION, index, index + 2);
            return true;
        } else if (tokenBranchAtMatchesCode(index, IDENTIFIER)
                && tokenBranchAtMatchesCode(index + 1, RELATIVE_OPERATOR)
                && tokenBranchAtMatchesCode(index + 2, IDENTIFIER)) {
            createAndAddTokenBranchObjectFromIndices(BOOLEAN_EXPRESSION, index, index + 2);
            return true;
        }
        return false;
    }

    private boolean relativeOperator(final int index) {
        if (tokenBranchAtMatchesCode(index, LE_OPERATOR)
                || tokenBranchAtMatchesCode(index, LT_OPERATOR)
                || tokenBranchAtMatchesCode(index, GE_OPERATOR)
                || tokenBranchAtMatchesCode(index, GT_OPERATOR)
                || tokenBranchAtMatchesCode(index, EQ_OPERATOR)
                || tokenBranchAtMatchesCode(index, NE_OPERATOR)) {
            createAndAddTokenBranchObjectFromIndices(RELATIVE_OPERATOR, index, index);
            return true;
        }
        return false;
    }

    private boolean arithmeticExpression(final int index) {
        if (tokenBranchAtMatchesCode(index, LITERAL_INTEGER)) {
            createAndAddTokenBranchObjectFromIndices(ARITHMETIC_EXPRESSION, index, index);
            return true;
        } else if (tokenBranchAtMatchesCode(index, ARITHMETIC_EXPRESSION)
                && tokenBranchAtMatchesCode(index + 1, ARITHMETIC_OPERATOR)
                && tokenBranchAtMatchesCode(index + 2, ARITHMETIC_EXPRESSION)) {
            createAndAddTokenBranchObjectFromIndices(ARITHMETIC_EXPRESSION, index, index + 2);
            return true;
        } else if (tokenBranchAtMatchesCode(index, IDENTIFIER)
                && tokenBranchAtMatchesCode(index + 1, ARITHMETIC_OPERATOR)
                && tokenBranchAtMatchesCode(index + 2, ARITHMETIC_EXPRESSION)) {
            createAndAddTokenBranchObjectFromIndices(ARITHMETIC_EXPRESSION, index, index + 2);
            return true;
        } else if (tokenBranchAtMatchesCode(index, IDENTIFIER)
                && tokenBranchAtMatchesCode(index + 1, ARITHMETIC_OPERATOR)
                && tokenBranchAtMatchesCode(index + 2, IDENTIFIER)) {
            createAndAddTokenBranchObjectFromIndices(ARITHMETIC_EXPRESSION, index, index + 2);
            return true;
        }
        return false;
    }

    private boolean arithmeticOperator(final int index) {
        if (tokenBranchAtMatchesCode(index, ADD_OPERATOR)
                || tokenBranchAtMatchesCode(index, SUB_OPERATOR)
                || tokenBranchAtMatchesCode(index, MUL_OPERATOR)
                || tokenBranchAtMatchesCode(index, DIV_OPERATOR)) {
            createAndAddTokenBranchObjectFromIndices(ARITHMETIC_OPERATOR, index, index);
            return true;
        }
        return false;
    }

    // Utility methods

    // Build a token branch from the start to end and assign it the given code
    private void createAndAddTokenBranchObjectFromIndices(
            final int code,
            final int start,
            final int end) {
        List<TokenBranch> children = new ArrayList<>();

        for (int i = start; i <= end; i++) {
            children.add(remainingTokenBranches.get(start));
            remainingTokenBranches.remove(start);
        }

        System.out.println(
                code
                        + " from "
                        + String.join(", ", convertTokenBranchesToStrings(children)));

        remainingTokenBranches.add(start, new TokenBranch(new ParsableToken(code, null, null), children));
    }

    // Check if the index is valid and contains the given code
    private boolean tokenBranchAtMatchesCode(
            final int index,
            final int code) {
        return index >= 0 && index < remainingTokenBranches.size()
                && remainingTokenBranches.get(index)
                .getToken().getTypeCode() == code;
    }

    private void printDerivationStep() {
        System.out.println(String.join(" - ", convertTokenBranchesToStrings(remainingTokenBranches)));
    }

    private List<String> convertTokenBranchesToStrings(
            final List<TokenBranch> tokenBranches) {
        return tokenBranches.stream()
                .map(TokenBranch::getToken)
                .map(ParsableToken::getTypeCode)
                .map(String::valueOf)
                .collect(Collectors.toList());
    }
}
