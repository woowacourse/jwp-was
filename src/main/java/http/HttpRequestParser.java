package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestParser {

    public static HttpRequest parse(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        String firstLine = br.readLine();
        RequestLine requestLine = RequestLineParser.parse(firstLine);

        List<String> headerLines = toHeaderList(br);
        RequestHeader requestHeader = RequestHeaderParser.parse(headerLines);

        return new HttpRequest(requestLine, requestHeader);
    }

    private static List<String> toHeaderList(final BufferedReader br) throws IOException {
        List<String> headerLines = new ArrayList<>();

        String line = br.readLine();
        while (isValidLine(line)) {
            headerLines.add(line);
            line = br.readLine();
        }
        return headerLines;
    }

    private static boolean isValidLine(String line) {
        return line != null && !"".equals(line) && !" ".equals(line);
    }
}
