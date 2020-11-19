package webserver.http.response;

import utils.FileIoUtils;
import webserver.http.ContentType;
import webserver.http.HttpHeaders;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class HttpResponse2 {
    private DataOutputStream dos;
    private HttpResponseStartLine httpResponseStartLine;
    private HttpHeaders httpHeaders;
    private HttpResponseBody httpResponseBody;

    public HttpResponse2(OutputStream out) {
        this.dos = new DataOutputStream(out);
        this.httpResponseStartLine = HttpResponseStartLine.defaultStartLine();
        this.httpHeaders = HttpHeaders.emptyHeaders();
        this.httpResponseBody = HttpResponseBody.emptyResponseBody();
    }

    public HttpResponse2() {
        this.httpResponseStartLine = HttpResponseStartLine.defaultStartLine();
        this.httpHeaders = HttpHeaders.emptyHeaders();
        this.httpResponseBody = HttpResponseBody.emptyResponseBody();
    }


    public void forward(String path) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(path);
        ContentType contentType = ContentType.from(path);
        
    }

    private void response200Header(byte[] body) {

    }

    public void sendRedirect(String s) {

    }

    public void addHeader(String s, String s1) {

    }
}
