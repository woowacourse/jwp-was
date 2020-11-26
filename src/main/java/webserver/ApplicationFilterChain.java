package webserver;

import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import webserver.controller.Controller;
import webserver.controller.RequestMapping;
import webserver.exception.MethodNotAllowedException;
import webserver.exception.NotFoundException;
import webserver.exception.NotImplementedException;
import webserver.filter.Filter;
import webserver.filter.FilterChain;

public class ApplicationFilterChain implements FilterChain {
    private final Iterator<Filter> filterIterator;
    private final List<Controller> controllers;

    public ApplicationFilterChain(List<Filter> filters, List<Controller> controllers) {
        this.filterIterator = filters.iterator();
        this.controllers = new ArrayList<>(controllers);
    }

    @Override
    public void doFilter(HttpRequest request, HttpResponse response) throws IOException {
        if (filterIterator.hasNext()) {
            filterIterator.next()
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
