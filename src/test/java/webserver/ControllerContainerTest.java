package webserver;

import db.DataBase;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerContainerTest {

    @Test
    void user_controller_get요청() throws IOException, URISyntaxException {
        String request = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*";

        InputStream in = new ByteArrayInputStream(request.getBytes());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpRequest httpRequest = new HttpRequest(in);
        HttpResponse httpResponse = new HttpResponse(out);

        ControllerContainer.service(httpRequest, httpResponse);

        assertThat(DataBase.findAll().size()).isEqualTo(1);

    }

    @Test
    void index문서_get요청() throws IOException, URISyntaxException {
        String request = "GET /index.html HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*";

        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = new HttpRequest(in);

        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        HttpResponse testHttpResponse = new HttpResponse(testOut);
        testHttpResponse.addHeader(httpRequest.getPath());
        testHttpResponse.addBody(httpRequest.getPath());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(out);

        ControllerContainer.service(httpRequest, httpResponse);
        assertThat(out.toByteArray()).isEqualTo(testOut.toByteArray());
    }
}