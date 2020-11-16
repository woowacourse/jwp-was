package implementedfilter;

import http.request.RequestEntity;
import http.response.HttpStatus;
import http.response.ResponseEntity;
import http.session.HttpSessionStorage;
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
        String cookie = requestEntity.getHttpHeader().findOrEmpty("Cookie");
        if (PATH_PATTERN.contains(path) && isNotAuthorized(cookie)) {
            responseEntity.status(HttpStatus.FOUND).addHeader("Location", "/user/login.html");
            return false;
        }
        return true;
    }

    private boolean isNotAuthorized(String cookie) {
        return !HttpSessionStorage.isValidSession(cookie);
    }
}
