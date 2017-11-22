package in.nickma;

import in.nickma.costants.Codes;

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
        if (arrayIdentifier(index))
            return true;

        if (expression(index))
            return true;

        if (variableType(index))
            return true;

        if (set(index))
            return true;

        if (returnStatement(index))
            return true;

        if (ifStart(index))
            return true;

        if (ifStatement(index))
            return true;

        if (display(index))
            return true;

        if (input(index))
            return true;

        if (forStart(index))
            return true;

        if (forStatement(index))
            return true;

        if (whileStart(index))
            return true;

        if (whileStatement(index))
            return true;

        if (functionCall(index))
            return true;

        if (variable(index))
            return true;

        if (functionVariables(index))
            return true;

        if (parameter(index))
            return true;

        if (functionParameters(index))
            return true;

        if (functionStart(index))
            return true;

        if (functionHeader(index))
            return true;

        if (function(index))
            return true;

        if (file(index))
            return true;

        if (repeatStart(index))
            return true;

        if (repeatStatement(index))
            return true;

        return false;
    }

    private boolean arrayIdentifier(final int index) {
        if (tokenBranchAtMatchesCode(index, IDENTIFIER)
                && tokenBranchAtMatchesCode(index + 1, LEFT_BRACKET)
                && tokenBranchAtMatchesCode(index + 2, EXPRESSION)
                && tokenBranchAtMatchesCode(index + 3, RIGHT_BRACKET)) {
            createAndAddTokenBranchObjectFromIndices(ARRAY_IDENTIFIER, index, index + 3);
            return true;
        }
        return false;
    }

    private boolean expression(final int index) {
        //Single token ways to make an expression
        if (tokenBranchAtMatchesCode(index, IDENTIFIER)
                || tokenBranchAtMatchesCode(index, ARRAY_IDENTIFIER)
                || tokenBranchAtMatchesCode(index, INTEGER)
                || tokenBranchAtMatchesCode(index, FLOAT)
                || tokenBranchAtMatchesCode(index, STRING)) {
            createAndAddTokenBranchObjectFromIndices(EXPRESSION, index, index);
            return true;
        }
        //Multiple token ways to make an expression
        if (tokenBranchAtMatchesCode(index, EXPRESSION)
                && (tokenBranchAtMatchesCode(index + 1, ADDITION_OPERATOR)
                || tokenBranchAtMatchesCode(index + 1, SUBTRACTION_OPERATOR)
                || tokenBranchAtMatchesCode(index + 1, MULTIPLICATION_OPERATOR)
                || tokenBranchAtMatchesCode(index + 1, DIVISION_OPERATOR)
                || tokenBranchAtMatchesCode(index + 1, EXPONENT_OPERATOR)
                || tokenBranchAtMatchesCode(index + 1, LESS_THAN_OPERATOR)
                || tokenBranchAtMatchesCode(index + 1, GREATER_THAN_OPERATOR))
                && tokenBranchAtMatchesCode(index + 2, EXPRESSION)) {

            createAndAddTokenBranchObjectFromIndices(EXPRESSION, index, index + 2);
            return true;
        }
        return false;
    }

    private boolean variableType(final int index) {
        if (tokenBranchAtMatchesCode(index, INTEGER_TYPE)) {
            createAndAddTokenBranchObjectFromIndices(VARIABLE_TYPE, index, index);
            return true;
        }
        return false;
    }

    //ASSIGNMENT_STATEMENT!!!!!!!!!
    private boolean set(final int index) {
        //check if there is an equals sign and an expression next
        // if so, return true, pull out those branches, and add a new parent in their place
        if (tokenBranchAtMatchesCode(index, SET)
                && tokenBranchAtMatchesCode(index + 1, EXPRESSION) // Only valid if the expression consists of one identifier
                && tokenBranchAtMatchesCode(index + 2, EQUAL_SIGN)
                && tokenBranchAtMatchesCode(index + 3, EXPRESSION)) {

            createAndAddTokenBranchObjectFromIndices(STATEMENT, index, index + 3);
            return true;
        }
        return false;
    }

    private boolean returnStatement(final int index) {
        if (tokenBranchAtMatchesCode(index, RETURN)
                && tokenBranchAtMatchesCode(index + 1, EXPRESSION)) {
            createAndAddTokenBranchObjectFromIndices(STATEMENT, index, index + 1);
            return true;
        }
        return false;
    }

    private boolean ifStart(final int index) {
        if (tokenBranchAtMatchesCode(index, IF)
                && tokenBranchAtMatchesCode(index + 1, EXPRESSION)
                && tokenBranchAtMatchesCode(index + 2, THEN)) {
            createAndAddTokenBranchObjectFromIndices(IF_START, index, index + 2);
            return true;
        }
        return false;
    }

    private boolean ifStatement(final int index) {
        if (tokenBranchAtMatchesCode(index, IF_START)) {
            int counter = index + 1;
            while (tokenBranchAtMatchesCode(counter, STATEMENT)) {
                counter++;
            }
            if (tokenBranchAtMatchesCode(counter, END_IF)) {
                createAndAddTokenBranchObjectFromIndices(STATEMENT, index, counter);
                return true;
            }
        }
        return false;
    }

    private boolean whileStart(final int index) {
        if (tokenBranchAtMatchesCode(index, WHILE)
                && tokenBranchAtMatchesCode(index + 1, EXPRESSION)
                && tokenBranchAtMatchesCode(index + 2, DO)) {

            createAndAddTokenBranchObjectFromIndices(WHILE_START, index, index + 2);
            return true;
        }
        return false;
    }

    private boolean whileStatement(final int index) {
        if (tokenBranchAtMatchesCode(index, WHILE_START)) {
            int counter = index + 1;
            while (tokenBranchAtMatchesCode(counter, STATEMENT)) {
                counter++;
            }
            if (tokenBranchAtMatchesCode(counter, END_WHILE)) {
                createAndAddTokenBranchObjectFromIndices(STATEMENT, index, counter);
                return true;
            }
        }
        return false;
    }

    private boolean functionCall(final int index) {
        //Look for the start of a function call
        if (tokenBranchAtMatchesCode(index, EXPRESSION)
                && tokenBranchAtMatchesCode(index + 1, LEFT_PARENTHESIS)) {
            //Check for a call with no arguments
            if (tokenBranchAtMatchesCode(index + 2, RIGHT_PARENTHESIS)) {
                createAndAddTokenBranchObjectFromIndices(EXPRESSION, index, index + 2);
                return true;
            } else if (tokenBranchAtMatchesCode(index + 2, EXPRESSION)) {
                int counter = index + 3;
                while (tokenBranchAtMatchesCode(counter, COMMA)
                        && tokenBranchAtMatchesCode(counter + 1, EXPRESSION)) {
                    counter += 2;
                }
                if (tokenBranchAtMatchesCode(counter, RIGHT_PARENTHESIS)) {
                    createAndAddTokenBranchObjectFromIndices(EXPRESSION, index, counter);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean display(final int index) {
        if (tokenBranchAtMatchesCode(index, DISPLAY)
                && tokenBranchAtMatchesCode(index + 1, EXPRESSION)) {
            if (tokenBranchAtMatchesCode(index + 2, COMMA)
                    && tokenBranchAtMatchesCode(index + 3, EXPRESSION)) {
                createAndAddTokenBranchObjectFromIndices(STATEMENT, index, index + 3);
                return true;
            } else {
                createAndAddTokenBranchObjectFromIndices(STATEMENT, index, index + 1);
                return true;
            }
        }
        return false;
    }

    private boolean input(final int index) {
        if (tokenBranchAtMatchesCode(index, INPUT)
                && tokenBranchAtMatchesCode(index + 1, EXPRESSION)
                && tokenBranchAtMatchesCode(index + 2, COMMA)
                && tokenBranchAtMatchesCode(index + 3, EXPRESSION)) {
            createAndAddTokenBranchObjectFromIndices(STATEMENT, index, index + 3);
            return true;
        }
        return false;
    }

    private boolean forStart(final int index) {
        if (tokenBranchAtMatchesCode(index, FOR)
                && tokenBranchAtMatchesCode(index + 1, EXPRESSION)
                && tokenBranchAtMatchesCode(index + 2, EQUAL_SIGN)
                && tokenBranchAtMatchesCode(index + 3, EXPRESSION)
                && tokenBranchAtMatchesCode(index + 4, TO)
                && tokenBranchAtMatchesCode(index + 5, EXPRESSION)
                && tokenBranchAtMatchesCode(index + 6, DO)) {
            createAndAddTokenBranchObjectFromIndices(FOR_START, index, index + 6);
            return true;
        }
        return false;
    }

    private boolean forStatement(final int index) {
        if (tokenBranchAtMatchesCode(index, FOR_START)) {
            int counter = index + 1;
            while (tokenBranchAtMatchesCode(counter, STATEMENT)) {
                counter++;
            }
            if (tokenBranchAtMatchesCode(counter, END_FOR)) {
                createAndAddTokenBranchObjectFromIndices(STATEMENT, index, counter);
                return true;
            }
        }
        return false;
    }

    private boolean variable(final int index) {
        //non-array variable
        if (tokenBranchAtMatchesCode(index, DEFINE)
                && tokenBranchAtMatchesCode(index + 1, EXPRESSION)
                && tokenBranchAtMatchesCode(index + 2, OF)
                && tokenBranchAtMatchesCode(index + 3, TYPE)
                && tokenBranchAtMatchesCode(index + 4, VARIABLE_TYPE)) {
            createAndAddTokenBranchObjectFromIndices(VARIABLE, index, index + 4);
            return true;
        }
        //array variable
        if (tokenBranchAtMatchesCode(index, DEFINE)
                && tokenBranchAtMatchesCode(index + 1, EXPRESSION)
                && tokenBranchAtMatchesCode(index + 2, ARRAY)
                && tokenBranchAtMatchesCode(index + 3, LEFT_BRACKET)
                && tokenBranchAtMatchesCode(index + 4, EXPRESSION)
                && tokenBranchAtMatchesCode(index + 5, RIGHT_BRACKET)
                && tokenBranchAtMatchesCode(index + 6, OF)
                && tokenBranchAtMatchesCode(index + 7, TYPE)
                && tokenBranchAtMatchesCode(index + 8, VARIABLE_TYPE)) {
            createAndAddTokenBranchObjectFromIndices(VARIABLE, index, index + 8);
            return true;
        }
        return false;
    }

    private boolean functionVariables(final int index) {
        if (tokenBranchAtMatchesCode(index, VARIABLES)) {
            int counter = index + 1;
            while (tokenBranchAtMatchesCode(counter, VARIABLE)) {
                counter++;
            }
            createAndAddTokenBranchObjectFromIndices(FUNCTION_VARIABLES, index, counter - 1);
            return true;
        }
        return false;
    }

    private boolean parameter(final int index) {
        // Ensure that functionVariables are not parsed as functionParameters
        if (tokenBranchAtMatchesCode(index - 1, DEFINE)) {
            return false;
        }

        if (tokenBranchAtMatchesCode(index, EXPRESSION)
                && tokenBranchAtMatchesCode(index + 1, OF)
                && tokenBranchAtMatchesCode(index + 2, TYPE)
                && tokenBranchAtMatchesCode(index + 3, VARIABLE_TYPE)) {
            createAndAddTokenBranchObjectFromIndices(PARAMETER, index, index + 3);
            return true;
        }
        //array parameter
        if (tokenBranchAtMatchesCode(index, EXPRESSION)
                && tokenBranchAtMatchesCode(index + 1, ARRAY)
                && tokenBranchAtMatchesCode(index + 2, LEFT_BRACKET)
                && tokenBranchAtMatchesCode(index + 3, RIGHT_BRACKET)
                && tokenBranchAtMatchesCode(index + 4, OF)
                && tokenBranchAtMatchesCode(index + 5, TYPE)
                && tokenBranchAtMatchesCode(index + 6, VARIABLE_TYPE)) {
            createAndAddTokenBranchObjectFromIndices(PARAMETER, index, index + 6);
            return true;
        }
        return false;
    }

    private boolean functionParameters(final int index) {
        if (tokenBranchAtMatchesCode(index, PARAMETERS)
                && tokenBranchAtMatchesCode(index + 1, PARAMETER)) {
            int counter = index + 2;
            while (tokenBranchAtMatchesCode(counter, COMMA)
                    && tokenBranchAtMatchesCode(counter + 1, PARAMETER)) {
                counter += 2;
            }
            createAndAddTokenBranchObjectFromIndices(FUNCTION_PARAMETERS, index, counter - 1);
            return true;
        }
        return false;
    }

    private boolean functionStart(final int index) {
        if (tokenBranchAtMatchesCode(index, FUNCTION)
                && tokenBranchAtMatchesCode(index + 1, EXPRESSION)) {
            // function start with a return type
            if (tokenBranchAtMatchesCode(index + 2, RETURN)
                    && tokenBranchAtMatchesCode(index + 3, TYPE)
                    && tokenBranchAtMatchesCode(index + 4, VARIABLE_TYPE)) {
                createAndAddTokenBranchObjectFromIndices(FUNCTION_START, index, index + 4);
                return true;
            } else {
                //function start without a return type
                createAndAddTokenBranchObjectFromIndices(FUNCTION_START, index, index + 1);
                return true;
            }
        }
        return false;
    }


    private boolean functionHeader(final int index) {
        // function with no functionParameters
        if (tokenBranchAtMatchesCode(index, FUNCTION_START)
                && tokenBranchAtMatchesCode(index + 1, IS)
                && tokenBranchAtMatchesCode(index + 2, FUNCTION_VARIABLES)
                && tokenBranchAtMatchesCode(index + 3, BEGIN)) {
            createAndAddTokenBranchObjectFromIndices(FUNCTION_HEADER, index, index + 3);
            return true;
        }
        // function with functionParameters
        if (tokenBranchAtMatchesCode(index, FUNCTION_START)
                && tokenBranchAtMatchesCode(index + 1, FUNCTION_PARAMETERS)
                && tokenBranchAtMatchesCode(index + 2, IS)
                && tokenBranchAtMatchesCode(index + 3, FUNCTION_VARIABLES)
                && tokenBranchAtMatchesCode(index + 4, BEGIN)) {
            createAndAddTokenBranchObjectFromIndices(FUNCTION_HEADER, index, index + 4);
            return true;
        }
        return false;
    }

    private boolean function(final int index) {
        if (tokenBranchAtMatchesCode(index, FUNCTION_HEADER)) {
            int counter = index + 1;
            while (tokenBranchAtMatchesCode(counter, STATEMENT)) {
                counter++;
            }
            if (tokenBranchAtMatchesCode(counter, END_FUNCTION)
                    && tokenBranchAtMatchesCode(counter + 1, EXPRESSION)) {
                createAndAddTokenBranchObjectFromIndices(STATEMENT, index, counter + 1);
                return true;
            }
        }
        return false;
    }

    private boolean file(final int index) {
        if (tokenBranchAtMatchesCode(index, BOF)) {
            int counter = index + 1;
            while (tokenBranchAtMatchesCode(counter, STATEMENT)) {
                counter++;
            }
            if (tokenBranchAtMatchesCode(counter, EOF)) {
                createAndAddTokenBranchObjectFromIndices(FILE, index, counter);
                return true;
            }
        }
        return false;
    }

    private boolean repeatStart(final int index) {
        if(tokenBranchAtMatchesCode(index, REPEAT)
                && tokenBranchAtMatchesCode(index + 1, EXPRESSION)){
            int counter = index + 1;
            while (tokenBranchAtMatchesCode(counter, EXPRESSION)){
                counter++;
            }
            if (tokenBranchAtMatchesCode(counter, UNTIL)){
                createAndAddTokenBranchObjectFromIndices(REPEAT_START, index, counter);
                return true;
            }
        }
        return false;
    }

    private boolean repeatStatement(final int index) {
        if (tokenBranchAtMatchesCode(index, REPEAT_START)){
            int counter = index + 1;
            while (tokenBranchAtMatchesCode(counter, STATEMENT)){
                counter++;
            }
            if (tokenBranchAtMatchesCode(counter, END_REPEAT)) {
                createAndAddTokenBranchObjectFromIndices(STATEMENT, index, counter);
                return true;
            }
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
