package webserver.http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.http.HttpVersion;
import webserver.http.exception.NoMatchHeaderFieldException;
import webserver.http.request.core.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestFactory {
    private static final Logger log = LoggerFactory.getLogger(HttpRequestFactory.class);
    private static final String CONTENT_LENGTH = "Content-Length";

    public static HttpRequest create(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        RequestLine requestLine = parseRequestLine(br);
        RequestHeader requestHeader = parseHeader(br);
        RequestData requestData;
        try {
            requestData = Integer.parseInt(requestHeader.getHeadersKey(CONTENT_LENGTH)) > 0 ?
                    parseBody(br, requestHeader) : parseQueryString(requestLine.getRequestPath());
        } catch (NoMatchHeaderFieldException e) {
            requestData = parseQueryString(requestLine.getRequestPath());
        }

        return requestData == null ? new HttpRequest(requestLine, requestHeader) : new HttpRequest(requestLine, requestHeader, requestData);
    }

    private static RequestLine parseRequestLine(BufferedReader br) throws IOException {
        String[] tokens = br.readLine().split(" ");
        log.debug("RequestHeader : {} {} {}", tokens[0], tokens[1], tokens[2]);
        return new RequestLine(
                RequestMethod.of(tokens[0]),
                new RequestPath(RequestPrefixPath.of(tokens[1]), tokens[1]),
                HttpVersion.of(tokens[2])
        );
    }

    private static RequestHeader parseHeader(BufferedReader br) throws IOException {
        List<String> parseHeaderLines = new ArrayList<>();
        String headerLine = br.readLine();

        while (!"".equals(headerLine)) {
            log.debug("RequestHeader : {}", headerLine);
            parseHeaderLines.add(headerLine);
            headerLine = br.readLine();
        }

        return new RequestHeader(parseHeaderLines);
    }

    private static RequestData parseQueryString(RequestPath requestPath) {
        String[] params = requestPath.getFullPath().split("\\?");
        if (params.length == 1) {
            return null;
        }
        return new RequestQueryString(requestPath);
    }

    private static RequestData parseBody(BufferedReader br, RequestHeader headers) throws IOException {
        String contentLength = headers.getHeadersKey(CONTENT_LENGTH);
        String bodyData = IOUtils.readData(br, Integer.parseInt(contentLength));
        return new RequestBody(bodyData);
    }

}
