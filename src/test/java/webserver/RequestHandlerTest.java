package webserver;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.Socket;
import java.net.URISyntaxException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import utils.FileIoUtils;
import webserver.domain.request.HttpRequest;
import webserver.domain.request.RequestLine;
import webserver.domain.response.HttpResponse;

class RequestHandlerTest {
    private static final String lineSeparator = System.getProperty("line.separator");

    @DisplayName("정적 컨텐츠 처리에 대한 요청에 응답한다.")
    @Test
    void controlRequestAndResponse_whenRequestIsForStaticContent() throws
        URISyntaxException,
        InstantiationException,
        IllegalAccessException,
        IOException {
        RequestHandler requestHandler = new RequestHandler(new Socket());
        RequestLine requestLine = RequestLine.of("GET /index.html HTTP/1.1");
        String header = "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36\n"
            + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n"
            + "Sec-Fetch-Site: none\n"
            + "Sec-Fetch-Mode: navigate\n"
            + "Sec-Fetch-User: ?1\n"
            + "Sec-Fetch-Dest: document\n"
            + "Accept-Encoding: gzip, deflate, br\n"
            + "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,ja;q=0.6,vi;q=0.5,la;q=0.4\n"
            + "Cookie: Idea-3be1aa82=33bac591-b163-42cb-9b63-333572a05b11";
        HttpRequest httpRequest = new HttpRequest(requestLine, header, "");
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        HttpResponse httpResponse = requestHandler.controlRequestAndResponse(httpRequest);

        assertThat(httpResponse.getStatusLine().getValue()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(httpResponse.getHeader()).isEqualTo("Content-Type: text/html;charset=utf-8" + lineSeparator +
            "Content-Length: 6902" + lineSeparator);
        assertThat(httpResponse.getBody()).isEqualTo(body);
    }

    @DisplayName("동적 컨텐츠 처리에 대한 요청에 응답한다.")
    @Test
    void controlRequestAndResponse_whenRequestIsForDynamicContent() throws
        URISyntaxException,
        InstantiationException,
        IllegalAccessException,
        IOException {
        RequestHandler requestHandler = new RequestHandler(new Socket());
        RequestLine requestLine = RequestLine.of("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1");
        String header = "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*";
        HttpRequest httpRequest = new HttpRequest(requestLine, header, "");
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        HttpResponse httpResponse = requestHandler.controlRequestAndResponse(httpRequest);

        assertThat(httpResponse.getStatusLine().getValue()).isEqualTo("HTTP/1.1 302 Found");
        assertThat(httpResponse.getHeader()).isEqualTo("Content-Type: text/html;charset=utf-8" + lineSeparator +
            "Content-Length: 6902" + lineSeparator);
        assertThat(httpResponse.getBody()).isEqualTo(body);
    }
}