package webserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.controller.IndexController;
import web.controller.UserController;
import webserver.exception.MethodNotAllowedException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import static webserver.controller.RequestMapping.getMapping;
import static webserver.controller.RequestMapping.postMapping;

public class RequestMapper {
    private RequestMapper() {
    }

    private static class RequestMapperHolder {
        private static final RequestMapper INSTANCE = new RequestMapper();
    }

    public static RequestMapper getInstance() {
        return RequestMapperHolder.INSTANCE;
    }

    private static final Logger logger = LoggerFactory.getLogger(RequestMapper.class);

    private static final ControllerHandler CONTROLLER_HANDLER = new ControllerHandler();

    // TODO: 2019-09-26 사용자 부분으로 추출
    static {
        get("/", IndexController.getInstance().goIndex());
        get("/user/form", UserController.getInstance().goForm());
        post("/user/create", UserController.getInstance().createUser());
    }

    private static void get(String uri, Responsive responsive) {
        CONTROLLER_HANDLER.put(getMapping(uri), responsive);
    }

    private static void post(String uri, Responsive responsive) {
        CONTROLLER_HANDLER.put(postMapping(uri), responsive);
    }

    public HttpResponse service(HttpRequest request) {
        HttpResponse response = new HttpResponse();
        RequestMapping requestMapping = request.getRequestMapping();

        Responsive responsive;
        try {
            responsive = CONTROLLER_HANDLER.get(requestMapping);
        } catch (MethodNotAllowedException e) {
            logger.error("path: {}, {}", requestMapping, e.getMessage());
            return HttpResponse.methodNotAllowed();
        } catch (Exception e) {
            logger.error("path: {}, {}", requestMapping, e.getMessage());
            return HttpResponse.internalServerError();
        }

        try {
            responsive.accept(request, response);
            return response;
        } catch (NullPointerException e) {
            logger.error("path: {}, 존재하지 않는 path 요청", request.getPath());
            return HttpResponse.notFound();
        }
    }
}
