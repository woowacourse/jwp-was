package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import utils.HttpRequestUtils;
import utils.IOUtils;

public class HttpRequest {

    private final HttpRequestLine httpRequestLine;
    private final HttpRequestParam httpRequestParam;
    private final HttpRequestHeader httpRequestHeader;
    private final HttpRequestBody httpRequestBody;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        HttpRequestLine httpRequestLine = getHttpRequestLine(bufferedReader);
        HttpRequestParam httpRequestParam = getHttpRequestParams(httpRequestLine);
        HttpRequestHeader httpRequestHeader = getHttpRequestHeader(bufferedReader);
        HttpRequestBody httpRequestBody = getHttpRequestBody(bufferedReader, httpRequestHeader.getContentLength());

        this.httpRequestLine = httpRequestLine;
        this.httpRequestParam = httpRequestParam;
        this.httpRequestHeader = httpRequestHeader;
        this.httpRequestBody = httpRequestBody;
    }

    private HttpRequestLine getHttpRequestLine(BufferedReader bufferedReader) throws IOException {
        String firstLine = bufferedReader.readLine();
        return new HttpRequestLine(firstLine);
    }

    private HttpRequestParam getHttpRequestParams(HttpRequestLine httpRequestLine) {
        Map<String, String> params = HttpRequestUtils.parse(httpRequestLine.getUrl());
        return new HttpRequestParam(params);
    }

    private HttpRequestHeader getHttpRequestHeader(BufferedReader bufferedReader) throws IOException {
        List<String> lines = new ArrayList<>();
        String line = bufferedReader.readLine();
        while (!"".equals(line) && line != null) {
            lines.add(line);
            line = bufferedReader.readLine();
        }
        Map<String, String> headers = HttpRequestUtils.parse(lines);
        return new HttpRequestHeader(headers);
    }

    private HttpRequestBody getHttpRequestBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        String bodyData = IOUtils.readData(bufferedReader, contentLength);
        Map<String, String> body = HttpRequestUtils.parse(bodyData);
        return new HttpRequestBody(body);
    }

    public HttpRequestMethod getMethod() {
        return httpRequestLine.getMethod();
    }

    public String getPath() {
        return httpRequestLine.getPath();
    }

    public String getHeader(String header) {
        return httpRequestHeader.get(header);
    }

    public String getQueryParameter(String parameter) {
        return httpRequestParam.get(parameter);
    }

    public String getBodyParameter(String parameter) {
        return httpRequestBody.get(parameter);
    }

    @Override
    public String toString() {
        return "HttpRequest{" + "\n" +
                "httpRequestLine=" + httpRequestLine + "\n" +
                ", httpRequestParam=" + httpRequestParam + "\n" +
                ", httpRequestHeader=" + httpRequestHeader + "\n" +
                ", httpRequestBody=" + httpRequestBody + "\n" +
                '}';
    }
}
