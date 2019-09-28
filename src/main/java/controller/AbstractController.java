package controller;

import http.common.ContentType;
import http.request.HttpRequest;
import http.request.RequestMethod;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(AbstractController.class);

    private final Map<RequestMethod, Controller> methodMap = new HashMap<>();

    {
        methodMap.put(RequestMethod.GET, this::doGet);
        methodMap.put(RequestMethod.POST, this::doPost);
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            methodMap.get(httpRequest.getMethod()).service(httpRequest, httpResponse);
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            httpResponse.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setResponseStatus(ResponseStatus.METHOD_NOT_ALLOWED);
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.setResponseStatus(ResponseStatus.METHOD_NOT_ALLOWED);
    }

    protected void setHttpResponse(ModelAndView modelAndView, HttpResponse httpResponse) throws IOException {
        String viewName = modelAndView.getViewName();
        if (viewName.startsWith("redirect: ")) {
            httpResponse.setResponseStatus(ResponseStatus.FOUND);
            httpResponse.addHeaderAttribute("Location", viewName.replace("redirect: ", ""));
            return;
        }
        byte[] body = TemplateManager.getInstance().render(modelAndView);
        httpResponse.setResponseStatus(ResponseStatus.OK);
        httpResponse.addHeaderAttribute("Content-Type", ContentType.HTML + ";charset=utf-8");
        httpResponse.addHeaderAttribute("Content-Length", String.valueOf(body.length));
        httpResponse.setBody(body);
    }
}
