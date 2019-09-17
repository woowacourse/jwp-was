package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    private static final Logger logger = LoggerFactory.getLogger(RequestParser.class);

    private static final int MAX_BODY_SIZE = 1024 * 100; // 100KB

    public static Request parse(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String firstLine = br.readLine();

        Map<String, String> headers = new HashMap<>();
        parseHeader(br, headers);
        char[] buf = new char[MAX_BODY_SIZE];
        if (br.ready()) {
            br.read(buf);
        }

        return new Request(getMethod(firstLine), getUrl(firstLine), headers, new String(buf).getBytes());
    }

    private static void parseHeader(BufferedReader br, Map<String, String> headers) throws IOException {
        String line;
        line = br.readLine();
        while (line != null && !line.isEmpty()) {
            String[] headerTokens = line.split(": ", 2);
            headers.put(headerTokens[0], headerTokens[1]);
            line = br.readLine();
        }
    }

    private static String getMethod(String firstLine) {
        return firstLine.split(" ")[0];
    }

    private static String getUrl(String firstLine) {
        return firstLine.split(" ")[1];
    }
}
