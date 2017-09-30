package in.nickma;

import java.util.Iterator;

public class Scanner implements Iterator<Token> {

    private String input;
    private int index = -1;
    private int rowNumber = 1;
    private int columnNumber = 1;
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
        advancePastWhiteSpace();

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
        Token singleCharacterToken = singleCharacterParseNode();
        if (singleCharacterToken != null) {
            advanceAndTrackLineNumber();
            return singleCharacterToken;
        }

        String runningLexeme = "";
        while (index < input.length()) {
            singleCharacterToken = singleCharacterParseNode();
            if (singleCharacterToken != null
                    || (!runningLexeme.isEmpty() && runningLexeme.charAt(0) != '"'
                    && Character.isWhitespace(input.charAt(index)))) {
                return getNumberOrStringTokenFromLexeme(runningLexeme);
            }
            runningLexeme = runningLexeme + input.charAt(index);
            advanceAndTrackLineNumber();
            TokenType tokenType = TokenType.getMatchingToken(runningLexeme);
            if (tokenType != null) {
                return buildToken(tokenType, runningLexeme);
            }
            if (runningLexeme.length() > 1 && runningLexeme.charAt(runningLexeme.length() - 1) == '"') {
                break;
            }
        }
        // We ran out of characters!
        if (runningLexeme.isEmpty() || !runningLexeme.matches(".*\\w.*")) {
            return buildToken(TokenType.EOF, "");
        } else {
            return getNumberOrStringTokenFromLexeme(runningLexeme);
        }
    }

    private Token getNumberOrStringTokenFromLexeme(final String lexeme) {
        try {
            String s = String.valueOf(Integer.parseInt(lexeme));
            return buildToken(TokenType.INTEGER, s);
        } catch (NumberFormatException e) {
            // Also ugly and also works
        }
        try {
            String s = String.valueOf(Float.parseFloat(lexeme));
            return buildToken(TokenType.FLOAT, s);
        } catch (NumberFormatException e) {
            // Ugly but it works
        }
        if (lexeme.charAt(0) == '"' && lexeme.charAt(lexeme.length() - 1) == '"') {
            return buildToken(TokenType.STRING, lexeme);
        } else {
            return buildToken(TokenType.IDENTIFIER, lexeme);
        }

    }

    private Token singleCharacterParseNode() {
        TokenType tokenType = TokenType.getMatchingToken(String.valueOf(input.charAt(index)));
        if (tokenType != null) {
            return buildToken(tokenType, String.valueOf(input.charAt(index)));
        } else {
            return null;
        }
    }

    private void advancePastWhiteSpace() {
        for (int i = index; i < input.length(); i++) {
            if (!(i == -1) && !Character.isWhitespace(input.charAt(i))) {
                break;
            }
            advanceAndTrackLineNumber();
        }
    }

    private Token buildToken(final TokenType tokenType, final String lexeme) {
        return new Token(tokenType, lexeme, rowNumber, columnNumber - lexeme.length() - 1);
    }

    private void advanceAndTrackLineNumber() {
        if (index > -1 && index < input.length() && input.charAt(index) == '\n') {
            columnNumber = 0;
            rowNumber++;
        } else {
            columnNumber++;
        }
        index++;
    }
}
