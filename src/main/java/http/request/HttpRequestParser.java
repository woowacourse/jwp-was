package http.request;

import http.common.HttpHeader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestParser {

    private static final Logger log = LoggerFactory.getLogger(HttpRequestParser.class);
    public static final String CONTENT_LENGTH = "Content-Length";

    public static HttpRequest parse(InputStream in) throws IOException, URISyntaxException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        String firstLine = br.readLine();
        log.debug("request: {}", firstLine);
        RequestLine requestLine = RequestLineParser.parse(firstLine);

        List<String> headerLines = toHeaderList(br);
        HttpHeader requestHeader = RequestHeaderParser.parse(headerLines);

        RequestBody requestBody = null;
        if (br.ready()) {
            requestBody = RequestBodyParser.parse(br, requestHeader.findHeader(CONTENT_LENGTH));
        }
        return new HttpRequest(requestLine, requestHeader, requestBody);
    }

    private static List<String> toHeaderList(final BufferedReader br) throws IOException {
        List<String> headerLines = new ArrayList<>();

        String line = br.readLine();

        while (isValidLine(line)) {
            log.debug("request: {}", line);
            headerLines.add(line);
            line = br.readLine();
        }
        return headerLines;
    }

    private static boolean isValidLine(String line) {
        return line != null && !StringUtils.isEmpty(line) && !StringUtils.isBlank(line);
    }
}
