package utils;

import java.util.Arrays;
import java.util.List;

public class ParseString {

    public static List<String> parseHeaderData(String line) {
        String[] lines = line.split("\r\n");
        return Arrays.asList(lines);
    }

    public static String[] parseFirstLine(String firstLine) {
        return firstLine.split(" ");
    }
}
