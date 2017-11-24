package in.nickma;

import in.nickma.costants.Codes;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) { // Make sure the correct number of arguments were used
            throw new IllegalArgumentException("Expected two arguments!");
        }

        String input;
        try {
            input = readFile(args[1]); // Attempt to read the file specified
        } catch (IOException e) {
            System.out.println(String.format("Failed to read the file %s !", args[1]));
            throw new RuntimeException(e);
        }

        // Lookup tables are here so the scanner can be used to build them out for the parser
        Map<Integer, String> literalLookup = new HashMap<>();
        Map<Integer, String> parameterLookup = new HashMap<>();

        switch (args[0]) { // Ability to only do certain levels of processing
            case "scan":
                scan(input, literalLookup, parameterLookup);
                return;
            case "parse":
                parse(scan(input, literalLookup, parameterLookup));
                return;
            case "interpret":
                // TODO
                return;
            default:
                throw new IllegalArgumentException("Expected valid argument! (scan, parse, or interpret)");
        }
    }

    private static TokenBranch parse(
            final List<ParsableToken> parsableTokens) {

        Parser parser = Parser.getParser(parsableTokens);

        //TODO print out results

        return parser.parse();
    }

    // Build a scanner for the given input, return a list of ParsableTokens from it, and populate the lookup tables
    private static List<ParsableToken> scan(
            final String input,
            final Map<Integer, String> literalLookup,
            final Map<Integer, String> parameterLookup) {

        // Make and run a scanner
        List<Token> tokens = Scanner.getScanner(input).scan();

        // Display info on all the token scanned
        tokens.forEach(Token::display);

        // Map the Tokens into ParsableTokens (tokens with codes)
        return tokens.stream()
                .map(token -> {
                    // Check to see if we need to use the lookup tables
                    Integer parameterCode = null;
                    Integer literalCode = null;
                    if (TokenType.IDENTIFIER.equals(token.getTokenType())) {
                        parameterCode = parameterLookup.size();
                        parameterLookup.put(parameterCode, token.getLexeme());
                    } else if (TokenType.LITERAL_INTEGER.equals(token.getTokenType())) {
                        literalCode = literalLookup.size();
                        literalLookup.put(literalCode, token.getLexeme());
                    }

                    // Make the new Parsable token
                    return new ParsableToken(
                            Codes.getCodeFromTokenType(token.getTokenType()),
                            parameterCode,
                            literalCode);
                })
                .collect(Collectors.toList());
    }

    // Utility method to read a file with the given path
    private static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, Charset.defaultCharset());
    }
}
