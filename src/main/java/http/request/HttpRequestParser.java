package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.IOUtils;
import webserver.RequestHandler;

public class HttpRequestParser {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String BLANK = " ";
    private static final String COLON = ":";

    public static HttpRequest parse(BufferedReader br) throws IOException {
        HttpRequestLine httpRequestLine = parseRequestLine(br);
        HttpRequestHeader httpRequestHeader = parseRequestHeader(br);
        HttpRequestBody httpRequestBody = parseRequestBody(br, httpRequestHeader);

        return new HttpRequest(httpRequestLine, httpRequestHeader, httpRequestBody);
    }

    private static HttpRequestLine parseRequestLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        String[] lines = line.split(BLANK);
        HttpRequestLine httpRequestLine = new HttpRequestLine(lines[0], new HttpRequestUrl(lines[1]), lines[2]);
        logger.debug("request line : {}", line);
        return httpRequestLine;
    }

    private static HttpRequestHeader parseRequestHeader(BufferedReader br) throws IOException {
        Map<String, String> httpRequestHeaderCache = new HashMap<>();
        String[] headers;
        String line = br.readLine();
        while (!line.equals("")) {
            headers = line.split(COLON + BLANK);
            httpRequestHeaderCache.put(headers[0], headers[1]);
            logger.debug("request header : {}", line);
            line = br.readLine();
        }
        return new HttpRequestHeader(httpRequestHeaderCache);
    }

    private static HttpRequestBody parseRequestBody(BufferedReader br, HttpRequestHeader httpRequestHeader) throws IOException {
        if (br.ready()) {
            int contentLength = Integer.parseInt(httpRequestHeader.getValue("Content-Length"));
            String data = IOUtils.readData(br, contentLength);
            logger.debug("request body : {}", data);
            return new HttpRequestBody(data);
        }
        return HttpRequestBody.emptyBody();
    }
}
