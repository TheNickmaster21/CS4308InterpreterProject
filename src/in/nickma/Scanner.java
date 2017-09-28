package in.nickma;

import java.util.Iterator;

public class Scanner implements Iterator<Token> {

    private String input;
    private int index = -1;
    private boolean finished;

    public Scanner(String input) {
        this.input = input;
        if (!input.isEmpty()) {
            advancePastWhiteSpace();
        }
        this.finished = index == -1;
    }

    @Override
    public boolean hasNext() {
        if (this.finished) {
            return false;
        }
        if (index >= input.length()) {
            this.finished = true;
            return false;
        }
        return true;
    }

    @Override
    public Token next() {
        advancePastWhiteSpace();

        Token singleCharacterToken = singleCharacterParseNode();
        if (singleCharacterToken != null) {
            index++;
            return singleCharacterToken;
        }

        String runningLexeme = "";
        while (index < input.length()) {
            singleCharacterToken = singleCharacterParseNode();
            if (singleCharacterToken != null
                    || (!runningLexeme.isEmpty() && runningLexeme.charAt(0) != '"'
                    && Character.isWhitespace(input.charAt(index)))) {
                return getAbstractParseNodeFromLexeme(runningLexeme);
            }
            runningLexeme = runningLexeme + input.charAt(index);
            index++;
            TokenType tokenType = TokenType.getMatchingToken(runningLexeme);
            if (tokenType != null) {
                return new Token(tokenType, runningLexeme);
            }
            if (runningLexeme.length() > 1 && runningLexeme.charAt(runningLexeme.length() - 1) == '"') {
                break;
            }
        }
        // We ran out of characters!
        if (runningLexeme.isEmpty() || !runningLexeme.matches(".*\\w.*")) {
            return new Token(TokenType.EOF, "");
        } else {
            return getAbstractParseNodeFromLexeme(runningLexeme);
        }
    }

    private Token getAbstractParseNodeFromLexeme(final String lexeme) {
        try {
            String s = String.valueOf(Float.parseFloat(lexeme));
            return new Token(TokenType.FLOAT, s);
        } catch (NumberFormatException e) {
            // Ugly but it works
        }
        try {
            String s = String.valueOf(Integer.parseInt(lexeme));
            return new Token(TokenType.INTEGER, s);
        } catch (NumberFormatException e) {
            // Also ugly and also works
        }
        if (lexeme.charAt(0) == '"' && lexeme.charAt(lexeme.length() - 1) == '"') {
            return new Token(TokenType.STRING, lexeme);
        } else {
            return new Token(TokenType.IDENTIFIER, lexeme);
        }

    }

    private Token singleCharacterParseNode() {
        TokenType tokenType = TokenType.getMatchingToken(String.valueOf(input.charAt(index)));
        if (tokenType != null) {
            return new Token(tokenType, String.valueOf(String.valueOf(input.charAt(index))));
        } else {
            return null;
        }
    }

    private void advancePastWhiteSpace() {
        for (int i = index; i < input.length(); i++) {
            if (!(i == -1) && !Character.isWhitespace(input.charAt(i))) {
                index = i;
                break;
            }
        }
    }
}
