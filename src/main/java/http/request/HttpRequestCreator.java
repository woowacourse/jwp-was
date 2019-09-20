package http.request;

import http.common.HttpHeader;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequestCreator {
    private static final String BLANK = "";
    private static final String CONTENT_LENGTH = "Content-Length";

    public static HttpRequest create(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        RequestLine requestLine = new RequestLine(bufferedReader.readLine());
        HttpHeader httpHeader = new HttpHeader(IOUtils.readBeforeBlankLine(bufferedReader));

        String body = extractBody(bufferedReader, requestLine.getMethod(), httpHeader);

        return new HttpRequest(requestLine, httpHeader, body);
    }

    private static String extractBody(BufferedReader bufferedReader, RequestMethod requestMethod, HttpHeader httpHeader) throws IOException {
        if (requestMethod.hasBody()) {
            int contentLength = Integer.parseInt(httpHeader.get(CONTENT_LENGTH));
            return IOUtils.readData(bufferedReader, contentLength);
        }
        return BLANK;
    }
}
