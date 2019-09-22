package http.utils;

import http.request.Request;
import http.request.RequestBody;
import http.request.RequestHeader;
import http.request.RequestLine;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestFactory {
    private static final String DELIMITER_OF_REQUEST = "\n";

    public static Request makeRequest(InputStream in) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(inputStreamReader);

        String[] requestData = IOUtils.parseData(br).split(DELIMITER_OF_REQUEST, 2);
        RequestLine requestLine = new RequestLine(requestData[0]);
        RequestHeader requestHeader = new RequestHeader(requestData[1]);

        if ("POST".equals(requestHeader.get("method"))) {
            String body = IOUtils.readData(br, Integer.parseInt(requestHeader.get("content-length")));
            RequestBody requestBody = new RequestBody(body);
            return new Request(requestLine, requestHeader, requestBody);
        }

        return new Request(requestLine, requestHeader);
    }
}
