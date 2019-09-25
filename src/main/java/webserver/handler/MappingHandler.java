package webserver.handler;

import exceptions.NotFoundException;
import webserver.request.HttpRequest;
import webserver.response.HttpStatus;
import webserver.servlet.FileServlet;
import webserver.servlet.HomeServlet;
import webserver.servlet.HttpServlet;
import webserver.servlet.UserCreateServlet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MappingHandler {
    private static Map<String, HttpServlet> servlets = new HashMap<>();
    private static HttpServlet fileServlet;

    static {
        servlets.put("/", new HomeServlet());
        servlets.put("/user/create", new UserCreateServlet());
        fileServlet = new FileServlet();
    }

    public static HttpServlet getServlets(HttpRequest request) {
        if (request.isFile()) {
            return fileServlet;
        }
        return Optional.ofNullable(servlets.get(request.getAbsPath()))
            .orElseThrow(() -> new NotFoundException("유효하지 않은 경로입니다.", HttpStatus.NOT_FOUND));
    }
}
