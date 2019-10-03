package http.response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import exception.FailResponseException;
import http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import static http.HttpVersion.HTTP11;
import static http.response.HttpResponse.HEADER_RESPONSE_LOCATION;
import static http.response.ResponseStatus.FOUND;
import static http.response.ResponseStatus.OK;

public class HttpResponseGenerator {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static final String HTTP_VERSION = "HttpVersion";
    public static final String STATUS_CODE = "StatusCode";
    public static final String REASON_PHRASE = "ReasonPhrase";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_CONTENT_LENGTH = "Content-Length";

    public static HttpResponse response200Header(String path, int bodyLength) {
        try {
            String mimeType = Files.probeContentType(Paths.get(path));
            StatusLine statusLine = new StatusLine(getStatusLines(OK));
            Header header = new Header(addHeaderElement(bodyLength, mimeType));

            return new HttpResponse(statusLine, header);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        throw new FailResponseException();
    }

    public static HttpResponse responseLoginSuccess(String path, int bodyLength, String sessionId) {
        try {
            String mimeType = Files.probeContentType(Paths.get(path));
            StatusLine statusLine = new StatusLine(getStatusLines(OK));
			
            Header header = new Header(addHeaderElementWithLoginFlag(bodyLength, mimeType, sessionId));
            return new HttpResponse(statusLine, header);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        throw new FailResponseException();
    }

    private static String getCookieValue(String sessionId) {
        return String.format("logined=true; jsessionId=%s; %s=/\r\n", "Path", sessionId);
    }

    private static Map<String, String> addHeaderElementWithLoginFlag(int bodyLength, String mimeType, String sessionId) {
        Map<String, String> headerElement = addHeaderElement(bodyLength, mimeType);
        headerElement.put("Set-Cookie", getCookieValue(sessionId));
        return headerElement;
    }

    private static Map<String, String> addHeaderElement(int bodyLength, String mimeType) {
        Map<String, String> headerElement = new LinkedHashMap<>();
        headerElement.put(HEADER_CONTENT_TYPE, mimeType + ";charset=utf-8\r\n");
        headerElement.put(HEADER_CONTENT_LENGTH, bodyLength + "\r\n");

        return headerElement;
    }

    private static Map<String, String> getStatusLines(ResponseStatus ok) {
        Map<String, String> statusLines = new LinkedHashMap<>();
        statusLines.put(HTTP_VERSION, HTTP11.getVersion());
        statusLines.put(STATUS_CODE, ok.getCode());
        statusLines.put(REASON_PHRASE, ok.getPhrase());
        return statusLines;
    }

    public static HttpResponse response302Header(String location) {
        StatusLine statusLine = new StatusLine(getStatusLines(FOUND));

        Map<String, String> headerElement = new LinkedHashMap<>();
        headerElement.put(HEADER_RESPONSE_LOCATION, location);

        Header header = new Header(headerElement);

        return new HttpResponse(statusLine, header);
    }
}
