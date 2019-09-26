package http.view;

import http.model.*;
import utils.FileIoUtils;

public class ViewTemplatesResolver implements Resolver {
    private static final String HTML_PATH = "./templates";
    private static final String REDIRECT_PREFIX = "redirect:";

    @Override
    public HttpResponse resolve(ModelAndView modelAndView) {
        String resource = modelAndView.getViewLocation();
        if (resource.startsWith(REDIRECT_PREFIX)) {
            StatusLine statusLine = new StatusLine(HttpProtocols.HTTP1_1, HttpStatus.FOUND);
            return new HttpResponse(statusLine, ContentType.HTML);
        }
        ContentType contentType = getContentType(modelAndView);
        StatusLine statusLine = new StatusLine(HttpProtocols.HTTP1_1, HttpStatus.OK);
        return new HttpResponse(statusLine, contentType, FileIoUtils.loadFileFromClasspath(HTML_PATH + modelAndView.getViewLocation()));
    }
}
