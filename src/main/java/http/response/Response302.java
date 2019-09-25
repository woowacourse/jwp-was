package http.response;

import http.common.HttpHeader;

import java.io.DataOutputStream;
import java.io.IOException;

public class Response302 extends HttpResponse {

    public static final String BLANK = " ";

    public Response302(final StatusLine statusLine, final HttpHeader responseHeader, final ResponseBody responseBody) {
        super(statusLine, responseHeader, responseBody);
    }

    @Override
    public void writeResponse(final DataOutputStream dos) {
        try {
            dos.writeBytes(statusLine.getHttpVersion() + BLANK + statusLine.getCodeAndStatus() + "\r\n");
            dos.writeBytes("Location: " + responseHeader.findHeader("Location") + "\r\n");

            dos.writeBytes("Set-Cookie: "+responseHeader.findHeader("Set-Cookie")+"\r\n");  // 1번
            dos.writeBytes("Set-Cookie: "+responseCookie.getCookie()+"\r\n");       // 2번
            dos.writeBytes("\r\n");
            responseBody(dos, responseBody.getBody());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
