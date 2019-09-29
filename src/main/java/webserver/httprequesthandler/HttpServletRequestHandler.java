package webserver.httprequesthandler;

import controller.*;
import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ModelAndView;
import view.View;
import view.ViewResolver;

import java.util.HashMap;
import java.util.Map;

public class HttpServletRequestHandler extends AbstractHttpRequestHandler {
    private static final Map<String, Controller> HANDLERS = new HashMap<>();

    static {
        HANDLERS.put("/user/create", UserController.getInstance());
        HANDLERS.put("/user/form", UserFormController.getInstance());
        HANDLERS.put("/user/login", LoginController.getInstance());
        HANDLERS.put("/user/login_failed", LoginFailedController.getInstance());
        HANDLERS.put("/user/list", UserListController.getInstance());
        HANDLERS.put("/", IndexController.getInstance());
    }

    private HttpServletRequestHandler() {
    }

    public static HttpServletRequestHandler getInstance() {
        return DynamicHttpRequestHandlerLazyHolder.INSTANCE;
    }

    @Override
    public boolean canHandle(String path) {
        return HANDLERS.containsKey(path);
    }

    @Override
    public void handleInternal(HttpRequest httpRequest, HttpResponse httpResponse) {
        Controller controller = HANDLERS.get(httpRequest.getPath());
        ModelAndView modelAndView = controller.service(httpRequest, httpResponse);
        View view = ViewResolver.getInstance().resolve(modelAndView.getViewName());
        view.render(modelAndView.getModelMap(), httpResponse);
    }

    private static class DynamicHttpRequestHandlerLazyHolder {
        private static final HttpServletRequestHandler INSTANCE = new HttpServletRequestHandler();
    }
}
