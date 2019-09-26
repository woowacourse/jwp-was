package controller;

import controller.core.AbstractController;
import webserver.http.HttpHeaderField;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.core.ResponseContentType;
import webserver.http.response.core.ResponseStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class ResourceController extends AbstractController {

    private static void send200Response(DataOutputStream dos, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        byte[] body = httpResponse.responseBody(httpRequest.getRequestPath().getFullPath());
        httpResponse.addHeader(HttpHeaderField.CONTENT_LENGTH, String.valueOf(body.length));
        dos.writeBytes(httpResponse.doResponse());
        dos.write(body, 0, body.length);
        dos.flush();
    }

    @Override
    public void service(OutputStream out, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        DataOutputStream dos = new DataOutputStream(out);
        doGet(httpRequest, httpResponse);
        send200Response(dos, httpRequest, httpResponse);
    }

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.addStatus(ResponseStatus.of(200));
        httpResponse.addHeader(HttpHeaderField.CONTENT_TYPE, ResponseContentType.of(httpRequest.getRequestPath()));
    }
}
