package in.nickma;

import java.util.Iterator;

public class Scanner implements Iterator<Token> {

    private String input; // Store the string being scanned
    private int index = -1; // Store the character the scanner is currently on
    private int rowNumber = 1; // Store the current location being checked
    private int columnNumber = 1;
    private boolean finished; // Store if we are finished searching

    private Scanner(String input) {
        this.input = input;
    }

    // Make a scanner for the given String input
    public static Scanner getScanner(final String input) {
        Scanner scanner = new Scanner(input);
        scanner.advancePastWhiteSpace(); //We can get this out of the way and also check if the entire input is blank
        return scanner;
    }

    // Method to see if there is another token to be found
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

    // Method to actually find the next token
    @Override
    public Token next() {
        Token singleCharacterToken = singleCharacterParseNode(); // First check for a single character token (e.g. '=')
        if (singleCharacterToken != null) {
            advanceAndTrackLineNumber();
            return singleCharacterToken;
        }

        String runningLexeme = ""; // Store the code from where we started
        while (index < input.length()) { // Stop if we get to the end of the file
            singleCharacterToken = singleCharacterParseNode(); //Finding a single character token means we need to stop
            if (singleCharacterToken != null // Check if we should keep searching this chunk for a token
                    || (!runningLexeme.isEmpty() && runningLexeme.charAt(0) != '"'
                    && Character.isWhitespace(input.charAt(index)))) {
                return getNumberOrStringTokenFromLexeme(runningLexeme); // Build a generic token
            }
            runningLexeme = runningLexeme + input.charAt(index); // Add the next character
            advanceAndTrackLineNumber();
            TokenType tokenType = TokenType.getMatchingToken(runningLexeme); // Check if our current lexeme is a token
            if (tokenType != null) {
                return buildToken(tokenType, runningLexeme); // We found a token
            }
            if (runningLexeme.length() > 1 && runningLexeme.charAt(runningLexeme.length() - 1) == '"') {
                break; // We are at the end of a literal string and need to escape
            }
        }
        // We ran out of characters!
        if (runningLexeme.isEmpty() || !runningLexeme.matches(".*\\w.*")) {
            return buildToken(TokenType.EOF, ""); // If we had to end and found nothing, it is the end of the file
        } else {
            return getNumberOrStringTokenFromLexeme(runningLexeme); // Get a generic token from what we found
        }
    }

    // Use exceptions to determine if the lexeme is an integer, a float, or a string
    private Token getNumberOrStringTokenFromLexeme(final String lexeme) {
        try {
            String s = String.valueOf(Integer.parseInt(lexeme)); // If the parse fails, the lexeme is not an integer
            return buildToken(TokenType.INTEGER, s);
        } catch (NumberFormatException e) {
            // Also ugly and also works
        }
        try {
            String s = String.valueOf(Float.parseFloat(lexeme)); // If the parse fails, the lexeme is not a float
            return buildToken(TokenType.FLOAT, s);
        } catch (NumberFormatException e) {
            // Ugly but it works
        }
        if (lexeme.charAt(0) == '"' && lexeme.charAt(lexeme.length() - 1) == '"') {
            return buildToken(TokenType.STRING, lexeme); // lexeme has quotes so it is a string
        } else {
            return buildToken(TokenType.IDENTIFIER, lexeme); // blocks of text are simply just identifiers
        }

    }

    // Check if there is a token that can be built with a single character
    private Token singleCharacterParseNode() {
        TokenType tokenType = TokenType.getMatchingToken(String.valueOf(input.charAt(index)));
        if (tokenType != null) {
            return buildToken(tokenType, String.valueOf(input.charAt(index)));
        } else {
            return null;
        }
    }

    // Move the index forward past any whitespace
    private void advancePastWhiteSpace() {
        for (int i = index; i < input.length(); i++) {
            if (!(i == -1) && !Character.isWhitespace(input.charAt(i))) {
                break;
            }
            advanceAndTrackLineNumber();
        }
    }

    // Build a token object of the given type with the lexeme with the current row and column
    private Token buildToken(final TokenType tokenType, final String lexeme) {
        return new Token(tokenType, lexeme, rowNumber, columnNumber - lexeme.length() - 1);
    }

    // Move forward the index variable and increment the row and column based on what the last character was
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
