package http.request;

import java.io.BufferedReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.IOUtils;
import webserver.RequestHandler;

public class RequestParser {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String BLANK = " ";
    private static final String COLON = ":";

    public static Request parse(BufferedReader br) throws IOException {
        RequestLine requestLine = parseRequestLine(br);
        RequestHeader requestHeader = parseRequestHeader(br);
        RequestBody requestBody = parseRequestBody(br, requestHeader);

        return new Request(requestLine, requestHeader, requestBody);
    }

    private static RequestLine parseRequestLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        String[] lines = line.split(BLANK);
        RequestLine requestLine = new RequestLine(lines[0], lines[1], lines[2]);
        logger.debug("request line : {}", line);
        return requestLine;
    }

    private static RequestHeader parseRequestHeader(BufferedReader br) throws IOException {
        RequestHeader requestHeader = new RequestHeader();
        String[] headers;
        String line = br.readLine();
        while (!line.equals("")) {
            headers = line.split(COLON + BLANK);
            requestHeader.add(headers[0], headers[1]);
            logger.debug("request header : {}", line);
            line = br.readLine();
        }
        return requestHeader;
    }

    private static RequestBody parseRequestBody(BufferedReader br, RequestHeader requestHeader) throws IOException {
        if (br.ready()) {
            int contentLength = Integer.parseInt(requestHeader.getValue("Content-Length"));
            String data = IOUtils.readData(br, contentLength);
            logger.debug("request body : {}", data);
            return new RequestBody(data);
        }
        return RequestBody.emptyBody();
    }
}
