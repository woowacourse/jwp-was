package http.response;

import enumType.MediaType;
import http.support.HttpStatus;
import utils.ExtractInformationUtils;
import utils.FileIoUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Response {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String LOCATION = "Location";
    private static final String STATUS = "Status";

    private static final String LOCATION_FORMAT = "%s\r\n";
    private static final String CONTENT_TYPE_FORMAT = "%s;charset=utf-8\r\n";
    private static final String CONTENT_LENGTH_FORMAT = "%d\r\n";
    private static final String NEST_LINE = "\r\n";

    private Map<String, String> header;
    private HttpStatus httpStatus;
    private byte[] body;

    public Response() {
        header = new LinkedHashMap<>();
    }

    public void forward(String location, HttpStatus httpStatus) {
        byte[] body = FileIoUtils.loadFileFromClasspath(location);
        String mimeType = MediaType.of(ExtractInformationUtils.extractExtension(location)).getMediaType();
        setBody(body);
        setHttpStatus(httpStatus);
        addHeader(CONTENT_TYPE, String.format(CONTENT_TYPE_FORMAT, mimeType));
        addHeader(CONTENT_LENGTH, body.length + NEST_LINE);
        addHeader(CONTENT_LENGTH, String.format(CONTENT_LENGTH_FORMAT, body.length));
    }

    public void sendRedirect(String location, HttpStatus httpStatus) {
        setHttpStatus(httpStatus);
        addHeader(LOCATION, String.format(LOCATION_FORMAT, location));
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
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

    private void addHeader(String key, String value) {
        header.put(key, value);
    }

    private void setBody(byte[] body) {
        this.body = body;
    }
}
