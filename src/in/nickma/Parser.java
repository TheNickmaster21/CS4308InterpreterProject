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
            for (int i = remainingTokenBranches.size() - 1; i > 0 && !branchesChanged; i--) {
                branchesChanged = buildParent(i);
            }
            if (!branchesChanged) {
                throw new RuntimeException("The whole tree was traversed and it was not simplified."
                        + " There must be a syntactical error!");
            }
        }
        //TODO There are some conditions when it no longer parses down and something went wrong
        //We need to watch for those cases and throw an exception

        return remainingTokenBranches.get(0);
    }

    private boolean buildParent(final int index) {

        TokenBranch currentTokenBranch = remainingTokenBranches.get(index);

        switch (currentTokenBranch.getToken().getTypeCode()) {
            case IDENTIFIER:
                if (expression(index))
                    return true;
            case SET:
                if (set(index))
                    return true;
                //if some other option if that wasn't right
            case DEFINE:
                if (define(index))
                    return true;
            default:
                return false; // we got this far and none matched
        }
    }

    private boolean expression(final int index) {
        //TODO Check for other cases that make an expression first
        if (tokenBranchAtMatchesCode(index, IDENTIFIER)) {
            createAndAddTokenBranchObjectFromIndices(EXPRESSION, index, index);
            return true;
        }
        return false;
    }

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

    private boolean define(final int index) {
        //check if there is a type and variable
        // if so, return true, pull out those branches, and add a new parent in their place
        // if not, we need to throw an exception because the syntax is wrong
        return false;
    }

    // Utility methods

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

    private boolean tokenBranchAtMatchesCode(
            final int index,
            final int code) {
        return index > 0 && index < remainingTokenBranches.size()
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
