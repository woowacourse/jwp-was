package utils;

import webserver.http.HttpSessionManager;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.request.RequestHeader;
import webserver.http.request.RequestLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpRequestParser {
    private static final String BLANK = " ";
    private static final String HEADER_SEPARATOR = ": ";
    private static final String END_OF_REQUEST_HEADER = "";
    private static final int METHOD_INDEX = 0;
    private static final int TARGET_INDEX = 1;
    private static final int VERSION_INDEX = 2;
    private static final int HEADER_KEY = 0;
    private static final int HEADER_VALUE = 1;

    public static HttpRequest parseRequest(InputStreamReader inputStream, HttpSessionManager sessionManager) throws IOException {
        BufferedReader br = new BufferedReader(inputStream);
        RequestLine requestLine = extractRequestLine(br);
        RequestHeader requestHeader = extractRequestHeaders(br);

        if (requestLine.isBodyExists()) {
            String body = IOUtils.readData(br, requestHeader.getContentLength());
            RequestBody requestBody = new RequestBody(body);

            return new HttpRequest(requestLine, requestHeader, requestBody, sessionManager);
        }

        return new HttpRequest(requestLine, requestHeader, sessionManager);
    }


    private static RequestLine extractRequestLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        String[] startLines = line.split(BLANK);
        return new RequestLine(startLines[METHOD_INDEX], startLines[TARGET_INDEX], startLines[VERSION_INDEX]);
    }

    private static RequestHeader extractRequestHeaders(BufferedReader br) throws IOException {
        RequestHeader requestHeader = new RequestHeader();

        while (true) {
            String line = br.readLine();
            if (line.equals(END_OF_REQUEST_HEADER)) {
                break;
            }
            String[] headers = line.split(HEADER_SEPARATOR);
            requestHeader.add(headers[HEADER_KEY], headers[HEADER_VALUE]);
        }

        return requestHeader;
    }
}
