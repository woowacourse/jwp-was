package http.utils;

import http.exception.InvalidRequestException;
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

        String[] requestData = validateRequest(IOUtils.parseData(br));
        RequestLine requestLine = new RequestLine(requestData[0]);
        RequestHeader requestHeader = new RequestHeader(requestData[1]);

        if (requestLine.isPost()) {
            return makeRequestWithBody(br, requestLine, requestHeader);
        }

        return new Request(requestLine, requestHeader);
    }

    private static String[] validateRequest(String parsedData) {
        String[] requestData = parsedData.split(DELIMITER_OF_REQUEST, 2);

        if(requestData.length != 2) {
            throw new InvalidRequestException("Invalid Request");
        }

        return requestData;
    }

    private static Request makeRequestWithBody(BufferedReader br, RequestLine requestLine, RequestHeader requestHeader) throws IOException {
        String body = IOUtils.readData(br, Integer.parseInt(requestHeader.get("content-length")));
        RequestBody requestBody = new RequestBody(body);
        return new Request(requestLine, requestHeader, requestBody);
    }
}
