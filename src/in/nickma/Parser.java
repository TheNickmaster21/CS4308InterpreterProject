package in.nickma;

import java.util.List;
import java.util.stream.Collectors;

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

        }

        return remainingTokenBranches.get(0);
    }

}
