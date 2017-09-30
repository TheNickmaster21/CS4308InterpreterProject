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
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected a single argument!");
        }
        String input;
        try {
            input = readFile("test.cpl", Charset.defaultCharset());
        } catch (IOException e) {
            System.out.println("Failed to read the file test.cpl !");
            throw new RuntimeException(e);
        }

        Map<Integer, String> literalLookup = new HashMap<>();
        Map<Integer, String> parameterLookup = new HashMap<>();

        switch (args[0]) {
            case "scan":
                scan(input, literalLookup, parameterLookup);
                return;
            case "parse":
                // TODO
                return;
            case "interpret":
                // TODO
                return;
            default:
                throw new IllegalArgumentException("Expected valid argument! (scan, parse, or interpret)");
        }
    }

    static List<ParsableToken> scan(
            final String input,
            final Map<Integer, String> literalLookup,
            final Map<Integer, String> parameterLookup) {

        LinkedList<Token> tokens = new LinkedList<>();
        new Scanner(input).forEachRemaining(tokens::add);

        tokens.forEach(Token::display);

        return tokens.stream()
                .map(token -> {
                    Integer parameterCode = null;
                    Integer literalCode = null;
                    if (TokenType.IDENTIFIER.equals(token.getTokenType())) {
                        parameterCode = parameterLookup.size();
                        parameterLookup.put(parameterCode, token.getLexeme());
                    } else if (TokenType.INTEGER.equals(token.getTokenType())
                            || TokenType.FLOAT.equals(token.getTokenType())
                            || TokenType.STRING.equals(token.getTokenType())) {
                        literalCode = literalLookup.size();
                        literalLookup.put(literalCode, token.getLexeme());
                    }

                    return new ParsableToken(
                            Codes.getCodeFromTokenType(token.getTokenType()),
                            parameterCode,
                            literalCode);
                })
                .collect(Collectors.toList());
    }

    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
