package webserver.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import webserver.request.ServletRequest;

public class ServletResponse {
    private final HttpStatusLine httpStatusLine;
    private final ResponseHeader headers;
    private final ResponseBody body;
    private final View view;

    private ServletResponse(HttpStatusLine httpStatusLine, ResponseHeader headers, ResponseBody body, View view) {
        this.httpStatusLine = httpStatusLine;
        this.headers = headers;
        this.body = body;
        this.view = view;
    }

    public static ServletResponse of(ModelAndView mav, View view) {
        HttpStatusLine httpStatusLine = new HttpStatusLine(mav.getStatusCode());
        ResponseHeader headers = mav.getHeaders();
        ResponseBody body = mav.getBody();

        return new ServletResponse(httpStatusLine, headers, body, view);
    }

    public static ServletResponse of(ModelAndView mav, ServletRequest request, View view) {
        HttpStatusLine httpStatusLine = new HttpStatusLine(request.getProtocolVersion(), mav.getStatusCode());
        ResponseHeader headers = mav.getHeaders();
        ResponseBody body = mav.getBody();

        return new ServletResponse(httpStatusLine, headers, body, view);
    }

    public void sendResponse(DataOutputStream dos) throws IOException {
        dos.writeBytes(String.format("%s %d %s \r\n", httpStatusLine.getProtocolVersion(),
            httpStatusLine.getStatusCode().getStatusCode(), httpStatusLine.getStatusCode().name()));

        for (Map.Entry<String, String> entry : headers.getHeaders().entrySet()) {
            dos.writeBytes(String.format("%s: %s \r\n", entry.getKey(), entry.getValue()));
        }

        dos.writeBytes("Content-Length: " + view.getLength() + " \r\n");
        dos.writeBytes("\r\n");
        responseBody(dos, view.getView());
    }

    private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

    public HttpStatusLine getHttpStatusLine() {
        return httpStatusLine;
    }

    public StatusCode getStatusCode() {
        return httpStatusLine.getStatusCode();
    }

    public String getProtocolVersion() {
        return httpStatusLine.getProtocolVersion();
    }

    public ResponseHeader getHeaders() {
        return headers;
    }

    public String getHeader(String key) {
        return headers.getHeader(key);
    }

    public ResponseBody getBody() {
        return body;
    }

    public String getAttribute(String key) {
        return body.getAttribute(key);
    }

    public View getView() {
        return view;
    }
}
