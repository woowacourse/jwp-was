package http.response.view;

import http.HTTP;
import http.response.ResponseStatus;

import java.util.Map;

public interface View {
    ResponseStatus getResponseStatus();

    Map<HTTP, String> getHeader();

    byte[] getBody();
}
