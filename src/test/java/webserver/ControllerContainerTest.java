package webserver;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import org.junit.jupiter.api.Test;
import view.DefaultView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerContainerTest {

    @Test
    void user_controller_POST요청() throws IOException, URISyntaxException {
        String request = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 93\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

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
        testHttpResponse.write(new DefaultView(httpRequest.getPath()));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(out);

        ControllerContainer.service(httpRequest, httpResponse);
        assertThat(out.toByteArray()).isEqualTo(testOut.toByteArray());
    }
}