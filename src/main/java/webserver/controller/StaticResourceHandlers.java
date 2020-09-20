package webserver.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import utils.FileIoUtils;
import webserver.HttpStatusLine;
import webserver.MethodType;
import webserver.ModelAndView;
import webserver.ServletRequest;
import webserver.ServletResponse;
import webserver.StatusCode;
import webserver.View;
import webserver.controller.Handlers;
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
