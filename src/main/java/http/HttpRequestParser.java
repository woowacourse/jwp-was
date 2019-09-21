package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestParser {

    private static final Logger log = LoggerFactory.getLogger(HttpRequestParser.class);

    public static HttpRequest parse(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        String firstLine = br.readLine();
        log.debug("request: {}",firstLine);
        RequestLine requestLine = RequestLineParser.parse(firstLine);

        List<String> headerLines = toHeaderList(br);
        RequestHeader requestHeader = RequestHeaderParser.parse(headerLines);

        return new HttpRequest(requestLine, requestHeader);
    }

    private static List<String> toHeaderList(final BufferedReader br) throws IOException {
        List<String> headerLines = new ArrayList<>();

        String line = br.readLine();

        while (isValidLine(line)) {
            log.debug("request: {}",line);
            headerLines.add(line);
            line = br.readLine();
        }
        return headerLines;
    }

    private static boolean isValidLine(String line) {
        return line != null && !"".equals(line) && !" ".equals(line);
    }
}
