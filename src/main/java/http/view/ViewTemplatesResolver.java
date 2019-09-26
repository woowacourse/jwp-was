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
            return new HttpResponse.Builder()
                    .protocols(HttpProtocols.HTTP1_1)
                    .status(HttpStatus.FOUND)
                    .addHeader("Content-Type", ContentType.HTML.getType())
                    .build();
        }
        ContentType contentType = getContentType(modelAndView);
        return new HttpResponse.Builder()
                .protocols(HttpProtocols.HTTP1_1)
                .status(HttpStatus.OK)
                .addHeader("Content-Type", contentType.getType())
                .body(FileIoUtils.loadFileFromClasspath(HTML_PATH + modelAndView.getViewLocation()))
                .build();
    }
}
