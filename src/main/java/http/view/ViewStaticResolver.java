package http.view;

import http.model.ContentType;
import http.model.HttpProtocols;
import http.model.HttpResponse;
import http.model.HttpStatus;
import utils.FileIoUtils;

public class ViewStaticResolver implements Resolver {
    private static final String STATIC_PATH = "./static";

    @Override
    public HttpResponse resolve(ModelAndView modelAndView) {
        ContentType contentType = getContentType(modelAndView);
        return new HttpResponse(HttpProtocols.HTTP1_1, HttpStatus.OK, contentType, FileIoUtils.loadFileFromClasspath(STATIC_PATH + modelAndView.getViewLocation()));
    }
}
