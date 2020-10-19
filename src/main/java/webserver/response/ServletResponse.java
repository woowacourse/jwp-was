package webserver.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import webserver.request.RequestHeader;
import webserver.request.ServletRequest;

public class ServletResponse {
    private final HttpStatusLine httpStatusLine;
    private final ResponseHeader headers;
    private final ModelAndView mav;

    public ServletResponse(HttpStatusLine httpStatusLine, ResponseHeader headers, ModelAndView mav) {
        this.httpStatusLine = httpStatusLine;
        this.headers = headers;
        this.mav = mav;
    }

    public static ServletResponse of(StatusCode statusCode, ModelAndView mav, View view, ServletRequest request) {

        return new ServletResponse(new HttpStatusLine(statusCode), ResponseHeader.of(view, request), mav);
    }

    public static ServletResponse of(ModelAndView mav, ServletRequest request) {
        HttpStatusLine httpStatusLine = new HttpStatusLine(request.getProtocolVersion(), StatusCode.of(mav.getView()));
        ResponseHeader headers = ResponseHeader.of(mav.getView(), request);

        return new ServletResponse(httpStatusLine, headers, mav);
    }

    public static ServletResponse of(StatusCode statusCode, ModelAndView mav) {
        HttpStatusLine httpStatusLine = new HttpStatusLine(statusCode);
        ResponseHeader headers = ResponseHeader.emptyHeader();

        return new ServletResponse(httpStatusLine, headers, mav);
    }

    public void sendResponse(DataOutputStream dos) throws IOException {
        View view = mav.getView();
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

    public Model getModel() {
        return mav.getModel();
    }

    public String getAttribute(String key) {
        return mav.getAttribute(key);
    }

    public View getView() {
        return mav.getView();
    }
}
