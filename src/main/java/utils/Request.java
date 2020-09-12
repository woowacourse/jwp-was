package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Request {

    private Map<String, Object> headers;
    private Map<String, String> params;
    private AcceptType type;
    private String body;

    public Request(List<String> requestHeaders, BufferedReader bufferedReader) throws IOException {
        headers = new HashMap<>();
        params = new HashMap<>();
        parseRequestFirstLine(requestHeaders.remove(0));
        parseHeaders(requestHeaders);
        parseBody(bufferedReader);
    }

    private void parseBody(BufferedReader bufferedReader) throws IOException {

        int contentLength = Optional.ofNullable(getHeader("Content-Length"))
            .map(x -> (String) x)
            .map(Integer::parseInt)
            .orElse(0);

        this.body = IOUtils.readData(bufferedReader, contentLength);
    }

    private void parseRequestFirstLine(String headerFirstLine) {
        String[] splitHeaderFirstLine = headerFirstLine.split(" ");

        headers.put("method", HttpMethod.of(splitHeaderFirstLine[0]));
        headers.put("filePath", splitHeaderFirstLine[1].split("\\?")[0]);
        headers.put("httpVersion", splitHeaderFirstLine[2]);

        parseParams(splitHeaderFirstLine[1]);
    }

    private void parseHeaders(List<String> requestHeaders) {
        requestHeaders.stream()
            .map(line -> line.split(": "))
            .forEach(pair -> headers.put(pair[0], pair[1]));
        parseAcceptType();
    }

    private void parseAcceptType() {
        String filePath = (String) getHeader("filePath");
        String fileExtension = IOUtils.extractExtension(filePath);
        this.type = AcceptType.of(fileExtension);
    }

    private void parseParams(String filePath) {
        if (!filePath.contains("?")) {
            return;
        }
        String paramsSequence = filePath.split("\\?")[1];

        parseParam(paramsSequence);
    }

    private void parseParam(String paramsSequence) {
        IOUtils.parseParamsSequence(paramsSequence)
            .forEach(pair -> params.put(pair[0], pair[1]));
    }

    public boolean isGetRequest() {
        HttpMethod method = (HttpMethod) headers.get("method");
        return method.isGet();
    }

    public boolean isPostRequest() {
        HttpMethod method = (HttpMethod) headers.get("method");
        return method.isPost();
    }

    public Object getHeader(String name) {
        return headers.get(name);
    }

    public AcceptType getType() {
        return type;
    }

    public String getBody() {
        return body;
    }
}
