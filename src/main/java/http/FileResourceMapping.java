package http;

import utils.FileIoUtils;

public class FileResourceMapping implements HandlerMapping {
    private HttpMethod method = HttpMethod.GET;

    @Override
    public ModelAndView handle(HttpRequest httpRequest) {
        HttpMethod.match(method, httpRequest);
        return new ModelAndView(resolve(httpRequest.getUri()));
    }

    private View resolve(HttpUri uri) {
        return new View(FileIoUtils.loadFileByUri(uri));
    }
}
