package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import utils.FileIoUtils;
import utils.RequestUtils;

public class HttpResponse {
    public static final String TEMPLATE_PATH = "./templates";
    public static final String STATIC_PATH = "./static";
    public static final String HTML = "html";
    private static final int OFFSET = 0;

    private final HttpStatus httpStatus;
    private final DataOutputStream dos;
    private final Map<String, String> header;
    private byte[] body;

    public HttpResponse(HttpStatus httpStatus, OutputStream out) {
        this.httpStatus = httpStatus;
        this.dos = new DataOutputStream(out);
        this.header = new HashMap<>();
    }

    public void addHeader(String key, String value) {
        header.put(key, value);
    }

    public void forward(String path) throws IOException, URISyntaxException {
        body = loadStaticFile(path);
        writeWithLineSeparator("HTTP 1.1 " + httpStatus.getNumber() + " " + httpStatus.name());
        for (Map.Entry<String, String> entry : header.entrySet()) {
            writeWithLineSeparator(entry.getKey() + ": " + entry.getValue());
        }
        dos.writeBytes(System.lineSeparator());
        dos.write(body, OFFSET, body.length);
        dos.flush();
    }

    private byte[] loadStaticFile(String path) throws IOException, URISyntaxException {
        if (HTML.equals(RequestUtils.extractExtension(path))) {
            return FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + path);
        }
        return FileIoUtils.loadFileFromClasspath(STATIC_PATH + path);
    }

    private void writeWithLineSeparator(String contents) throws IOException {
        dos.writeBytes(String.format("%s%s", contents, System.lineSeparator()));
    }
}
