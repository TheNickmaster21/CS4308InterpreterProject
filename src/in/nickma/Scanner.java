package in.nickma;

import java.util.Iterator;

public class Scanner implements Iterator<ParseNode> {

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
    public ParseNode next() {
        advancePastWhiteSpace();

        ParseNode singleCharacterParseNode = singleCharacterParseNode();
        if (singleCharacterParseNode != null) {
            index++;
            return singleCharacterParseNode;
        }

        String runningLexeme = "";
        while (index < input.length()) {
            singleCharacterParseNode = singleCharacterParseNode();
            if (singleCharacterParseNode != null || Character.isWhitespace(input.charAt(index))) {
                return getAbstractParseNodeFromLexeme(runningLexeme);
            }
            runningLexeme = runningLexeme + input.charAt(index);
            index++;
            Token token = Token.getMatchingToken(runningLexeme);
            if (token != null) {
                return new ParseNode(token, runningLexeme);
            }
        }
        // We ran out of characters!
        if (runningLexeme.isEmpty()) {
            return new ParseNode(Token.EOF, "");
        } else {
            return getAbstractParseNodeFromLexeme(runningLexeme);
        }
    }

    private ParseNode getAbstractParseNodeFromLexeme(final String lexeme) {
        try {
            String s = String.valueOf(Float.parseFloat(lexeme));
            return new ParseNode(Token.FLOAT, s);
        } catch (NumberFormatException e) {
            // Ugly but it works
        }
        try {
            String s = String.valueOf(Integer.parseInt(lexeme));
            return new ParseNode(Token.INTEGER, s);
        } catch (NumberFormatException e) {
            // Also ugly and also works
        }
        return new ParseNode(Token.STRING, lexeme);
    }

    private ParseNode singleCharacterParseNode() {
        Token token = Token.getMatchingToken(String.valueOf(input.charAt(index)));
        if (token != null) {
            return new ParseNode(token, String.valueOf(String.valueOf(input.charAt(index))));
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
