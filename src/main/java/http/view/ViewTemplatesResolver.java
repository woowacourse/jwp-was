package http.view;

import http.model.ContentType;
import http.model.HttpProtocols;
import http.model.HttpResponse;
import http.model.HttpStatus;
import utils.FileIoUtils;

public class ViewTemplatesResolver implements Resolver {
    private static final String HTML_PATH = "./templates";
    private static final String REDIRECT_PREFIX = "redirect:";

    @Override
    public HttpResponse resolve(ModelAndView modelAndView) {
        String resource = modelAndView.getViewLocation();
        if (resource.startsWith(REDIRECT_PREFIX)) {
            return new HttpResponse(HttpProtocols.HTTP1_1, HttpStatus.FOUND, ContentType.HTML);
        }
        ContentType contentType = getContentType(modelAndView);
        return new HttpResponse(HttpProtocols.HTTP1_1, HttpStatus.OK, contentType, FileIoUtils.loadFileFromClasspath(HTML_PATH + modelAndView.getViewLocation()));
    }
}
