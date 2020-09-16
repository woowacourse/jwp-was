package webserver.filter;

import java.util.ArrayList;
import java.util.List;

import http.request.RequestEntity;
import http.response.ResponseEntity;

public class FilterStorage {

    private static final List<Filter> filters = new ArrayList<>();

    static {
        filters.add(new StaticFileFilter());
    }

    public static boolean doFilters(RequestEntity requestEntity, ResponseEntity responseEntity) {
        for (Filter filter : filters) {
            boolean isPassed = filter.doFilter(requestEntity, responseEntity);
            if (!isPassed) {
                return false;
            }
        }
        return true;
    }
}
