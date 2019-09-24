package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.request.requestline.HttpRequestLine;
import webserver.request.requestline.QueryParams;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequestParser {

    private static final Logger log = LoggerFactory.getLogger(HttpRequestParser.class);
    private static final String BLANK_DELIMITER = " ";
    private static final String COLON_DELIMITER = ":";
    private static final int SIZE_OF_PART = 2;
    private static final String EMPTY = "";
    private static final String NEW_LINE = "\n";

    public static HttpRequest parseHttpRequest(BufferedReader br) throws IOException {
        HttpRequestLine httpRequestLine = parseHttpRequestLine(br);
        HttpRequestHeaderFields httpRequestHeaderFields = parseHeaderFields(br);

        if (br.ready()) {
            String contentLength = httpRequestHeaderFields.findField("Content-Length");
            HttpRequestBody httpRequestBody = parseHttpRequestBody(br, Integer.parseInt(contentLength));

            return new HttpRequest(httpRequestLine, httpRequestHeaderFields, httpRequestBody);
        }
        HttpRequestBody emptyBody = new HttpRequestBody(new QueryParams());
        return new HttpRequest(httpRequestLine, httpRequestHeaderFields, emptyBody);
    }

    private static HttpRequestLine parseHttpRequestLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        log.debug("request : {}", line);

        String[] tokens = line.split(BLANK_DELIMITER);
        String method = tokens[0];
        String uri = tokens[1];
        String version = tokens[2];

        return new HttpRequestLine(method, uri, version);
    }

    private static HttpRequestHeaderFields parseHeaderFields(BufferedReader br) throws IOException {
        HttpRequestHeaderFields httpRequestHeaderFields = new HttpRequestHeaderFields();

        String line = br.readLine();
        log.debug("request : {}", line);

        while (isValidLine(line)) {
            log.debug("request : {}", line);

            String[] tokens = line.split(COLON_DELIMITER, SIZE_OF_PART);

            String name = tokens[0].trim();
            String value = tokens[1].trim();
            httpRequestHeaderFields.addField(name, value);

            line = br.readLine();
        }

        return httpRequestHeaderFields;
    }

    private static boolean isValidLine(final String line) {
        return line != null && !EMPTY.equals(line) && !NEW_LINE.equals(line);
    }

    private static HttpRequestBody parseHttpRequestBody(final BufferedReader br, final int contentLength) throws IOException {
        String line = IOUtils.readData(br, contentLength);
        log.debug("request body : {}", line);

        QueryParams queryParams = QueryStringParser.parseQueryParams(line);
        return new HttpRequestBody(queryParams);
    }
}
