package http.response;

import http.common.HttpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public abstract class HttpResponse {

    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

    protected StatusLine statusLine;
    protected HttpHeader responseHeader;
    protected ResponseBody responseBody;

    public HttpResponse(final StatusLine statusLine, final HttpHeader responseHeader, final ResponseBody responseBody) {
        this.statusLine = statusLine;
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
    }

    public void putHeader(String name, String value) {
        responseHeader.putHeader(name, value);
    }

    public abstract void writeResponse(final DataOutputStream dos);


    protected void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
