package http.response.core;

import http.request.HttpRequest;
import http.request.core.RequestVersion;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public abstract class Response {
    protected HttpRequest httpRequest;

    public Response(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public abstract void doResponse(DataOutputStream dos) throws IOException, URISyntaxException;

    protected void responseHeader(DataOutputStream dos, ResponseStatus responseStatus,
                                  String headerLine) throws IOException, URISyntaxException {
        RequestVersion version = httpRequest.getRequestVersion();

        dos.writeBytes(version.getVersion() + " " + responseStatus.getResponseStatusHeader());
        dos.writeBytes(headerLine);
        dos = !headerLine.contains("Location: ") ? getBody(dos) : dos;
        dos.flush();
    }

    private DataOutputStream getBody(DataOutputStream dos) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.getRequestPath().getFullPath());
        dos.writeBytes("Content-Length: " + body.length + "\r\n");
        dos.writeBytes("\r\n");
        dos.write(body, 0, body.length);
        return dos;
    }
}
