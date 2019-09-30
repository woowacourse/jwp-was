package webserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.controller.IndexController;
import web.controller.UserController;
import webserver.exception.MethodNotAllowedException;
import webserver.exception.PageNotFoundException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.storage.HttpSession;

import static webserver.controller.RequestMapping.getMapping;
import static webserver.controller.RequestMapping.postMapping;
import static webserver.response.ResponseStatus.*;

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
        get("/", IndexController::goIndex);
        get("/user/form", UserController::goForm);
        get("/login", UserController::goLoginForm);
        get("/login-fail", UserController::goLoginFail);
        get("/user/list", UserController::goUserList);

        post("/user/create", UserController::createUser);
        post("/login", UserController::login);
    }

    private static void get(String uri, Responsive responsive) {
        CONTROLLER_HANDLER.put(getMapping(uri), responsive);
    }

    private static void post(String uri, Responsive responsive) {
        CONTROLLER_HANDLER.put(postMapping(uri), responsive);
    }

    public HttpResponse service(HttpRequest request) {
        HttpResponse response = new HttpResponse(request.getVersion());
        RequestMapping requestMapping = request.getRequestMapping();

        try {
            CONTROLLER_HANDLER.get(requestMapping).accept(request, response);
            registerSession(request, response);
            return response;
        } catch (MethodNotAllowedException e) {
            logger.error("METHOD_NOT_ALLOWED, path: {}, {}", requestMapping, e.getMessage());
            return HttpResponse.sendErrorResponse(METHOD_NOT_ALLOWED);
        } catch (PageNotFoundException e) {
            logger.error("NOT_FOUND, path: {}, 존재하지 않는 path 요청", request.getPath());
            return HttpResponse.sendErrorResponse(NOT_FOUND);
        } catch (Exception e) {
            logger.error("INTERNAL_SERVER_ERROR, path: {}, {}", requestMapping, e.getMessage());
            return HttpResponse.sendErrorResponse(INTERNAL_SERVER_ERROR);
        }
    }

    private void registerSession(HttpRequest request, HttpResponse response) {
        HttpSession session = request.getSession();
        if (session != null) {
            response.registerSession(session.getId());
        }
    }
}
