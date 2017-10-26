package in.nickma;

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
        while (remainingTokenBranches.size() > 1) {
            for (int i = 0; i < remainingTokenBranches.size(); i++) {
                if (buildParent(i)) {
                    break; // Restart the for loop from the beginning
                }
            }
            throw new RuntimeException("The whole tree was traversed and it was not simplified."
                    + " There must be a syntactical error!");
        }
        //TODO There are some conditions when it no longer parses down and something went wrong
        //We need to watch for those cases and throw an exception

        return remainingTokenBranches.get(0);
    }

    private boolean buildParent(final int index) {

        TokenBranch currentTokenBranch = remainingTokenBranches.get(index);

        switch (currentTokenBranch.getToken().getTypeCode()) {
            case SET_CODE:
                if (set(index))
                    return true;
                //if some other option if that wasn't right
            case DEFINE_CODE:
                if (define(index))
                    return true;
            default:
                return false; // we got this far and none matched
        }
    }

    private boolean set(final int index) {
        //check if there is an equals sign and an expression next
        // if so, return true, pull out those branches, and add a new parent in their place
        return true;
    }

    private boolean define(final int index) {
        //check if there is a type and variable
        // if so, return true, pull out those branches, and add a new parent in their place
        // if not, we need to throw an exception because the syntax is wrong
        return true;
    }

}
