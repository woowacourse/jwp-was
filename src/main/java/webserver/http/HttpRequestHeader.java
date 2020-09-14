package webserver.http;

import utils.RequestPathUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequestHeader {
    private final InputStream inputStream;

    public HttpRequestHeader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getPath() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String requestLine = bufferedReader.readLine();
        return RequestPathUtil.getPathFromRequestLine(requestLine);
    }
}
