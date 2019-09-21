package webserver.request;

import java.util.List;

public class HttpRequest {
    private static final String BLANK = "";
    private static final String COLON = ":";

    private RequestFirstLine requestFirstLine;
    private RequestHeaders requestHeaders;
    private RequestData requestParams;
    private RequestData requestBody;

    public HttpRequest(List<String> lines) {
        this.requestFirstLine = new RequestFirstLine(lines.get(0));
        this.requestParams = getRequestParams(requestFirstLine);
        this.requestHeaders = new RequestHeaders();
        this.requestBody = new RequestData();
        setRequestHeaderAndBody(lines);
    }

    private static RequestData getRequestParams(RequestFirstLine requestFirstLine) {
        RequestData requestParams = new RequestData();

        if (requestFirstLine.hasParams()) {
            String search = requestFirstLine.getSearch();
            requestParams.put(search);
        }
        return requestParams;
    }

    private void setRequestHeaderAndBody(List<String> lines) {
        int index = 0;
        String line;
        while (!BLANK.equals(line = lines.get(++index))) {
            buildRequestHeader(line);
        }

        if (requestHeaders.containsKey("Content-Length")) {
            buildRequestBody(lines.get(index + 1));
        }
    }

    private void buildRequestHeader(String line) {
        String[] splitHeader = line.split(COLON, 2);
        requestHeaders.put(splitHeader[0].trim(), splitHeader[1].trim());
    }

    private void buildRequestBody(String bodyLine) {
        requestBody.put(bodyLine);
    }

    public String getMethod() {
        return requestFirstLine.getMethod();
    }

    public String getHeader(String key) {
        return requestHeaders.get(key);
    }

    public String getParam(String key) {
        if (requestParams.isEmpty()) {
            throw new IllegalArgumentException("Parameter 가 없습니다.");
        }
        return requestParams.get(key);
    }

    public String getBody(String key) {
        return requestBody.get(key);
    }

    public String getPath() {
        return requestFirstLine.getUri();
    }

    public String getVersion() {
        return requestFirstLine.getVersion();
    }
}
