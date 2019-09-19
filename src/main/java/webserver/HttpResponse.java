package webserver;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private Map<String, String> header = new HashMap<>();
    private HttpStatus httpStatus;

    public void forward(String source) {
        setHttpStatus("200");
    }

    public void setHttpStatus(String httpStatus) {

    }

    public void addHeader(String key, String value) {
        header.put(key, value);
    }

    public void setContentType(String contentType) {
        header.put("Content-Type", contentType);
    }
}
