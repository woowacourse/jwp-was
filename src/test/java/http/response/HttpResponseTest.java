package http.response;

import http.ContentType;
import http.request.HttpRequest;
import http.response.view.DefaultView;
import http.response.view.RedirectView;
import http.response.view.View;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.*;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    @Test
    void index문서_get요청_response_body확인() throws IOException, URISyntaxException {
        String request = "GET /index.html HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*";
        HttpRequest httpRequest = createRequest(request);
        HttpResponse response = createResponse(new DefaultView(httpRequest.getPath()));

        assertThat(response.getBody()).isEqualTo(FileIoUtils.loadFileFromClasspath(httpRequest.getPath()));
    }

    @Test
    void css문서_get요청_response_body_확인() throws IOException, URISyntaxException {
        String request = "GET /css/styles.css HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*";
        HttpRequest httpRequest = createRequest(request);
        HttpResponse response = createResponse(new DefaultView(httpRequest.getPath()));

        assertThat(response.getBody()).isEqualTo(FileIoUtils.loadFileFromClasspath(httpRequest.getPath()));
    }

    @Test
    void js문서_get요청_response_body_확인() throws IOException, URISyntaxException {
        String request = "GET /js/bootstrap.min.js HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*";
        HttpRequest httpRequest = createRequest(request);
        HttpResponse response = createResponse(new DefaultView(httpRequest.getPath()));

        assertThat(response.getBody()).isEqualTo(FileIoUtils.loadFileFromClasspath(httpRequest.getPath()));
    }

    @Test
    void css문서_get요청_response_header_확인() throws IOException, URISyntaxException {
        String request = "GET /css/styles.css HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*";
        HttpRequest httpRequest = createRequest(request);
        HttpResponse response = createResponse(new DefaultView(httpRequest.getPath()));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        set200Header(httpRequest, byteArrayOutputStream);
        assertThat(response.getHeader().getBytes()).isEqualTo(byteArrayOutputStream.toByteArray());
    }

    @Test
    void js문서_get요청_response_header_확인() throws IOException, URISyntaxException {
        String request = "GET /css/styles.css HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*";
        HttpRequest httpRequest = createRequest(request);
        HttpResponse response = createResponse(new DefaultView(httpRequest.getPath()));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        set200Header(httpRequest, byteArrayOutputStream);

        assertThat(response.getHeader().getBytes()).isEqualTo(byteArrayOutputStream.toByteArray());
    }

    private void set200Header(HttpRequest httpRequest, ByteArrayOutputStream byteArrayOutputStream) throws IOException, URISyntaxException {
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeBytes("HTTP/1.1 200 OK\r\n");
        dataOutputStream.writeBytes("Content-Type: " + ContentType.valueByPath(httpRequest.getPath()).getContents() + ";charset=utf-8\r\n");
        dataOutputStream.writeBytes("Content-Length: " + FileIoUtils.loadFileFromClasspath(httpRequest.getPath()).length + "\r\n");
        dataOutputStream.writeBytes("\r\n");
    }

    private HttpRequest createRequest(String request) throws IOException {
        InputStream in = new ByteArrayInputStream(request.getBytes());
        HttpRequest httpRequest = new HttpRequest(in);
        return httpRequest;
    }

    @Test
    void redirect_response_header_확인() throws IOException {
        String path = "index.html";
        HttpResponse response = createResponse(new RedirectView(path));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        setRedirectHeader(path, byteArrayOutputStream);
        assertThat(response.getHeader().getBytes()).isEqualTo(byteArrayOutputStream.toByteArray());
    }

    private void setRedirectHeader(String path, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeBytes("HTTP/1.1 302 Found\r\n");
        dataOutputStream.writeBytes("Location: " + path + "\r\n");
        dataOutputStream.writeBytes("\r\n");
    }

    private HttpResponse createResponse(View view) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpResponse response = new HttpResponse(out);
        response.render(view);

        return response;
    }
}