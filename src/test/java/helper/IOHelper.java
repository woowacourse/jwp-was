package helper;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.stream.Collectors;

public class IOHelper {
    public static BufferedReader createBuffer(String... line) {
        String collectedLine = Arrays.stream(line).collect(Collectors.joining(System.lineSeparator()));
        return new BufferedReader(new StringReader(collectedLine));
    }
}
