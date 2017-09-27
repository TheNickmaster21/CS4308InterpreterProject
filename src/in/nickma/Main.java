package in.nickma;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Expected argument!");
        }
        switch (args[0]) {
            case "scan":

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
}
