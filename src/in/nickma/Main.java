package in.nickma;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

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

        switch (args[0]) {
            case "scan":
                new Scanner(input).forEachRemaining(Token::display);
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

    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
