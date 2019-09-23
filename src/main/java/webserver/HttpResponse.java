package webserver;

import enumType.MediaType;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ExtractInformationUtils;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String STATUS = "Status";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String LOCATION = "Location";
    private static final String HTTP_1_1 = "HTTP/1.1";

    private DataOutputStream dos;
    private HttpStatus httpStatus;
    private Map<String, String> header;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
        header = new LinkedHashMap<>();
    }

    private void forward(String location) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(location);
        header.put("Location", location + "\r\n");
        header.put(CONTENT_TYPE, MediaType.of(ExtractInformationUtils.extractExtension(location)).getMediaType() + ";charset=utf-8\r\n");
        System.out.println( MediaType.of(ExtractInformationUtils.extractExtension(location)));

        a(body);
    }

    private void a(byte[] body) throws IOException {
        header.put(CONTENT_LENGTH, body.length + "\r\n");
        writeResponse();
        responseBody(body);
    }


    private void sendRedirect(String location) throws IOException {
        header.put("Location", location + "\r\n");
        writeResponse();
        dos.flush();
    }

    public void response(String location) {
        try {
            header.put(STATUS, String.format("%s %d %s %s", HTTP_1_1, httpStatus.getStatusCode(), httpStatus.getMessage(), "\r\n"));
            if (httpStatus.getStatusCode() == 302) {
                sendRedirect(location);
            }
            if (httpStatus.getStatusCode() == 200) {
                forward(location);
            }
        } catch (IOException e) {
            e.getMessage();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }


    public void response405() {
        header.put(STATUS, HTTP_1_1 + "405 Method Not Allowed \r\n");
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeResponse() throws IOException {
        for (String key : header.keySet()) {
            if (key.equals(STATUS)) {
                dos.writeBytes(header.get(key));
            }
            dos.writeBytes(key + ": " + header.get(key));
        }

        dos.writeBytes("\r\n");
    }
}
