package http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.StaticFile;
import http.exceptions.FailToBuildResponse;
import utils.FileIoUtils;
import view.ViewResolver;

public class ResponseEntity {

    public static final Logger logger = LoggerFactory.getLogger(ResponseEntity.class);
    private static final String lineSeparator = System.lineSeparator();
    private static final String HEADER_DELIMITER = ": ";
    private static final String SPACE = " ";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private final ViewResolver viewResolver;

    public ResponseEntity(ViewResolver viewResolver) {
        this.viewResolver = viewResolver;
    }

    public void build(HttpResponse httpResponse) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(httpResponse.getOutputStream());
            responseStatus(httpResponse, dataOutputStream);
            responseHeader(httpResponse, dataOutputStream);
            responseBody(httpResponse, dataOutputStream);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
            throw new FailToBuildResponse(e.getMessage());
        }
    }

    private void responseStatus(HttpResponse httpResponse, DataOutputStream dataOutputStream) throws
        IOException {
        dataOutputStream.writeBytes(
            httpResponse.getVersion().getVersion() + SPACE + httpResponse.getStatus().getMessage() + lineSeparator);
    }

    private void responseHeader(HttpResponse httpResponse, DataOutputStream dataOutputStream) throws
        IOException {
        for (Map.Entry<String, String> headerKeyValue : httpResponse.getHeaders().entrySet()) {
            logger.info("{}", headerKeyValue.getKey() + HEADER_DELIMITER + headerKeyValue.getValue());
            dataOutputStream.writeBytes(
                headerKeyValue.getKey() + HEADER_DELIMITER + headerKeyValue.getValue() + lineSeparator);
        }
    }

    private void responseBody(HttpResponse httpResponse, DataOutputStream dataOutputStream) throws
        IOException,
        URISyntaxException {
        if (!httpResponse.hasResource()) {
            return;
        }

        if (httpResponse.hasModel()) {
            this.viewResolver.render(httpResponse, dataOutputStream);
            return;
        }

        String resource = httpResponse.getResource();
        StaticFile staticFile = StaticFile.findStaticFile(resource);
        byte[] body = FileIoUtils.loadFileFromClasspath(staticFile.getResourcePath() + resource);

        dataOutputStream.writeBytes(CONTENT_TYPE + HEADER_DELIMITER + staticFile.getContentType() + lineSeparator);
        dataOutputStream.writeBytes(CONTENT_LENGTH + HEADER_DELIMITER + body.length + lineSeparator);
        // HEADER 와 BODY 사이릃 한 줄 띄워야 한다.
        dataOutputStream.writeBytes(lineSeparator);
        dataOutputStream.write(body, 0, body.length);
        dataOutputStream.flush();

    }

}
