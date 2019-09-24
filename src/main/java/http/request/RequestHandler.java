package http.request;

import http.common.Parameters;
import http.utils.HttpUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler {
    private static final String CONTENT_LENGTH_KEY = "Content-Length";

    private final BufferedReader br;

    public RequestHandler(BufferedReader br) {
        this.br = br;
    }

    public HttpRequest create() throws IOException {
        RequestLine requestLine = readRequestLine();
        RequestHeader requestHeader = readRequestHeader();
        Parameters parameters = readParameters(requestLine);
        readBody(parameters, requestHeader);

        return HttpRequest.of(requestLine, requestHeader, parameters);
    }

    private RequestLine readRequestLine() throws IOException {
        String startLine = br.readLine();
        return RequestLine.of(startLine);
    }

    private RequestHeader readRequestHeader() throws IOException {
        List<String> header = new ArrayList<>();
        String line;
        while (!(line = br.readLine()).isEmpty()) {
            header.add(line);
        }
        return RequestHeader.of(header);
    }

    private Parameters readParameters(RequestLine requestLine) {
        Parameters parameters = new Parameters();
        if (!requestLine.getQueryString().isEmpty()) {
            parameters.addAll(HttpUtils.parseQuery(requestLine.getQueryString()));
        }
        return parameters;
    }

    private void readBody(Parameters parameters, RequestHeader requestHeader) throws IOException {
        String contentLength = requestHeader.getHeader(CONTENT_LENGTH_KEY);
        if (contentLength != null) {
            String params = IOUtils.readData(br, Integer.parseInt(contentLength));
            parameters.addAll(HttpUtils.parseQuery(params));
        }
    }
}
