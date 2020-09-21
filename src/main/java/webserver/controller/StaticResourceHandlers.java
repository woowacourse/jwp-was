package webserver.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import webserver.controller.annotation.Controller;
import webserver.controller.annotation.RequestMapping;
import webserver.request.MethodType;
import webserver.request.ServletRequest;
import webserver.response.ModelAndView;
import webserver.response.StatusCode;

@Controller
public class StaticResourceHandlers implements Handlers {

    @RequestMapping(type = MethodType.GET, isResource = true)
    public ModelAndView resolve(final ServletRequest servletRequest) {
        final String path = servletRequest.getPath();
        final String contentType = servletRequest.getAccept().split(",")[0];
        final Map<String, String> headers = new LinkedHashMap<>();
        final Map<String, String> attributes = new LinkedHashMap<>();
        headers.put("Content-Type", contentType);

        return ModelAndView.of(StatusCode.OK, headers, attributes, path);
    }
}
