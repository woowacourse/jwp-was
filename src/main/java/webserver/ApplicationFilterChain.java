package webserver;

import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import webserver.controller.Controller;
import webserver.controller.RequestMapping;
import webserver.exception.MethodNotAllowedException;
import webserver.exception.NotFoundException;
import webserver.exception.NotImplementedException;
import webserver.filter.Filter;
import webserver.filter.FilterChain;

public class ApplicationFilterChain implements FilterChain {
    private final List<Filter> filters;
    private final List<Controller> controllers;
    private int filterIndex;
    private final int filterLength;

    public ApplicationFilterChain(List<Filter> filters, List<Controller> controllers) {
        this.filters = new ArrayList<>(filters);
        this.controllers = new ArrayList<>(controllers);
        this.filterIndex = 0;
        this.filterLength = this.filters.size();
    }

    @Override
    public void doFilter(HttpRequest request, HttpResponse response) throws IOException {
        if (filterIndex < filterLength) {
            filters.get(filterIndex++)
                .doFilter(request, response, this);
            return;
        }

        controllers.stream()
            .filter(it -> request.path()
                .matches(it.getClass().getAnnotation(RequestMapping.class).value()))
            .findFirst()
            .orElseThrow(NotFoundException::new)
            .service(request, response);
    }

    public void run(HttpRequest request, HttpResponse response) throws IOException {
        try {
            doFilter(request, response);
        } catch (NotFoundException e) {
            response.status(HttpStatus.NOT_FOUND).end();
        } catch (MethodNotAllowedException e) {
            response.status(HttpStatus.METHOD_NOT_ALLOWED).end();
        } catch (NotImplementedException e) {
            response.status(HttpStatus.NOT_IMPLEMENTED).end();
        } catch (Exception e) {
            response.status(HttpStatus.INTERNAL_SERVER_ERROR).end();
        }
    }
}
