package http.response.view;

import http.HTTP;
import http.response.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public abstract class View {
    final Map<HTTP, String> header = new HashMap<>();
    byte[] body;

    View() {
        this.body = "".getBytes();
    }

    String getHeader(ResponseStatus responseStatus) {
        StringBuffer sb = new StringBuffer();
        sb.append(HTTP.VERSION.getPhrase()).append(" ").append(responseStatus.getInfo()).append("\r\n");

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

    public void addHeader(HTTP key, String value) {
        header.put(key, value);
    }
}
