package webserver.http.response;

import webserver.http.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class HttpResponse {
    private final DataOutputStream dos;

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    public void forward(HttpResponseStartLine httpResponseStartLine, HttpHeaders httpResponseHeaders,
                        Body httpResponseBody) throws IOException {
        writeHttpResponseStartLine(httpResponseStartLine);
        writeHttpResponseHeaders(httpResponseHeaders);
        writeHttpResponseBody(httpResponseBody);
    }

    private void writeHttpResponseStartLine(HttpResponseStartLine httpResponseStartLine) throws IOException {
        dos.writeBytes(httpResponseStartLine.toString());
        dos.writeBytes(System.lineSeparator());
    }

    private void writeHttpResponseHeaders(HttpHeaders httpResponseHeaders) throws IOException {
        HttpHeadersState responseHeaderState = httpResponseHeaders.getHttpHeadersState();
        if (HttpHeadersState.EMPTY.equals(responseHeaderState)) {
            dos.writeBytes(System.lineSeparator());
            return;
        }
        List<HttpHeader> httpHeaders = httpResponseHeaders.getHttpHeaders();
        for (HttpHeader httpHeader : httpHeaders) {
            dos.writeBytes(httpHeader.getType() + ": " + httpHeader.getContent());
            dos.writeBytes(System.lineSeparator());
        }
        dos.writeBytes(System.lineSeparator());
    }

    private void writeHttpResponseBody(Body httpResponseBody) throws IOException {
        BodyState bodyState = httpResponseBody.getState();
        if (BodyState.EMPTY.equals(bodyState)) {
            dos.flush();
            return;
        }
        dos.write(httpResponseBody.getContent(), 0, httpResponseBody.getLength());
        dos.flush();
    }
}
