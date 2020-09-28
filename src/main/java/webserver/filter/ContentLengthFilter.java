package webserver.filter;

import http.request.RequestEntity;
import http.response.ResponseEntity;

public class ContentLengthFilter implements Filter {
    private static final String CONTENT_LENGTH = "Content-Length";

    @Override
    public boolean doFilter(RequestEntity requestEntity, ResponseEntity responseEntity) {
        long length = responseEntity.getHttpBody()
            .getContent()
            .length();

        responseEntity.getHttpHeader()
            .setOrReplaceHeader(CONTENT_LENGTH, String.valueOf(length));

        return true;
    }
}
