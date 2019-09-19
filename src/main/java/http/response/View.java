package http.response;

import http.HTTP;
import http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public abstract class View {
    final Map<HTTP, String> header = new HashMap<>();
    byte[] body;

    View() {
        this.body = "".getBytes();
    }

    String getHeader(HttpStatus httpStatus) {
        StringBuffer sb = new StringBuffer();
        sb.append(HTTP.VERSION.getPhrase()).append(" ").append(httpStatus.getInfo()).append("\r\n");

        for (HTTP key : HTTP.values()) {
            if (header.containsKey(key)) {
                sb.append(key.getPhrase()).append(": ").append(header.get(key)).append("\r\n");
            }
        }
        sb.append("\r\n");

        return sb.toString();
    }

    public abstract String getHeader();

    public byte[] getBody() {
        return body;
    }
}
