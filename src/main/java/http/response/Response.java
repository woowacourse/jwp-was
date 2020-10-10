package http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.ContentType;
import http.HttpHeaders;
import http.request.RequestMethod;
import utils.Directory;
import utils.FileIoUtils;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(Response.class);
    private static final String HTTP_1_1 = "HTTP/1.1";

    private DataOutputStream dataOutputStream;
    private StatusLine statusLine;
    private HttpHeaders headers;
    private ResponseBody body;

    public Response(OutputStream out) {
        dataOutputStream = new DataOutputStream(out);
        headers = new HttpHeaders();
    }

    public void setHeader(String key, String value) {
        headers.setHeader(key, value);
    }

    public void ok(String path, String contentType) throws IOException, URISyntaxException {
        statusLine = new StatusLine(HTTP_1_1, Status.OK);
        setHeader(HttpHeaders.CONTENT_TYPE, contentType + ";charset=UTF-8");
        body = setResponseBody(path);
        setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(body.getContentLength()));
        write();
    }

    public void found(String locationUri) {
        statusLine = new StatusLine(HTTP_1_1, Status.FOUND);
        setHeader(HttpHeaders.LOCATION, locationUri);
        write();
    }

    public void methodNotAllowed(RequestMethod requestMethod) {
        statusLine = new StatusLine(HTTP_1_1, Status.METHOD_NOT_ALLOWED);
        body = new ResponseBody(String.format("Request method %s not supported", requestMethod).getBytes());
        write();
    }

    private ResponseBody setResponseBody(String path) throws IOException, URISyntaxException {
        if (ContentType.HTML.isHtml(headers.getContentType())) {
            return new ResponseBody(FileIoUtils.loadFileFromClasspath(Directory.TEMPLATES.getDirectory() + path));
        }
        return new ResponseBody(FileIoUtils.loadFileFromClasspath(Directory.STATIC.getDirectory() + path));
    }

    private void write() {
        try {
            statusLine.write(dataOutputStream);
            headers.write(dataOutputStream);
            if (Objects.nonNull(body)) {
                body.write(dataOutputStream);
            }
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
