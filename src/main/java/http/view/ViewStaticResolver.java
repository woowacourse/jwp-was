package http.view;

import http.model.*;
import utils.FileIoUtils;

public class ViewStaticResolver implements Resolver {
    private static final String STATIC_PATH = "./static";

    @Override
    public HttpResponse resolve(ModelAndView modelAndView) {
        ContentType contentType = getContentType(modelAndView);
        StatusLine statusLine = new StatusLine(HttpProtocols.HTTP1_1, HttpStatus.OK);
        return new HttpResponse(statusLine, contentType, FileIoUtils.loadFileFromClasspath(STATIC_PATH + modelAndView.getViewLocation()));
    }
}
