package http.response;

import http.common.HttpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class Response200 extends HttpResponse {

    private static final Logger log = LoggerFactory.getLogger(Response200.class);

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
