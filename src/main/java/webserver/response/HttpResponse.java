package webserver.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import utils.FileIoUtils;

public class HttpResponse {

    private static final String TEMPLATES_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

    private final DataOutputStream dataOutputStream;
    private StatusLine statusLine;
    private final ResponseHeader responseHeader;
    private ResponseBody responseBody;

    public HttpResponse(OutputStream outputStream) {
        dataOutputStream = new DataOutputStream(outputStream);
        responseHeader = new ResponseHeader(new HashMap<>());
    }

    public void addHeader(String key, String value) {
        responseHeader.putHeader(key, value);
    }

    public void forward(String path) throws IOException, URISyntaxException {
        statusLine = new StatusLine("HTTP/1.1 200");
        setResponseBody(path);
        responseHeader.putHeader("Content-Type", HttpContentType.findContentType(path));
        responseHeader.putHeader("Content-Length", String.valueOf(responseBody.getContentLength()));
        writeHttpResponse();
    }

    public void sendRedirect(String path) throws IOException, URISyntaxException {
        statusLine = new StatusLine("HTTP/1.1 302");
        setResponseBody(path);
        responseHeader.putHeader("Location", path);
        writeHttpResponse();
    }

    public void methodNotAllowed(String path) throws IOException, URISyntaxException {
        statusLine = new StatusLine("HTTP/1.1 405");
        setResponseBody(path);
        writeHttpResponse();
    }

    public void notImplemented(String path) throws IOException, URISyntaxException {
        statusLine = new StatusLine("HTTP/1.1 501");
        setResponseBody(path);
        writeHttpResponse();
    }

    private void setResponseBody(String path) throws IOException, URISyntaxException {
        if (HttpContentType.isStaticFile(path)) {
            responseBody = new ResponseBody(FileIoUtils.loadFileFromClasspath(STATIC_PATH + path));
            return;
        }
        responseBody = new ResponseBody(FileIoUtils.loadFileFromClasspath(TEMPLATES_PATH + path));
    }

    private void writeHttpResponse() throws IOException {
        statusLine.write(dataOutputStream);
        responseHeader.write(dataOutputStream);
        dataOutputStream.writeBytes(System.lineSeparator());
        responseBody.write(dataOutputStream);
    }
}
