package webserver.filter;

import java.util.ArrayList;
import java.util.List;

import http.request.RequestEntity;
import http.response.ResponseEntity;
import implementedfilter.AuthFilter;

public class FilterStorage {

    private static final List<Filter> inputFilters = new ArrayList<>();
    private static final List<Filter> outputFilters = new ArrayList<>();

    static {
        inputFilters.add(new AuthFilter());
        inputFilters.add(new StaticFileFilter());
        outputFilters.add(new ContentLengthFilter());
    }

    public static boolean doInputFilters(RequestEntity requestEntity, ResponseEntity responseEntity) {
        for (Filter filter : inputFilters) {
            boolean isPassed = filter.doFilter(requestEntity, responseEntity);
            if (!isPassed) {
                return false;
            }
        }
        return true;
    }

    public static boolean doOutputFilters(RequestEntity requestEntity, ResponseEntity responseEntity) {
        for (Filter filter : outputFilters) {
            boolean isPassed = filter.doFilter(requestEntity, responseEntity);
            if (!isPassed) {
                return false;
            }
        }
        return true;
    }
}
