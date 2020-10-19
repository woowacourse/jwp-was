package webserver.controller;

import webserver.controller.annotation.Controller;
import webserver.controller.annotation.RequestMapping;
import webserver.request.MethodType;
import webserver.request.ServletRequest;
import webserver.response.ModelAndView;
import webserver.response.StatusCode;

@Controller
public class StaticResourceHandlers {

    @RequestMapping(type = MethodType.GET, isResource = true)
    public ModelAndView resolve(ServletRequest servletRequest) {
        String path = servletRequest.getPath();
        String contentType = servletRequest.getAccept();
        ModelAndView mav = ModelAndView.of(StatusCode.OK, path);
        mav.addHeader("Content-Type", contentType);

        return mav;
    }
}
