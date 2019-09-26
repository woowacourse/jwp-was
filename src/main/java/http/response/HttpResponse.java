package http.response;

import http.common.Cookies;
import http.common.HttpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;

public class HttpResponse {

    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private DataOutputStream dos;
    private StatusLine statusLine;
    private HttpHeader responseHeader = new HttpHeader();
    private Cookies cookies;
    private ResponseBody responseBody;

    public HttpResponse(final DataOutputStream dos) {
        this.dos = dos;
    }

    public void setStatusLine(final StatusLine statusLine) {
        this.statusLine = statusLine;
    }

    public void setResponseHeader(final HttpHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public void setCookies(final Cookies cookies) {
        this.cookies = cookies;
    }

    public void setResponseBody(final ResponseBody responseBody) {
        this.responseBody = responseBody;
    }

    public void putHeader(String name, String value) {
        responseHeader.putHeader(name, value);
    }

//    public abstract void writeResponse(final DataOutputStream dos);
//
//    protected void responseBody(DataOutputStream dos, byte[] body) {
//        try {
//            dos.writeBytes("\r\n");
//            dos.write(body, 0, body.length);
//            dos.flush();
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
//    }

    public void notFound() {
    }

    public int getBodyLength() {
        return responseBody.getLength();
    }
}
