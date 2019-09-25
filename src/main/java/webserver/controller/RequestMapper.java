package webserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FilePathUtils;
import webserver.exception.MethodNotAllowedException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.MediaType;

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

    static {
        get("/", IndexController.getInstance().goIndex());
        get("/user/form", UserController.getInstance().goForm());
        post("/user/create", UserController.getInstance().createUser());
    }

    public static void get(String uri, Responsive responsive) {
        CONTROLLER_HANDLER.put(getMapping(uri), responsive);
    }

    public static void post(String uri, Responsive responsive) {
        CONTROLLER_HANDLER.put(postMapping(uri), responsive);
    }

    public HttpResponse service(HttpRequest request) {
        HttpResponse response = new HttpResponse();
        RequestMapping requestMapping = request.getRequestMapping();
        try {
            CONTROLLER_HANDLER.get(requestMapping).accept(request, response);
        } catch (MethodNotAllowedException e) {
            logger.error("path: {}, {}", requestMapping, e.getMessage());
            return HttpResponse.sendErrorResponse(METHOD_NOT_ALLOWED);
        } catch (Exception e) {
            logger.error("path: {}, {}", requestMapping, e.getMessage());
            return HttpResponse.sendErrorResponse(INTERNAL_SERVER_ERROR);
        }

        if (response.hasNotContent() ||
                FilePathUtils.isTemplateFile(FilePathUtils.getResourcePath(response.getViewPath()))) {
            return response;
        }

        String path = response.getViewPath();

        if (FilePathUtils.isStaticFile(path)) {
            return getStaticFileResponse(request, response);
        }

        logger.error("path: {}, 존재하지 않는 path 요청", path);
        return HttpResponse.sendErrorResponse(NOT_FOUND);
    }

    private HttpResponse getStaticFileResponse(HttpRequest httpRequest, HttpResponse httpResponse) {
        String path = httpRequest.getPath();
        httpResponse.forward(path);

        String extension = FilePathUtils.getExtension(path);
        httpResponse.setContentType(MediaType.of(extension));
        return httpResponse;
    }
}
