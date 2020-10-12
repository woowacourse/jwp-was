package web.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import web.HttpHeader;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Objects;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String HTTP_VERSION = "HTTP/1.1";

    private final DataOutputStream dataOutputStream;
    private final HttpHeader httpHeader;
    private ResponseLine responseLine;
    private ResponseBody responseBody;

    public HttpResponse(OutputStream outputStream) {
        dataOutputStream = new DataOutputStream(outputStream);
        httpHeader = new HttpHeader();
    }

    private void addHeader(String key, String value) {
        httpHeader.addHeader(key, value);
    }

    public void ok(String filePath, String contentType) throws IOException, URISyntaxException {
        responseLine = new ResponseLine(Status.OK, HTTP_VERSION);
        addHeader(HttpHeader.CONTENT_TYPE, contentType + ";charset=UTF-8");
        responseBody = new ResponseBody(FileIoUtils.loadFileFromClasspath(filePath));
        write();
    }

    public void found(String location) {
        responseLine = new ResponseLine(Status.FOUND, HTTP_VERSION);
        addHeader(HttpHeader.LOCATION, location);
        write();
    }

    private void write() {
        try {
            responseLine.write(dataOutputStream);
            httpHeader.write(dataOutputStream);
            if (Objects.nonNull(responseBody)) {
                responseBody.write(dataOutputStream);
            }
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
