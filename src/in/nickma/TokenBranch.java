package in.nickma;

import java.util.List;

public class TokenBranch {

    private ParsableToken token;
    private List<TokenBranch> children;

    public TokenBranch(final ParsableToken token) {
        this.token = token;
    }

    public TokenBranch(final ParsableToken token, final List<TokenBranch> children) {
        this.token = token;
        this.children = children;
    }

    public ParsableToken getToken() {
        return token;
    }

    public boolean isLeaf() {
        return children == null || children.isEmpty();
    }

    public List<TokenBranch> getChildren() {
        return children;
    }
}
