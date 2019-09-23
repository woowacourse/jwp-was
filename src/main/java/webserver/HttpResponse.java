package webserver;

import enumType.MediaType;
import utils.ExtractInformationUtils;
import utils.FileIoUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class HttpResponse {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String LOCATION = "Location";
    private static final String HTTP_1_1 = "HTTP/1.1";
    private static final String STATUS = "Status";

    private HttpStatus httpStatus;
    private Map<String, String> header;
    private byte[] body;

    public HttpResponse() {
        header = new LinkedHashMap<>();
    }

    public void forward(String location, HttpStatus httpStatus) {
        byte[] body = FileIoUtils.loadFileFromClasspath(location);
        String mimeType = MediaType.of(ExtractInformationUtils.extractExtension(location)).getMediaType();
        setBody(body);
        setHttpStatus(httpStatus);
        addHeader(STATUS, String.format("%s %d %s %s", HTTP_1_1, httpStatus.getStatusCode(), httpStatus.getMessage(), "\r\n"));
        addHeader(LOCATION, String.format("%s\r\n", location));
        addHeader(CONTENT_TYPE, String.format("%s;charset=utf-8\r\n", mimeType));
        addHeader(CONTENT_LENGTH, body.length + "\r\n");
        addHeader(CONTENT_LENGTH, String.format("%d\r\n", body.length));
    }

    public void sendRedirect(String location, HttpStatus httpStatus) {
        setHttpStatus(httpStatus);
        setBody(null);
        addHeader(STATUS, String.format("%s %d %s %s", HTTP_1_1, httpStatus.getStatusCode(), httpStatus.getMessage(), "\r\n"));
        addHeader(LOCATION, String.format("%s\r\n", location));
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void response405() {
        header.put(STATUS, HTTP_1_1 + "405 Method Not Allowed \r\n");
    }

    private void addHeader(String key, String value) {
        header.put(key, value);
    }

    private void setBody(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

    public Set<String> getKeySet() {
        return header.keySet();
    }

    public String getHeader(String key) {
        return header.get(key);
    }
}
