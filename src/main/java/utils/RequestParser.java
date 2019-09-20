package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RequestParser {
    private static final Logger log = LoggerFactory.getLogger(RequestParser.class);

    public static List<String> parse(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = bufferedReader.readLine();
        List<String> lines = new ArrayList<>();

        readLine(bufferedReader, line, lines);
        lines.add("");

        if (bufferedReader.ready()) {
            line = bufferedReader.readLine();
            readLine(bufferedReader, line, lines);
        }

        return lines;
    }

    private static void readLine(BufferedReader bufferedReader, String line, List<String> lines) throws IOException {
        while (!"".equals(line)) {
            lines.add(line);
            line = bufferedReader.readLine();
        }
    }
}
