package http.request;

import http.common.HttpHeader;
import http.common.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestParser {
    static final String QUERY_STRING_DELIMITER = "\\?";
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestParser.class);
    private static final String BLANK = " ";
    private static final String LAST_HEADER_LINE = "";
    private static final String CONTENT_TYPE = "Content-Type";

    public static HttpRequest parse(final InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        RequestLine requestLine = parseRequestLine(bufferedReader.readLine());
        HttpRequestParams requestParams = parseQueryParams(requestLine);
        HttpHeader httpHeader = parseRequestHeader(convertHeaderLines(bufferedReader));
        ContentType contentType = ContentType.of(httpHeader.get(CONTENT_TYPE));
        MessageBody messageBody = parseMessageBody(bufferedReader, contentType, httpHeader.getContentLength());

        logger.info("new Request: {}", requestLine);
        return new HttpRequest(requestLine, requestParams, httpHeader, messageBody);
    }

    private static RequestLine parseRequestLine(String requestLine) {
        String[] startLine = requestLine.split(BLANK);

        return new RequestLine(
                new Url(startLine[1]),
                HttpMethod.valueOf(startLine[0]),
                HttpVersion.of(startLine[2]));
    }

    private static HttpHeader parseRequestHeader(List<String> rawHeaderLines) {
        return HttpHeader.of(rawHeaderLines);
    }

    private static HttpRequestParams parseQueryParams(final RequestLine requestLine) {
        String[] tokens = requestLine.getUrl().getRawUrl().split(QUERY_STRING_DELIMITER);

        if (tokens.length == 2) {
            return HttpRequestParams.of(tokens[1]);
        }
        return HttpRequestParams.init();
    }

    private static List<String> convertHeaderLines(final BufferedReader bufferedReader) throws IOException {
        List<String> headerLines = new ArrayList<>();
        String line;

        while (!(line = bufferedReader.readLine()).equals(LAST_HEADER_LINE)) {
            headerLines.add(line);
        }

        return headerLines;
    }

    private static MessageBody parseMessageBody(BufferedReader bufferedReader,
                                                ContentType contentType,
                                                int contentLength) throws IOException {
        if (contentType == null) {
            return null;
        }

        String body = IOUtils.readData(bufferedReader, contentLength);
        return contentType.getMessageBodyGenerator().apply(body);
    }
}