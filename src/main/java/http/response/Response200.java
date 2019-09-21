package http.response;

import http.common.HttpHeader;

import java.io.DataOutputStream;
import java.io.IOException;

public class Response200 extends HttpResponse {

    public Response200(final StatusLine statusLine, final HttpHeader responseHeader, final ResponseBody responseBody) {
        super(statusLine, responseHeader, responseBody);
    }

    @Override
    public void writeResponse(final DataOutputStream dos) {
        try {
            dos.writeBytes(statusLine.getHttpVersion() + " " + statusLine.getCodeAndStatus() + "\r\n");
            dos.writeBytes("Content-Type: " + responseHeader.findHeader("Content-Type") + "\r\n");
            dos.writeBytes("Content-Length: " + responseHeader.findHeader("Content-Length") + "\r\n");
            dos.writeBytes("\r\n");
            responseBody(dos, responseBody.getBody());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


}
