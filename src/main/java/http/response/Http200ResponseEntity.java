package http.response;

import http.HttpHeaders;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static http.HttpVersion.DEFAULT_VERSION;
import static http.response.HttpStatus.OK;

public class Http200ResponseEntity implements HttpResponseEntity {
    private static final String STYLESHEET = "css";
    private static final String DEFAULT_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

    private String viewTemplatePath;

    public Http200ResponseEntity(String viewTemplatePath) {
        this.viewTemplatePath = viewTemplatePath;
    }

    @Override
    public HttpResponse makeResponse() throws IOException, URISyntaxException {
        String contentType = getMediaType(viewTemplatePath);
        byte[] body = getBody(viewTemplatePath);

        HttpStatusLine statusLine = new HttpStatusLine(DEFAULT_VERSION, OK);
        HttpHeaders headers = new HttpHeaders();
        headers.put("Content-Type", contentType);
        headers.put("Content-Length", Integer.toString(body.length));
        return new HttpResponse(statusLine, headers, body);
    }

    private String getMediaType(String path) {
        String fileName = path.substring(path.lastIndexOf('/') + 1);
        String[] splicedFileName = fileName.split("\\.");
        String extension = splicedFileName[splicedFileName.length - 1];
        return extension.equals(STYLESHEET)
                ? "text/css"
                : "text/html";
    }

    private byte[] getBody(String path) throws IOException, URISyntaxException {
        byte[] body;
        try {
            body = FileIoUtils.loadFileFromClasspath(DEFAULT_PATH + path);
        } catch (NullPointerException e) {
            body = FileIoUtils.loadFileFromClasspath(STATIC_PATH + path);
        }
        return body;
    }
}
