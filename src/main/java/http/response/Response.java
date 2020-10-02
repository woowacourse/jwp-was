package http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.ContentType;
import http.HttpHeader;
import utils.Directory;
import utils.FileIoUtils;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(ResponseEntity.class);

    private DataOutputStream dataOutputStream;
    private StatusLine statusLine;
    private HttpHeader header;
    private ResponseBody body;

    public Response(OutputStream out) {
        dataOutputStream = new DataOutputStream(out);
        header = new HttpHeader();
    }

    public void setHeader(String key, String value) {
        header.setHeader(key, value);
    }

    public void ok(String path) throws IOException, URISyntaxException {
        statusLine = new StatusLine("HTTP/1.1", Status.OK);
        body = setResponseBody(path);
        setHeader("Content-Length", String.valueOf(body.getContentLength()));
        write();
    }

    private ResponseBody setResponseBody(String path) throws IOException, URISyntaxException {
        if (header.getContentType().contains(ContentType.HTML.getContentType())) {
            return new ResponseBody(FileIoUtils.loadFileFromClasspath(Directory.TEMPLATES.getDirectory() + path));
        }
        return new ResponseBody(FileIoUtils.loadFileFromClasspath(Directory.STATIC.getDirectory() + path));
    }

    private void write() {
        try {
            statusLine.write(dataOutputStream);
            header.write(dataOutputStream);
            body.write(dataOutputStream);
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
