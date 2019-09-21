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
        String line = bufferedReader.readLine();
        List<String> lines = new ArrayList<>();

        String contentLength = "0";

        while (!"".equals(line)) {
            lines.add(line);
            line = bufferedReader.readLine();
            if (line.contains("Content-Length")) {
                contentLength = line.split(": ")[1];
            }
        }
        lines.add("");

        if (bufferedReader.ready()) {
            lines.add(IOUtils.readData(bufferedReader, Integer.parseInt(contentLength)));
        }

        return lines;
    }
}
