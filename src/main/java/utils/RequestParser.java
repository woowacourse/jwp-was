package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RequestParser {

    public static List<String> parse(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> lines = new ArrayList<>();

        while (bufferedReader.ready()) {
            lines.add(bufferedReader.readLine());
        }

        return lines;
    }
}
