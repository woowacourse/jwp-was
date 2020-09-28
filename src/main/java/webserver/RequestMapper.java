package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.controller.Controller;
import webserver.controller.PageController;
import slipp.web.UserController;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;

public class RequestMapper {
    private static final Logger logger = LoggerFactory.getLogger(RequestMapper.class);
    private static final Map<String, Controller> mapper = new HashMap<>();

    static {
        mapper.put("/user/create", new UserController());
    }

    private RequestMapper() {
    }

    public static void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            mapService(httpRequest, httpResponse);
        } catch (Exception e) {
            logger.error(e.getMessage());
            httpResponse.sendError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public static void mapService(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        Controller controller = mapper.get(httpRequest.getUrl());
        if (Objects.isNull(controller)) {
            controller = new PageController();
        }
        controller.service(httpRequest, httpResponse);
    }
}
