package http.response;

import http.request.Request;

public class FontResponse extends AbstractResponse {
    public FontResponse(Request request) {
        super(request);
        contentType = "font/opentype";
    }
}
