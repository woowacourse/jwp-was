package http.request;

import http.common.HttpHeader;
import http.common.exception.InvalidHttpHeaderException;
import http.request.exception.InvalidHttpRequestException;
import http.request.exception.InvalidRequestLineException;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.google.common.net.HttpHeaders.CONTENT_LENGTH;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static utils.StringUtils.BLANK;

public class HttpRequestCreator {
    public static HttpRequest create(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            RequestLine requestLine = new RequestLine(bufferedReader.readLine());
            HttpHeader httpHeader = new HttpHeader(IOUtils.readBeforeBlankLine(bufferedReader));

            String body = extractBody(bufferedReader, requestLine.getMethod(), httpHeader);
            RequestBody requestBody = new RequestBody(body, httpHeader.getHeaderAttribute(CONTENT_TYPE));

            return new HttpRequest(requestLine, httpHeader, requestBody);
        } catch (InvalidRequestLineException | InvalidHttpHeaderException e) {
            throw new InvalidHttpRequestException(e);
        }
    }

    private static String extractBody(BufferedReader bufferedReader, RequestMethod requestMethod, HttpHeader httpHeader) throws IOException {
        if (requestMethod.hasBody()) {
            int contentLength = Integer.parseInt(httpHeader.getHeaderAttribute(CONTENT_LENGTH));
            return IOUtils.readData(bufferedReader, contentLength);
        }
        return BLANK;
    }
}
