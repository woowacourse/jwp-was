package implementedfilter;

import http.request.RequestEntity;
import http.response.HttpStatus;
import http.response.ResponseEntity;
import webserver.filter.Filter;

import java.util.Arrays;
import java.util.List;

public class AuthFilter implements Filter {
    private static final List<String> PATH_PATTERN = Arrays.asList(
        "/user/list"
    );

    @Override
    public boolean doFilter(RequestEntity requestEntity, ResponseEntity responseEntity) {
        String path = requestEntity.getHttpUrl().getPath();
        if (PATH_PATTERN.contains(path)) {
            String cookie = requestEntity.getHttpHeader().findOrEmpty("Cookie");
            if ("logined=true".equals(cookie)) {
                return true;
            } else {
                responseEntity.status(HttpStatus.FOUND)
                    .addHeader("Location", "/user/login.html");
                return false;
            }
        }
        return true;
    }
}
