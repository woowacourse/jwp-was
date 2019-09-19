package http.request;

import http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
    private static final String NEW_LINE = "\n";

    private HttpRequestLine httpRequestLine;
    private HttpRequestHeader httpRequestHeader;
    private HttpRequestBody httpRequestBody;

    public HttpRequest(BufferedReader bufferedReader) throws IOException {
        makeRequestLine(bufferedReader);
        makeRequestHeader(bufferedReader);
        makeHttpBody(bufferedReader);
    }

    private void makeRequestLine(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        this.httpRequestLine = new HttpRequestLine(line);
        log.debug("Request Line: {}", httpRequestLine);
    }

    private void makeRequestHeader(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();

        StringBuilder stringBuilder = new StringBuilder();

        while (!"".equals(line)) {
            stringBuilder.append(line).append(NEW_LINE);
            line = bufferedReader.readLine();
        }

        this.httpRequestHeader = new HttpRequestHeader(stringBuilder.toString());
        log.debug("Header: {}", stringBuilder.toString());
    }

    private void makeHttpBody(BufferedReader bufferedReader) throws IOException {
        if (httpRequestHeader.getContentLength() != 0) {
            this.httpRequestBody = new HttpRequestBody(IOUtils.readData(bufferedReader, httpRequestHeader.getContentLength()).getBytes());
            log.debug("Request Body: {}", httpRequestBody);
        }
    }

    public byte[] getHttpRequestBody() {
        return httpRequestBody.getBody();
    }

    public HttpMethod getMethod() {
        return httpRequestLine.getMethod();
    }

    public String getUri() {
        return httpRequestLine.getUri();
    }
}
