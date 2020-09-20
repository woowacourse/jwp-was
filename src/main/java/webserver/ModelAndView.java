package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class ModelAndView {
    private final HttpStatusLine httpStatusLine;
    private final ServletResponse servletResponse;
    private final View view;

    private ModelAndView(final HttpStatusLine httpStatusLine, final ServletResponse servletResponse, final View view) {
        this.httpStatusLine = httpStatusLine;
        this.servletResponse = servletResponse;
        this.view = view;
    }

    public static ModelAndView of(final StatusCode statusCode, final Map<String, String> response, final String viewName) {
        final HttpStatusLine httpStatusLine = new HttpStatusLine(statusCode);
        final ServletResponse servletResponse = new ServletResponse(response);
        final View view = View.of(viewName);

        return new ModelAndView(httpStatusLine, servletResponse, view);
    }

    public static ModelAndView of(final String protocolVersion, final StatusCode statusCode, final Map<String, String> response, final String viewName) {
        final HttpStatusLine httpStatusLine = new HttpStatusLine(protocolVersion, statusCode);
        final ServletResponse servletResponse = new ServletResponse(response);
        final View view = View.of(viewName);

        return new ModelAndView(httpStatusLine, servletResponse, view);
    }

    public void sendResponse(DataOutputStream dos) throws IOException {
        dos.writeBytes(String.format("%s %d %s \r\n", httpStatusLine.getProtocolVersion(),
            httpStatusLine.getStatusCode().getStatusCode(), httpStatusLine.getStatusCode().name()));

        for (Map.Entry<String, String> entry : servletResponse.getHeaders().entrySet()) {
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

    public ServletResponse getResponse() {
        return servletResponse;
    }

    public HttpStatusLine getHttpStatusLine() {
        return httpStatusLine;
    }

    public ServletResponse getServletResponse() {
        return servletResponse;
    }

    public View getView() {
        return view;
    }

    public void setHttpProtocolVersion(final String httpProtocolVersion) {
        httpStatusLine.setHttpProtocolVersion(httpProtocolVersion);
    }
}
