package webserver.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import webserver.request.MethodType;
import webserver.response.ModelAndView;
import webserver.request.ServletRequest;
import webserver.response.StatusCode;
import webserver.controller.annotation.Controller;
import webserver.controller.annotation.RequestMapping;

@Controller
public class StaticResourceHandlers implements Handlers {

    @RequestMapping(type = MethodType.GET, isResource = true)
    public ModelAndView resolve(final ServletRequest servletRequest) throws IOException {
        final String path = servletRequest.getPath();
        final String contentType = servletRequest.getAccept().split(",")[0];
        final Map<String, String> attributes = new LinkedHashMap<>();
        attributes.put("Content-Type", contentType);

        return ModelAndView.of(servletRequest.getProtocolVersion(), StatusCode.OK, attributes, path);
    }
}
