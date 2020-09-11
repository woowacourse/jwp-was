package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestUtils {

    public static String[] parseMethod(String line) {
        if ("".equals(line)) {
            throw new IllegalArgumentException();
        }
        return line.split(" ");
    }
}
