package webserver.domain.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

import utils.IOUtils;
import webserver.domain.Header;

public class HttpRequest {
    private final RequestLine requestLine;
    private final Header header;
    private final String body;

    private HttpRequest(RequestLine requestLine, Header header, String body) {
        this.requestLine = requestLine;
        this.header = header;
        this.body = body;
    }

    public static HttpRequest of(InputStream inputStream) throws IOException {
        // TODO: 2020/09/25 requestLine, header, body의 생성 책임을 각각에게 넘기기
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        RequestLine requestLine = RequestLine.of(bufferedReader);

        Header header = Header.of(bufferedReader);

        String contentLength = header.getContentLength();
        if (Objects.isNull(contentLength)) {
            return new HttpRequest(requestLine, header, "");
        }
        String body = IOUtils.readData(bufferedReader, Integer.parseInt(contentLength));

        return new HttpRequest(requestLine, header, body);
    }

    public String getPath() {
        if (requestLine.isTemplatesResource()) {
            return String.format("./templates%s", requestLine.getPath());
        }

        if (requestLine.isStaticResource()) {
            return String.format("./static%s", requestLine.getPath());
        }
        // TODO: 2020/09/25 HttpRequest는 프로토콜 구현에 해당하고 해당 로직은 비즈니스 로직에 해당합니다!
        // 둘 사이의 의존성을 제거해보아요

        return requestLine.getPath();
    }

    public Map<String, String> getParameters() {
        return requestLine.getParameters();
    }

    public String getBody() {
        return body;
    }

    public boolean isForStaticContent() {
        return requestLine.isTemplatesResource() || requestLine.isStaticResource();
    }

    public boolean isForDynamicContent() {
        return !isForStaticContent();
    }

    public boolean isGet() {
        return requestLine.getMethod() == Method.GET;
    }

    public boolean isPost() {
        return requestLine.getMethod() == Method.POST;
    }
}
