package controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public static void resetScanner(String input) {
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
    }

    public static ByteArrayOutputStream setByteArrayOutputStream() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        return outContent;
    }

    public static Matcher getMatcher(String regex, String command) {
        return Pattern.compile(regex).matcher(command);
    }
}
