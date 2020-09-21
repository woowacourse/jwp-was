package webserver;

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

    public void forward(String path) throws IOException, URISyntaxException {
        statusLine = new StatusLine("HTTP/1.1 200");
        setResponseBody(path);
        responseHeader.setHeader("Content-Type", HttpContentType.findContentType(path));
        responseHeader.setHeader("Content-Length", String.valueOf(responseBody.getContentLength()));
        writeHttpResponse();
    }

    public void sendRedirect(String path) throws IOException, URISyntaxException {
        statusLine = new StatusLine("HTTP/1.1 302");
        setResponseBody(path);
        responseHeader.setHeader("Location", path);
        writeHttpResponse();
    }

    public void addHeader(String key, String value) {
        responseHeader.setHeader(key, value);
    }

    private void setResponseBody(String path) throws IOException, URISyntaxException {
        if (HttpContentType.isStaticFile(path)) {
            responseBody = new ResponseBody(FileIoUtils.loadFileFromClasspath(STATIC_PATH + path));
        } else {
            responseBody = new ResponseBody(FileIoUtils.loadFileFromClasspath(TEMPLATES_PATH + path));
        }
    }

    public void writeHttpResponse() throws IOException {
        statusLine.write(dataOutputStream);
        responseHeader.write(dataOutputStream);
        dataOutputStream.writeBytes(System.lineSeparator());
        responseBody.write(dataOutputStream);
    }
}
